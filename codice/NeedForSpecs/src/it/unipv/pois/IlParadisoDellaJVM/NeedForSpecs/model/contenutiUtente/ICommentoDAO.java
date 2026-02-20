package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.ForumException;

public interface ICommentoDAO {
	
	public ArrayList<Commento> getCommenti(Post p) throws ForumException;
	
	public ArrayList<Commento> getCommentiDiCommenti(Post p, Commento c) throws ForumException;

	boolean creaCommento(Commento c, Post p, ContenutoUtente parent) throws ForumException;
	
	public boolean eliminaCommento(Commento c) throws ForumException;
	

}
