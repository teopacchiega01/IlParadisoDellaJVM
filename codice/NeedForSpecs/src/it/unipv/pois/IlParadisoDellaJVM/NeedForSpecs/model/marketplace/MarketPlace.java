package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.ProdottiFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;

//	@author teopacchiega

public class MarketPlace {
	private ArrayList<Prodotto> prodotti;
	private ArrayList<Annuncio> annunci;
	
	public MarketPlace(ArrayList<Prodotto> prodotti, ArrayList<Annuncio> annunci) {
		super();
		this.prodotti = prodotti;
		this.annunci = annunci;
	}
	
	public MarketPlace() {
		super();
	}
	
	public boolean aggiungiComponente(double prezzo, String marca, String modello, TipoComponente tipo,
			ArrayList<String> valori_specifiche, int potenza) {
		Prodotto nuovo_componente = ProdottiFactory.creaComponente(prezzo, marca, modello, tipo, valori_specifiche, potenza);
		prodotti.add(nuovo_componente);
		return true;
	}
	
	public boolean aggiungiBuild(String nome) {
		Prodotto nuova_build = ProdottiFactory.creaBuild(nome);
		prodotti.add(nuova_build);
		return true;
	}
	
	
	
	
}
