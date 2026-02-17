package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDate;
import java.time.LocalDateTime;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Ticket;


/*
 * @author: Persy
 */

public class Messaggio extends ContenutoUtente{

	private Ticket ticket_di_riferimento;
	
	
	
	
	
	public Messaggio(Utente autore, String testo, LocalDateTime data_pubblicazione, Ticket ticket_di_riferimento) {
		super(autore, testo, data_pubblicazione);
		this.ticket_di_riferimento = ticket_di_riferimento;
	}


	


	public Messaggio(Ticket ticket_di_riferimento) {
		super();
		this.ticket_di_riferimento = ticket_di_riferimento;
	}



	
	//query del db per aggiungerlo alla conversazione

	
	
	
	
	
}
