package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.ForumException;

public class CommentoDaoDb implements ICommentoDAO {

	private String schema;

	public CommentoDaoDb() {
		super();
		this.schema = "Commento";
	}

	@Override
	public ArrayList<Commento> getCommenti(Post p) throws ForumException{
		// TODO Auto-generated method stub

		ArrayList<Commento> commenti = new ArrayList<Commento>();

		 
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		ResultSet resultset;


		String query = "SELECT u.user_name, u.email, u.psw, u.nome, u.cognome, c.id_contenutoUtente, c.testo, c.data_pubblicazione"
				+ "FROM ContenutoUtente AS c"
				+ "JOIN Commento AS com ON c.id_contenutoUtente = com.id_commento"
				+ "JOIN Post AS p ON com.id_post_di_riferimento = p.id_post"
				+ "JOIN Utente AS u ON u.id_utente = c.id_utente"
				+ "WHERE p.titolo = ?;";
		;

		try {

			statement = conn.prepareStatement(query);

			statement.setString(1, p.getTitolo());

			resultset = statement.executeQuery(query);

			while(resultset.next()) {

				Utente u = new Utente(resultset.getString(1), resultset.getString(2), resultset.getString(3), resultset.getString(4),
						resultset.getString(5));

				LocalDateTime d = resultset.getTimestamp(8).toLocalDateTime();

				Commento c = new Commento(resultset.getString(6), u, resultset.getString(7), d, p, p);

				commenti.add(c);

			}

			conn.close();


		} catch (SQLException e) {

			throw new ForumException("errore caricamento dei commenti", e);

		}

		DatabaseManager.closeConnection(conn);

		return commenti;

	}


	@Override
	public ArrayList<Commento> getCommentiDiCommenti(Post p, Commento c) throws ForumException{
		// TODO Auto-generated method stub

		ArrayList<Commento> commenti = new ArrayList<Commento>();

		 
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		ResultSet resultset;

		String query = "SELECT u.user_name, u.email, u.psw, u.nome, u.cognome, c.id_contenutoUtente, c.testo, c.data_pubblicazione"
				+ "FROM ContenutoUtente AS c"
				+ "JOIN Commento AS com ON c.id_contenutoUtente = com.id"
				+ "JOIN Utente AS u ON u.id_utente = c.id_utente"
				+ "WHERE com.id_post_riferimento = ?"
				+ "AND com.id_contenuto_parent = ?"
				;

		try {

			statement = conn.prepareStatement(query);

			statement.setString(1, p.getId_contenuto_utente());
			statement.setString(2, c.getId_contenuto_utente());

			resultset = statement.executeQuery(query);

			while(resultset.next()) {

				Utente u = new Utente(resultset.getString(1), resultset.getString(2), resultset.getString(3), resultset.getString(4),
						resultset.getString(5));

				LocalDateTime d = resultset.getTimestamp(8).toLocalDateTime();

				Commento c2 = new Commento(resultset.getString(6), u, resultset.getString(7), d, p, c);

				commenti.add(c2);

			}

			return commenti;

		} catch (SQLException e) {
			
			throw new ForumException("errore caricamento dei commenti", e);

		}

	}


	@Override
	public boolean creaCommento(Commento c, Post p, ContenutoUtente parent) throws ForumException{

		String queryCommento = "INSERT INTO Commento VALUES (?, ?, ?)";
		String queryContenutoUtente = "INSERT INTO ContenutoUtente VALUES (?, ?, ?, ?)";

		//CONNESSIONE DB

		boolean success = false;
		Connection conn = DatabaseManager.getConnection();


		try {

			DatabaseManager.setAutoCommit(conn, false);

			PreparedStatement psComm = conn.prepareStatement(queryCommento);

			psComm.setString(1, c.getId_contenuto_utente());
			psComm.setString(2, p.getId_contenuto_utente());
			psComm.setString(3, parent.getId_contenuto_utente());

			psComm.executeUpdate();

			PreparedStatement psCu = conn.prepareStatement(queryContenutoUtente);

			psCu.setString(1, c.getId_contenuto_utente());
			psCu.setString(2, c.getAutore().getUser_name());
			psCu.setString(3, c.getTesto());
			psCu.setObject(4, c.getData_pubblicazione());

			psCu.executeUpdate();

			DatabaseManager.commitConnection(conn);
			success = true;

		} catch (SQLException e) {

			DatabaseManager.rollbackConnection(conn);
			
			throw new ForumException("Errore creazione commento", e);

		}finally {

			DatabaseManager.setAutoCommit(conn, true);
			DatabaseManager.closeConnection(conn);
		}

		return success;

	}

	@Override
	public boolean eliminaCommento(Commento c) throws ForumException{

		String queryCommento = "DELETE FROM Commento WHERE id_commento = ?";
		String queryContenutoUtente = "DELETE FROM ContenutiUtente WHERE id_contenutoUtente = ?";



		boolean success = false;
		Connection conn = DatabaseManager.getConnection();

		try {

			DatabaseManager.setAutoCommit(conn, false);

			PreparedStatement psComm = conn.prepareStatement(queryCommento);

			psComm.setString(1, c.getId_contenuto_utente());

			psComm.executeUpdate();

			PreparedStatement psCu = conn.prepareStatement(queryContenutoUtente);

			psCu.setString(1, c.getId_contenuto_utente());

			psCu.executeUpdate();

			DatabaseManager.commitConnection(conn);
			success = true;

		} catch (SQLException e) {

			DatabaseManager.rollbackConnection(conn);
			
			throw new ForumException("Errore eliminazione commento", e);

		}finally {

			DatabaseManager.setAutoCommit(conn, true);
			DatabaseManager.closeConnection(conn);
		}

		return success;



	}




}
