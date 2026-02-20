package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci;

import java.security.SecureRandom;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.GeneratoreId;

//	@author teopacchiega

public class Annuncio {
	private final static int DIM_ID_ANNUNCIO = 20;
	private String id_annuncio;
	private Prodotto prodotto_in_vendita;
	private UtenteGenerico venditore;
	private double prezzo;
	
	public Annuncio(Prodotto prodotto_in_vendita, UtenteGenerico venditore, double prezzo) {
		super();
		this.id_annuncio = GeneratoreId.generaId(DIM_ID_ANNUNCIO);
		this.prodotto_in_vendita = prodotto_in_vendita;
		this.venditore = venditore;
		this.prezzo = prezzo;
	}

	public String getId_annuncio() {
		return id_annuncio;
	}

	public void setId_annuncio(String id_annuncio) {
		this.id_annuncio = id_annuncio;
	}

	public Prodotto getProdotto_in_vendita() {
		return prodotto_in_vendita;
	}

	public void setProdotto_in_vendita(Prodotto prodotto_in_vendita) {
		this.prodotto_in_vendita = prodotto_in_vendita;
	}

	public UtenteGenerico getVenditore() {
		return venditore;
	}

	public void setVenditore(UtenteGenerico venditore) {
		this.venditore = venditore;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	// Di fatto è un toString()
	public String getInfoAnnuncio() {
		String ret = "[ "+getId_annuncio()+" ]\nProdotto in vendita: "+prodotto_in_vendita.getInfoProdotto()+"\nVenditore: "+venditore.getUser_name()+"\nPrezzo: "+getPrezzo()+"€\n\n";
		return ret;
	}
	
}
