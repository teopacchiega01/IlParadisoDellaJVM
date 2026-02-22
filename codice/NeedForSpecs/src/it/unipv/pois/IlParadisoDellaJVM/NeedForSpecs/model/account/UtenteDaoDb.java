package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;

// @author persy

public class UtenteDaoDb implements IUtenteDAO {

	
	
	final String QUERY_LOGIN_STAFF = "SELECT user_name, email, pw, nome, cognome FROM Utente WHERE email = ? AND pw = ?";
	
	
	final String QUERY_LOGIN_UTENTE_GEN = "SELECT U.user_name, U.email, U.pw, U.nome, U.cognome "
										+ "FROM Utente as U "
										+ "JOIN UtenteGenerico as Gen ON Gen.user_name = U.user_name "
										+ "WHERE U.email = ? AND U.pw = ?";
	
	final String QUERY_INSERT_UTENTE = "INSERT INTO Utente (user_name, email, pw, nome, cognome) VALUES (?, ?, ?, ?, ?)";
	final String QUERY_INSERT_UTENTE_GEN = "INSERT INTO UtenteGenerico (user_name, id_indirizzo, numero_carta) VALUES (?, ?, ?)";
	

	@Override
	public Utente login(String email, String psw) {
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement = null; 
		ResultSet rs = null; 
		Utente utenteLoggato = null;
		
		try {
			if (email.contains("@staff")) {
				statement = conn.prepareStatement(QUERY_LOGIN_STAFF);
				statement.setString(1, email);
				statement.setString(2, psw);
				rs = statement.executeQuery();
				
				if (rs.next()) {
					utenteLoggato = new UtenteStaff(
							rs.getString("user_name"),
							rs.getString("email"),
							rs.getString("pw"),
							rs.getString("nome"),
							rs.getString("cognome")
					);
				}
				
			} else {
				statement = conn.prepareStatement(QUERY_LOGIN_UTENTE_GEN);
				statement.setString(1, email);
				statement.setString(2, psw);
				rs = statement.executeQuery();
				
				if (rs.next()) {
					utenteLoggato = new UtenteGenerico(
							rs.getString("user_name"),
							rs.getString("email"),
							rs.getString("pw"),
							rs.getString("nome"),
							rs.getString("cognome")
					);
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Errore durante il login DB!");
			e.printStackTrace();
		} finally {
			
			DatabaseManager.closeConnection(conn);
		}
		
		return utenteLoggato;
	}

	
	@Override
	public boolean registrazioneUtente(Utente u) {
		Connection conn = DatabaseManager.getConnection();
		boolean success = false;

		try {
			
			DatabaseManager.setAutoCommit(conn, false);
	
			PreparedStatement psUtente = conn.prepareStatement(QUERY_INSERT_UTENTE);
			psUtente.setString(1, u.getUser_name());
			psUtente.setString(2, u.getEmail());
			psUtente.setString(3, u.getPsw()); 
			psUtente.setString(4, u.getNome());
			psUtente.setString(5, u.getCognome());
			psUtente.executeUpdate();

		
			if (!u.isStaff()) {
				UtenteGenerico gen = (UtenteGenerico) u;
				PreparedStatement psUtenteGen = conn.prepareStatement(QUERY_INSERT_UTENTE_GEN);
				
				psUtenteGen.setString(1, gen.getUser_name());
				
				
				if (gen.getInd_di_spedizione() != null) {
					psUtenteGen.setString(2, gen.getInd_di_spedizione().getIdIndirizzo());
				} else {
					psUtenteGen.setNull(2, Types.VARCHAR);
				}
				
				
				if (gen.getMetodo_di_pagamento() != null) {
					psUtenteGen.setString(3, gen.getMetodo_di_pagamento().getNumeroCarta());
				} else {
					psUtenteGen.setNull(3, Types.VARCHAR);
				}
				
				psUtenteGen.executeUpdate();
			}

		
			DatabaseManager.commitConnection(conn);
			success = true;
			System.out.println("Registrazione completata con successo per: " + u.getUser_name());

		} catch (SQLException e) {
			System.err.println("Errore durante la registrazione. Rollback in corso...");
			e.printStackTrace();
			// 

			DatabaseManager.rollbackConnection(conn);
		} finally {
			// 
			DatabaseManager.setAutoCommit(conn, true);
			DatabaseManager.closeConnection(conn);
		}

		return success;
	}
}