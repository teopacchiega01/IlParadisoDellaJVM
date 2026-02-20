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
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.Persistenza;


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
	
	

	public Assistenza(DAOFactory factory) {
		// TODO Auto-generated constructor stub
		richieste_assistenza = new HashMap<String, Ticket>();
		this.msg_dao = factory.getMessaggioDAO();
		this.ticket_dao = factory.getTicketDAO();
		
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
		Stato statoVecchio = t.getStato_ticket();
		t.setStato_ticket(nuovo_stato);
		boolean status = ticket_dao.aggiornaStatoTicket(nuovo_stato, t);
		if(status) {
			System.out.println("stato cambiato correttamente");
			
		}else {
			t.setStato_ticket(statoVecchio);
	        System.err.println("Non è stato possibile cambiare lo stato nel DB. Ripristino lo stato precedente in memoria.");
			throw new TicketStatusUnchangedException(id_ticket,nuovo_stato,t.getStato_ticket());
			
		}
		
	
		 
		
	}
	
	/*
	 * Gestore a NULL inizialmente che verrà modificato tramite l'assegnazione automatica dei ticket senza gestore
	 */
	public boolean apriTicket(UtenteGenerico richiedente) {
		
		Ticket t = new Ticket(richiedente, null, Stato.IN_ASSEGNAZIONE, null);
		boolean inseritoNelDb = ticket_dao.inserisciTicket(t);
		if (inseritoNelDb) {
	       
	        richieste_assistenza.put(t.getId_ticket(), t);
	        System.out.println("Ticket Creato e salvato con Successo! ID: " + t.getId_ticket());
	        return true;
	    } else {
	        System.err.println("Errore: impossibile salvare il ticket nel database. Operazione annullata.");
	        return false;
	    }
		
	

		
	}
	
	
	
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
	
	public boolean assegnaTicketToGestore(UtenteStaff staff) {
		
		ArrayList<Ticket> ticket_da_assegnare = ticket_dao.getTicketSenzaGestore();
		int ticket_assegnati = 0;
		
		
		if(ticket_da_assegnare != null) {
			for(Ticket t : ticket_da_assegnare) {
				
				boolean salvato = ticket_dao.aggiornaGestoreTicket(staff, t);
				if(salvato ) {
					t.setGestore(staff);
					richieste_assistenza.put(t.getId_ticket(), t);
					ticket_assegnati++;
					
				}else {
					t.setGestore(null);
					System.err.println("Errore DB: Impossibile assegnare il ticket " + t.getId_ticket());
					break;
				}
				
			}
			
			System.out.println("Assegnazione COMPLETATA, sono stati assegnati"+ticket_assegnati+ "allo staff"+staff.getNome_utente());	
			return ticket_assegnati ==  ticket_da_assegnare.size();
			
		}else {
			System.err.println("L'assegnazione ha riscontrato dei problemi controllare");
			return false;
		}
		
	
	}
	
	public boolean creaMessaggio(Utente autore,String testo,String id_ticket) {
		if (!esisteTicket(id_ticket)) {
	        System.err.println("Ticket non trovato");
	        return false; 
	    }
		Ticket t = richieste_assistenza.get(id_ticket);
	    Messaggio msg = t.creaMessaggio(autore, testo);
	    boolean salvatoNelDb = msg_dao.inserisciMessaggioInTicketRiferimento(msg);
	    
	    if (salvatoNelDb) {
	        System.out.println("Messaggio inviato e salvato correttamente.");
	        return true;
	    }
	    t.getConversazione().remove(msg);
	    System.err.println("Errore di connessione: impossibile inviare il messaggio.");
	    return false;
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
	
	public ArrayList<Ticket> inizializzaTicketDaUtenteGenerico(UtenteGenerico u) {
		ArrayList<Ticket> ticket_assistito = ticket_dao.getTicketDaRichiedente(u);
		inizializzaMessaggiDatiTickets(ticket_assistito);
		return ticket_assistito;
	}
	
	public ArrayList<Ticket> inizializzaTicketDaUtenteStaff(UtenteStaff staff) {
		ArrayList<Ticket> ticket_assistito = ticket_dao.getTicketDaStaff(staff);
		inizializzaMessaggiDatiTickets(ticket_assistito);
		return ticket_assistito;
			
	}
	public void aggiornaConversazioneDatoTicket(String id_ticket) {
		
		Ticket t = visualizzaTicketDaId(id_ticket);
		if(t.getConversazione().isEmpty() || t.getConversazione() == null) {
			System.out.println("Conversazione vuota: impossibile cercare aggiornamenti senza un messaggio di partenza.");
			return;
		}
		
		Messaggio m = t.getUltimoMessaggio();
		ArrayList<Messaggio> messaggi = msg_dao.getMessaggiNuovi(t, m.getData_pubblicazione());
		if(!messaggi.isEmpty() && messaggi != null) {	
			t.agguiungiMessessaggiAllaConversazione(messaggi);
			System.out.println("Conversazione aggiornata con " + messaggi.size() + " messaggi.");	
		}else {
			System.out.println("Nessun nuovo messaggio dal database.");
		}
		
		
		
		
	}

}
