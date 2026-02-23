package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

import java.time.LocalDate;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.IIndirizzoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.Indirizzo;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.Carta;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.ICartaDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;

public class GestoreAccount {

	private static GestoreAccount instance;
	private Utente utenteLoggato;
	private IUtenteDAO utente_dao;
	private IIndirizzoDAO indirizzo_dao;
	private ICartaDAO carta_dao;

	private GestoreAccount() {
		this.utenteLoggato = null; 
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

		if (ind != null) {
			indirizzo_dao.inserisciIndirizzo(ind);
		}
		if (c != null) {
			carta_dao.inserisciCarta(c);
		}

		return utente_dao.registrazioneUtente(gen); 
	}

	public boolean registraStaff(String nome, String cognome, String username, String email, String password) {
		UtenteStaff staff = new UtenteStaff(username, email, password, nome, cognome);
		return utente_dao.registrazioneUtente(staff);
	}
	
	public boolean loginUtente(String mail, String psw) {
		Utente utenteDalDb = utente_dao.login(mail, psw);

		if (utenteDalDb == null) {
			return false; 
		}
		
		if (!utenteDalDb.isStaff()) {
			UtenteGenerico gen = (UtenteGenerico) utenteDalDb;
			gen.caricaDatiAggiuntivi(DAOFactory.getInstance());
		}
		
		this.utenteLoggato = utenteDalDb;
		return true;
	}

	public void logout() {
		this.utenteLoggato = null;
	}

	public Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public void setUtenteLoggato(Utente utenteLoggato) {
		this.utenteLoggato = utenteLoggato;
	}
	
}