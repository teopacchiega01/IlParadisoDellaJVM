package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza;

import java.util.ArrayList;
import java.util.Map;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IMessaggioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;


/*
 * @author: Persy
 * Note: 
 * 1)Implementare controllo errori
 * 2)Implementare inizializzazion dell'assistenza tramite il db 
 * 
 * 
 */
public class Assistenza {
	
	private Map<String,Ticket> richieste_assistenza;
	private IMessaggioDAO msg_dao;
	private ITicketDAO  ticket_dao; 
	
	

	public Assistenza() {
		// TODO Auto-generated constructor stub
	
	}
	
	
	
	private boolean trovaTicketDaId(String id_ticket) {
		if(richieste_assistenza.containsKey(id_ticket)) {
			System.out.println("Ticket trovato correttamente");
			return true;
		}else {
			System.out.println("Ticket non trovato, controllare l'id fornito");
			return false;
		}
			
		
		
	}
	
	
	public Ticket visualizzaTicketDaId(String id_ticket) {
		
		if(trovaTicketDaId(id_ticket)) {
			Ticket t =richieste_assistenza.get(id_ticket);
			System.out.println("Ticket trovato correttamente, ticket: "+t);
			return t;
			
		}else {
			System.out.println("Ticket non trovato");
			return null;
		}
		
		
	}
	
	public boolean cambioStatoTicket(String id_ticket,Stato nuovo_stato) {
		
		if(trovaTicketDaId(id_ticket)) {
			Ticket t = richieste_assistenza.get(id_ticket);
			t.setStato_ticket(nuovo_stato);
			System.out.println("Stato del ticket cambiato con successo");
			return true;	
			
		}else {
			System.out.println("Ticket non trovato");
			return false;
		}
		
		
		
	}
	
	public boolean apriTicket(UtenteGenerico richiedente,UtenteStaff gestore, Stato stato_ticket,ArrayList<Messaggio> conversazione) {
		
		Ticket t = new Ticket(richiedente, gestore, stato_ticket, conversazione);
		richieste_assistenza.put(t.getId_ticket(), t);
		System.out.println("Ticket Creato con Successo!, ID: "+t.getId_ticket());
		return true ;
		
	}
	
	
	
	public boolean chiudiTicket(String id_ticket) {
		
		if(trovaTicketDaId(id_ticket)) {
			richieste_assistenza.remove(id_ticket);
			System.out.println("Ticket rimosso correttamente");
			return true;
			
			
		}else {
			return false;
		}
		
		
		
	}
	
	public boolean creaMessaggio(Utente autore,String testo,String id_ticket) {
		
		if(trovaTicketDaId(id_ticket)) {
			Ticket t = richieste_assistenza.get(id_ticket);
			t.creaMessaggio(autore, testo);
			return true;
			
			
		}else {
			System.out.println("Ticket non trovato");
			return false;
		}
		
		
	}
	
	
	public void inizializzaTicketAssistenza(ArrayList<Ticket> tickets) {
		for(Ticket t : tickets) {
			richieste_assistenza.put(t.getId_ticket(), t);
		}
	
		
	}
	public void inizializzaMessaggiDatiTickets(ArrayList<Ticket> tickets) {
		for (Ticket t : tickets) {
			ArrayList<Messaggio> msgs = msg_dao.getMessaggiDaTicket(t);
			t.setConversazione(msgs);
			System.out.println("Ticket " + t + " inizializzato con " + msgs.size() + " messaggi.");
		}
		inizializzaTicketAssistenza(tickets);
	}
	
	public void inizializzaTicketDaUtenteGenerico(UtenteGenerico u) {
		ArrayList<Ticket> ticket_assistito = ticket_dao.getTicketDaRichiedente(u);
		inizializzaMessaggiDatiTickets(ticket_assistito);
	}
	
	public void inizializzaTicketDaUtenteStaff(UtenteStaff staff) {
		ArrayList<Ticket> ticket_assistito = ticket_dao.getTicketDaStaff(staff);
		inizializzaMessaggiDatiTickets(ticket_assistito);
			
	}

}
