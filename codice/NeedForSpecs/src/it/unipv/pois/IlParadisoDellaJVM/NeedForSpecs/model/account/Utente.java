package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

public  class Utente {
	
	private String nome_utente;
	private String email;
	private String psw;
	private String nome;
	private String cognome;
	private boolean loggato;
	
	
	public Utente(String nome_utente, String email, String psw, String nome, String cognome) {
		super();
		this.nome_utente = nome_utente;
		this.email = email;
		this.psw = psw;
		this.nome = nome;
		this.cognome = cognome;
	}

	public Utente() {
		// TODO Auto-generated constructor stub	
		
		
	}
	
	boolean creaUtente() {
		
		return false;
	}
	
	boolean login() {
			
			return false;
	}
	
	boolean logout() {
		
		return false;
	}
	
	boolean registrazione() {
		
		return false;
	}
	

	

}
