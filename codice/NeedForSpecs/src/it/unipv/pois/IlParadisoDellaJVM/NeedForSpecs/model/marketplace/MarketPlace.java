package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Build;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.ProdottiFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException;
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

	public boolean rimuoviAnnuncio(int id_annuncio_da_rimuovere) {
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


}
