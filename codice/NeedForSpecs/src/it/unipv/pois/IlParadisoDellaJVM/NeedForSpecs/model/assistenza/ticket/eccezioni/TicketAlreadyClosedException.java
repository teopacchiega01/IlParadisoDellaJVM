package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.eccezioni;

public class TicketAlreadyClosedException extends TicketExeception {

	public TicketAlreadyClosedException(String id) {
		super("Il ticket selezionato risulta già chiuso ID: "+id);
		// TODO Auto-generated constructor stub
	}

}
