package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.IIndirizzoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.Indirizzo;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.Carta;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.ICartaDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;

//	@Author teopacchiega

public class UtenteGenerico extends Utente {
	private Carrello carrello;
	private Indirizzo ind_di_spedizione;
	private Carta metodo_di_pagamento;
	private IIndirizzoDAO indirizzo_dao;
	private ICartaDAO carta_dao;
	
	
	public UtenteGenerico() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UtenteGenerico(String nome_utente, String email, String psw, String nome, String cognome,
			Carrello c, Indirizzo ind_di_spedizione, Carta metodo_di_pagamento) {
		super(nome_utente, email, psw, nome, cognome);
		this.carrello = c;
		this.ind_di_spedizione = ind_di_spedizione;
		this.metodo_di_pagamento = metodo_di_pagamento;
	}



	public UtenteGenerico(String nome_utente, String email, String psw, String nome, String cognome) {
		super(nome_utente, email, psw, nome, cognome);
		

	}
	
	
	
	

	public UtenteGenerico(String nome_utente, String email, String psw, String nome, String cognome, DAOFactory factory,
			Carrello carrello, Indirizzo ind_di_spedizione, Carta metodo_di_pagamento, IIndirizzoDAO indirizzo_dao,
			ICartaDAO carta_dao) {
		super(nome_utente, email, psw, nome, cognome, factory);
		this.carrello = carrello;
		this.ind_di_spedizione = ind_di_spedizione;
		this.metodo_di_pagamento = metodo_di_pagamento;
		this.indirizzo_dao = factory.getIndirizzoDAO();
		this.carta_dao = factory.getCartaDAO();
	}

	@Override
	public Utente login(String mail, String pw) {
		
		
		Utente utenteDalDb = super.login(mail, pw);
		if (utenteDalDb == null) {
			return null;
		}
		UtenteGenerico gen = (UtenteGenerico) utenteDalDb;
		Carta cartaTrovata = this.carta_dao.getCarta(gen);
		Indirizzo indirizzoTrovato = this.indirizzo_dao.getIndirizzo(gen);
		gen.setMetodo_di_pagamento(cartaTrovata);
		gen.setInd_di_spedizione(indirizzoTrovato);
		gen.carta_dao = this.carta_dao;
		gen.indirizzo_dao = this.indirizzo_dao;
		
		return gen;
	}

	@Override
	public boolean registrazione() {
		
		if (this.ind_di_spedizione != null) {
			indirizzo_dao.inserisciIndirizzo(this.ind_di_spedizione);
		}
		
		if (this.metodo_di_pagamento != null) {
			carta_dao.inserisciCarta(this.metodo_di_pagamento);
		}
		return super.registrazione();
	}

	public void resetCarrello() {
		carrello.eliminaCarrello();
	}

	public boolean aggiungiElementoAlCarrello(Annuncio elemento) {
		return carrello.aggiungiElementoAlCarrello(elemento);
	}
	
	public boolean eliminaElementoAlCarrello(Annuncio elemento) {
		return carrello.eliminaElementoDalCarrello(elemento);
	}

	public Carrello getCarr() {
		return carrello;
	}

	public void setCarr(Carrello c) {
		this.carrello = c;
	}

	public Indirizzo getInd_di_spedizione() {
		return ind_di_spedizione;
	}

	public void setInd_di_spedizione(Indirizzo ind_di_spedizione) {
		this.ind_di_spedizione = ind_di_spedizione;
	}

	public Carta getMetodo_di_pagamento() {
		return metodo_di_pagamento;
	}

	public void setMetodo_di_pagamento(Carta metodo_di_pagamento) {
		this.metodo_di_pagamento = metodo_di_pagamento;
	}

	@Override
	public boolean isStaff() {
		return false;
	}

}
