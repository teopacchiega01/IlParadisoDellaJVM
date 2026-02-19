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

/*
 * author: Persy
 */

public class TicketDAOdb implements ITicketDAO {

	@Override
	public ArrayList<Ticket> getTicketDaRichiedente(UtenteGenerico u) {
		// TODO Auto-generated method stub
		ArrayList<Ticket> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnetcion();
		Statement statement;
		ResultSet resultset;
		
		
		try {
			statement = conn.createStatement();
			String query = "SELECT T.id_ticket, T.stato, Staff.user_name "
					+ "FROM Ticket as T " 
					+ "JOIN Utente as Staff ON Staff.user_name = T.id_utente_gestore "
					+ "JOIN Utente as Assistito ON Assistito.user_name = T.id_utente_richiedente "
					+ "WHERE Assistito.user_name = '" + u.getNome_utente() + "'"
					+ ";"
					;
					
			
			resultset = statement.executeQuery(query);
			
			while(resultset.next()) {
				
				String id_ticket = resultset.getString(1);
				String stato = resultset.getString(2);
				Stato e_stato = Stato.valueOf(stato);
				String user_name = resultset.getString(3);
				//Carico solo i dati che dobbiamo mostrare per non appensatire il db
				UtenteStaff staff = new UtenteStaff(user_name,null,null,null,null);
				Ticket t = new Ticket(id_ticket, u, staff, e_stato, null);
				
				result.add(t);
				
				
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatabaseManager.closeConnection(conn);
		
		return result;
	}

	
	@Override
	public ArrayList<Ticket> getTicketDaStaff(UtenteStaff u) {
		// TODO Auto-generated method stub
		ArrayList<Ticket> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnetcion();
		Statement statement;
		ResultSet resultset;
		
		try {
			statement = conn.createStatement();
			String query = "SELECT T.id_ticket, T.stato, Assistito.user_name, Assistito.nome, Assistito.cognome "
					+ "FROM Ticket as T "
					+ "LEFT JOIN Utente as Staff ON Staff.user_name = T.id_utente_gestore "
					+ "LEFT JOIN Utente as Assistito ON Assistito.user_name = T.id_utente_richiedente "
					+ "WHERE Staff.user_name = '" + u.getNome_utente() + "'"; 
			resultset = statement.executeQuery(query);
			
			while(resultset.next()) {
				String id_ticket = resultset.getString(1);
				String stato = resultset.getString(2); 
				Stato e_stato = Stato.valueOf(stato);
				
				String user_name = resultset.getString(3);
				String nome = resultset.getString(4);
				String cognome = resultset.getString(5);
				
				UtenteGenerico assistito = new UtenteGenerico(user_name, null, null, nome, cognome);
				Ticket t = new Ticket(id_ticket,assistito, u, e_stato, null);
				result.add(t);
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println("ERRORE NEL DAO STAFF DURANTE IL CARICAMENTO:");
			System.err.println("Messaggio: " + e.getMessage());
		    e.printStackTrace(); 
		}
		
		DatabaseManager.closeConnection(conn);
		return result;
	}

	// Analizzare il controllo sul ticket
	@Override
	public boolean inserisciTicket(Ticket t) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseManager.getConnetcion();
		PreparedStatement statement;
		boolean status = false; 
		int righe_inserite = 0;
		try {
			
			String query = "INSERT INTO Ticket (id_ticket, id_utente_richiedente, id_utente_gestore, stato) "
					+ "VALUES (?, ?, ?, ?)";
			statement = conn.prepareStatement(query);
			statement.setString(1, t.getId_ticket());
			statement.setString(2, t.getRichiedente_assistenza().getNome_utente());
			if (t.getGestore() != null) {
			    statement.setString(3, t.getGestore().getNome_utente());
			} else {
			    statement.setNull(3, java.sql.Types.NULL); // Inserisco NULL nel DB
			}
			//statement.setString(3, t.getGestore().getNome_utente());
			statement.setString(4, t.getStato_ticket().toString());
			righe_inserite = statement.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(righe_inserite > 0) {
				status = true;
			}
		}

		
		DatabaseManager.closeConnection(conn);
		return status;
		
	}


	@Override
	public boolean aggiornaStatoTicket(Stato nuovo_stato,Ticket t) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseManager.getConnetcion();
		PreparedStatement statement;
		boolean status;
		int righe_inserite;
		String query = "UPDATE Ticket SET stato = ? WHERE id_ticket = ?";
				
	
		try {
			
			statement = conn.prepareStatement(query);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = false;
			
			
		}
		DatabaseManager.closeConnection(conn);
		return status;
		
		
	}


	@Override
	public ArrayList<Ticket> getTicketSenzaGestore() {
		// TODO Auto-generated method stub
		ArrayList<Ticket> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnetcion();
		PreparedStatement statement;
		ResultSet resultset;
		
		String query = "SELECT T.id_ticket, T.stato, Assistito.user_name, Assistito.nome, Assistito.cognome"
				+ "FROM Ticket as T "
				+ "JOIN Utente as Assistito ON Assistito.user_name = T.id_utente_richiedente "
				+ "WHERE T.id_utente_gestore IS NULL"
				+ "LIMIT 5"
				+ ";";
		try {
			statement = conn.prepareStatement(query);
			resultset = statement.executeQuery();
			while(resultset.next()) {
				String user_name = resultset.getString(3);
				String nome_assistito = resultset.getString(4);
				String cognome_assistito = resultset.getString(5);
				
				
				String ticket_id = resultset.getString(1);
				String stato_string = resultset.getString(2);
				Stato e_stato = Stato.valueOf(stato_string);
				
				UtenteGenerico assistito = new UtenteGenerico(user_name, null, null, nome_assistito, cognome_assistito);
				Ticket t = new Ticket(ticket_id, assistito, null, e_stato, null);
				result.add(t);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DatabaseManager.closeConnection(conn);
		}
	
		return result;
	}

}
