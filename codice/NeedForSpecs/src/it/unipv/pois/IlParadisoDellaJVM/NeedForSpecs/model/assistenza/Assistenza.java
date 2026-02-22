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

public class Assistenza {
	
	private Map<String,Ticket> richieste_assistenza;
	private IMessaggioDAO msg_dao;
	private ITicketDAO ticket_dao; 
	private Utente utente_loggato;

	public Assistenza(DAOFactory factory) {
		richieste_assistenza = new HashMap<String, Ticket>();
		this.msg_dao = factory.getMessaggioDAO();
		this.ticket_dao = factory.getTicketDAO();
	}

	public Assistenza(DAOFactory factory, Utente utente_loggato) {
		richieste_assistenza = new HashMap<String, Ticket>();
		this.msg_dao = factory.getMessaggioDAO();
		this.ticket_dao = factory.getTicketDAO();
		this.utente_loggato = utente_loggato;
	}
	
	private boolean esisteTicket(String id_ticket) {
		return richieste_assistenza.containsKey(id_ticket);
	}
	
	public Ticket visualizzaTicketDaId(String id_ticket) {
		if(esisteTicket(id_ticket)) {
			Ticket t = richieste_assistenza.get(id_ticket);
			System.out.println("Ticket trovato correttamente, ticket: " + t);
			return t;
		} else {
			throw new TicketNotFoundException(id_ticket);
		}
	}
	
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
	
	public boolean apriTicket() {
		// Il ticket viene aperto direttamente dall'utente loggato nel model
		if (this.utente_loggato == null || this.utente_loggato.isStaff()) {
			System.err.println("Errore: Solo un utente generico loggato può aprire un ticket.");
			return false;
		}
		
		Ticket t = new Ticket((UtenteGenerico) this.utente_loggato, null, Stato.IN_ASSEGNAZIONE, null);
		boolean inseritoNelDb = ticket_dao.inserisciTicket(t);
		if (inseritoNelDb) {
			richieste_assistenza.put(t.getId_ticket(), t);
			System.out.println("Ticket Creato e salvato con Successo! ID: " + t.getId_ticket());
			return true;
		} else {
			System.err.println("Errore: impossibile salvare il ticket nel database.");
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
	
	public boolean creaMessaggio(String testo, String id_ticket) {
		if (!esisteTicket(id_ticket)) {
			System.err.println("Ticket non trovato");
			return false; 
		}
		
		Ticket t = richieste_assistenza.get(id_ticket);
		// Usa direttamente l'utente loggato nel Model
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
	
	// ==========================================================
	// LOGICA DI CARICAMENTO TICKET (Usa isStaff!)
	// ==========================================================
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
	
	public ArrayList<Ticket> getTuttiITicketCaricati() {
		return new ArrayList<>(richieste_assistenza.values());
	}

	private void inizializzaTicketAssistenza(ArrayList<Ticket> tickets) {
		for(Ticket t : tickets) {
			richieste_assistenza.put(t.getId_ticket(), t);
		}
	}

	private void inizializzaMessaggiDatiTickets(ArrayList<Ticket> tickets) {
		for (Ticket t : tickets) {
			ArrayList<Messaggio> msgs = msg_dao.getMessaggiDaTicket(t);
			t.setConversazione(msgs);
			System.out.println("Ticket " + t + " inizializzato con " + msgs.size() + " messaggi.");
		}
		inizializzaTicketAssistenza(tickets);
	}
	
	public void aggiornaConversazioneDatoTicket(String id_ticket) {
		Ticket t = visualizzaTicketDaId(id_ticket);
		if(t.getConversazione().isEmpty() || t.getConversazione() == null) {
			return;
		}
		
		Messaggio m = t.getUltimoMessaggio();
		ArrayList<Messaggio> messaggi = msg_dao.getMessaggiNuovi(t, m.getData_pubblicazione());
		if(!messaggi.isEmpty() && messaggi != null) {	
			t.agguiungiMessessaggiAllaConversazione(messaggi);
		}
	}

	public String eseguiRicercaSuTicket(String id_ticket, Ricerca tipoRicerca, String parametro) {
		Ticket t = visualizzaTicketDaId(id_ticket);
		return t.ricercaEFormattaMessaggi(parametro, tipoRicerca);
	}
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

	public Utente getUtente_loggato() {
		return utente_loggato;
	}

	public void setUtente_loggato(Utente utente_loggato) {
		this.utente_loggato = utente_loggato;
	}
}