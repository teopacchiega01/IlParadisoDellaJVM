package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.ForumException;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;

public interface IPostDAO {

	//Metodo di appoggio al metodo inizializzaForum che preleva tutti i post dal DB
	public ArrayList<Post> getPost() throws ForumException;

	public ArrayList<Post> getPostUtente(Utente u) throws ForumException;

	//Metodo che popola il DB con un post creato tramite il metodo creaPost della classe Forum
	public boolean creaPost(Post p) throws ForumException;

	public boolean eliminaPost(Post p) throws ForumException;
	
	

}
