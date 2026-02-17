package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDate;
import java.time.LocalDateTime;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;

public abstract class ContenutoUtente {
		
	private Utente autore;
	private String testo;
	private LocalDateTime data_pubblicazione;

	public ContenutoUtente() {
		// TODO Auto-generated constructor stub
	}

	public ContenutoUtente(Utente autore, String testo, LocalDateTime data_pubblicazione) {
		super();
		this.autore = autore;
		this.testo = testo;
		this.data_pubblicazione = data_pubblicazione;
	}

	
	
	
	public Utente getAutore() {
		return autore;
	}

	public void setAutore(Utente autore) {
		this.autore = autore;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public LocalDateTime getData_pubblicazione() {
		return data_pubblicazione;
	}

	public void setData_pubblicazione(LocalDateTime data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
	}

	
	
	
}
