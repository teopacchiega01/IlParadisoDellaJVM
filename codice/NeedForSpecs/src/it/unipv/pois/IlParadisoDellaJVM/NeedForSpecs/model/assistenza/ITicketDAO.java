package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza;

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
	
	
	
}
