package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.ForumException;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;

/**
 *
 * * @author teomiraldi
 * 
 */

public interface IPostDAO {

	public ArrayList<Post> getPost() throws ForumException;

	public ArrayList<Post> getPostUtente(Utente u) throws ForumException;

	public boolean creaPost(Post p) throws ForumException;

	public boolean eliminaPost(Post p) throws ForumException;
	
	

}
