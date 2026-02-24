package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;

/**
 * Rappresenta un Post scritto da un Utente all'interno del Forum.
 * Estende ContenutoUtente aggiungendo attributi specifici come titolo, sottotitolo 
 * e la lista dei commenti associati.
 * * @author teomiraldi
 * @version 1.0
 */
public class Post extends ContenutoUtente {

	private String titolo; 
	private String sottotitolo;

	private ArrayList<Commento> commenti = new ArrayList<>();

	public Post(Utente autore, String testo, LocalDateTime data_pubblicazione, String titolo, String sottotitolo) {
		super(autore, testo, data_pubblicazione);
		this.titolo = titolo;
		this.sottotitolo = sottotitolo;
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

	/**
	 * Aggiunge un commento alla lista interna dei commenti del post.
	 * * @param c Il Commento da aggiungere.
	 * @throws IllegalArgumentException Se il commento passato è nullo.
	 */
	public void aggiungiCommento(Commento c) {
		if (c == null) {
			throw new IllegalArgumentException("Il commento non può essere nullo");
		}
		this.commenti.add(c);
	}

	/**
	 * Metodo di utilità per l'interfaccia grafica. 
	 * Restituisce il sottotitolo in modo sicuro per evitare NullPointerException nella View.
	 * * @return Il sottotitolo se presente, oppure una stringa vuota se nullo.
	 */
	public String getSottotitoloSicuro() {
		if (this.sottotitolo == null) {
			return "";
		} else {
			return this.sottotitolo;
		}
	}

}