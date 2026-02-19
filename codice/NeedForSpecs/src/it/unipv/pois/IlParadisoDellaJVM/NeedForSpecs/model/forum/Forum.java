package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum;

import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.IPostDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.PostDaoDb;

public class Forum {
	
	private static Forum instance = null;
	
	private ArrayList<Post> post;
	private IPostDAO postDao;

	public Forum() {
		super();
		this.post = new ArrayList<Post>();
		postDao = new PostDaoDb();
	}
	
	public static Forum getInstance() {
		
		if(instance == null) {
			
			instance = new Forum();
		}
		
		return instance;
	}
	
	
	public ArrayList<Post> getPost() {
		return post;
	}

	public void setPost(ArrayList<Post> post) {
		this.post = post;
	}
	
	public boolean creaPost(Utente autore, String testo, LocalDateTime data_pubblicazione, String titolo, String sottotitolo) {
		
		try {
			
			Post p = new Post(autore, testo, data_pubblicazione, titolo, sottotitolo);
			
			postDao.creaPost(p);
			
			this.post.add(p);
			
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			return false;
		}
				
		
	}
	
	public boolean inizializzaForum() {
		
		try {
			
			post = postDao.getPost();
			
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			
			return false;
		}
		
		
		
	}
}
