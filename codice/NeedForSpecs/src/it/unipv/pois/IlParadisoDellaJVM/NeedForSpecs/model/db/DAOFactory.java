package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db;


import java.util.Properties;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.IUtenteDAO;
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
	
	
	private static DAOFactory instance;
	
	
	
	
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
    public abstract IUtenteDAO getUtenteDAO();
    
    
    
  
	
	
	
	public static DAOFactory getInstance() {

		if (instance == null) {
			Properties p = new Properties(); 
			try (java.io.FileInputStream fis = new java.io.FileInputStream("properties/properties")) {
				p.load(fis);
				String factoryClassName = p.getProperty("persistenza");
				java.lang.reflect.Constructor<?> c = Class.forName(factoryClassName).getConstructor();
				instance = (DAOFactory) c.newInstance();
			} catch (Exception e) { 
				throw new RuntimeException("ERRORE CRITICO: Impossibile avviare la persistenza dal file properties. Controlla il nome della classe!", e);
			}
		}
		return instance;
	}

	
}
