package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;


/*
 * @author: Persy
 */

public class Messaggio extends ContenutoUtente implements Comparable<Messaggio>{

	private Ticket ticket_di_riferimento;
	
	
	
	
	
	public Messaggio(Utente autore, String testo, LocalDateTime data_pubblicazione, Ticket ticket_di_riferimento) {
		super(autore, testo, data_pubblicazione);
		this.ticket_di_riferimento = ticket_di_riferimento;
	}

	
	


	public Messaggio(String id_contenuto_utente, Utente autore, String testo, LocalDateTime data_pubblicazione,
			Ticket ticket_di_riferimento) {
		super(id_contenuto_utente, autore, testo, data_pubblicazione);
		this.ticket_di_riferimento = ticket_di_riferimento;
	}





	public Messaggio(Ticket ticket_di_riferimento) {
		super();
		this.ticket_di_riferimento = ticket_di_riferimento;
	}



	
	
	public Messaggio() {
		super();
		
	}





	public Ticket getTicket_di_riferimento() {
		return ticket_di_riferimento;
	}





	public void setTicket_di_riferimento(Ticket ticket_di_riferimento) {
		this.ticket_di_riferimento = ticket_di_riferimento;
	}





	@Override
	public int compareTo(Messaggio altro_messaggio) {
		// TODO Auto-generated method stub
		
		
		return this.getData_pubblicazione().compareTo(altro_messaggio.getData_pubblicazione());
	}



	
	
	
	
	
}
