package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Ticket;


/*
 * @author: Persy
 */

public class Messaggio extends ContenutoUtente{

	private Ticket ticket_di_riferimento;
	private IMessaggioDAO dao;
	
	
	
	
	public Messaggio(Utente autore, String testo, LocalDateTime data_pubblicazione, Ticket ticket_di_riferimento) {
		super(autore, testo, data_pubblicazione);
		this.ticket_di_riferimento = ticket_di_riferimento;
	}


	


	public Messaggio(Ticket ticket_di_riferimento) {
		super();
		this.ticket_di_riferimento = ticket_di_riferimento;
	}



	
	
	public Messaggio() {
		super();
		dao = new MessaggioDAOdb();
	}





	//query del db per aggiungerlo alla conversazione
	public ArrayList<Messaggio>  getMessaggiDaTicket(Ticket t){
		System.out.println("Eseguo la query dal database...");
		return dao.getMessaggiDaTicket(t);
		
	}
	
	
	
	
	
}
