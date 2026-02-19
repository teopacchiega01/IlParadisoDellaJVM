package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException.TipoErrore;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.AspettiTecnici;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;

//	@author teopacchiega

public class Build extends Prodotto{
	// Questi due attributi forse sono inutili
	final static int MAX_RAM = 4;
	final static int MAX_GPU = 2;
	private String nome;
	private EnumMap<TipoComponente, ArrayList<Componente>> componenti;

	public Build(double prezzo, String nome,
			EnumMap<TipoComponente, ArrayList<Componente>> componenti) {
		super(prezzo);
		this.nome = nome;
		this.componenti = componenti;
	}

	public Build(double prezzo, String nome) {
		super(prezzo);
		this.nome = nome;
		this.componenti = new EnumMap<>(TipoComponente.class);
		for(TipoComponente appoggio : TipoComponente.values()) {
			componenti.put(appoggio, new ArrayList<Componente>());
		}

		this.setId_prodotto(generaId());
	}

	public Build() {
		super();
	}

	public Build(String nome) {
		super();
		this.nome = nome;
	}

	@Override
	protected String generaId() {
		String nome = formattazione(this.nome, 15);
		String extra = formattazione((int)Math.random()*100000+"", 10);
		String id = nome+extra;
		return id;
	}

	@Override
	public TipologiaProdotto getTipologia() {
		return TipologiaProdotto.BUILD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public EnumMap<TipoComponente, ArrayList<Componente>> getComponenti() {
		return componenti;
	}

	public void setComponenti(EnumMap<TipoComponente, ArrayList<Componente>> componenti) {
		this.componenti = componenti;
	}

	public boolean aggiungiComponente(Componente nuovoComponente) throws ComponentiException {
		TipoComponente tipoNuovoComponente = nuovoComponente.getTipo();
		ArrayList<Componente> componentiPresenti = componenti.get(tipoNuovoComponente);

		//	Prima di tutto controllo se l'utente sta cercando di aggiungere una scheda madre
		if(tipoNuovoComponente==TipoComponente.MOBO) {
			//	Se non c'è nessuna mobo, la può aggiungere
			if(componentiPresenti.isEmpty()) {
				componentiPresenti.add(nuovoComponente);
				componenti.put(tipoNuovoComponente, componentiPresenti);
				return true;
			}
			//	Se c'è già una mobo, verrà lanciato un errore
			else {
				throw new ComponentiException("Schede madri multiple rilevate", TipoErrore.SCHEDE_MADRI_MULTIPLE);
			}	
		}
		//	Se l'utente cerca di aggiungere un componente senza aver selezionato una mobo, verrà lanciato un errore
		else if((tipoNuovoComponente!=TipoComponente.MOBO) && componenti.get(TipoComponente.MOBO).isEmpty()) {
			throw new ComponentiException("Il primo componente aggiunto deve essere una scheda madre", TipoErrore.SCHEDA_MADRE_MANCANTE);
		}
		/* 	Una volta scelta una mobo, l'utente può scegliere:
		 *		- 1 CPU
		 *		- 4 stick di RAM (ossia MAX_RAM)
		 *		- 2 GPU (ossia MAX_GPU)
		 *		- 1 PSU
		 */
		else{
			/* Controllo il tipo del componente da aggiungere:
			 *  - se possono essercene multipli
			 *  	> controllo se posso aggiungerne un altro, altrimenti lancio un'eccezione
			 *  - se NON possono essercene multipli
			 *  	> controllo se ce ne sono altri, in tal caso lancio un'eccezione
			 */
			switch (tipoNuovoComponente) {
			case CPU:
				if(componentiPresenti.isEmpty()) {
					if(verificaCompatibilità(componenti.get(TipoComponente.MOBO).get(0), nuovoComponente)) {
						componentiPresenti.add(nuovoComponente);
						componenti.put(tipoNuovoComponente, componentiPresenti);
						return true;
					}else {
						throw new ComponentiException("Socket CPU e scheda madre incompatibili", TipoErrore.SOCKET_CPU_INCOMPATIBILE);
					}
				}else {
					throw new ComponentiException("CPU multiple rilevate", TipoErrore.CPU_MULTIPLE);
				}
			case RAM:
				if(verificaCompatibilità(componenti.get(TipoComponente.MOBO).get(0), nuovoComponente)) {
					// Controllo se ci sono slot RAM liberi
					int slot_ram = Integer.parseInt(componenti.get(TipoComponente.MOBO).get(0).getScheda_tecnica().get(AspettiTecnici.N_MODULI_RAM));
					int n_stick_ram_da_aggiungere = Integer.parseInt(nuovoComponente.getScheda_tecnica().get(AspettiTecnici.N_MODULI_RAM));

					int n_stick_ram_presenti = 0;
					for(Componente appoggio : componenti.get(TipoComponente.RAM)) {
						n_stick_ram_presenti += Integer.parseInt(appoggio.getScheda_tecnica().get(AspettiTecnici.N_MODULI_RAM));
					}

					int tot_stick_ram = n_stick_ram_da_aggiungere + n_stick_ram_presenti;

					if(tot_stick_ram<=slot_ram) {
						componentiPresenti.add(nuovoComponente);
						componenti.put(tipoNuovoComponente, componentiPresenti);
						return true;
					}else {
						throw new ComponentiException("Slot RAM insufficienti per l'aggiunta della RAM richiesta", TipoErrore.SLOT_RAM_INSUFFICIENTI);
					}
				}else {
					throw new ComponentiException("Tipo RAM incompatibile con la scheda madre", TipoErrore.TIPO_RAM_INCOMPATIBILE);
				}
			case GPU:
				if(verificaCompatibilità(componenti.get(TipoComponente.MOBO).get(0), nuovoComponente)) {
					// Controllo se ci sono slot PCIe liberi
					int slot_pcie = Integer.parseInt(componenti.get(TipoComponente.MOBO).get(0).getScheda_tecnica().get(AspettiTecnici.N_SLOT_PCIE));
					int n_slot_pcie_da_aggiungere = Integer.parseInt(nuovoComponente.getScheda_tecnica().get(AspettiTecnici.N_SLOT_PCIE));


					int n_slot_pcie_occupati = 0;
					for(Componente appoggio : componenti.get(TipoComponente.GPU)) {
						n_slot_pcie_occupati += Integer.parseInt(appoggio.getScheda_tecnica().get(AspettiTecnici.N_SLOT_PCIE));
					}

					int tot_slot_pcie_occupati = n_slot_pcie_occupati + n_slot_pcie_da_aggiungere;

					if(tot_slot_pcie_occupati<slot_pcie) {
						componentiPresenti.add(nuovoComponente);
						componenti.put(tipoNuovoComponente, componentiPresenti);
						return true;
					}else {
						throw new ComponentiException("Slot PCIe insufficienti per l'aggiunta della GPU richiesta", TipoErrore.SLOT_PCIE_INSUFFICIENTI);
					}
				}else {
					throw new ComponentiException("CPU multiple rilevate", TipoErrore.CPU_MULTIPLE);
				}
			case PSU:
				//	Controllo se c'è già un alimentatore
				if(componenti.get(TipoComponente.PSU).isEmpty()) {
					componenti.get(TipoComponente.PSU).add(nuovoComponente);
					if(controllaPotenza()) {
						return true;
					}else {
						throw new ComponentiException("Potenza insufficiente", TipoErrore.POTENZA_INSUFFICIENTE);
					}
				}else {
					throw new ComponentiException("E' già presente un PSU", TipoErrore.PSU_MULTIPLI);
				}
			default:
				return false;

			}

		}

	}


	public void rimuoviTipoDiComponenti(TipoComponente componente_da_rimuovere) {
		componenti.put(componente_da_rimuovere, new ArrayList<Componente>());
	}

	//TODO
	//	public boolean rimuoviComponente(TipoComponente componente_da_rimuovere, int posizione_comp_da_rim) {
	//		return false;
	//	}

	private boolean verificaCompatibilità(Componente c1, Componente c2) throws ComponentiException{

		//	Controllo se uno dei due componenti è una scheda madre
		if(c1.getTipo()==TipoComponente.MOBO || c2.getTipo()==TipoComponente.MOBO){
			TipoComponente mobo = null;
			TipoComponente comp = null;
			EnumMap<AspettiTecnici, String> st_mobo = null;
			EnumMap<AspettiTecnici, String> st_comp = null;
			if(c1.getTipo()==TipoComponente.MOBO){
				mobo = c1.getTipo();
				comp = c2.getTipo();
				st_mobo = c1.getScheda_tecnica();
				st_comp = c2.getScheda_tecnica();
			}else if(c2.getTipo()==TipoComponente.MOBO){
				mobo = c2.getTipo();
				comp = c1.getTipo();
				st_mobo = c2.getScheda_tecnica();
				st_comp = c1.getScheda_tecnica();
			}

			// Controllo la compatibilità delle mobo con altri tipi di componenti 
			switch (comp) {
			case CPU:
				if(st_mobo.get(AspettiTecnici.SOCKET_CPU).equals(st_comp.get(AspettiTecnici.SOCKET_CPU))) {
					return true;
				}else {
					throw new ComponentiException("Socket CPU e scheda madre incompatibili", TipoErrore.SOCKET_CPU_INCOMPATIBILE);
				}
			case RAM:
				if(st_mobo.get(AspettiTecnici.TIPO_RAM).equals(st_comp.get(AspettiTecnici.TIPO_RAM))) {
					return true;
				}else {
					throw new ComponentiException("Tipo RAM incompatibil con la scheda madre", TipoErrore.TIPO_RAM_INCOMPATIBILE);
				}
			case GPU:
				if(st_mobo.get(AspettiTecnici.TIPO_SLOT_PCIE).equals(st_comp.get(AspettiTecnici.TIPO_SLOT_PCIE))) {
					return true;
				}else {
					throw new ComponentiException("Slot PCIe GPU e scheda madre incompatibili", TipoErrore.SLOT_PCIE_INCOMPATIBILE);
				}
			default:
				return false;
			}
		}
		// Se nessuno dei componenti è una scheda madre, controllo che abbiano la stessa interfaccia
		else {
			TipoComponente t1 = c1.getTipo();
			TipoComponente t2 = c2.getTipo();
			EnumMap<AspettiTecnici, String> st1 = c1.getScheda_tecnica();
			EnumMap<AspettiTecnici, String> st2 = c2.getScheda_tecnica();

			if(t1.equals(t2)) {
				switch (t1) {
				case CPU:
					if(st1.get(AspettiTecnici.SOCKET_CPU).equals(st2.get(AspettiTecnici.SOCKET_CPU))) {
						return true;
					}else {
						return false;
					}
				case RAM:
					if(st1.get(AspettiTecnici.TIPO_RAM).equals(st2.get(AspettiTecnici.TIPO_RAM))) {
						return true;
					}else {
						return false;
					}
				case GPU:
					if(st1.get(AspettiTecnici.TIPO_SLOT_PCIE).equals(st2.get(AspettiTecnici.TIPO_SLOT_PCIE))) {
						return true;
					}else {
						return false;
					}
				default:
					return false;
				}
			}
			// Se si tratta di due componenti di tipo diverso non ha senso controllare la compatibilità
			else {
				return false;
			}

		}

	}

	public int getPotenzaDisponibile() {
		return componenti.get(TipoComponente.PSU).get(0).getPotenza();
	}

	public int getPotenzaRichiesta() {
		int potenza_richiesta = 0;
		for(TipoComponente tipo_appoggio : TipoComponente.values()) {
			if(tipo_appoggio==TipoComponente.PSU) {
				continue;
			}
			for(Componente comp_appoggio : componenti.get(tipo_appoggio)) {
				potenza_richiesta += comp_appoggio.getPotenza();
			}
		}
		return potenza_richiesta;
	}

	public boolean controllaPotenza() {
		//		int potenza_disponibile = componenti.get(TipoComponente.PSU).get(0).getPotenza();
		//		int potenza_richiesta = 0;
		//
		//		for(TipoComponente tipo_appoggio : TipoComponente.values()) {
		//			if(tipo_appoggio==TipoComponente.MOBO || tipo_appoggio==TipoComponente.PSU) {
		//				continue;
		//			}
		//			for(Componente comp_appoggio : componenti.get(tipo_appoggio)) {
		//				potenza_richiesta += comp_appoggio.getPotenza();
		//			}
		//		}

		int potenza_disponibile = getPotenzaDisponibile();
		int potenza_richiesta = getPotenzaRichiesta();

		if(potenza_disponibile>=potenza_richiesta) {
			return true;
		}else {
			return false;
		}
	}

	public boolean controllaPotenza(Componente nuovoComponente) {
		//		int potenza_disponibile = componenti.get(TipoComponente.PSU).get(0).getPotenza();
		//		int potenza_richiesta = nuovoComponente.getPotenza();
		//
		//		for(TipoComponente tipo_appoggio : TipoComponente.values()) {
		//			if(tipo_appoggio==TipoComponente.MOBO || tipo_appoggio==TipoComponente.PSU) {
		//				continue;
		//			}
		//			for(Componente comp_appoggio : componenti.get(tipo_appoggio)) {
		//				potenza_richiesta += comp_appoggio.getPotenza();
		//			}
		//		}

		int potenza_disponibile = getPotenzaDisponibile();
		int potenza_richiesta = getPotenzaRichiesta() + nuovoComponente.getPotenza();

		if(potenza_disponibile>=potenza_richiesta) {
			return true;
		}else {
			return false;
		}
	}

	public String getInfoProdotto() {
		StringBuilder info = new StringBuilder();
		info.append("Build: "+getNome()+" ");
		if(componenti!=null) {
			for(Map.Entry<TipoComponente, ArrayList<Componente>> entry_mappa : componenti.entrySet() ){
				TipoComponente tipo = entry_mappa.getKey();
				ArrayList<Componente> lista_componenti = entry_mappa.getValue();
				if(lista_componenti!=null && !lista_componenti.isEmpty()) {
					info.append("\n[ ").append(tipo.name()).append(" ]\n");
					for(Componente comp : lista_componenti) {
						info.append(comp.getInfoProdotto()+" -- ");
					}
				}
			}
		}else {
			info.append("nessun componente presente");
		}
		return info.toString();
	}
}
