package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;

//	@Author teopacchiega

public interface IIndirizzoDAO {
	public Indirizzo getIndirizzo(UtenteGenerico utente);
	public boolean inserisciIndirizzo(Indirizzo indirizzo_da_inserire);
}
