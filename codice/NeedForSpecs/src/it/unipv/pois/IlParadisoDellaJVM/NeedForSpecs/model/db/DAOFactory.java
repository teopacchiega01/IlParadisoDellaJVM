package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.ITicketDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IMessaggioDAO;


/*
 * @author: Persy
 */

public abstract class DAOFactory{
	public abstract ITicketDAO getTicketDAO();
    public abstract IMessaggioDAO getMessaggioDAO();
    
    
    
    public static DAOFactory getPersistenceFactory(Persistenza persistenza_scelta) {
    	
    	switch(persistenza_scelta) {
    	case MYSQL_DB:
    		return new MySQLDAOFactory();
    	default:
    		throw new IllegalArgumentException("Persistenza non ancora implementata dall'applicazione");
		
    		
    	}
    	
    }
	

	
}
