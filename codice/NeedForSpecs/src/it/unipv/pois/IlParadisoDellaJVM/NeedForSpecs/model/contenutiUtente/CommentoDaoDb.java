package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;

public class CommentoDaoDb implements ICommentoDAO {

	private String schema;

	public CommentoDaoDb() {
		super();
		this.schema = "Commento";
	}

	@Override
	public ArrayList<Commento> getCommenti(Post p) {
		// TODO Auto-generated method stub

		ArrayList<Commento> commenti = new ArrayList<Commento>();

		//CONNESSIONE AL DB
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


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}

		DatabaseManager.closeConnection(conn);

		return commenti;

	}


	@Override
	public ArrayList<Commento> getCommentiDiCommenti(Post p, Commento c) {
		// TODO Auto-generated method stub

		ArrayList<Commento> commenti = new ArrayList<Commento>();

		//CONNESSIONE AL DB
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			return null;
		}

	}


}
