package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci;

import java.util.ArrayList;

//	@Author teopacchiega

public interface IAnnuncioDAO {
	public Annuncio getAnnuncioFromId(String id_annuncio);
	public ArrayList<Annuncio> getAnnunci();
	public boolean inserisciAnnuncio(Annuncio annuncio_da_inserire);
}
