package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.ITicketDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Stato;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.eccezioni.TicketNotFoundException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.eccezioni.TicketStatusUnchangedException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy.Ricerca;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IMessaggioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;

/**
 * Classe singleton che funge da Manager/Facade per l'intero sottosistema di Assistenza.
 * Gestisce la logica di business relativa ai ticket, l'interazione con i DAO 
 * e mantiene in memoria i ticket della sessione corrente.
 * * @author persy
 */
public class Assistenza {
	
	private static Assistenza instance;
	
	private Map<String,Ticket> richieste_assistenza;
	private IMessaggioDAO msg_dao;
	private ITicketDAO ticket_dao; 
	private Utente utente_loggato;

	/**
	 * Costruttore privato (Pattern Singleton). 
	 * Inizializza le strutture dati e ottiene le istanze dei DAO tramite la DAOFactory.
	 */
	private Assistenza() {
        DAOFactory factory = DAOFactory.getInstance();
        this.msg_dao = factory.getMessaggioDAO();
        this.ticket_dao = factory.getTicketDAO();
        richieste_assistenza = new HashMap<>();
    }

	/**
	 * Restituisce l'unica istanza della classe Assistenza (Singleton).
	 * Se non esiste, la crea.
	 * * @return L'istanza singleton di Assistenza.
	 */
    public static Assistenza getInstance() {
        if (instance == null) {
            instance = new Assistenza();
        }
        return instance;
    }
	
    /**
     * Verifica la presenza di un ticket specifico all'interno della memoria di sessione.
     * * @param id_ticket L'identificativo univoco del ticket da cercare.
     * @return true se il ticket è presente in memoria, false altrimenti.
     */
	private boolean esisteTicket(String id_ticket) {
		return richieste_assistenza.containsKey(id_ticket);
	}
	
	/**
	 * Recupera e restituisce un ticket a partire dal suo ID.
	 * * @param id_ticket L'identificativo univoco del ticket richiesto.
	 * @return L'oggetto Ticket corrispondente all'ID.
	 * @throws TicketNotFoundException Se il ticket non è presente in memoria.
	 */
	public Ticket visualizzaTicketDaId(String id_ticket) {
		if(esisteTicket(id_ticket)) {
			Ticket t = richieste_assistenza.get(id_ticket);
			System.out.println("Ticket trovato correttamente, ticket: " + t);
			return t;
		} else {
			throw new TicketNotFoundException(id_ticket);
		}
	}
	
	/**
	 * Cambia lo stato di un ticket esistente, sia in memoria che nel database.
	 * In caso di errore sul database, ripristina lo stato originale in memoria.
	 * * @param id_ticket L'identificativo del ticket da aggiornare.
	 * @param nuovo_stato Il nuovo stato da assegnare al ticket.
	 * @throws TicketStatusUnchangedException Se l'aggiornamento nel database fallisce.
	 */
	public void cambioStatoTicket(String id_ticket, Stato nuovo_stato) {
		Ticket t = visualizzaTicketDaId(id_ticket);
		Stato statoVecchio = t.getStato_ticket();
		t.setStato_ticket(nuovo_stato);
		
		boolean status = ticket_dao.aggiornaStatoTicket(nuovo_stato, t);
		if(status) {
			System.out.println("Stato cambiato correttamente");
		} else {
			t.setStato_ticket(statoVecchio);
			System.err.println("Non è stato possibile cambiare lo stato nel DB. Ripristino lo stato precedente.");
			throw new TicketStatusUnchangedException(id_ticket, nuovo_stato, t.getStato_ticket());
		}
	}
	
	/**
	 * Crea un nuovo ticket per l'utente loggato (che deve essere un UtenteGenerico),
	 * lo salva nel database e lo aggiunge alla sessione corrente.
	 * * @return true se il ticket è stato creato e salvato con successo, false altrimenti.
	 */
	public boolean apriTicket() {
		if (this.utente_loggato == null || this.utente_loggato.isStaff()) {
			System.err.println("Errore: Solo un utente generico loggato può aprire un ticket.");
			return false;
		}
		
		Ticket t = new Ticket((UtenteGenerico) this.utente_loggato, null, Stato.IN_ASSEGNAZIONE, null);
		boolean inserito_nel_db = ticket_dao.inserisciTicket(t);
		if (inserito_nel_db) {
			richieste_assistenza.put(t.getId_ticket(), t);
			System.out.println("Ticket Creato e salvato con Successo! ID: " + t.getId_ticket());
			return true;
		} else {
			System.err.println("Errore: impossibile salvare il ticket nel database.");
			return false;
		}
	}
	
