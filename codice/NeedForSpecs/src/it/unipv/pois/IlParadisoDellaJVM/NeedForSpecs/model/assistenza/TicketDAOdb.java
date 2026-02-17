package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
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
					+ "JOIN Utente as Staff ON Staff.id_utente = T.id_utente_gestore "
					+ "JOIN Utente as Assistito ON Assistito.id_utente = T.id_utente_richiedente "
					+ "WHERE Assistito.user_name = '" + u.getNome_utente() + "'";
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
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
					+ "LEFT JOIN Utente as Staff ON Staff.id_utente = T.id_utente_gestore "
					+ "LEFT JOIN Utente as Assistito ON Assistito.id_utente = T.id_utente_richiedente "
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
			conn.close();
			
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println("ERRORE NEL DAO STAFF DURANTE IL CARICAMENTO:");
			System.err.println("Messaggio: " + e.getMessage());
		    e.printStackTrace(); 
		}
		
		
		return result;
	}

}
