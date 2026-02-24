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

	public Commento(String id_contenuto_utente, Utente autore, String testo, LocalDateTime data_pubblicazione, Post post_di_riferimento,
			ContenutoUtente parent) {
		super(id_contenuto_utente, autore, testo, data_pubblicazione);
		this.post_di_riferimento = post_di_riferimento;
		this.parent = parent;
	}

	public Commento(Utente autore, String testo, LocalDateTime data_pubblicazione, Post post_di_riferimento, ContenutoUtente parent) {
		super(autore, testo, data_pubblicazione);
		this.post_di_riferimento = post_di_riferimento;
		this.parent = parent;
	}

	public Post getPost_di_riferimento() {
		return post_di_riferimento;
	}

	public void setPost_di_riferimento(Post post_di_riferimento) {
		this.post_di_riferimento = post_di_riferimento;
	}

	public ContenutoUtente getParent() {
		return parent;
	}

	public void setParent(ContenutoUtente parent) {
		this.parent = parent;
	}

	/**
	 * Metodo di utilità per l'interfaccia grafica. 
	 * Restituisce il nome dell'autore del commento in modo sicuro.
	 * * @return L'username dell'autore, oppure "Sconosciuto" se l'autore è nullo.
	 */
	public String getNomeAutoreVisibile() {
		if (this.getAutore() != null && this.getAutore().getUser_name() != null) {
			return this.getAutore().getUser_name();
		} else {
			return "Sconosciuto";
		}
	}

}