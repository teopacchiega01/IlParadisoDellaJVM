package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti;

import java.util.EnumMap;
import java.util.Random;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.TipologiaProdotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.AspettiTecnici;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;

//	@author teopacchiega

public class Componente extends Prodotto{
	private String marca;
	private String modello;
	private TipoComponente tipo;
	private EnumMap<AspettiTecnici, String> scheda_tecnica;
	private int potenza;
	
	public Componente(double prezzo, String marca, String modello, TipoComponente tipo,
			EnumMap<AspettiTecnici, String> scheda_tecnica, int potenza) {
		super(prezzo);
		this.marca = marca;
		this.modello = modello;
		this.tipo = tipo;
		this.scheda_tecnica = scheda_tecnica;
		this.potenza = potenza;
	}

	public Componente() {
		super();
	}

	@Override
	protected String generaId() {
		String tipo = formattazione(this.tipo.name(), 5);
		String marca = formattazione(this.marca, 5);
		String modello = formattazione(this.modello, 5);
		String extra = formattazione((int)Math.random()*100000+"", 5);
		String id = tipo+marca+modello+extra;
		return id;
	}

	@Override
	public TipologiaProdotto getTipologia() {
		return TipologiaProdotto.COMPONENTE;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public TipoComponente getTipo() {
		return tipo;
	}

	public void setTipo(TipoComponente tipo) {
		this.tipo = tipo;
	}

	public EnumMap<AspettiTecnici, String> getScheda_tecnica() {
		return scheda_tecnica;
	}

	public void setScheda_tecnica(EnumMap<AspettiTecnici, String> scheda_tecnica) {
		this.scheda_tecnica = scheda_tecnica;
	}

	public int getPotenza() {
		return potenza;
	}

	public void setPotenza(int potenza) {
		this.potenza = potenza;
	}

}
