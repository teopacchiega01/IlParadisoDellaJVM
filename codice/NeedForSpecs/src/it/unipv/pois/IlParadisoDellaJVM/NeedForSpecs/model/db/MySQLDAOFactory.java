package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.ITicketDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.TicketDAOdb;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IMessaggioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.MessaggioDAOdb;

public class MySQLDAOFactory extends DAOFactory {
	public ITicketDAO getTicketDAO() {
		return new TicketDAOdb();
	}
	public IMessaggioDAO getMessaggioDAO() {
		return new MessaggioDAOdb();
	}

}
