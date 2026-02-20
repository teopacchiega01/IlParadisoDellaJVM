package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;

public interface IMessaggioDAO {
	
	public ArrayList<Messaggio> getMessaggiDaTicket(Ticket ticket_di_riferimento);
	public boolean inserisciMessaggioInTicketRiferimento(Messaggio m);
	public ArrayList<Messaggio> getMessaggiNuovi(Ticket ticket_di_riferimento, LocalDateTime data_ultimo_messaggio );
	

}
