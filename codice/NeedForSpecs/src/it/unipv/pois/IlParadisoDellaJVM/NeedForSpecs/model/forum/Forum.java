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

/**
 * Classe Model principale che funge da Facade per la logica di business del Forum.
 * Implementa il pattern Singleton e gestisce la creazione, eliminazione, 
 * ricerca e ordinamento dei Post e dei relativi Commenti, applicando le policy di sicurezza.
 * * @author teomiraldi
 * @version 1.0
 */
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

	/**
	 * Restituisce l'istanza Singleton del Forum.
	 * * @return L'unica istanza di Forum.
	 */
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

	/**
	 * Inizializza il forum recuperando tutti i post dal database tramite il DAO.
	 * * @return Un ArrayList contenente tutti i Post.
	 * @throws ForumException Se si verifica un errore durante il recupero dei dati.
	 */
	public ArrayList<Post> inizializzaForum() throws ForumException {
		return postDao.getPost();
	}

	/**
	 * Valida i permessi di un utente per la modifica o l'eliminazione di un contenuto.
	 * Ha il permesso solo lo Staff o l'autore originale del contenuto.
	 * * @param u L'Utente che richiede l'azione.
	 * @param contenuto Il ContenutoUtente su cui eseguire l'azione.
	 * @return true se l'utente ha i permessi necessari, false altrimenti.
	 */
	public boolean puoModificareOEliminare(Utente u, ContenutoUtente contenuto) {
		if (u == null) return false;
		if (u.isStaff()) return true;
		if (contenuto.getAutore() != null && contenuto.getAutore().getUser_name().equals(u.getUser_name())) {
			return true;
		}
		return false;
	}

	/**
	 * Crea un nuovo Post applicando la validazione dei dati e delegando il salvataggio al DAO.
	 * Assegna automaticamente la data e l'ora di sistema correnti.
	 * * @param autore L'Utente che sta creando il post.
	 * @param testo Il corpo del post.
	 * @param titolo Il titolo del post.
	 * @param sottotitolo Il sottotitolo opzionale del post.
	 * @return true se il post viene creato e salvato con successo.
	 * @throws IllegalArgumentException Se l'autore è nullo o se titolo/testo sono vuoti.
	 * @throws ForumException Se si verifica un errore nel DAO durante il salvataggio.
	 */
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

	/**
	 * Crea un nuovo Commento associato a un post padre, validando l'input e delegando il salvataggio.
	 * * @param autore L'Utente che sta scrivendo il commento.
	 * @param testo Il testo del commento.
	 * @param p Il Post in cui si sta commentando.
	 * @param parent Il ContenutoUtente genitore diretto (può essere il Post o un altro Commento).
	 * @return true se il commento viene salvato con successo.
	 * @throws IllegalArgumentException Se l'autore è nullo o se il testo è vuoto.
	 * @throws ForumException Se si verifica un errore durante il salvataggio.
	 */
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

	/**
	 * Rimuove logicamente e fisicamente un Post dal database e dalla lista in memoria.
	 * * @param p Il Post da eliminare.
	 * @return true se l'eliminazione ha successo, false in caso di errore non bloccante.
	 * @throws IllegalArgumentException Se il parametro è nullo o privo di ID.
	 * @throws ForumException Se si verifica un errore a livello di database.
	 */
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

	/**
	 * Elimina un Commento dal database e aggiorna i riferimenti in memoria nel Post associato.
	 * * @param c Il Commento da eliminare.
	 * @return true se l'eliminazione ha successo, false altrimenti.
	 * @throws IllegalArgumentException Se il commento passato non è valido.
	 */
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

	/**
	 * Recupera tutti i commenti di primo livello appartenenti a uno specifico Post.
	 * * @param p Il Post di riferimento.
	 * @return La lista dei commenti estratti.
	 * @throws ForumException Se la query al database fallisce.
	 */
	public ArrayList<Commento> getCommenti(Post p) throws ForumException {
		return commentoDao.getCommenti(p);
	}

	/**
	 * Recupera le risposte dirette (commenti annidati) a un commento genitore specifico.
	 * * @param p Il Post di riferimento a cui appartiene l'albero di commenti.
	 * @param c Il Commento genitore.
	 * @return La lista delle risposte (Commenti).
	 * @throws ForumException Se la query al database fallisce.
	 */
	public ArrayList<Commento> getCommentiDiCommenti(Post p, Commento c) throws ForumException {
		return commentoDao.getCommentiDiCommenti(p, c);
	}

	/**
	 * Ordina la lista interna dei Post utilizzando il pattern Strategy.
	 * * @param tipoOrdinamento Un enum Ordinamento che specifica l'algoritmo desiderato.
	 * @return L'ArrayList di Post ordinato secondo il criterio richiesto.
	 * @throws ForumException Se non viene trovata la strategia o ci sono errori di ordinamento.
	 */
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

	/**
	 * Esegue una ricerca lineare sui titoli dei post per trovare quelli contenenti la parola chiave specificata.
	 * La ricerca è case insensitive (ignora le differenze tra maiuscole e minuscole).
	 * * @param parola La parola chiave o la sottostringa da cercare nei titoli.
	 * @return Un ArrayList contenente i Post che corrispondono ai criteri di ricerca.
	 * @throws ForumException Se la lista originale dei post non può essere caricata.
	 */
	public ArrayList<Post> cercaPostPerTitolo(String parola) throws ForumException {
		ArrayList<Post> tutti = inizializzaForum();
		if (parola == null || parola.trim().isEmpty()) { //trim leva gli spazi
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