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
	
	
	
	
	
	
	public String getNome_utente() {
		return nome_utente;
	}

	public void setNome_utente(String nome_utente) {
		this.nome_utente = nome_utente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public boolean isLoggato() {
		return loggato;
	}

	public void setLoggato(boolean loggato) {
		this.loggato = loggato;
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
