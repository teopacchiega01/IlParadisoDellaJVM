package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;



import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;
/**
 * @author Persy
 */
public class MessaggioDAOdb implements IMessaggioDAO {

	private static final String QUERY_GET_MESSAGGI_DA_TICKET = 
			"SELECT U.user_name, U.email, U.pw, U.nome, U.cognome, C.testo, C.data_pubblicazione, C.id_contenutoUtente "
			+ "FROM Messaggio as M "
			+ "JOIN ContenutoUtente as C ON C.id_contenutoUtente = M.id_contenutoUtente "
			+ "JOIN Utente as U ON U.user_name = C.id_utente " 
			+ "WHERE M.id_ticket = ?;";

	private static final String QUERY_INSERISCI_CONTENUTO = 
			"INSERT INTO ContenutoUtente (id_contenutoUtente, id_utente, testo, data_pubblicazione) VALUES (?, ?, ?, ?)";

	private static final String QUERY_INSERISCI_MESSAGGIO = 
			"INSERT INTO Messaggio (id_contenutoUtente, id_ticket) VALUES (?, ?)";

	private static final String QUERY_GET_MESSAGGI_NUOVI = 
			"SELECT U.user_name, U.email, U.pw, U.nome, U.cognome, C.testo, C.data_pubblicazione, C.id_contenutoUtente "
			+ "FROM Messaggio as M "
			+ "JOIN ContenutoUtente as C ON C.id_contenutoUtente = M.id_contenutoUtente "
			+ "JOIN Utente as U ON U.user_name = C.id_utente "
			+ "WHERE M.id_ticket = ? AND C.data_pubblicazione > ?";

	public MessaggioDAOdb() {
		super();
	}

	@Override
	public ArrayList<Messaggio> getMessaggiDaTicket(Ticket ticket_di_riferimento) {
		ArrayList<Messaggio> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		ResultSet resultset;
		
		try {
			statement = conn.prepareStatement(QUERY_GET_MESSAGGI_DA_TICKET);
			statement.setString(1, ticket_di_riferimento.getId_ticket());
			resultset = statement.executeQuery();
			
			while(resultset.next()) {
				Utente autore = new UtenteGenerico(resultset.getString(1), resultset.getString(2), resultset.getString(3), resultset.getString(4), resultset.getString(5));
				LocalDateTime data = resultset.getTimestamp(7).toLocalDateTime();
				Messaggio msg = new Messaggio(resultset.getString(8), autore, resultset.getString(6), data, ticket_di_riferimento);
				result.add(msg);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseManager.closeConnection(conn);
		}
		
		return result;
	}

	@Override
	public boolean inserisciMessaggioInTicketRiferimento(Messaggio m) {
		Connection conn = DatabaseManager.getConnection();
		boolean success = false;

		try {
			DatabaseManager.setAutoCommit(conn, false);

			PreparedStatement psContenuto = conn.prepareStatement(QUERY_INSERISCI_CONTENUTO);
			psContenuto.setString(1, m.getId_contenuto_utente());
			psContenuto.setString(2, m.getAutore().getUser_name());
			psContenuto.setString(3, m.getTesto());
			psContenuto.setObject(4, m.getData_pubblicazione());
			psContenuto.executeUpdate();

			PreparedStatement psMessaggio = conn.prepareStatement(QUERY_INSERISCI_MESSAGGIO);
			psMessaggio.setString(1, m.getId_contenuto_utente());
			psMessaggio.setString(2, m.getTicket_di_riferimento().getId_ticket());
			psMessaggio.executeUpdate();

			DatabaseManager.commitConnection(conn);
			success = true;

		} catch (SQLException e) {
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
		ArrayList<Messaggio> result = new ArrayList<>();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement; 
		ResultSet resultset;
		
		try {
			statement = conn.prepareStatement(QUERY_GET_MESSAGGI_NUOVI);
			statement.setString(1, ticket_di_riferimento.getId_ticket());
			statement.setObject(2, data_ultimo_messaggio);
			resultset = statement.executeQuery();
			
			while(resultset.next()) {
				Utente autore = new UtenteGenerico(resultset.getString(1), resultset.getString(2), resultset.getString(3), resultset.getString(4), resultset.getString(5));
				LocalDateTime data = resultset.getTimestamp(7).toLocalDateTime();
				Messaggio msg = new Messaggio(resultset.getString(8), autore, resultset.getString(6), data, ticket_di_riferimento);
				result.add(msg);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseManager.closeConnection(conn);
		}

		return result;
	}

}