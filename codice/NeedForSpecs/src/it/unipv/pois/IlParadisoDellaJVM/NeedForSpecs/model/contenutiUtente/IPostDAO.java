package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;

public interface IPostDAO {
	
	//Metodo che popola il DB con un post creato tramite il metodo creaPost della classe Forum
	public boolean creaPost(Post p);
	
	//Metodo di appoggio al metodo inizializzaForum che preleva tutti i post dal DB
	public ArrayList<Post> getPost();
	
	public ArrayList<Post> getPostUtente(Utente u);

}
