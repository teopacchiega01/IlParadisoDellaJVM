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
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy.ForumStrategy;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy.Ordinamento;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy.OrdinamentoStrategyFactory;

public class Forum {

	private static Forum instance = null;
	
	private Utente u;
	private ArrayList<Post> post;
	private IPostDAO postDao;
	private ICommentoDAO commentoDao;

	private Forum() {
		super();
		DAOFactory factory = DAOFactory.getInstance();
		this.post = new ArrayList<Post>();
		this.postDao = factory.getPostDAO();
		this.commentoDao = factory.getCommentoDAO();
	}

	public static Forum getInstance() {
		if(instance == null) {
			instance = new Forum();
		}
		return instance;
	}

	public Utente getU() {
		return u;
	}

	public void setU(Utente u) {
		this.u = u;
	}

	public ArrayList<Post> getPost() {
		return post;
	}

	public void setPost(ArrayList<Post> post) {
		this.post = post;
	}

	public ArrayList<Post> inizializzaForum() throws ForumException {
		return postDao.getPost();
	}


	public boolean puoModificareOEliminare(Utente u, ContenutoUtente contenuto) {
		if (u == null) return false;
		if (u.isStaff()) return true;
		if (contenuto.getAutore() != null && contenuto.getAutore().getUser_name().equals(u.getUser_name())) {
			return true;
		}
		return false;
	}


	public boolean creaPost(Utente autore, String testo, String titolo, String sottotitolo) throws ForumException {

		if (autore == null) {
			throw new IllegalArgumentException("Devi effettuare il login per scrivere un post.");
		}
		if (titolo == null || titolo.trim().isEmpty()) {
			throw new IllegalArgumentException("Errore: Il titolo del post non può essere vuoto!");
		}
		if (testo == null || testo.trim().isEmpty()) {
			throw new IllegalArgumentException("Errore: Il testo del post non può essere vuoto!");
		}

		try {
			LocalDateTime data_pubblicazione = LocalDateTime.now();
			Post p = new Post(autore, testo, data_pubblicazione, titolo, sottotitolo);
			postDao.creaPost(p);
			post.add(p);
			return true;
		} catch (ForumException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	// CREAZIONE COMMENTO CON VALIDAZIONE BLINDATA
	public boolean creaCommento(Utente autore, String testo, Post p, ContenutoUtente parent) throws ForumException {
		if (autore == null) {
			throw new IllegalArgumentException("Devi effettuare il login per commentare.");
		}
		if (testo == null || testo.trim().isEmpty()) {
			throw new IllegalArgumentException("Errore: Il testo del commento non può essere vuoto!");
		}

		try {
			LocalDateTime data_pubblicazione = LocalDateTime.now();
			Commento c = new Commento(autore, testo, data_pubblicazione, p, parent);
			commentoDao.creaCommento(c, p, parent);
			p.aggiungiCommento(c);
			return true;
		} catch (ForumException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	public boolean eliminaPost(Post p) throws ForumException {
		if(p == null || p.getId_contenuto_utente() == null || p.getId_contenuto_utente().isEmpty()) {
			throw new IllegalArgumentException("Parametro non valido per l'eliminazione.");
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
			throw new IllegalArgumentException("Parametro non valido per l'eliminazione.");
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

	public ArrayList<Commento> getCommenti(Post p) throws ForumException {
		return commentoDao.getCommenti(p);
	}

	public ArrayList<Commento> getCommentiDiCommenti(Post p, Commento c) throws ForumException {
		return commentoDao.getCommentiDiCommenti(p, c);
	}

	public ArrayList<Post> ordinaPost(Ordinamento tipoOrdinamento) throws ForumException {
		if (this.post == null || this.post.isEmpty()) {
			this.post = postDao.getPost();
		}
		ForumStrategy strategy = OrdinamentoStrategyFactory.getInstance().getStrategy(tipoOrdinamento);
		if (strategy != null) {
			strategy.ordinamento(this.post);
		} else {
			throw new ForumException("Strategia di ordinamento non trovata o non valida.");
		}
		return this.post;
	}

	public ArrayList<Post> cercaPostPerTitolo(String parola) throws ForumException {
		ArrayList<Post> tutti = inizializzaForum();
		if (parola == null || parola.trim().isEmpty()) {
			return tutti;
		}
		ArrayList<Post> filtrati = new ArrayList<>();
		String parolaLower = parola.toLowerCase();
		for (Post p : tutti) {
			if (p.getTitolo().toLowerCase().contains(parolaLower)) {
				filtrati.add(p);
			}
		}
		return filtrati;
	}
}