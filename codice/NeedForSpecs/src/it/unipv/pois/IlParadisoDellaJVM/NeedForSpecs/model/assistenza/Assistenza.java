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
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IMessaggioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;


/*
 * @author: Persy
 * Note: 
 * 1)Implementare controllo errori
 * 2)Implementare inizializzazion dell'assistenza tramite il db 
 * 3)Implementare un enum per scegliere il livello di persistenza desiderato che inizializzerà tutti i dao nella variante scelta es (dbDao) nel main.
 * 4)Sinconizzazine della chat tra staff e utente (data dell'utlimo messaggio inserito)
 * 5)assegnazione casuale dello staff a un ticket
 * 6)controllo conversazione vuota nel metodo di aggiornamento;
 */
public class Assistenza {
	
	private Map<String,Ticket> richieste_assistenza;
	private IMessaggioDAO msg_dao;
	private ITicketDAO  ticket_dao; 
	
	

	public Assistenza() {
		// TODO Auto-generated constructor stub
		richieste_assistenza = new HashMap<String, Ticket>();
		
	}
	
	
	
	private boolean esisteTicket(String id_ticket) {
		return richieste_assistenza.containsKey(id_ticket);
	}
	
	
	public Ticket visualizzaTicketDaId(String id_ticket) {
		
		if(esisteTicket(id_ticket)) {
			Ticket t =richieste_assistenza.get(id_ticket);
			System.out.println("Ticket trovato correttamente, ticket: "+t);
			return t;
			
		}else {
			throw new TicketNotFoundException(id_ticket);
		}
		
		
	}
	
	public void cambioStatoTicket(String id_ticket,Stato nuovo_stato) {
		
		Ticket t = visualizzaTicketDaId(id_ticket);
		t.setStato_ticket(nuovo_stato);
		boolean status = ticket_dao.aggiornaStatoTicket(nuovo_stato, t);
		if(status) {
			System.out.println("stato cambiato correttamente");
			
		}else {
			System.err.println("Non è stato possibile cambiare lo stato del ticket a causa di un malfunzionamento");
			throw new TicketStatusUnchangedException(id_ticket,nuovo_stato,t.getStato_ticket());
			
		}
		
	
		 
		
	}
	
	/*
	 * Gestore a NULL inizialmente che verrà modificato tramite l'assegnazione automatica dei ticket senza gestore
	 */
	public boolean apriTicket(UtenteGenerico richiedente) {
		
		Ticket t = new Ticket(richiedente, null, Stato.IN_ASSEGNAZIONE, null);
		richieste_assistenza.put(t.getId_ticket(), t);
		ticket_dao.inserisciTicket(t);
		System.out.println("Ticket Creato con Successo!, ID: "+t.getId_ticket());
		return true ;
		
	}
	
	
	
	public boolean chiudiTicket(String id_ticket) {
		
		if(esisteTicket(id_ticket)) {
			richieste_assistenza.remove(id_ticket);
			System.out.println("Ticket rimosso correttamente");
			return true;
			
			
		}else {
			return false;
		}
		
		
		
	}
	
	public boolean assegnaTicketToGestore(UtenteStaff staff) {
		
		ArrayList<Ticket> ticket_da_assegnare = ticket_dao.getTicketSenzaGestore();
		int ticket_assegnati = 0;
		
		boolean status = false;
		if(ticket_da_assegnare != null) {
			for(Ticket t : ticket_da_assegnare) {
				t.setGestore(staff);
				
				ticket_assegnati++;
			}
			
			System.out.println("Assegnazione COMPLETATA, sono stati assegnati"+ticket_assegnati+ "allo staff"+staff.getNome_utente());	
			
			status = true;
		}else {
			System.err.println("L'assegnazione ha riscontrato dei problemi controllare");
		}
		
		return status;
	}
	
	public boolean creaMessaggio(Utente autore,String testo,String id_ticket) {
		
		if(esisteTicket(id_ticket)) {
			Ticket t = richieste_assistenza.get(id_ticket);
			Messaggio msg = t.creaMessaggio(autore, testo);
			msg_dao.inserisciMessaggioInTicketRiferimento(msg);
			
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
	public void aggiornaConversazioneDatoTicket(String id_ticket) {
		
		Ticket t = visualizzaTicketDaId(id_ticket);
		Messaggio m = t.getUltimoMessaggio();
		ArrayList<Messaggio> messaggi = msg_dao.getMessaggiNuovi(t, m.getData_pubblicazione());
		t.agguiungiMessessaggiAllaConversazione(messaggi);
		
		
		
		
		
	}

}
