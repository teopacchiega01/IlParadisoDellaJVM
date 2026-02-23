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
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utili.StringChecker;

/*
 * @author: Persy
 */
public class Ticket {

	private String id_ticket;
	private UtenteGenerico richiedente_assistenza;
	private UtenteStaff gestore;
	private Stato stato_ticket;
	private ArrayList<Messaggio> conversazione;
	private IRicercaMessaggiStrategy strategy;
	private RicercaStrategyFactory factory;
	
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

	public Ticket() {
		super();
		conversazione = new ArrayList<>();
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
	
	public void aggiungiMessaggioAllaConversazione(Messaggio msg) {
		for (Messaggio m : this.conversazione) {
			if (m.getId_contenuto_utente().equals(msg.getId_contenuto_utente())) {
				return; 
			}
		}
		
		conversazione.add(msg);
		System.out.println("Messaggio aggiunto alla conversazione");
	}
	
	public void agguiungiMessessaggiAllaConversazione(ArrayList<Messaggio> messaggi) {
		for(Messaggio m : messaggi) {
			aggiungiMessaggioAllaConversazione(m);
		}
	}
		
	public Messaggio creaMessaggio(Utente autore, String testo) {
		Messaggio msg = new Messaggio(autore, testo, LocalDateTime.now(), this);
		System.out.println("Messaggio creato con successo..");
		aggiungiMessaggioAllaConversazione(msg);
		return msg;
	}
	
	public Messaggio getUltimoMessaggio() {
		return conversazione.getLast();
	}
	
	private void ordinaConversazionePerData() {
	    Collections.sort(conversazione); 
	}
	
	public String getCronologiaMessaggiFormattata() {
		ordinaConversazionePerData();
		
		if (this.conversazione == null || this.conversazione.isEmpty()) {
			return "Nessun messaggio presente.\n";
		}
		
		StringBuilder sb = new StringBuilder();
		for (Messaggio m : this.conversazione) {
			sb.append(StringChecker.formattaSingoloMessaggio(
				m.getAutore().getUser_name(), 
				m.getData_pubblicazione(), 
				m.getTesto()
			));
		}
		
		return sb.toString();
	}
	
	public ArrayList<Messaggio> filtraConversazione(String elemento_ricerca, Ricerca scelta) {
		strategy = factory.getStrategy(scelta);
		if (strategy != null) {
			return (ArrayList<Messaggio>) strategy.cerca(conversazione, elemento_ricerca);
		}
		System.err.println("Errore: Strategia non configurata!");
		return new ArrayList<>();
	}
	
	public String ricercaEFormattaMessaggi(String parametro, Ricerca scelta) {
		ArrayList<Messaggio> trovati = filtraConversazione(parametro, scelta);
		
		if (trovati == null || trovati.isEmpty()) {
			return "Nessun messaggio trovato per questa ricerca.\n";
		}

		StringBuilder sb = new StringBuilder();
		for (Messaggio m : trovati) {
			sb.append(StringChecker.formattaSingoloMessaggio(
				m.getAutore().getUser_name(), 
				m.getData_pubblicazione(), 
				m.getTesto()
			));
		}
		
		return sb.toString();
	}

	private String generaIdTicket() {
		return "Ticket" + (int)(Math.random() * 10000);
	}
	
	@Override
	public String toString() {
		return this.id_ticket + " - Stato: " + this.stato_ticket + " - Responsabile: " + this.gestore.getUser_name() + " - Utente Richiedente " + this.richiedente_assistenza.getUser_name();	
	}
	
}