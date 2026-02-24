package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;
/**
 * @author Persy
 */

public class TicketDAOdb implements ITicketDAO {

	private static final String QUERY_GET_TICKET_DA_RICHIEDENTE = 
			"SELECT T.id_ticket, T.stato, Staff.user_name "
			+ "FROM Ticket as T "
			+ "JOIN Utente as Staff ON Staff.user_name = T.id_utente_gestore "
			+ "JOIN Utente as Assistito ON Assistito.user_name = T.id_utente_richiedente "
			+ "WHERE Assistito.user_name = ?";
			
	private static final String QUERY_GET_TICKET_DA_STAFF = 
			"SELECT T.id_ticket, T.stato, Assistito.user_name, Assistito.nome, Assistito.cognome "
			+ "FROM Ticket as T "
			+ "LEFT JOIN Utente as Staff ON Staff.user_name = T.id_utente_gestore "
			+ "LEFT JOIN Utente as Assistito ON Assistito.user_name = T.id_utente_richiedente "
			+ "WHERE Staff.user_name = ?";
			
	private static final String QUERY_INSERISCI_TICKET = 
			"INSERT INTO Ticket (id_ticket, id_utente_richiedente, id_utente_gestore, stato) "
			+ "VALUES (?, ?, ?, ?)";
			
	private static final String QUERY_AGGIORNA_STATO_TICKET = 
			"UPDATE Ticket SET stato = ? WHERE id_ticket = ?";
			
	private static final String QUERY_GET_TICKET_SENZA_GESTORE = 
			"SELECT T.id_ticket, T.stato, Assistito.user_name, Assistito.nome, Assistito.cognome "
			+ "FROM Ticket as T "
			+ "JOIN Utente as Assistito ON Assistito.user_name = T.id_utente_richiedente "
			+ "WHERE T.id_utente_gestore IS NULL "
			+ "LIMIT 5;";
			
	private static final String QUERY_AGGIORNA_GESTORE_TICKET = 
			"UPDATE Ticket SET id_utente_gestore = ? WHERE id_ticket = ?";

	@Override
	public ArrayList<Ticket> getTicketDaRichiedente(UtenteGenerico u) {
		ArrayList<Ticket> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		ResultSet resultset;
		
		try {
			statement = conn.prepareStatement(QUERY_GET_TICKET_DA_RICHIEDENTE);
			statement.setString(1, u.getUser_name());
			resultset = statement.executeQuery();
			
			while(resultset.next()) {
				String id_ticket = resultset.getString(1);
				String stato = resultset.getString(2);
				Stato e_stato = Stato.valueOf(stato);
				String user_name = resultset.getString(3);
				UtenteStaff staff = new UtenteStaff(user_name,null,null,null,null);
				Ticket t = new Ticket(id_ticket, u, staff, e_stato, null);
				result.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseManager.closeConnection(conn);
		}
		
		return result;
	}

	@Override
	public ArrayList<Ticket> getTicketDaStaff(UtenteStaff u) {
		ArrayList<Ticket> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		ResultSet resultset;
		
		try {
			statement = conn.prepareStatement(QUERY_GET_TICKET_DA_STAFF);
			statement.setString(1, u.getUser_name());
			resultset = statement.executeQuery();
			
			while(resultset.next()) {
				String id_ticket = resultset.getString(1);
				String stato = resultset.getString(2); 
				Stato e_stato = Stato.valueOf(stato);
				
				String user_name = resultset.getString(3);
				String nome = resultset.getString(4);
				String cognome = resultset.getString(5);
				
				UtenteGenerico assistito = new UtenteGenerico();
				assistito.setUser_name(user_name);
				assistito.setNome(nome);
				assistito.setCognome(cognome);
				Ticket t = new Ticket(id_ticket,assistito, u, e_stato, null);
				result.add(t);
			}
		}catch (Exception e) {
			System.err.println("ERRORE NEL DAO STAFF DURANTE IL CARICAMENTO:");
			System.err.println("Messaggio: " + e.getMessage());
		    e.printStackTrace(); 
		} finally {
			DatabaseManager.closeConnection(conn);
		}
		
		return result;
	}

	@Override
	public boolean inserisciTicket(Ticket t) {
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		boolean status = false; 
		int righe_inserite = 0;
		try {
			statement = conn.prepareStatement(QUERY_INSERISCI_TICKET);
			statement.setString(1, t.getId_ticket());
			statement.setString(2, t.getRichiedente_assistenza().getUser_name());
			if (t.getGestore() != null) {
			    statement.setString(3, t.getGestore().getUser_name());
			} else {
			    statement.setNull(3, java.sql.Types.NULL);
			}
			statement.setString(4, t.getStato_ticket().toString());
			righe_inserite = statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(righe_inserite > 0) {
				status = true;
			}
			DatabaseManager.closeConnection(conn);
		}

		return status;
	}

	@Override
	public boolean aggiornaStatoTicket(Stato nuovo_stato,Ticket t) {
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		boolean status;
		int righe_inserite;
				
		try {
			statement = conn.prepareStatement(QUERY_AGGIORNA_STATO_TICKET);
			statement.setString(1, nuovo_stato.toString());
			statement.setString(2, t.getId_ticket());
			righe_inserite = statement.executeUpdate();
			if(righe_inserite > 0) {
				System.out.println("Update ESEGUITO con successo controllare le corrispondenze nel db");
				status = true;
			}else {
				System.err.println("Update FALLITO controllare le corrispondenze nel db");
				status = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		} finally {
			DatabaseManager.closeConnection(conn);
		}
		
		return status;
	}

	@Override
	public ArrayList<Ticket> getTicketSenzaGestore() {
		ArrayList<Ticket> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		ResultSet resultset;
		
		try {
			statement = conn.prepareStatement(QUERY_GET_TICKET_SENZA_GESTORE);
			resultset = statement.executeQuery();
			while(resultset.next()) {
				String user_name = resultset.getString(3);
				String nome_assistito = resultset.getString(4);
				String cognome_assistito = resultset.getString(5);
				
				String ticket_id = resultset.getString(1);
				String stato_string = resultset.getString(2);
				Stato e_stato = Stato.valueOf(stato_string);
				
				UtenteGenerico assistito = new UtenteGenerico();
				assistito.setUser_name(user_name);
				assistito.setNome(nome_assistito);
				assistito.setCognome(cognome_assistito);

				Ticket t = new Ticket(ticket_id, assistito, null, e_stato, new ArrayList<>()); 
				result.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseManager.closeConnection(conn);
		}
	
		return result;
	}

	@Override
	public boolean aggiornaGestoreTicket(UtenteStaff staff, Ticket t) {
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		boolean status;
		int righe_inserite = 0;

		try {
	        statement = conn.prepareStatement(QUERY_AGGIORNA_GESTORE_TICKET);
	        statement.setString(1, staff.getUser_name());
	        statement.setString(2, t.getId_ticket());
	        
	        righe_inserite = statement.executeUpdate();
	        if(righe_inserite > 0) {
				System.out.println("Update ESEGUITO con successo controllare le corrispondenze nel db");
				status = true;
			}else {
				System.err.println("Update FALLITO controllare le corrispondenze nel db");
				status = false;
			}
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	        status = false;
	    } finally {
	        DatabaseManager.closeConnection(conn);
	    }
	    return status;
	}

}