package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

//	@author teopacchiega

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;

public class Carrello {
	
	private ArrayList<Annuncio> prodotti;
	private double prezzo_totale;
	
	
	
	public Carrello(ArrayList<Annuncio> prodotti, double prezzo_totale) {
		super();
		this.prodotti = prodotti;
		this.prezzo_totale = prezzo_totale;
	}
	
	
		

}
