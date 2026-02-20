package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

//	@author teopacchiega

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;

public class Carrello {
	
	private ArrayList<Annuncio> acquisti;
	private double prezzo_totale;
	
	
	
	public Carrello(ArrayList<Annuncio> acquisti, double prezzo_totale) {
		super();
		this.acquisti = acquisti;
		this.prezzo_totale = getPrezzoTotale();
	}
	
	public Carrello() {
		super();
		this.acquisti = new ArrayList<Annuncio>();
		this.prezzo_totale = 0;
	}

	private double getPrezzoTotale() {
		double totale = 0;
		for(Annuncio prodotto_nel_carrello : acquisti) {
			totale += prodotto_nel_carrello.getPrezzo();
		}
		return totale;
	}
	
	public boolean aggiungiElemento(Annuncio acquisto) {
		return this.acquisti.add(acquisto);
	}
	
	public void eliminaCarrello() {
		this.acquisti = new ArrayList<Annuncio>();
		this.prezzo_totale = 0;
	}
	
//	TODO
//	public boolean eliminaElementoDalCarrello(Annuncio acquisto_da_eliminare) {
//		
//	}

}
