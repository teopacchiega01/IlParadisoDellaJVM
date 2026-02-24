package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;


/**
 * @author Persy
 */
public class UtenteDAOdb implements IUtenteDAO {

	
	
	final String QUERY_LOGIN_STAFF = "SELECT user_name, email, pw, nome, cognome FROM Utente WHERE email = ? AND pw = ?";
	
	
	final String QUERY_LOGIN_UTENTE_GEN = "SELECT U.user_name, U.email, U.pw, U.nome, U.cognome "
										+ "FROM Utente as U "
										+ "JOIN UtenteGenerico as Gen ON Gen.user_name = U.user_name "
										+ "WHERE U.email = ? AND U.pw = ?";
	
	final String QUERY_INSERT_UTENTE = "INSERT INTO Utente (user_name, email, pw, nome, cognome) VALUES (?, ?, ?, ?, ?)";
	final String QUERY_INSERT_UTENTE_GEN = "INSERT INTO UtenteGenerico (user_name, id_indirizzo, numero_carta) VALUES (?, ?, ?)";
	
	final String QUERY_GET_UTENTE_FROM_ID = "SELECT user_name, email, pw, nome, cognome FROM Utente WHERE user_name = ?;";

	@Override
	public Utente login(String email, String psw) {
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement = null; 
		ResultSet rs = null; 
		Utente utente_loggato = null;
		
		try {
			if (email.contains("@staff")) {
				statement = conn.prepareStatement(QUERY_LOGIN_STAFF);
				statement.setString(1, email);
				statement.setString(2, psw);
				rs = statement.executeQuery();
				
				if (rs.next()) {
					utente_loggato = new UtenteStaff(
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
					utente_loggato = new UtenteGenerico(
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
		
		return utente_loggato;
	}

	
	@Override
	public boolean registrazioneUtente(Utente u) {
		Connection conn = DatabaseManager.getConnection();
		boolean success = false;

		try {
			
			DatabaseManager.setAutoCommit(conn, false);
	
			PreparedStatement ps_utente = conn.prepareStatement(QUERY_INSERT_UTENTE);
			ps_utente.setString(1, u.getUser_name());
			ps_utente.setString(2, u.getEmail());
			ps_utente.setString(3, u.getPsw()); 
			ps_utente.setString(4, u.getNome());
			ps_utente.setString(5, u.getCognome());
			ps_utente.executeUpdate();

		
			if (!u.isStaff()) {
				UtenteGenerico gen = (UtenteGenerico) u;
				PreparedStatement ps_utente_gen = conn.prepareStatement(QUERY_INSERT_UTENTE_GEN);
				
				ps_utente_gen.setString(1, gen.getUser_name());
				
				
				if (gen.getInd_di_spedizione() != null) {
					ps_utente_gen.setString(2, gen.getInd_di_spedizione().getIdIndirizzo());
				} else {
					ps_utente_gen.setNull(2, Types.VARCHAR);
				}
				
				
				if (gen.getMetodo_di_pagamento() != null) {
					ps_utente_gen.setString(3, gen.getMetodo_di_pagamento().getNumeroCarta());
				} else {
					ps_utente_gen.setNull(3, Types.VARCHAR);
				}
				
				ps_utente_gen.executeUpdate();
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


	@Override
	public Utente getUtenteFromId(String id_utente_da_trovare) {
	    Utente utente_trovato = null;
	    Connection conn = DatabaseManager.getConnection();
	    PreparedStatement pr_stat = null;
	    ResultSet res_set = null;
	
	    String query = "SELECT user_name, email, pw, nome, cognome FROM Utente WHERE user_name = ?;";
	    
	    try {
	        pr_stat = conn.prepareStatement(query);
	        pr_stat.setString(1, id_utente_da_trovare);
	        
	        res_set = pr_stat.executeQuery();
	        
	        if (res_set.next()) {
	            String email_trovata = res_set.getString("email");
	            if (email_trovata != null && email_trovata.contains("@staff")) {
	                utente_trovato = new UtenteStaff();
	            } else {
	                utente_trovato = new UtenteGenerico();
	            }
	            utente_trovato.setUser_name(res_set.getString("user_name"));
	            utente_trovato.setEmail(email_trovata);
	            utente_trovato.setPsw(res_set.getString("pw"));
	            utente_trovato.setNome(res_set.getString("nome"));
	            utente_trovato.setCognome(res_set.getString("cognome"));
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if (res_set != null) res_set.close(); } catch (SQLException e) {}
	        try { if (pr_stat != null) pr_stat.close(); } catch (SQLException e) {}
	    }
	    
	    DatabaseManager.closeConnection(conn);
	    return utente_trovato; 
	}
}