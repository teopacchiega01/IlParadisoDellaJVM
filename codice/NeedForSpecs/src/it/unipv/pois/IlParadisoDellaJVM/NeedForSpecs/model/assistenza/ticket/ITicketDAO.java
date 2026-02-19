package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;


/*
 * @author: Persy
 */
public interface ITicketDAO {

	public ArrayList<Ticket> getTicketDaRichiedente(UtenteGenerico u);
	public ArrayList<Ticket> getTicketDaStaff(UtenteStaff u);
	public boolean inserisciTicket(Ticket t);
	
	boolean aggiornaStatoTicket(Stato nuovo_stato,Ticket t);
	
	public ArrayList<Ticket> getTicketSenzaGestore();
	
	
}
