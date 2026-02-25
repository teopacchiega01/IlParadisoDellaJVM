package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDateTime;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;

/**
 * Rappresenta un Commento scritto da un Utente all'interno del Forum.
 * Estende ContenutoUtente e mantiene un riferimento al Post padre e 
 * all'eventuale commento genitore (nel caso di risposte a cascata).
 * * @author teomiraldi
 * @version 1.0
 */
public class Commento extends ContenutoUtente {

	private Post post_di_riferimento;
	private ContenutoUtente parent;

	/**
	 * Costruttore per caricare un commento esistente, completo di ID generato dal database.
	 * * @param id_contenuto_utente L'ID univoco del commento.
	 * @param autore L'Utente che ha scritto il commento.
	 * @param testo Il testo del commento.
	 * @param data_pubblicazione La data e l'ora in cui è stato pubblicato.
	 * @param post_di_riferimento Il Post a cui il commento appartiene.
	 * @param parent Il ContenutoUtente (Post o Commento) a cui questo commento risponde direttamente.
	 */
	public Commento(String id_contenuto_utente, Utente autore, String testo, LocalDateTime data_pubblicazione, Post post_di_riferimento,
			ContenutoUtente parent) {
		super(id_contenuto_utente, autore, testo, data_pubblicazione);
		this.post_di_riferimento = post_di_riferimento;
		this.parent = parent;
	}

	/**
	 * Costruttore per creare un nuovo commento (l'ID verrà generato successivamente).
	 * * @param autore L'Utente che ha scritto il commento.
	 * @param testo Il testo del commento.
	 * @param data_pubblicazione La data e l'ora in cui è stato pubblicato.
	 * @param post_di_riferimento Il Post a cui il commento appartiene.
	 * @param parent Il ContenutoUtente (Post o Commento) a cui questo commento risponde direttamente.
	 */
	public Commento(Utente autore, String testo, LocalDateTime data_pubblicazione, Post post_di_riferimento, ContenutoUtente parent) {
		super(autore, testo, data_pubblicazione);
		this.post_di_riferimento = post_di_riferimento;
		this.parent = parent;
	}

	/**
	 * Restituisce il post di riferimento di questo commento.
	 * * @return L'oggetto Post in cui si trova il commento.
	 */
	public Post getPost_di_riferimento() {
		return post_di_riferimento;
	}

	/**
	 * Imposta o modifica il post di riferimento.
	 * * @param post_di_riferimento Il Post da associare al commento.
	 */
	public void setPost_di_riferimento(Post post_di_riferimento) {
		this.post_di_riferimento = post_di_riferimento;
	}

	/**
	 * Restituisce il contenuto utente (genitore) a cui questo commento risponde.
	 * * @return Un oggetto ContenutoUtente (che può essere un Post o un altro Commento).
	 */
	public ContenutoUtente getParent() {
		return parent;
	}

	/**
	 * Imposta il contenuto utente (genitore) a cui questo commento risponde.
	 * * @param parent Il ContenutoUtente da impostare come padre.
	 */
	public void setParent(ContenutoUtente parent) {
		this.parent = parent;
	}

	/**
	 * Metodo di utilità per l'interfaccia grafica. 
	 * Restituisce il nome dell'autore del commento in modo sicuro, gestendo eventuali null.
	 * * @return L'username dell'autore, oppure la stringa "Sconosciuto" se l'autore o il nome sono nulli.
	 */
	public String getNomeAutoreVisibile() {
		if (this.getAutore() != null && this.getAutore().getUser_name() != null) {
			return this.getAutore().getUser_name();
		} else {
			return "Sconosciuto";
		}
	}

}