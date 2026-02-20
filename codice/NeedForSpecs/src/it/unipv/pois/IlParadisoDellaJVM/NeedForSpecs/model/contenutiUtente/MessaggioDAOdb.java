package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;



import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;
/*
 * @author Persy
 */

public class MessaggioDAOdb implements IMessaggioDAO {
	public MessaggioDAOdb() {
		super();
	
		
	}





	@Override
	public ArrayList<Messaggio> getMessaggiDaTicket(Ticket ticket_di_riferimento) {
		// TODO Auto-generated method stub
		ArrayList<Messaggio> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnetcion();
		Statement statement;
		ResultSet resultset;
		
		try {
			statement = conn.createStatement();
			String query = "SELECT U.user_name, U.email, U.pw, U.nome, U.cognome, C.testo, C.data_pubblicazione,C.id_contenutoUtente "
	                + "FROM Messaggio as M "
	                + "JOIN ContenutoUtente as C ON C.id_contenutoUtente = M.id_contenutoUtente "
	                + "JOIN Utente as U ON U.user_name = C.id_utente " 
	                + "WHERE M.id_ticket = '" + ticket_di_riferimento.getId_ticket() + "';";
			
			
			
			resultset = statement.executeQuery(query);
			while(resultset.next()) {
				Utente autore = new Utente(resultset.getString(1),resultset.getString(2),resultset.getString(3),resultset.getString(4),resultset.getString(5));
				LocalDateTime data = resultset.getTimestamp(7).toLocalDateTime();
				Messaggio msg = new Messaggio(resultset.getString(8),autore, resultset.getString(6), data, ticket_di_riferimento);
				result.add(msg);
				
				 
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		
		}
		DatabaseManager.closeConnection(conn);
		return result;
	}




	@Override
	public boolean inserisciMessaggioInTicketRiferimento(Messaggio m) {
	    String queryContenuto = "INSERT INTO ContenutoUtente (id_contenutoUtente, id_utente, testo, data_pubblicazione) VALUES (?, ?, ?, ?)";
	    String queryMessaggio = "INSERT INTO Messaggio (id_contenutoUtente, id_ticket) VALUES (?, ?)";

	    Connection conn = DatabaseManager.getConnetcion();
	    boolean success = false;

	    try {
	        
	        DatabaseManager.setAutoCommit(conn, false);

	        PreparedStatement psContenuto = conn.prepareStatement(queryContenuto);
	        psContenuto.setString(1, m.getId_contenuto_utente());
	        psContenuto.setString(2, m.getAutore().getNome_utente());
	        psContenuto.setString(3, m.getTesto());
	        psContenuto.setObject(4, m.getData_pubblicazione());
	        psContenuto.executeUpdate();

	        PreparedStatement psMessaggio = conn.prepareStatement(queryMessaggio);
	        psMessaggio.setString(1, m.getId_contenuto_utente());
	        psMessaggio.setString(2, m.getTicket_di_riferimento().getId_ticket());
	        psMessaggio.executeUpdate();


	        DatabaseManager.commitConnection(conn);
	        success = true;

	    } catch (SQLException e) {
	        System.err.println("Errore inserimento messaggio!");
	        e.printStackTrace();
	        

	        DatabaseManager.rollbackConnection(conn);
	        
	    } finally {
	        
	        DatabaseManager.setAutoCommit(conn, true);
	        DatabaseManager.closeConnection(conn);
	    }

	    return success;
	}





	@Override
	public ArrayList<Messaggio> getMessaggiNuovi(Ticket ticket_di_riferimento, LocalDateTime data_ultimo_messaggio) {
		// TODO Auto-generated method stub
		ArrayList<Messaggio> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnetcion();
		PreparedStatement statement; 
		ResultSet resultset;
		
		
		String query = "SELECT U.user_name, U.email, U.pw, U.nome, U.cognome, C.testo, C.data_pubblicazione, C.id_contenutoUtente "
	            + "FROM Messaggio as M "
	            + "JOIN ContenutoUtente as C ON C.id_contenutoUtente = M.id_contenutoUtente "
	            + "JOIN Utente as U ON U.user_name = C.id_utente "
	            + "WHERE M.id_ticket = ? AND C.data_pubblicazione > ?";
		
		try {
			statement = conn.prepareStatement(query);
			statement.setString(1,ticket_di_riferimento.getId_ticket());
			statement.setObject(2, data_ultimo_messaggio);
			resultset = statement.executeQuery();
			while(resultset.next()) {
				Utente autore = new Utente(resultset.getString(1),resultset.getString(2),resultset.getString(3),resultset.getString(4),resultset.getString(5));
				LocalDateTime data = resultset.getTimestamp(7).toLocalDateTime();
				Messaggio msg = new Messaggio(resultset.getString(8),autore, resultset.getString(6), data, ticket_di_riferimento);
				result.add(msg);

				
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
				
				
				
		DatabaseManager.closeConnection(conn);

		return result;
	}





	

}
