package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;

public abstract class Utente {

	private String user_name;
	private String email;
	private String psw;
	private String nome;
	private String cognome;
	private IUtenteDAO utente_dao;


	public Utente(String nome_utente, String email, String psw, String nome, String cognome) {
		super();
		this.user_name = nome_utente;
		this.email = email;
		this.psw = psw;
		this.nome = nome;
		this.cognome = cognome;
		
	}
	public Utente(String nome_utente, String email, String psw, String nome, String cognome,DAOFactory factory) {
		super();
		this.user_name = nome_utente;
		this.email = email;
		this.psw = psw;
		this.nome = nome;
		this.cognome = cognome;
		this.utente_dao = factory.getUtenteDAO();
		
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

	public abstract boolean isStaff();
	
	//	TODO
	public Utente login(String mail, String pw) {
		//		pesca dal db l'utente corrispondente
		
		return utente_dao.login(mail, pw);
		
	}

	//	TODO
	public boolean loginStaff(String user_name, String pw) {
		//		pesca dal db l'utente corrispondente
		return false;
	}

	//	TODO
	public Utente logout() {
		return null;
	}

	// TODO
	public boolean registrazione() {
		//		cerca sul db se c'è un altro user con lo stesso user_name, se non lo trova, fa un'insert into Utente sul db
		return  utente_dao.registrazioneUtente(this);
	}
	

}
