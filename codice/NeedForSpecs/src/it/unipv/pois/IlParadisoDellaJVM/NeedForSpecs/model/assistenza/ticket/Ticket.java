package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy.IRicercaMessaggiStrategy;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy.Ricerca;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy.RicercaStrategyFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.StringChecker;

/**
 * Rappresenta un'Entità Ticket all'interno del sistema di assistenza.
 * Mantiene le informazioni sul richiedente, il gestore (staff), lo stato attuale 
 * della richiesta e l'intera cronologia della conversazione (messaggi).
 * * @author Persy
 */
public class Ticket {

	private String id_ticket;
	private UtenteGenerico richiedente_assistenza;
	private UtenteStaff gestore;
	private Stato stato_ticket;
	private ArrayList<Messaggio> conversazione;
	private IRicercaMessaggiStrategy strategy;
	private RicercaStrategyFactory factory;
	
	/**
	 * Costruttore completo per inizializzare un ticket recuperato dal database.
	 * * @param id_ticket L'identificativo univoco del ticket.
	 * @param richiedente_assistenza L'utente generico che ha aperto il ticket.
	 * @param gestore Il membro dello staff assegnato al ticket (può essere null se non assegnato).
	 * @param stato_ticket Lo stato attuale della richiesta (es. APERTO, CHIUSO).
	 * @param conversazione La lista dei messaggi scambiati finora.
	 */
	public Ticket(String id_ticket, UtenteGenerico richiedente_assistenza, UtenteStaff gestore, Stato stato_ticket,
			ArrayList<Messaggio> conversazione) {
		super();
		this.id_ticket = id_ticket;
		this.richiedente_assistenza = richiedente_assistenza;
		this.gestore = gestore;
		this.stato_ticket = stato_ticket;
		this.conversazione = conversazione;
		this.factory = RicercaStrategyFactory.getInstance();
	}

	/**
	 * Costruttore per creare un nuovo ticket da zero.
	 * L'ID del ticket viene generato automaticamente dal sistema.
	 * * @param richiedente_assistenza L'utente generico che sta aprendo il ticket.
	 * @param gestore Il membro dello staff assegnato (solitamente null alla creazione).
	 * @param stato_ticket Lo stato iniziale del ticket.
	 * @param conversazione La lista iniziale dei messaggi.
	 */
	public Ticket(UtenteGenerico richiedente_assistenza, UtenteStaff gestore, Stato stato_ticket,
			ArrayList<Messaggio> conversazione) {
		super();
		this.id_ticket = generaIdTicket();
		this.richiedente_assistenza = richiedente_assistenza;
		this.gestore = gestore;
		this.stato_ticket = stato_ticket;
		this.conversazione = conversazione;
		this.factory = RicercaStrategyFactory.getInstance();
	}

	/**
	 * Costruttore di default.
	 * Inizializza un ticket vuoto con una conversazione priva di messaggi.
	 */
	public Ticket() {
		super();
		conversazione = new ArrayList<>();
	}

	/**
	 * Aggiunge un singolo messaggio alla conversazione del ticket, 
	 * verificando prima che non sia già presente per evitare duplicati.
	 * * @param msg L'oggetto Messaggio da aggiungere.
	 */
	public void aggiungiMessaggioAllaConversazione(Messaggio msg) {
		for (Messaggio m : this.conversazione) {
			if (m.getId_contenuto_utente().equals(msg.getId_contenuto_utente())) {
				return; 
			}
		}
		conversazione.add(msg);
		System.out.println("Messaggio aggiunto alla conversazione");
	}
	
	/**
	 * Aggiunge una lista di messaggi alla conversazione del ticket.
	 * * @param messaggi L'ArrayList di oggetti Messaggio da aggiungere.
	 */
	public void aggiungiMessaggiAllaConversazione(ArrayList<Messaggio> messaggi) {
		for(Messaggio m : messaggi) {
			aggiungiMessaggioAllaConversazione(m);
		}
	}
		
	/**
	 * Istanzia un nuovo messaggio, lo collega a questo ticket e lo aggiunge alla conversazione.
	 * * @param autore L'utente (Generico o Staff) che ha scritto il messaggio.
	 * @param testo Il corpo del testo del messaggio.
	 * @return L'oggetto Messaggio appena creato.
	 */
	public Messaggio creaMessaggio(Utente autore, String testo) {
		Messaggio msg = new Messaggio(autore, testo, LocalDateTime.now(), this);
		System.out.println("Messaggio creato con successo..");
		aggiungiMessaggioAllaConversazione(msg);
		return msg;
	}
	
