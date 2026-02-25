package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException.TipoErrore;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.AspettiTecnici;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.GeneratoreId;

/**
 * La classe {@code Build} rappresenta un computer assemblato (o in via di assemblaggio),
 * composto da una collezione di {@link Componente} raggruppati per {@link TipoComponente}.
 * Estende la classe astratta {@link Prodotto}.
 * * Gestisce la logica di inserimento, rimozione e verifica di compatibilità 
 * fisica ed energetica tra i vari pezzi hardware scelti dall'utente.
 * * @author teopacchiega
 */
public class Build extends Prodotto{
	/**
	 * Definisce la lunghezza massima del nome da utilizzare per la generazione dell'ID.
	 */
	private final static int DIM_NOME_ID = 15;
	/**
	 * Il nome assegnato alla build dall'utente.
	 */
	private String nome;
	/**
	 * Mappa che contiene le liste dei componenti attualmente inseriti nella build, 
	 * suddivisi per la loro categoria hardware (CPU, MOBO, RAM, ecc.).
	 */
	private EnumMap<TipoComponente, ArrayList<Componente>> componenti;

	/**
	 * Costruttore che inizializza una build completa con prezzo, nome e mappa dei componenti pre-esistente.
	 * * @param prezzo Il prezzo totale della build.
	 * @param nome Il nome identificativo della build.
	 * @param componenti La mappa contenente le liste dei componenti suddivisi per tipo.
	 */
	public Build(double prezzo, String nome,
			EnumMap<TipoComponente, ArrayList<Componente>> componenti) {
		super(prezzo);
		this.nome = nome;
		this.componenti = componenti;
	}
	/**
	 * Costruttore che inizializza una build vuota, pronta per l'aggiunta di componenti.
	 * Crea le strutture dati interne per ogni possibile {@link TipoComponente} e genera l'ID.
	 * * @param prezzo Il prezzo iniziale della build (solitamente 0).
	 * @param nome Il nome identificativo della build.
	 */
	public Build(double prezzo, String nome) {
		super(prezzo);
		this.nome = nome;
		this.componenti = new EnumMap<>(TipoComponente.class);
		for(TipoComponente appoggio : TipoComponente.values()) {
			componenti.put(appoggio, new ArrayList<Componente>());
		}

		this.setId_prodotto(generaId());
	}

	/**
	 * Costruttore di default vuoto. Richiama il costruttore della superclasse {@link Prodotto}.
	 */
	public Build() {
		super();
	}

	/**
	 * Costruttore che inizializza una build assegnandogli solo il nome.
	 * * @param nome Il nome della build.
	 */
	public Build(String nome) {
		super();
		this.nome = nome;
	}
	
	/**
	 * Genera un ID univoco per la build, combinando il nome formattato e una stringa casuale.
	 * * @return La stringa rappresentante l'ID univoco della build.
	 */
	@Override
	protected String generaId() {
		String nome = formattazione(this.nome, DIM_NOME_ID);
		String extra = GeneratoreId.generaId(getDimIdProdotto()-DIM_NOME_ID);
		String id = nome+extra;
		return id;
	}

	/**
	 * Restituisce la tipologia del prodotto.
	 * * @return {@link TipologiaProdotto#BUILD}
	 */
	@Override
	public TipologiaProdotto getTipologia() {
		return TipologiaProdotto.BUILD;
	}
	
	/**
	 * Restituisce il nome della build.
	 * * @return Il nome attuale della build.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Imposta il nome della build.
	 * * @param nome Il nuovo nome da assegnare.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Restituisce la mappa completa dei componenti inseriti nella build.
	 * * @return Una {@link EnumMap} contenente le liste dei componenti divisi per {@link TipoComponente}.
	 */
	public EnumMap<TipoComponente, ArrayList<Componente>> getComponenti() {
		return componenti;
	}

	/**
	 * Sostituisce l'attuale mappa dei componenti con una nuova.
	 * * @param componenti La nuova {@link EnumMap} di componenti da assegnare alla build.
	 */
	public void setComponenti(EnumMap<TipoComponente, ArrayList<Componente>> componenti) {
		this.componenti = componenti;
	}

