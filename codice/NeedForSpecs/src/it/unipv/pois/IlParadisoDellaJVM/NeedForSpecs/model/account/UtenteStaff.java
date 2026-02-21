package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

public class UtenteStaff extends Utente {

	public UtenteStaff(String nome_utente, String email, String psw, String nome, String cognome) {
		super(nome_utente, email, psw, nome, cognome);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isStaff() {
		return true;
	}

}
