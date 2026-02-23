package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

public abstract class Utente {

	private String user_name;
	private String email;
	private String psw;
	private String nome;
	private String cognome;

	public Utente() {
		super();
	}

	public Utente(String nome_utente, String email, String psw, String nome, String cognome) {
		super();
		this.user_name = nome_utente;
		this.email = email;
		this.psw = psw;
		this.nome = nome;
		this.cognome = cognome;
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

	public abstract boolean isStaff();
}