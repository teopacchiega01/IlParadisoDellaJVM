package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Ticket;
/*
 * @author Persy
 */

public interface IMessaggioDAO {
	
	public ArrayList<Messaggio> getMessaggiDaTicket(Ticket ticket_di_riferimento);
	//public ArrayList<Messaggio> getMessaggiDaUtente(Utente u);
	public boolean inserisciMessaggioInTicketRiferimento(Messaggio m);

}
