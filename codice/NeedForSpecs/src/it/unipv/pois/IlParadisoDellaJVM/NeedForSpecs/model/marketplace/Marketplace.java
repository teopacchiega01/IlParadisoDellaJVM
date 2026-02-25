package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.IAnnuncioDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.IOrdineDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.Ordine;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Build;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.IProdottoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.ProdottiFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;


/**
 * La classe {@code Marketplace} funge da controller centrale (Facade) per la gestione dell'intero
 * sistema di compravendita e configurazione. Implementa il pattern Singleton.
 * Coordina le operazioni tra prodotti, annunci, utenti, ordini e i rispettivi DAO (Data Access Object).
 * * @author teopacchiega
 */
public class Marketplace {
	/**
	 * L'unica istanza statica della classe, usata per implementare il pattern Singleton.
	 */
	private static Marketplace instance;
	private IProdottoDAO prod_dao;
	private IAnnuncioDAO ann_dao;
	private IOrdineDAO ord_dao;
	private ArrayList<Prodotto> prodotti;
	private ArrayList<Annuncio> annunci;
	private Utente utente_loggato;
	private Build build_configuratore;

	/**
	 * Costruttore privato completo.
	 * * @param prodotti Lista iniziale di prodotti.
	 * @param annunci Lista iniziale di annunci.
	 * @param utente_loggato L'utente attualmente in sessione.
	 */
	private Marketplace(ArrayList<Prodotto> prodotti, ArrayList<Annuncio> annunci, Utente utente_loggato) {
		super();
		this.prodotti = prodotti;
		this.annunci = annunci;
		this.utente_loggato = utente_loggato;
		DAOFactory factory = DAOFactory.getInstance();
		this.prod_dao = factory.getProdottoDAO();
		this.ann_dao = factory.getAnnuncioDAO();
		this.ord_dao = factory.getOrdineDAO();
		this.build_configuratore = (Build)creaBuild();
	}

	/**
	 * Costruttore privato che inizializza il marketplace assegnandogli solo l'utente loggato 
	 * e istanziando liste vuote per prodotti e annunci.
	 * * @param utente_loggato L'utente attualmente in sessione.
	 */
	private Marketplace(Utente utente_loggato) {
		super();
		this.utente_loggato = utente_loggato;
		this.prodotti = new ArrayList<Prodotto>();
		this.annunci = new ArrayList<Annuncio>();
		DAOFactory factory = DAOFactory.getInstance();
		this.prod_dao = factory.getProdottoDAO();
		this.ann_dao = factory.getAnnuncioDAO();
		this.ord_dao = factory.getOrdineDAO();
		this.build_configuratore = (Build)creaBuild();
	}
	
	/**
	 * Costruttore privato di default utilizzato per l'inizializzazione del Singleton.
	 */
	private Marketplace() {
		super();
		DAOFactory factory = DAOFactory.getInstance();
		this.prod_dao = factory.getProdottoDAO();
		this.ann_dao = factory.getAnnuncioDAO();
		this.ord_dao = factory.getOrdineDAO();
		this.build_configuratore = (Build)creaBuild();
	}

	/**
	 * Restituisce l'istanza Singleton di {@code Marketplace}. Se non esiste, la crea.
	 * * @return L'istanza univoca di {@code Marketplace}.
	 */
	public static Marketplace getInstance() {
		if(instance == null) {
			instance = new Marketplace();
			System.out.println("Istanza del Singleton di Marketplace ottenuta");
		}
		return instance;
	}

	/**
	 * Crea un nuovo componente tramite la factory e lo aggiunge alla lista dei prodotti del marketplace.
	 * * @param prezzo Il prezzo di listino del componente.
	 * @param marca La marca produttrice.
	 * @param modello Il nome del modello.
	 * @param tipo La categoria hardware (es. CPU, RAM) tramite {@link TipoComponente}.
	 * @param valori_specifiche Le specifiche tecniche sotto forma di lista di stringhe.
	 * @param potenza La potenza richiesta o erogata in Watt.
	 * @return Il {@link Prodotto} appena creato e aggiunto.
	 */
	public Prodotto aggiungiComponente(double prezzo, String marca, String modello, TipoComponente tipo,
			ArrayList<String> valori_specifiche, int potenza) {
		Prodotto nuovo_componente = ProdottiFactory.creaComponente(prezzo, marca, modello, tipo, valori_specifiche, potenza);
		prodotti.add(nuovo_componente);
		return nuovo_componente;
	}

