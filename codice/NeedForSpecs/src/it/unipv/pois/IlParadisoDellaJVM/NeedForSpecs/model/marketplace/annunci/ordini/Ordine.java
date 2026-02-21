package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies.ISaleStrategy;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies.SaleFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.GeneratoreId;

//	@author teopacchiega

public class Ordine {
	private final static int DIM_ID_ORDINE = 20;
	private String id_ordine;
	private ArrayList<Annuncio> prodotti_acquistati;
	private double prezzo_totale;
	private UtenteGenerico acquirente;
	private ISaleStrategy sale_strategy;
	
	public Ordine(UtenteGenerico acquirente) {
		super();
		this.id_ordine = GeneratoreId.generaId(DIM_ID_ORDINE);
		this.prodotti_acquistati = acquirente.getCarr().getAcquisti();
		this.prezzo_totale = acquirente.getCarr().getPrezzo_totale();
		this.acquirente = acquirente;
		SaleFactory sf = new SaleFactory();
		this.sale_strategy = sf.getDiscountStrategy();
		this.prezzo_totale = sale_strategy.getTotaleElaborato(this);
	}

	public Ordine() {
		super();
		this.id_ordine = GeneratoreId.generaId(DIM_ID_ORDINE);
	}

	public ArrayList<Annuncio> getProdotti_acquistati() {
		return prodotti_acquistati;
	}

	public void setProdotti_acquistati(ArrayList<Annuncio> prodotti_acquistati) {
		this.prodotti_acquistati = prodotti_acquistati;
	}

	public double getPrezzo_totale() {
		return prezzo_totale;
	}

	public void setPrezzo_totale(double prezzo_totale) {
		this.prezzo_totale = prezzo_totale;
	}

	public UtenteGenerico getAcquirente() {
		return acquirente;
	}

	public void setAcquirente(UtenteGenerico acquirente) {
		this.acquirente = acquirente;
	}

	public String getId_ordine() {
		return id_ordine;
	}

	public void setId_ordine(String id_ordine) {
		this.id_ordine = id_ordine;
	}
	
}
