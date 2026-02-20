package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.IAnnuncioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.IOrdineDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.Ordine;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Build;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.IProdottoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.ProdottiFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;


//	@author teopacchiega

public class MarketPlace {
	private IProdottoDAO prod_dao;
	private IAnnuncioDAO ann_dao;
	private IOrdineDAO ord_dao;
	private ArrayList<Prodotto> prodotti;
	private ArrayList<Annuncio> annunci;

	public MarketPlace(ArrayList<Prodotto> prodotti, ArrayList<Annuncio> annunci, DAOFactory factory) {
		super();
		this.prodotti = prodotti;
		this.annunci = annunci;
		this.prod_dao = factory.getProdottoDAO();
		this.ann_dao = factory.getAnnuncioDAO();
		this.ord_dao = factory.getOrdineDAO();
	}

	public MarketPlace(DAOFactory factory) {
		super();
		this.prod_dao = factory.getProdottoDAO();
		this.ann_dao = factory.getAnnuncioDAO();
		this.ord_dao = factory.getOrdineDAO();
	}

	public Prodotto aggiungiComponente(double prezzo, String marca, String modello, TipoComponente tipo,
			ArrayList<String> valori_specifiche, int potenza) {
		Prodotto nuovo_componente = ProdottiFactory.creaComponente(prezzo, marca, modello, tipo, valori_specifiche, potenza);
		prodotti.add(nuovo_componente);
		return nuovo_componente;
	}

	public Prodotto aggiungiBuild(String nome) {
		Prodotto nuova_build = ProdottiFactory.creaBuild(nome);
		prodotti.add(nuova_build);
		return nuova_build;
	}

	public boolean aggiungiComponenteABuild(Build build_da_aggiornare, Componente componente_da_aggiungere) throws ComponentiException {
		boolean operazione_riuscita = false;

		operazione_riuscita = build_da_aggiornare.aggiungiComponente(componente_da_aggiungere);

		if(operazione_riuscita) {
			return true;
		}else {
			return false;
		}
	}

	public boolean aggiungiAnnuncio(Prodotto prodotto_in_vendita, UtenteGenerico venditore, double prezzo) {
		boolean operazione_riuscita = false;

		Annuncio nuovo_annuncio = new Annuncio(prodotto_in_vendita, venditore, prezzo);
		operazione_riuscita = annunci.add(nuovo_annuncio);

		if(operazione_riuscita) {
			return true;
		}else {
			return false;
		}


	}

	public boolean rimuoviProdotto(String id_prodotto_da_rimuovere) {
		boolean operazione_riuscita = false;

		for(int i=0; i<prodotti.size(); i++) {
			Prodotto prodotto_nella_lista = prodotti.get(i);
			if(prodotto_nella_lista.getId_prodotto().equals(id_prodotto_da_rimuovere)) {
				prodotti.remove(i);
				operazione_riuscita = true;
				break;
			}
		}

		if(operazione_riuscita) {
			return true;
		}else {
			return false;
		}
	}

	public boolean rimuoviAnnuncio(String id_annuncio_da_rimuovere) {
		boolean operazione_riuscita = false;

		for(int i=0; i<annunci.size(); i++) {
			Annuncio annuncio_nella_lista = annunci.get(i);
			if(annuncio_nella_lista.getId_annuncio().equals(id_annuncio_da_rimuovere)) {
				annunci.remove(i);
				operazione_riuscita = true;
				break;
			}
		}

		if(operazione_riuscita) {
			return true;
		}else {
			return false;
		}
	}

	public String mostraAnnunci() {
		if(annunci.isEmpty()) {
			return null;
		}else {
			String ret = "=[]== Annunci presenti ==[]=\n";
			for(Annuncio ann_in_lista : annunci) {
				ret = ret + ann_in_lista.toString();
			}
			return ret;
		}

	}

	public String mostraProdotti() {
		if(prodotti.isEmpty()) {
			return null;
		}else {
			String ret = "=[]== Prodotti presenti ==[]=\n";
			for(Prodotto prod_in_lista : prodotti) {
				ret = ret + prod_in_lista.getInfoProdotto();
			}
			return ret;
		}
	}


	public boolean aggiungiAlCarrello(UtenteGenerico utente_loggato, Annuncio annuncio_da_acquistare) {
		if(utente_loggato.aggiungiElementoAlCarrello(annuncio_da_acquistare)){
			return true;
		}else {
			return false;
		}
	}

	public boolean rimuoviElementoDalCarrello(UtenteGenerico utente_loggato, Annuncio annuncio_da_acquistare) {
		if(utente_loggato.getCarr().eliminaElementoDalCarrello(annuncio_da_acquistare)){
			return true;
		}else {
			return false;
		}
	}

	public boolean effettuaOrdine(UtenteGenerico acquirente) {
		if(acquirente.getCarr().getAcquisti().isEmpty()) {
			return false;
		}else {
			Ordine nuovo_ordine = new Ordine(acquirente);
			acquirente.getCarr().eliminaCarrello();
			if(ord_dao.inserisciOrdine(nuovo_ordine)) {
				return true;
			}else {
				return false;
			}
		}
	}



	public void inizializzaMarketPlace() {
		setProdotti(prod_dao.getProdotti());
		setAnnunci(ann_dao.getAnnunci());
	}

	public IProdottoDAO getProd_dao() {
		return prod_dao;
	}

	public IAnnuncioDAO getAnn_dao() {
		return ann_dao;
	}

	public ArrayList<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(ArrayList<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}

	public ArrayList<Annuncio> getAnnunci() {
		return annunci;
	}

	public void setAnnunci(ArrayList<Annuncio> annunci) {
		this.annunci = annunci;
	}

	public IOrdineDAO getOrd_dao() {
		return ord_dao;
	}

	public void setOrd_dao(IOrdineDAO ord_dao) {
		this.ord_dao = ord_dao;
	}

	public void setProd_dao(IProdottoDAO prod_dao) {
		this.prod_dao = prod_dao;
	}

	public void setAnn_dao(IAnnuncioDAO ann_dao) {
		this.ann_dao = ann_dao;
	}

}
