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
	
	public boolean aggiungiElementoAlCarrello(Annuncio acquisto) {
		if(this.acquisti.add(acquisto)) {
			this.prezzo_totale = getPrezzoTotale();
			return true;
		}else{
			return false;
		}
	}
	
	public void eliminaCarrello() {
		this.acquisti = new ArrayList<Annuncio>();
		this.prezzo_totale = 0;
	}
	
	public boolean eliminaElementoDalCarrello(Annuncio acquisto_da_eliminare) {
		if(this.acquisti.add(acquisto_da_eliminare)) {
			this.prezzo_totale = getPrezzoTotale();
			return true;
		}else{
			return false;
		}
	}

	public ArrayList<Annuncio> getAcquisti() {
		return acquisti;
	}

	public void setAcquisti(ArrayList<Annuncio> acquisti) {
		this.acquisti = acquisti;
	}

	public double getPrezzo_totale() {
		return prezzo_totale;
	}

	public void setPrezzo_totale(double prezzo_totale) {
		this.prezzo_totale = prezzo_totale;
	}

}
