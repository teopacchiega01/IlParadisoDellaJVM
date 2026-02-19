package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti;

import java.util.ArrayList;
import java.util.EnumMap;

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
		this.setId_prodotto(generaId());
	}

	public Componente(double prezzo, String marca, String modello, TipoComponente tipo,
			ArrayList<String> valori_specifiche, int potenza) {
		super(prezzo);
		this.marca = marca;
		this.modello = modello;
		this.tipo = tipo;
		this.scheda_tecnica = new EnumMap<>(AspettiTecnici.class);
		switch (tipo) {
		case MOBO:
			// Ordine atteso nella lista: 
			// [0] Socket CPU, [1] Form Factor, [2] Tipo RAM, [3] N° Slot RAM, [4] Tipo PCIe, [5] N° Slot PCIe
			this.scheda_tecnica.put(AspettiTecnici.SOCKET_CPU, valori_specifiche.get(0));
			this.scheda_tecnica.put(AspettiTecnici.FORM_FACTOR_MOBO, valori_specifiche.get(1));
			this.scheda_tecnica.put(AspettiTecnici.TIPO_RAM, valori_specifiche.get(2));
			this.scheda_tecnica.put(AspettiTecnici.N_MODULI_RAM, valori_specifiche.get(3));
			this.scheda_tecnica.put(AspettiTecnici.TIPO_SLOT_PCIE, valori_specifiche.get(4));
			this.scheda_tecnica.put(AspettiTecnici.N_SLOT_PCIE, valori_specifiche.get(5));
			break;
		case CPU:
			// Ordine atteso nella lista: 
			// [0] Socket CPU, [1] Frequenza Clock
			this.scheda_tecnica.put(AspettiTecnici.SOCKET_CPU, valori_specifiche.get(0));
			this.scheda_tecnica.put(AspettiTecnici.FREQUENZA_CLOCK_CPU, valori_specifiche.get(1));
			break;
		case GPU:
			// Ordine atteso nella lista: 
			// [0] Tipo PCIe, [1] N° Slot PCIe occupati/richiesti, [2] VRAM (GB)
			this.scheda_tecnica.put(AspettiTecnici.TIPO_SLOT_PCIE, valori_specifiche.get(0));
			this.scheda_tecnica.put(AspettiTecnici.N_SLOT_PCIE, valori_specifiche.get(1));	
			this.scheda_tecnica.put(AspettiTecnici.VRAM_GB, valori_specifiche.get(2));
			break;
		case RAM:
			// Ordine atteso nella lista: 
			// [0] Tipo RAM , [1] Frequenza , [2] N° Moduli, [3] Dimensione Singolo Modulo
			this.scheda_tecnica.put(AspettiTecnici.TIPO_RAM, valori_specifiche.get(0));
			this.scheda_tecnica.put(AspettiTecnici.FREQUENZA, valori_specifiche.get(1));
			this.scheda_tecnica.put(AspettiTecnici.N_MODULI_RAM, valori_specifiche.get(2));
			this.scheda_tecnica.put(AspettiTecnici.DIM_SINGOLO_MODULO_RAM, valori_specifiche.get(3));
			break;
		case PSU:
			// Il PSU usa principalmente l'attributo potenza. Nessuna mappa necessaria.
			this.scheda_tecnica = null;
			break;
		}
		this.setId_prodotto(generaId());
	}


	public Componente() {
		super();
	}

	@Override
	protected String generaId() {
		String tipo = formattazione(this.tipo.name(), 5);
		String marca = formattazione(this.marca, 5);
		String modello = formattazione(this.modello, 5);
		String extra = formattazione((int)(Math.random()*100000)+"", 5);
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

	public String getInfoProdotto() {
		StringBuilder info = new StringBuilder(); 
		info.append(getMarca()+" "+getModello()+" ");
		if(scheda_tecnica!=null) {
			for(AspettiTecnici elemento_scheda_tecnica : scheda_tecnica.keySet()) {
				info.append(elemento_scheda_tecnica.name()+": "+scheda_tecnica.get(elemento_scheda_tecnica)+" ");
			}
		}

		info.append(getPotenza()+" W");
		return info.toString();
	}
}