	/**
	 * Chiude un ticket aggiornandone lo stato sul database e rimuovendolo dalla memoria.
	 * * @param id_ticket L'identificativo del ticket da chiudere.
	 * @return true se la chiusura è avvenuta con successo, false altrimenti.
	 */
	public boolean chiudiTicket(String id_ticket) {
		if(esisteTicket(id_ticket)) {
			try {
				cambioStatoTicket(id_ticket, Stato.CHIUSO);
				richieste_assistenza.remove(id_ticket);
				System.out.println("Ticket chiuso nel DB e rimosso dalla sessione corrente.");
				return true;
			} catch (TicketStatusUnchangedException e) {
				System.err.println("Impossibile chiudere il ticket a causa di un errore DB.");
				return false;
			}
		} else {
			System.out.println("Ticket non trovato in sessione.");
			return false;
		}
	}
	
	/**
	 * Recupera dal database i ticket in attesa di assegnazione e li assegna 
	 * allo staff attualmente loggato.
	 * * @param staff L'oggetto UtenteStaff a cui assegnare i ticket in attesa.
	 * @return true se tutti i ticket previsti sono stati assegnati con successo, false in caso di errori parziali/totali.
	 */
	public boolean assegnaTicketToGestore(UtenteStaff staff) {
		ArrayList<Ticket> ticket_da_assegnare = ticket_dao.getTicketSenzaGestore();
		int ticket_assegnati = 0;
		
		if(ticket_da_assegnare != null) {
			for(Ticket t : ticket_da_assegnare) {
				boolean salvato = ticket_dao.aggiornaGestoreTicket(staff, t);
				if(salvato) {
					t.setGestore(staff);
					richieste_assistenza.put(t.getId_ticket(), t);
					ticket_assegnati++;
				} else {
					t.setGestore(null);
					System.err.println("Errore DB: Impossibile assegnare il ticket " + t.getId_ticket());
					break;
				}
			}
			System.out.println("Assegnazione COMPLETATA, sono stati assegnati " + ticket_assegnati + " allo staff " + staff.getUser_name());	
			return ticket_assegnati == ticket_da_assegnare.size();
		} else {
			System.err.println("L'assegnazione ha riscontrato dei problemi, controllare.");
			return false;
		}
	}
	
	/**
	 * Crea un nuovo messaggio all'interno di un ticket esistente e lo salva sul database.
	 * * @param testo Il contenuto del messaggio da inviare.
	 * @param id_ticket L'identificativo del ticket a cui aggiungere il messaggio.
	 * @return true se il messaggio è stato salvato con successo, false in caso di errore.
	 */
	public boolean creaMessaggio(String testo, String id_ticket) {
		if (!esisteTicket(id_ticket)) {
			System.err.println("Ticket non trovato");
			return false; 
		}
		
		Ticket t = richieste_assistenza.get(id_ticket);
		Messaggio msg = t.creaMessaggio(this.utente_loggato, testo);
		boolean salvatoNelDb = msg_dao.inserisciMessaggioInTicketRiferimento(msg);
		
		if (salvatoNelDb) {
			System.out.println("Messaggio inviato e salvato correttamente.");
			return true;
		}
		
		t.getConversazione().remove(msg);
		System.err.println("Errore di connessione: impossibile inviare il messaggio.");
		return false;
	}
	
	/**
	 * Carica dal database tutti i ticket pertinenti all'utente attualmente loggato.
	 * Se l'utente è uno Staff, tenta anche di assegnargli i ticket in sospeso.
	 * Se l'utente è Generico, filtra ed estrae solo i ticket non ancora chiusi.
	 */
	public void caricaTicketUtenteLoggato() {
		if (this.utente_loggato == null) {
			System.err.println("Nessun utente loggato presente nel Model!");
			return;
		}
		
		if (this.utente_loggato.isStaff()) {
			UtenteStaff staff = (UtenteStaff) this.utente_loggato;
			assegnaTicketToGestore(staff);
			ArrayList<Ticket> tickets = ticket_dao.getTicketDaStaff(staff);
			inizializzaMessaggiDatiTickets(tickets); 
			
		} else {
			UtenteGenerico generico = (UtenteGenerico) this.utente_loggato;
			ArrayList<Ticket> ticketsGrezzzi = ticket_dao.getTicketDaRichiedente(generico);
			ArrayList<Ticket> ticketAttivi = estraiTicketAttivi(ticketsGrezzzi);
			inizializzaMessaggiDatiTickets(ticketAttivi);
		}
	}
	
