package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

public class UtenteStaff extends Utente {

	public UtenteStaff(String nome_utente, String email, String psw, String nome, String cognome) {
		super(nome_utente, email, psw, nome, cognome);
	}

	public UtenteStaff() {
		super();
	}

	@Override
	public boolean isStaff() {
		return true;
	}
	
	@Override
	public String toString() {
	    return "=== PROFILO STAFF ===\n" +
	           "Username: " + getUser_name() + "\n" +
	           "Nome:     " + getNome() + " " + getCognome() + "\n" +
	           "Email:    " + getEmail() + "\n" +
	           "Ruolo:    Membro dello Staff\n" 
	           ;
	}
}