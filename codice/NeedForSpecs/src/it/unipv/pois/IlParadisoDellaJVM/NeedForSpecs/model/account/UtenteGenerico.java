package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

import java.time.LocalDate;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi.Indirizzo;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento.Carta;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;

public class UtenteGenerico extends Utente {
	
	private Carrello carrello;
	private Indirizzo ind_di_spedizione;
	private Carta metodo_di_pagamento;
	
	public UtenteGenerico() {
		super();
		this.carrello = new Carrello();
	}

	public UtenteGenerico(String nome_utente, String email, String psw, String nome, String cognome) {
		super(nome_utente, email, psw, nome, cognome);
		this.carrello = new Carrello();
	}

	public UtenteGenerico(String nome_utente, String email, String psw, String nome, String cognome,
			Carrello c, Indirizzo ind_di_spedizione, Carta metodo_di_pagamento) {
		super(nome_utente, email, psw, nome, cognome);
		this.carrello = c;
		this.ind_di_spedizione = ind_di_spedizione;
		this.metodo_di_pagamento = metodo_di_pagamento;
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
	
	public Carta creaCarta(LocalDate data_scadenza, String cvv) {
		return new Carta(data_scadenza, cvv);
	}
	
	public Indirizzo creaIndirizzo(String via, String civico, String cap, String provincia, String citta) {
		return new Indirizzo(via, civico, cap, provincia, citta);
	}
	
	@Override
	public boolean isStaff() {
		return false;
	}

	public void caricaDatiAggiuntivi(DAOFactory factory) {
		Carta cartaTrovata = factory.getCartaDAO().getCarta(this);
		Indirizzo indirizzoTrovato = factory.getIndirizzoDAO().getIndirizzo(this);

		this.setMetodo_di_pagamento(cartaTrovata);
		this.setInd_di_spedizione(indirizzoTrovato);
	}
}