	/**
	 * Crea una nuova Build con nome e la aggiunge alla lista dei prodotti del marketplace.
	 * * @param nome Il nome da assegnare alla nuova Build.
	 * @return L'istanza di {@link Prodotto} (di tipo Build) appena creata.
	 */
	public Prodotto aggiungiBuild(String nome) {
		Prodotto nuova_build = ProdottiFactory.creaBuild(nome);
		prodotti.add(nuova_build);
		return nuova_build;
	}
	
	/**
	 * Crea un'istanza vuota di una Build tramite la factory senza aggiungerla direttamente alle liste.
	 * * @return Una nuova {@link Build} vuota castata a {@link Prodotto}.
	 */
	public Prodotto creaBuild() {
		return ProdottiFactory.creaBuild();
	}

	/**
	 * Tenta di aggiungere un componente hardware a una specifica configurazione (Build).
	 * * @param build_da_aggiornare La {@link Build} a cui aggiungere il pezzo.
	 * @param componente_da_aggiungere Il {@link Componente} da inserire.
	 * @return {@code true} se l'aggiunta ha successo.
	 * @throws ComponentiException Se ci sono problemi di compatibilità o vincoli violati.
	 */
	public boolean aggiungiComponenteABuild(Build build_da_aggiornare, Componente componente_da_aggiungere) throws ComponentiException {
		boolean operazione_riuscita = false;

		operazione_riuscita = build_da_aggiornare.aggiungiComponente(componente_da_aggiungere);

		if(operazione_riuscita) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Crea un nuovo annuncio di vendita per un prodotto e lo aggiunge alla lista in memoria.
	 * * @param prodotto_in_vendita Il {@link Prodotto} oggetto dell'annuncio.
	 * @param venditore L'{@link UtenteGenerico} che mette in vendita il prodotto.
	 * @param prezzo Il prezzo di vendita stabilito per l'annuncio.
	 * @return {@code true} se l'annuncio viene aggiunto con successo.
	 */
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

	/**
	 * Rimuove un prodotto dal catalogo in memoria del marketplace cercando il suo ID.
	 * * @param id_prodotto_da_rimuovere L'ID univoco del prodotto da eliminare.
	 * @return {@code true} se il prodotto viene trovato e rimosso, {@code false} altrimenti.
	 */
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

	/**
	 * Rimuove un annuncio sia dalla lista in memoria del marketplace che dal database (tramite DAO),
	 * cercando la corrispondenza tramite l'ID testuale.
	 * * @param id_annuncio_da_rimuovere L'ID univoco dell'annuncio da eliminare.
	 * @return {@code true} se l'annuncio viene trovato e rimosso con successo.
	 */
	public boolean rimuoviAnnuncio(String id_annuncio_da_rimuovere) {
		boolean operazione_riuscita = false;

		for(int i=0; i<annunci.size(); i++) {
			Annuncio annuncio_nella_lista = annunci.get(i);
			if(annuncio_nella_lista.getId_annuncio().equals(id_annuncio_da_rimuovere)) {
				annunci.remove(i);
				ann_dao.rimuoviAnnuncio(annuncio_nella_lista);
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
	
	/**
	 * Rimuove un annuncio specifico (passato come oggetto) dalla lista in memoria e dal database.
	 * * @param annuncio_da_rimuovere L'oggetto {@link Annuncio} da rimuovere.
	 * @return {@code true} se la rimozione ha successo sia in memoria che su DB.
	 */
	public boolean rimuoviAnnuncio(Annuncio annuncio_da_rimuovere) {
		if(annunci.remove(annuncio_da_rimuovere)) {
			ann_dao.rimuoviAnnuncio(annuncio_da_rimuovere);
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Restituisce una stringa formattata contenente la lista di tutti gli annunci attualmente presenti.
	 * * @return Una stringa riepilogativa degli annunci, o {@code null} se la lista è vuota.
	 */
	public String mostraAnnunci() {
		if(annunci.isEmpty()) {
			return null;
		}else {
			String ret = "=[]== Annunci presenti ==[]=\n";
			for(Annuncio ann_in_lista : annunci) {
				ret = ret + ann_in_lista.toString();
			}
			return ret;
		}

	}

	/**
	 * Restituisce una stringa formattata contenente la lista e le info di tutti i prodotti a catalogo.
	 * * @return Una stringa riepilogativa dei prodotti, o {@code null} se il catalogo è vuoto.
	 */
	public String mostraProdotti() {
		if(prodotti.isEmpty()) {
			return null;
		}else {
			String ret = "=[]== Prodotti presenti ==[]=\n";
			for(Prodotto prod_in_lista : prodotti) {
				ret = ret + prod_in_lista.getInfoProdotto();
			}
			return ret;
		}
	}

	/**
	 * Resetta l'attuale build in configurazione creandone una nuova vuota.
	 */
	public void resetBuild() {
		this.build_configuratore = (Build)creaBuild();
	}
	
	/**
	 * Salva la configurazione della build attuale (se contiene almeno 2 componenti) 
	 * aggiungendola alla lista dei prodotti del marketplace, per poi resettare il configuratore.
	 * * @return {@code true} se il salvataggio va a buon fine, {@code false} se mancano i requisiti minimi.
	 */
	public boolean salvaBuildConfiguratore() {
		if (build_configuratore != null && build_configuratore.getNumeroTotaleComponenti() >= 2) {
	        this.prodotti.add(build_configuratore);
	        resetBuild();
	        return true;
	    }
	    return false;
	}
	
	/**
	 * Aggiunge un annuncio al carrello personale di un utente loggato.
	 * * @param utente_loggato L'utente che esegue l'operazione.
	 * @param annuncio_da_acquistare L'{@link Annuncio} che l'utente vuole inserire nel carrello.
	 * @return {@code true} se l'operazione va a buon fine.
	 */
	public boolean aggiungiAlCarrello(UtenteGenerico utente_loggato, Annuncio annuncio_da_acquistare) {
		if(utente_loggato.aggiungiElementoAlCarrello(annuncio_da_acquistare)){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Rimuove un annuncio dal carrello personale dell'utente loggato.
	 * * @param utente_loggato L'utente che esegue l'operazione.
	 * @param annuncio_da_acquistare L'{@link Annuncio} da togliere dal carrello.
	 * @return {@code true} se la rimozione ha successo.
	 */
	public boolean rimuoviElementoDalCarrello(UtenteGenerico utente_loggato, Annuncio annuncio_da_acquistare) {
		if(utente_loggato.getCarr().eliminaElementoDalCarrello(annuncio_da_acquistare)){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Finalizza l'acquisto processando gli elementi presenti nel carrello dell'utente.
	 * Crea un nuovo {@link Ordine}, svuota il carrello e salva l'ordine nel database tramite DAO.
	 * * @param acquirente L'utente che sta completando l'acquisto.
	 * @return {@code true} se l'ordine viene creato e salvato con successo, {@code false} se 
	 * mancano requisiti fondamentali (es. carrello vuoto, metodo di pagamento o indirizzo mancanti).
	 */
	//TODO implementare degli errori specifici
	public boolean effettuaOrdine(UtenteGenerico acquirente) {
		if(acquirente.getCarr().getAcquisti().isEmpty() || (acquirente.getMetodo_di_pagamento()==null) || (acquirente.getInd_di_spedizione()==null)) {
			return false;
		}else {
			Ordine nuovo_ordine = new Ordine(acquirente);
			acquirente.getCarr().eliminaCarrello();
			if(ord_dao.inserisciOrdine(nuovo_ordine)) {
				return true;
			}else {
				return false;
			}
		}
	}


	/**
	 * Carica i dati iniziali del marketplace popolando le liste in memoria 
	 * interrogando il database tramite i rispettivi DAO.
	 */
	public void inizializzaMarketplace() {
		setProdotti(prod_dao.getProdotti());
		setAnnunci(ann_dao.getAnnunci());
	}

	public IProdottoDAO getProd_dao() {
		return prod_dao;
	}

	public IAnnuncioDAO getAnn_dao() {
		return ann_dao;
	}

	public ArrayList<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(ArrayList<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}

	public ArrayList<Annuncio> getAnnunci() {
		return annunci;
	}

	public void setAnnunci(ArrayList<Annuncio> annunci) {
		this.annunci = annunci;
	}

	public IOrdineDAO getOrd_dao() {
		return ord_dao;
	}

	public void setOrd_dao(IOrdineDAO ord_dao) {
		this.ord_dao = ord_dao;
	}

	public void setProd_dao(IProdottoDAO prod_dao) {
		this.prod_dao = prod_dao;
	}

	public void setAnn_dao(IAnnuncioDAO ann_dao) {
		this.ann_dao = ann_dao;
	}

	public Utente getUtente_loggato() {
		return utente_loggato;
	}

	public void setUtente_loggato(Utente utente_loggato) {
		this.utente_loggato = utente_loggato;
	}

	public Build getBuild_configuratore() {
		return build_configuratore;
	}

	public void setBuild_configuratore(Build build_configuratore) {
		this.build_configuratore = build_configuratore;
	}

}
