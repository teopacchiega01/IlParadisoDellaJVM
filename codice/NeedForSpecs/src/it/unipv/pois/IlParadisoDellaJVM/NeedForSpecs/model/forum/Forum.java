package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum;

import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Commento;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.ContenutoUtente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.ICommentoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IPostDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;

public class Forum {

	private static Forum instance = null;

	private ArrayList<Post> post;
	private IPostDAO postDao;
	private ICommentoDAO commentoDao;

	public Forum(DAOFactory factory) {
		super();
		this.post = new ArrayList<Post>();
		this.postDao = factory.getPostDAO();
		this.commentoDao = factory.getCommentoDAO();
	}

	public static Forum getInstance(DAOFactory factory) {

		if(instance == null) {

			instance = new Forum(factory);
		}

		return instance;
	}


	public ArrayList<Post> getPost() {
		return post;
	}

	public void setPost(ArrayList<Post> post) {
		this.post = post;
	}







	public ArrayList<Post> inizializzaForum() throws ForumException{
		
		return postDao.getPost();

	}



	public boolean creaPost(Utente autore, String testo, LocalDateTime data_pubblicazione, String titolo, String sottotitolo) {

		if (autore == null || titolo == null || titolo.isEmpty()) {
			throw new IllegalArgumentException("Dati del post non validi");
		}

		try {

			Post p = new Post(autore, testo, data_pubblicazione, titolo, sottotitolo);

			postDao.creaPost(p);

			post.add(p);

			return true;

		} catch (ForumException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());

			return false;
		}


	}



	public boolean creaCommento(Utente autore, String testo, LocalDateTime data_pubblicazione, Post p, ContenutoUtente parent) {

		if (autore == null || testo == null || testo.isEmpty()) {
			throw new IllegalArgumentException("Dati del post non validi");
		}

		try {

			Commento c = new Commento(autore, testo, data_pubblicazione, p, parent);

			commentoDao.creaCommento(c, p, parent);

			p.aggiungiCommento(c);

			return true;

		} catch (ForumException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());

			return false;
		}


	}



	public boolean eliminaPost(Post p) {

		if(p == null || p.getId_contenuto_utente() == null || p.getId_contenuto_utente().isEmpty()) {
			throw new IllegalArgumentException("Parametro non valido");
		}

		try {

			postDao.eliminaPost(p);


			for (Post pst : post) {

				if(pst.getId_contenuto_utente().equals(p.getId_contenuto_utente())) {

					post.remove(pst); 

					break; 
				}
			}

			return true;

		} catch (ForumException e) {
			System.out.println("Errore durante l'eliminazione del post: " + e.getMessage());
			return false;
		}
	}




	public boolean eliminaCommento(Commento c) {

		if(c == null || c.getId_contenuto_utente() == null || c.getId_contenuto_utente().isEmpty()) {

			throw new IllegalArgumentException("Parametro non valido");

		}

		try {

			commentoDao.eliminaCommento(c);

			for (Post p: post) {

				for (Commento cDaRimuovere : p.getCommenti()) {

					if(cDaRimuovere.getId_contenuto_utente().equals(c.getId_contenuto_utente())) {

						p.getCommenti().remove(cDaRimuovere);
						break;
					}
				}
			}

			return true;

		} catch (ForumException e) {
			System.err.println("Errore durante l'eliminazione del commento: " + e.getMessage());
			return false;
		}



	}
	
	
	




}
