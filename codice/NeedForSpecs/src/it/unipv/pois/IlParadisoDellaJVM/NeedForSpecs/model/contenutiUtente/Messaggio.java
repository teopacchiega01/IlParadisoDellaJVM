package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;


/**
 * @author Persy
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


	/**
	 * Confronta questo messaggio con un altro in base alla data e ora di pubblicazione.
	 * Questo metodo è fondamentale per permettere a Collections.sort() di ordinare 
	 * cronologicamente (dal più vecchio al più recente) l'intera conversazione del Ticket.
	 * * @param altro_messaggio L'oggetto Messaggio con cui effettuare il confronto.
	 * @return Un intero negativo, zero, o un intero positivo se la data di questo messaggio 
	 * è rispettivamente precedente, uguale o successiva a quella passata come parametro.
	 */
	@Override
	public int compareTo(Messaggio altro_messaggio) {
		return this.getData_pubblicazione().compareTo(altro_messaggio.getData_pubblicazione());
	}


	
	
	
	
	
}
