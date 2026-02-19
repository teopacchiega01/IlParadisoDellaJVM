package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.eccezioni;

public class TicketNotFoundException extends TicketExeception {

	public TicketNotFoundException(String id) {
		super("Impossibile Trovare il ticket con ID: "+id);
		// TODO Auto-generated constructor stub
	}

}
