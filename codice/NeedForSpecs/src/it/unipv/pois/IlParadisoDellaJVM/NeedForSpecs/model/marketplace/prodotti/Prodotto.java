package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti;

//@author teopacchiega

public abstract class Prodotto {
	private String id_prodotto;
	private double prezzo;
	
	
	
	public Prodotto(double prezzo) {
		super();
		this.id_prodotto = generaId();
		this.prezzo = prezzo;
	}
	
	public Prodotto() {
		super();
	}



	public abstract TipologiaProdotto getTipologia();
	
	protected abstract String generaId();
	
	protected String formattazione(String testo, int max_char) {
		//Rimuovo tutti gli spazi e metto tutte le lettere in maiuscolo
		testo = testo.toUpperCase().replaceAll("\\s+", "");
		//Se la stringa ottenuta è della dimensione richiesta, la ritorno
		if(testo.length()>max_char) {
			return testo.substring(0, max_char);
		}
		//Se la stringa è troppo corta, ci attacco degli 0
		StringBuilder sb = new StringBuilder(testo);
		while(sb.length() < max_char) {
			sb.append("0");
		}
		return sb.toString();
	}

}
