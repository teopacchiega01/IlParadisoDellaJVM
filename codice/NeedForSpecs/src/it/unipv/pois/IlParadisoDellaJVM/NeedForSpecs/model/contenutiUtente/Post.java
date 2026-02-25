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

	/**
	 * Costruttore per la creazione di un nuovo Post (l'ID verrà generato successivamente).
	 * * @param autore L'Utente che crea il post.
	 * @param testo Il contenuto testuale del post.
	 * @param data_pubblicazione La data e l'ora di pubblicazione.
	 * @param titolo Il titolo del post.
	 * @param sottotitolo Il sottotitolo opzionale del post.
	 */
	public Post(Utente autore, String testo, LocalDateTime data_pubblicazione, String titolo, String sottotitolo) {
		super(autore, testo, data_pubblicazione);
		this.titolo = titolo;
		this.sottotitolo = sottotitolo;
	}

	/**
	 * Costruttore per il caricamento di un Post esistente dal database.
	 * * @param id_contenuto_utente L'identificativo univoco del contenuto.
	 * @param autore L'Utente autore del post.
	 * @param testo Il contenuto testuale del post.
	 * @param data_pubblicazione La data e l'ora di pubblicazione.
	 * @param titolo Il titolo del post.
	 * @param sottotitolo Il sottotitolo opzionale del post.
	 */
	public Post(String id_contenuto_utente, Utente autore, String testo, LocalDateTime data_pubblicazione, String titolo,
			String sottotitolo) {
		super(id_contenuto_utente, autore, testo, data_pubblicazione);
		this.titolo = titolo;
		this.sottotitolo = sottotitolo;
	}

	/**
	 * Restituisce il titolo del post.
	 * * @return Il titolo del post.
	 */
	public String getTitolo() {
		return titolo;
	}

	/**
	 * Imposta un nuovo titolo per il post.
	 * * @param titolo Il titolo da impostare.
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	/**
	 * Restituisce il sottotitolo del post.
	 * * @return Il sottotitolo del post.
	 */
	public String getSottotitolo() {
		return sottotitolo;
	}

	/**
	 * Imposta un nuovo sottotitolo per il post.
	 * * @param sottotitolo Il sottotitolo da impostare.
	 */
	public void setSottotitolo(String sottotitolo) {
		this.sottotitolo = sottotitolo;
	}

	/**
	 * Restituisce la lista di tutti i commenti associati al post.
	 * * @return Un ArrayList di oggetti Commento associati al post.
	 */
	public ArrayList<Commento> getCommenti() {
		return commenti;
	}

	/**
	 * Sostituisce la lista attuale di commenti con una nuova lista.
	 * * @param commenti L'ArrayList di Commento da associare al post.
	 */
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
	 * * @return Il sottotitolo se presente, oppure una stringa vuota ("") se nullo.
	 */
	public String getSottotitoloSicuro() {
		if (this.sottotitolo == null) {
			return "";
		} else {
			return this.sottotitolo;
		}
	}
}