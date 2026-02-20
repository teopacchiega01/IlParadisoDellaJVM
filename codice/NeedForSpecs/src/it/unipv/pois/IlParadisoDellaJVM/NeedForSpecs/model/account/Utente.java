package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

public  class Utente {
	
	private String user_name;
	private String email;
	private String psw;
	private String nome;
	private String cognome;
	private boolean loggato;
	
	
	public Utente(String nome_utente, String email, String psw, String nome, String cognome) {
		super();
		this.user_name = nome_utente;
		this.email = email;
		this.psw = psw;
		this.nome = nome;
		this.cognome = cognome;
	}

	public Utente() {
		// TODO Auto-generated constructor stub	
		super();
	}
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String nome_utente) {
		this.user_name = nome_utente;
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

//	boolean login(String user_name, String pw) {
//				pesca dal db l'utente corrispondente
//			return false;
//	}
//	
//	boolean logout() {
//			setta a null tutti gli elementi dell'utente corrente, verrà chiamato dai singleton Forum, Assistenza, MarketPlace
//		return false;
//	}
//	
//	boolean registrazione(String user_name, String email, String pw, String nome, String cognome) {
//			cerca sul db se c'è un altro user con lo stesso user_name, se non lo trova, fa un'insert into Utente sul db
//		return false;
//	}
	

	

}
