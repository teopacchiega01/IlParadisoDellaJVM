package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza;

/*
 * @author: Persy
 * Note: 
 * 	1)Implementare controllo errori
 * 	2)Implementare inizializzazion dei ticket tramite il db, e salvataggio dei messaggi tramite il db 
 */
import java.time.LocalDate;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;

public class Ticket {

	private String id_ticket;
	private UtenteGenerico richiedente_assistenza;
	private UtenteStaff gestore;
	private Stato stato_ticket;
	private ArrayList<Messaggio> conversazione;
	
	
	
	

	
	public Ticket(String id_ticket, UtenteGenerico richiedente_assistenza, UtenteStaff gestore, Stato stato_ticket,
			ArrayList<Messaggio> conversazione) {
		super();
		this.id_ticket = id_ticket;
		this.richiedente_assistenza = richiedente_assistenza;
		this.gestore = gestore;
		this.stato_ticket = stato_ticket;
		this.conversazione = conversazione;
	}






	public Ticket(UtenteGenerico richiedente_assistenza, UtenteStaff gestore, Stato stato_ticket,
			ArrayList<Messaggio> conversazione) {
		super();
		this.id_ticket = generaIdTicket();
		this.richiedente_assistenza = richiedente_assistenza;
		this.gestore = gestore;
		this.stato_ticket = stato_ticket;
		this.conversazione = conversazione;
	}






	public String getId_ticket() {
		return id_ticket;
	}






	public void setId_ticket(String id_ticket) {
		this.id_ticket = id_ticket;
	}






	public UtenteGenerico getRichiedente_assistenza() {
		return richiedente_assistenza;
	}






	public void setRichiedente_assistenza(UtenteGenerico richiedente_assistenza) {
		this.richiedente_assistenza = richiedente_assistenza;
	}






	public UtenteStaff getGestore() {
		return gestore;
	}






	public void setGestore(UtenteStaff gestore) {
		this.gestore = gestore;
	}






	public Stato getStato_ticket() {
		return stato_ticket;
	}






	public void setStato_ticket(Stato stato_ticket) {
		this.stato_ticket = stato_ticket;
	}






	public ArrayList<Messaggio> getConversazione() {
		return conversazione;
	}






	public void setConversazione(ArrayList<Messaggio> conversazione) {
		this.conversazione = conversazione;
	}
	
	public void creaMessaggio(Utente autore,String testo) {
		Messaggio msg = new Messaggio(autore, testo, LocalDate.now(), this);
		System.out.println("messaggio creato con successo..");
		aggiungiMessaggioAllaConversazione(msg);
		
		
	}
	
	
	public void aggiungiMessaggioAllaConversazione(Messaggio msg) {
		
		conversazione.add(msg);
		System.out.println("Messaggio aggiunto alla conversazione");
		
	}


	/*
	 * Generazione ID ticket tramite la stringa Ticket e un numero casuale a 4 cifre. La scelta di avere 10 caratteri massimi è un vincolo del camppo id nel db 
	 */
	private String generaIdTicket() {
		
		String ticket_id = "Ticket "+(Math.random()*10000);
		return ticket_id;
	}
	
	
}