	/**
	 * Restituisce la lista di tutti i ticket attualmente gestiti in memoria dalla sessione.
	 * * @return Un ArrayList contenente gli oggetti Ticket caricati.
	 */
	public ArrayList<Ticket> getTuttiITicketCaricati() {
		return new ArrayList<>(richieste_assistenza.values());
	}

	/**
	 * Popola la mappa interna (richieste_assistenza) con i ticket forniti.
	 * * @param tickets La lista di ticket da memorizzare.
	 */
	private void inizializzaTicketAssistenza(ArrayList<Ticket> tickets) {
		for(Ticket t : tickets) {
			richieste_assistenza.put(t.getId_ticket(), t);
		}
	}

	/**
	 * Interroga il database per scaricare i messaggi relativi ai ticket forniti 
	 * e popola la lista "conversazione" di ciascun ticket.
	 * * @param tickets La lista di ticket da inizializzare con i rispettivi messaggi.
	 */
	private void inizializzaMessaggiDatiTickets(ArrayList<Ticket> tickets) {
		for (Ticket t : tickets) {
			ArrayList<Messaggio> msgs = msg_dao.getMessaggiDaTicket(t);
			t.setConversazione(msgs);
			System.out.println("Ticket " + t + " inizializzato con " + msgs.size() + " messaggi.");
		}
		inizializzaTicketAssistenza(tickets);
	}
	
	/**
	 * Ricarica dal database l'intera conversazione di un determinato ticket 
	 * per aggiornarne il contenuto in memoria.
	 * * @param id_ticket L'identificativo del ticket di cui aggiornare i messaggi.
	 */
	public void aggiornaConversazioneDatoTicket(String id_ticket) {
		Ticket t = visualizzaTicketDaId(id_ticket);
		
		ArrayList<Messaggio> tuttiIMessaggiDb = msg_dao.getMessaggiDaTicket(t);
		
		if (tuttiIMessaggiDb != null && !tuttiIMessaggiDb.isEmpty()) {
			t.setConversazione(tuttiIMessaggiDb);
		}
	}

	/**
	 * Esegue una ricerca all'interno dei messaggi di un ticket sfruttando 
	 * lo Strategy Pattern e formatta il risultato in una stringa leggibile.
	 * * @param id_ticket L'identificativo del ticket su cui cercare.
	 * @param tipoRicerca L'enumerativo che indica la strategia di ricerca da applicare.
	 * @param parametro La stringa di ricerca inserita dall'utente.
	 * @return La cronologia dei messaggi trovati formattata, oppure un messaggio vuoto/errore.
	 */
	public String eseguiRicercaSuTicket(String id_ticket, Ricerca tipoRicerca, String parametro) {
		Ticket t = visualizzaTicketDaId(id_ticket);
		return t.ricercaEFormattaMessaggi(parametro, tipoRicerca);
	}

	/**
	 * Filtra una lista di ticket rimuovendo quelli con stato CHIUSO.
	 * * @param tickets La lista originale contenente tutti i ticket.
	 * @return Una nuova lista contenente solo i ticket attualmente attivi (non chiusi).
	 */
	private ArrayList<Ticket> estraiTicketAttivi(ArrayList<Ticket> tickets) {
		ArrayList<Ticket> ticketAttivi = new ArrayList<>();
		
		if (tickets != null) {
			for (Ticket t : tickets) {
				if (t.getStato_ticket() != Stato.CHIUSO) {
					ticketAttivi.add(t);
				}
			}
		}
		
		return ticketAttivi;
	}
	
	/**
	 * Restituisce l'utente attualmente collegato e associato alla sessione di Assistenza.
	 * * @return L'oggetto Utente loggato.
	 */
	public Utente getUtente_loggato() {
		return utente_loggato;
	}

	/**
	 * Imposta l'utente che sta utilizzando il modulo di Assistenza in questa sessione.
	 * * @param utente_loggato L'oggetto Utente da settare come loggato.
	 */
	public void setUtente_loggato(Utente utente_loggato) {
		this.utente_loggato = utente_loggato;
	}
}