package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.time.LocalDateTime;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;

public class Commento extends ContenutoUtente {
	
	private Post post_di_riferimento;
	private ContenutoUtente parent;
	
	public Commento(Post post_di_riferimento, ContenutoUtente parent) {
		super();
		this.post_di_riferimento = post_di_riferimento;
		this.parent = parent;
	}

	public Commento() {
		super();
	}

	public Commento(String id_contenuto_utente, Utente autore, String testo, LocalDateTime data_pubblicazione) {
		super(id_contenuto_utente, autore, testo, data_pubblicazione);
		// TODO Auto-generated constructor stub
	}

	public Commento(String id_contenuto_utente, Utente autore, String testo, LocalDateTime data_pubblicazione, Post post_di_riferimento,
			ContenutoUtente parent) {
		super(id_contenuto_utente, autore, testo, data_pubblicazione);
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
	
	

	
	
	
	
	
	

}
