package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

import java.time.LocalDate;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.IIndirizzoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.Indirizzo;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.Carta;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.ICartaDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;


/**
 * @author Persy
 */
public class GestoreAccount {

	private static GestoreAccount instance;
	private Utente utente_loggato;
	private IUtenteDAO utente_dao;
	private IIndirizzoDAO indirizzo_dao;
	private ICartaDAO carta_dao;

	private GestoreAccount() {
		this.utente_loggato = null; 
		DAOFactory factory = DAOFactory.getInstance();
		this.utente_dao = factory.getUtenteDAO();
		this.indirizzo_dao = factory.getIndirizzoDAO();
		this.carta_dao = factory.getCartaDAO();
	}

	public static GestoreAccount getInstance() {
		if (instance == null) {
			instance = new GestoreAccount();
		}
		return instance;
	}

	public boolean registraUtenteGenerico(String nome, String cognome, String username, String email, String password,
			String via, String civico, String citta, String provincia, String cap, 
			String titolareCarta, LocalDate scadenzaCarta, String cvvCarta) {

		UtenteGenerico gen = new UtenteGenerico(username, email, password, nome, cognome);

		Carta c = gen.creaCarta(scadenzaCarta, cvvCarta);
		Indirizzo ind = gen.creaIndirizzo(via, civico, cap, provincia, citta);
		gen.setMetodo_di_pagamento(c);
		gen.setInd_di_spedizione(ind);

		if (ind != null  && c!= null) {
			indirizzo_dao.inserisciIndirizzo(ind);
			carta_dao.inserisciCarta(c);
		}
		
		return utente_dao.registrazioneUtente(gen); 
	}

	public boolean registraStaff(String nome, String cognome, String username, String email, String password) {
		UtenteStaff staff = new UtenteStaff(username, email, password, nome, cognome);
		return utente_dao.registrazioneUtente(staff);
	}
	
	public boolean loginUtente(String mail, String psw) {
		Utente utente_db = utente_dao.login(mail, psw);

		if (utente_db == null) {
			return false; 
		}
		
		if (!utente_db.isStaff()) {
			UtenteGenerico gen = (UtenteGenerico) utente_db;
			gen.caricaDatiAggiuntivi();
		}
		
		this.utente_loggato = utente_db;
		System.out.println(utente_loggato);
		return true;
	}
	
	public boolean aggiornaPagamentoUtenteGenerico(String nuovoNumero, LocalDate nuovaScadenza, String nuovoCvv) {
		if(this.utente_loggato.isStaff()) {
			System.out.println("Lo staff NON ha una carta");
			return false;
		}
		UtenteGenerico utente_gen = (UtenteGenerico) utente_loggato;
		boolean successo = utente_gen.modificaMetodoPagamento(nuovoNumero, nuovaScadenza, nuovoCvv);
		if(successo) {
			System.out.println(utente_loggato);
			return true;
		}
		return false;
	}

	public void logout() {
		this.utente_loggato = null;
	}

	public Utente getUtenteLoggato() {
		return utente_loggato;
	}

	public void setUtenteLoggato(Utente utenteLoggato) {
		this.utente_loggato = utenteLoggato;
	}
	
}