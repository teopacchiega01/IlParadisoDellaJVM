package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.util.ArrayList;

public interface ICommentoDAO {
	
	public ArrayList<Commento> getCommenti(Post p);
	
	public ArrayList<Commento> getCommentiDiCommenti(Post p, Commento c);
	

}
