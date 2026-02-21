package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.IIndirizzoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.ICartaDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.ITicketDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.ICommentoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IMessaggioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IPostDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.IAnnuncioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.IOrdineDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.IProdottoDAO;


/*
 * @author: Persy
 */

public abstract class DAOFactory{
	public abstract ITicketDAO getTicketDAO();
    public abstract IMessaggioDAO getMessaggioDAO();
    public abstract IProdottoDAO getProdottoDAO();
    public abstract IAnnuncioDAO getAnnuncioDAO();
    public abstract IOrdineDAO getOrdineDAO();
    public abstract ICartaDAO getCartaDAO();
    public abstract IIndirizzoDAO getIndirizzoDAO();
    public abstract IPostDAO getPostDAO();
    public abstract ICommentoDAO getCommentoDAO();
//    public abstract IUtenteDAO getUtenteDAO();
//    public abstract IUtenteGenericoDAO getUtenteGenericoDAO();
    
    
    
    
    
    
    public static DAOFactory getPersistenceFactory(Persistenza persistenza_scelta) {
    	
    	switch(persistenza_scelta) {
    	case MYSQL_DB:
    		return new MySQLDAOFactory();
    	default:
    		throw new IllegalArgumentException("Persistenza non ancora implementata dall'applicazione");
		
    		
    	}
    	
    }
	

	
}
