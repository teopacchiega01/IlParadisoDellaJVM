package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.IIndirizzoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.IndirizzoDAOdb;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.CartaDAOdb;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.ICartaDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.ITicketDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.TicketDAOdb;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IMessaggioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.MessaggioDAOdb;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.AnnuncioDAOdb;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.IAnnuncioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.IOrdineDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.OrdineDAOdb;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.IProdottoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.ProdottoDAOdb;

public class MySQLDAOFactory extends DAOFactory {
	public ITicketDAO getTicketDAO() {
		return new TicketDAOdb();
	}
	public IMessaggioDAO getMessaggioDAO() {
		return new MessaggioDAOdb();
	}
	public IProdottoDAO getProdottoDAO() {
		return new ProdottoDAOdb();
	}
	public IAnnuncioDAO getAnnuncioDAO() {
		return new AnnuncioDAOdb();
	}
	public IOrdineDAO getOrdineDAO() {
		return new OrdineDAOdb();
	}
	public ICartaDAO getCartaDAO() {
		return new CartaDAOdb();
	}
	public IIndirizzoDAO getIndirizzoDAO() {
		return new IndirizzoDAOdb();
	}
	

}
