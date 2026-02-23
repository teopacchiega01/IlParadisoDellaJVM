package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

public interface IUtenteDAO {
	public Utente login(String email,String psw);
	public boolean registrazioneUtente(Utente u);
	public Utente getUtenteFromId(String id_utente_da_trovare);
	
}