	/**
	 * Tenta di aggiungere un nuovo componente alla build, eseguendo rigorosi controlli di 
	 * compatibilità hardware e logica di inserimento (es. obbligo di inserire prima la MOBO, 
	 * controllo degli slot disponibili e dei socket).
	 * * @param nuovo_componente L'oggetto {@link Componente} che si desidera aggiungere.
	 * @return {@code true} se l'aggiunta ha successo, {@code false} in caso di componente non gestito dal costrutto switch.
	 * @throws ComponentiException Se viene violato un vincolo fisico o logico 
	 * (es. MOBO mancante, slot esauriti, socket incompatibile, componenti singoli multipli).
	 */
	public boolean aggiungiComponente(Componente nuovo_componente) throws ComponentiException {
		TipoComponente tipo_nuovo_componente = nuovo_componente.getTipo();
		ArrayList<Componente> componenti_presenti = componenti.get(tipo_nuovo_componente);

		//	Prima di tutto controllo se l'utente sta cercando di aggiungere una scheda madre
		if(tipo_nuovo_componente==TipoComponente.MOBO) {
			//	Se non c'è nessuna mobo, la può aggiungere
			if(componenti_presenti.isEmpty()) {
				componenti_presenti.add(nuovo_componente);
				componenti.put(tipo_nuovo_componente, componenti_presenti);
				aggiornaPrezzo();
				return true;
			}
			//	Se c'è già una mobo, verrà lanciato un errore
			else {
				throw new ComponentiException("Schede madri multiple rilevate", TipoErrore.SCHEDE_MADRI_MULTIPLE);
			}	
		}
		//	Se l'utente cerca di aggiungere un componente senza aver selezionato una mobo, verrà lanciato un errore
		else if((tipo_nuovo_componente!=TipoComponente.MOBO) && componenti.get(TipoComponente.MOBO).isEmpty()) {
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
			switch (tipo_nuovo_componente) {
			case CPU:
				if(componenti_presenti.isEmpty()) {
					if(verificaCompatibilità(componenti.get(TipoComponente.MOBO).get(0), nuovo_componente)) {
						componenti_presenti.add(nuovo_componente);
						componenti.put(tipo_nuovo_componente, componenti_presenti);
						aggiornaPrezzo();
						return true;
					}else {
						throw new ComponentiException("Socket CPU e scheda madre incompatibili", TipoErrore.SOCKET_CPU_INCOMPATIBILE);
					}
				}else {
					throw new ComponentiException("CPU multiple rilevate", TipoErrore.CPU_MULTIPLE);
				}
			case RAM:
				if(verificaCompatibilità(componenti.get(TipoComponente.MOBO).get(0), nuovo_componente)) {
					// Controllo se ci sono slot RAM liberi
					int slot_ram = Integer.parseInt(componenti.get(TipoComponente.MOBO).get(0).getScheda_tecnica().get(AspettiTecnici.N_MODULI_RAM));
					int n_stick_ram_da_aggiungere = Integer.parseInt(nuovo_componente.getScheda_tecnica().get(AspettiTecnici.N_MODULI_RAM));

					int n_stick_ram_presenti = 0;
					for(Componente appoggio : componenti.get(TipoComponente.RAM)) {
						n_stick_ram_presenti += Integer.parseInt(appoggio.getScheda_tecnica().get(AspettiTecnici.N_MODULI_RAM));
					}

					int tot_stick_ram = n_stick_ram_da_aggiungere + n_stick_ram_presenti;

					if(tot_stick_ram<=slot_ram) {
						componenti_presenti.add(nuovo_componente);
						componenti.put(tipo_nuovo_componente, componenti_presenti);
						aggiornaPrezzo();
						return true;
					}else {
						throw new ComponentiException("Slot RAM insufficienti per l'aggiunta della RAM richiesta", TipoErrore.SLOT_RAM_INSUFFICIENTI);
					}
				}else {
					throw new ComponentiException("Tipo RAM incompatibile con la scheda madre", TipoErrore.TIPO_RAM_INCOMPATIBILE);
				}
			case GPU:
				if(verificaCompatibilità(componenti.get(TipoComponente.MOBO).get(0), nuovo_componente)) {
					// Controllo se ci sono slot PCIe liberi
					int slot_pcie = Integer.parseInt(componenti.get(TipoComponente.MOBO).get(0).getScheda_tecnica().get(AspettiTecnici.N_SLOT_PCIE));
					int n_slot_pcie_da_aggiungere = Integer.parseInt(nuovo_componente.getScheda_tecnica().get(AspettiTecnici.N_SLOT_PCIE));


					int n_slot_pcie_occupati = 0;
					for(Componente appoggio : componenti.get(TipoComponente.GPU)) {
						n_slot_pcie_occupati += Integer.parseInt(appoggio.getScheda_tecnica().get(AspettiTecnici.N_SLOT_PCIE));
					}

					int tot_slot_pcie_occupati = n_slot_pcie_occupati + n_slot_pcie_da_aggiungere;

					if(tot_slot_pcie_occupati<slot_pcie) {
						componenti_presenti.add(nuovo_componente);
						componenti.put(tipo_nuovo_componente, componenti_presenti);
						aggiornaPrezzo();
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
					componenti.get(TipoComponente.PSU).add(nuovo_componente);
					aggiornaPrezzo();
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

	/**
	 * Rimuove tutti i componenti appartenenti a una specifica categoria, tranne la scheda madre.
	 * Aggiorna automaticamente il prezzo totale della build.
	 * * @param componente_da_rimuovere Il {@link TipoComponente} di cui svuotare la lista.
	 * @return {@code true} se la rimozione ha successo, {@code false} se si tenta di rimuovere la categoria MOBO in questo modo.
	 */
	public boolean rimuoviTipoDiComponenti(TipoComponente componente_da_rimuovere) {
		if(!componente_da_rimuovere.equals(TipoComponente.MOBO)) {
			componenti.put(componente_da_rimuovere, new ArrayList<Componente>());
			aggiornaPrezzo();
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Rimuove un singolo componente specifico dalla build.
	 * Gestisce la logica di dipendenza della scheda madre, impedendone la rimozione 
	 * se sono presenti altri componenti agganciati ad essa.
	 * Aggiorna automaticamente il prezzo totale.
	 * * @param componente_da_rimuovere L'istanza di {@link Componente} esatta da rimuovere.
	 * @return {@code true} se la rimozione ha successo, {@code false} se il componente è null, non presente o se si tenta di rimuovere la MOBO con altri pezzi presenti.
	 */
	public boolean rimuoviComponente(Componente componente_da_rimuovere) {
		if (componente_da_rimuovere == null) return false;

		ArrayList<Componente> componenti_del_tipo = componenti.get(componente_da_rimuovere.getTipo());

		if (componenti_del_tipo == null) {
			System.err.println("Impossibile rimuovere: categoria inesistente.");
			return false;
		}

		if (componente_da_rimuovere.getTipo() == TipoComponente.MOBO) { 
			boolean altri_componenti_presenti = false;

			for (Map.Entry<TipoComponente, ArrayList<Componente>> entry : componenti.entrySet()) {
				if (entry.getKey() != TipoComponente.MOBO) {
					if (!entry.getValue().isEmpty()) {
						aggiornaPrezzo();
						altri_componenti_presenti = true;
						break; 
					}
				}
			}

			if (altri_componenti_presenti) {
				System.out.println("Impossibile rimuovere la scheda madre quando altre componenti sono presenti");
				return false;
			} else {
				componenti_del_tipo.remove(componente_da_rimuovere);
				System.out.println("Componente rimossa (MOBO)");
				aggiornaPrezzo();
				return true;
			}

		} else {
			if (componenti_del_tipo.remove(componente_da_rimuovere)) {
				System.out.println("Componente rimossa");
				aggiornaPrezzo();
				return true;
			} else {
				System.err.println("Impossibile rimuovere componente (non trovato nella lista)");
				return false;
			}
		}
	}

	/**
	 * Metodo di supporto che verifica la compatibilità fisica e tecnica tra due componenti.
	 * Controlla in particolare il Socket tra CPU e MOBO, e l'interfaccia/frequenza/slot tra MOBO e RAM/GPU.
	 * * @param c1 Il primo {@link Componente} da confrontare.
	 * @param c2 Il secondo {@link Componente} da confrontare.
	 * @return {@code true} se i due componenti sono compatibili.
	 * @throws ComponentiException Se i componenti risultano incompatibili a livello hardware.
	 */
	private boolean verificaCompatibilità(Componente c1, Componente c2) throws ComponentiException{

		//	Controllo se uno dei due componenti è una scheda madre
		if(c1.getTipo()==TipoComponente.MOBO || c2.getTipo()==TipoComponente.MOBO){
			TipoComponente comp = null;
			EnumMap<AspettiTecnici, String> st_mobo = null;
			EnumMap<AspettiTecnici, String> st_comp = null;
			if(c1.getTipo()==TipoComponente.MOBO){
				comp = c2.getTipo();
				st_mobo = c1.getScheda_tecnica();
				st_comp = c2.getScheda_tecnica();
			}else if(c2.getTipo()==TipoComponente.MOBO){
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

	/**
	 * Ricalcola il prezzo totale della build sommando i prezzi di tutti i singoli componenti attualmente inseriti.
	 * Imposta il risultato sull'attributo prezzo della classe madre.
	 */
	public void aggiornaPrezzo() {
		double totale = 0;
		for (ArrayList<Componente> lista_di_un_tipo : componenti.values()) {
			for (Componente singolo_pezzo : lista_di_un_tipo) {
				totale += singolo_pezzo.getPrezzo();
			}
		}
		this.setPrezzo(totale);
	}
	
	/**
	 * Calcola il numero totale di pezzi hardware fisicamente presenti all'interno della build.
	 * * @return Un intero rappresentante il conteggio totale dei componenti.
	 */
	public int getNumeroTotaleComponenti() {
	    int n_componenti = 0;
	    for (ArrayList<Componente> lista : componenti.values()) {
	    	n_componenti += lista.size();
	    }
	    return n_componenti;
	}

	/**
	 * Restituisce la potenza erogabile dall'alimentatore (PSU) attualmente inserito nella build.
	 * * @return La potenza disponibile in Watt. Presuppone che esista già un PSU.
	 */
	public int getPotenzaDisponibile() {
		return componenti.get(TipoComponente.PSU).get(0).getPotenza();
	}

	/**
	 * Calcola il consumo energetico totale stimato (TDP) sommando la potenza richiesta
	 * da tutti i componenti della build, escludendo l'alimentatore.
	 * * @return La potenza totale richiesta in Watt.
	 */
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

	/**
	 * Verifica se la potenza erogata dall'alimentatore è sufficiente a coprire 
	 * i consumi dell'intera configurazione attuale.
	 * * @return {@code true} se la potenza disponibile è maggiore o uguale a quella richiesta, {@code false} altrimenti.
	 */
	public boolean controllaPotenza() {
		int potenza_disponibile = getPotenzaDisponibile();
		int potenza_richiesta = getPotenzaRichiesta();

		if(potenza_disponibile>=potenza_richiesta) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Simula l'aggiunta di un nuovo componente e verifica se la potenza dell'alimentatore 
	 * sarebbe ancora sufficiente a reggere il carico aggiuntivo.
	 * * @param nuovoComponente Il componente ipotetico di cui sommare il consumo.
	 * @return {@code true} se la potenza disponibile reggerebbe il nuovo componente, {@code false} altrimenti.
	 */
	public boolean controllaPotenza(Componente nuovoComponente) {

		int potenza_disponibile = getPotenzaDisponibile();
		int potenza_richiesta = getPotenzaRichiesta() + nuovoComponente.getPotenza();

		if(potenza_disponibile>=potenza_richiesta) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Genera una stringa formattata contenente il nome della build e un riepilogo 
	 * dettagliato di tutti i componenti attualmente inseriti, raggruppati per categoria.
	 * * @return Una {@link String} multilinea con le informazioni di riepilogo della build.
	 */
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
