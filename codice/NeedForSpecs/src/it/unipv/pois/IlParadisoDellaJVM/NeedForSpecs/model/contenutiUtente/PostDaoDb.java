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

public class PostDaoDb implements IPostDAO {

	private String schema;

	public PostDaoDb() {
		super();
		this.schema = "Post";
	}

	@Override
	public ArrayList<Post> getPostUtente(Utente u) throws ForumException{
		// TODO Auto-generated method stub

		ArrayList<Post> post = new ArrayList<Post>();

		 
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		ResultSet resultset;


		String query = "SELECT c.id_contenuto_utente, c.testo, c.data_pubblicazione, p.titolo, p.sottotitolo"
				+ "FROM ContenutoUtente AS c"
				+ "JOIN Utente AS u ON c.id_utente = u.user_name"
				+ "JOIN Post AS p ON c.id_contenuto_utente = p.id_contenuto_utente"
				+ "WHERE u.user_name = ?" 
				;


		try {

			statement = conn.prepareStatement(query);

			statement.setString(1, u.getUser_name());

			resultset = statement.executeQuery(query);

			while(resultset.next()) {

				LocalDateTime d = resultset.getTimestamp(3).toLocalDateTime();

				Post p = new Post(resultset.getString(1), u, resultset.getString(2), d, resultset.getString(4), resultset.getString(5));

				post.add(p);

			}

			conn.close();




		} catch (SQLException e) {

			throw new ForumException("Errore caricamento dei post", e);

		}

		DatabaseManager.closeConnection(conn);

		return post;

	}


	@Override
	public ArrayList<Post> getPost() throws ForumException{
		// TODO Auto-generated method stub

		ArrayList<Post> post = new ArrayList<Post>();

		 
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement statement;
		ResultSet resultset;


		String query = "SELECT c.id_contenuto_utente, c.testo, c.data_pubblicazione, p.titolo, p.sottotitolo,"
				+ "u.user_name, u.email, u.pw, u.nome, u.cognome"
				+ "FROM ContenutoUtente AS c"
				+ "JOIN Utente AS u ON c.id_utente = u.user_name"
				+ "JOIN Post AS p ON c.id_contenuto_utente = p.id_contenuto_utente"
				+ ";"
				;


		try {

			statement = conn.prepareStatement(query);

			resultset = statement.executeQuery(query);

			while(resultset.next()) {

				LocalDateTime d = resultset.getTimestamp(3).toLocalDateTime();

				Utente u = new Utente(resultset.getString(6), resultset.getString(7),
						resultset.getString(8), resultset.getString(9), resultset.getString(10));

				Post p = new Post(resultset.getString(1), u, resultset.getString(2), d, resultset.getString(4), resultset.getString(5));

				post.add(p);

			}

			conn.close();




		} catch (SQLException e) {

			throw new ForumException("Errore caricamento dei post", e);

		}

		DatabaseManager.closeConnection(conn);

		return post;

	}


	@Override
	public boolean creaPost(Post p) throws ForumException{

		String queryPost = "INSERT INTO Post VALUES (?, ?, ?)";
		String queryContenutoUtente = "INSERT INTO ContenutoUtente VALUES (?, ?, ?, ?)";

		 
		ResultSet resultset;

		boolean success = false;
		Connection conn = DatabaseManager.getConnection();


		try {

			DatabaseManager.setAutoCommit(conn, false);

			PreparedStatement psPost = conn.prepareStatement(queryPost);

			psPost.setString(1, p.getId_contenuto_utente());
			psPost.setString(2, p.getTitolo());
			psPost.setString(3, p.getSottotitolo());

			psPost.executeUpdate();

			PreparedStatement psCu = conn.prepareStatement(queryContenutoUtente);

			psCu.setString(1, p.getId_contenuto_utente());
			psCu.setString(2, p.getAutore().getUser_name());
			psCu.setString(3, p.getTesto());
			psCu.setObject(4, p.getData_pubblicazione());

			psCu.executeUpdate();

			DatabaseManager.commitConnection(conn);
			success = true;

		} catch (SQLException e) {

			DatabaseManager.rollbackConnection(conn);
			
			throw new ForumException("Errore creazione post", e);

		}finally {

			DatabaseManager.setAutoCommit(conn, true);
			DatabaseManager.closeConnection(conn);
		}

		return success;

	}

	@Override
	public boolean eliminaPost(Post p) throws ForumException{

		String queryPost = "DELETE FROM Post WHERE contenuto_utente = ?";
		String queryContenutoUtente = "DELETE FROM ContenutiUtente WHERE id_contenutoUtente = ?";



		boolean success = false;
		Connection conn = DatabaseManager.getConnection();

		try {

			DatabaseManager.setAutoCommit(conn, false);

			PreparedStatement psPost = conn.prepareStatement(queryPost);

			psPost.setString(1, p.getId_contenuto_utente());

			psPost.executeUpdate();

			PreparedStatement psCu = conn.prepareStatement(queryContenutoUtente);

			psCu.setString(1, p.getId_contenuto_utente());

			psCu.executeUpdate();

			DatabaseManager.commitConnection(conn);
			success = true;

		} catch (SQLException e) {

			DatabaseManager.rollbackConnection(conn);
			
			throw new ForumException("Errore eliminazione post", e);

		}finally {

			DatabaseManager.setAutoCommit(conn, true);
			DatabaseManager.closeConnection(conn);
		}

		return success;










	}



}