	/**
	 * Restituisce l'ultimo messaggio inserito nella conversazione.
	 * * @return Il messaggio più recente.
	 */
	public Messaggio getUltimoMessaggio() {
		return conversazione.getLast();
	}
	
	/**
	 * Ordina la lista dei messaggi in ordine cronologico.
	 * Esegue l'ordinamento solo se la conversazione non è nulla o vuota.
	 */
	private void ordinaConversazionePerData() {
	    if (this.conversazione != null && !this.conversazione.isEmpty()) {
	        Collections.sort(conversazione); 
	    }
	}

	/**
	 * Genera una stringa contenente l'intera cronologia della conversazione
	 * formattata per essere mostrata nell'interfaccia grafica.
	 * * @return La cronologia formattata o un messaggio di avviso se vuota.
	 */
	public String getCronologiaMessaggiFormattata() {
	    if (this.conversazione == null || this.conversazione.isEmpty()) {
	        return "Nessun messaggio presente.\n";
	    }

	    ordinaConversazionePerData();
	    return StringChecker.formattaListaMessaggi(this.conversazione);
	}
	
	/**
	 * Filtra i messaggi della conversazione applicando una specifica strategia di ricerca.
	 * Sfrutta il pattern Strategy per decidere dinamicamente come effettuare la ricerca.
	 * * @param elemento_ricerca La stringa o la data da cercare.
	 * @param scelta L'enumerativo che definisce il tipo di ricerca (es. PER_TESTO, PER_DATA).
	 * @return Un ArrayList contenente i messaggi che corrispondono ai criteri di ricerca.
	 */
	public ArrayList<Messaggio> filtraConversazione(String elemento_ricerca, Ricerca scelta) {
		strategy = factory.getStrategy(scelta);
		if (strategy != null) {
			return (ArrayList<Messaggio>) strategy.cerca(conversazione, elemento_ricerca);
		}
		System.err.println("Errore: Strategia non configurata!");
		return new ArrayList<>();
	}
	
	/**
	 * Cerca dei messaggi specifici e restituisce il risultato formattato per la View.
	 * * @param parametro Il termine da cercare.
	 * @param scelta La strategia di ricerca da applicare.
	 * @return I messaggi trovati e formattati, oppure un avviso se non ci sono risultati.
	 */
	public String ricercaEFormattaMessaggi(String parametro, Ricerca scelta) {
	    ArrayList<Messaggio> trovati = filtraConversazione(parametro, scelta);
	    
	    if (trovati == null || trovati.isEmpty()) {
	        return "Nessun messaggio trovato per questa ricerca.\n";
	    }
	    return StringChecker.formattaListaMessaggi(trovati);
	}

	/**
	 * Genera un identificativo casuale per un nuovo ticket.
	 * * @return L'ID generato sotto forma di stringa (es. "Ticket1234").
	 */
	private String generaIdTicket() {
		return "Ticket" + (int)(Math.random() * 10000);
	}
	
	public String getId_ticket() { return id_ticket; }
	public void setId_ticket(String id_ticket) { this.id_ticket = id_ticket; }

	public UtenteGenerico getRichiedente_assistenza() { return richiedente_assistenza; }
	public void setRichiedente_assistenza(UtenteGenerico richiedente_assistenza) { this.richiedente_assistenza = richiedente_assistenza; }

	public UtenteStaff getGestore() { return gestore; }
	public void setGestore(UtenteStaff gestore) { this.gestore = gestore; }

	public Stato getStato_ticket() { return stato_ticket; }
	public void setStato_ticket(Stato stato_ticket) { this.stato_ticket = stato_ticket; }
	
	public ArrayList<Messaggio> getConversazione() { return conversazione; }
	public void setConversazione(ArrayList<Messaggio> conversazione) { this.conversazione = conversazione; }
	
	/**
	 * Restituisce una rappresentazione in stringa del ticket, utile per la JComboBox 
	 * o per stampe di debug. Gestisce automaticamente i ticket senza gestore assegnato.
	 * * @return La stringa con ID, Stato e Gestore del ticket.
	 */
	@Override
	public String toString() {
	    String nome_gestore = (this.gestore != null) ? this.gestore.getUser_name() : "In attesa di assegnazione";
	    return "Ticket ID: " + this.id_ticket + " | Stato: " + this.stato_ticket + " | Gestore: " + nome_gestore; 
	}
	
}