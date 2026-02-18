package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;



import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Ticket;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;
/*
 * @author Persy
 */

public class MessaggioDAOdb implements IMessaggioDAO {
	private String schema; 

	
	
	
	

	public MessaggioDAOdb() {
		super();
		schema = "Messaggio";
		
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
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		
		}
			
		return result;
	}





	@Override
	public boolean inserisciMessaggioInTicketRiferimento(Messaggio m) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseManager.getConnetcion();
		PreparedStatement statement;
		ResultSet resultset;
		boolean status = false; 
		int righe_inserite = 0;
		
		String query_contenuto_utente = "INSERT INTO ContenutoUtente VALUES (?.?,?)";
		String query_messaggio= "INSERT INTO Messaggio(id_contenutoUtente,id_ticket) VALUES (?.?)";
		
		
		return false;
	}





	

}
