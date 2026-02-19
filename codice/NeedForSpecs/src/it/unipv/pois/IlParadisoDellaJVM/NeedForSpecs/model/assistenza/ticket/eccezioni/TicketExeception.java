package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.eccezioni;

public class TicketExeception extends RuntimeException {

	public TicketExeception(String message) {
		super(message);
	}

	public TicketExeception(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	

}
