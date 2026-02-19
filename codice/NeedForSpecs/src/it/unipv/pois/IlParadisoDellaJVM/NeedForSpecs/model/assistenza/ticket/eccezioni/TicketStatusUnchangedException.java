package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.eccezioni;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Stato;

public class TicketStatusUnchangedException extends TicketExeception {

	public TicketStatusUnchangedException(String id,Stato nuovo_stato,Stato vecchio_stato) {
		super("Il NUOVO Stato"+nuovo_stato+"del ticket NON è stato aggiornato correttamente, id del ticket: "+id+ "stato corretnte"+vecchio_stato);
		// TODO Auto-generated constructor stub
	}

}
