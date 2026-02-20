package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;

public class Post extends ContenutoUtente {

	private String titolo; 
	private String sottotitolo;
	private ArrayList<Commento> commenti;



	public Post(String titolo, String sottotitolo, ArrayList<Commento> commenti) {
		super();
		this.titolo = titolo;
		this.sottotitolo = sottotitolo;
		this.commenti = commenti;
	}


	public Post(Utente autore, String testo, LocalDateTime data_pubblicazione, String titolo, String sottotitolo,
			ArrayList<Commento> commenti) {
		super(autore, testo, data_pubblicazione);
		this.titolo = titolo;
		this.sottotitolo = sottotitolo;
		this.commenti = commenti;
	}

	public Post(Utente autore, String testo, LocalDateTime data_pubblicazione, String titolo, String sottotitolo) {
		super(autore, testo, data_pubblicazione);
		this.titolo = titolo;
		this.sottotitolo = sottotitolo;
	}


	public Post(String id_contenuto_utente, Utente autore, String testo, LocalDateTime data_pubblicazione, String titolo,
			String sottotitolo, ArrayList<Commento> commenti) {
		super(id_contenuto_utente, autore, testo, data_pubblicazione);
		this.titolo = titolo;
		this.sottotitolo = sottotitolo;
		this.commenti = commenti;
	}

	public Post(String id_contenuto_utente, Utente autore, String testo, LocalDateTime data_pubblicazione, String titolo,
			String sottotitolo) {
		super(id_contenuto_utente, autore, testo, data_pubblicazione);
		this.titolo = titolo;
		this.sottotitolo = sottotitolo;
	}







	public String getTitolo() {
		return titolo;
	}








	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}








	public String getSottotitolo() {
		return sottotitolo;
	}








	public void setSottotitolo(String sottotitolo) {
		this.sottotitolo = sottotitolo;
	}








	public ArrayList<Commento> getCommenti() {
		return commenti;
	}








	public void setCommenti(ArrayList<Commento> commenti) {
		this.commenti = commenti;
	}



	public void aggiungiCommento(Commento c) {

		if (c == null) {
			throw new IllegalArgumentException("Il commento non può essere nullo");
		}

		this.commenti.add(c);

	}






}
