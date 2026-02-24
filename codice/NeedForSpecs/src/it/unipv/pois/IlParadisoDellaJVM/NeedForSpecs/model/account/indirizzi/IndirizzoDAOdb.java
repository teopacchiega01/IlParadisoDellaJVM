package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;

public class IndirizzoDAOdb implements IIndirizzoDAO {

	private static final String QUERY_GET_INDIRIZZO = 
			"SELECT i.* FROM Indirizzo i " +
			"JOIN UtenteGenerico ug ON i.id_indirizzo = ug.id_indirizzo " +
			"WHERE ug.user_name = ?";
	
	private static final String QUERY_INSERT_INDIRIZZO = 
			"INSERT INTO Indirizzo (id_indirizzo, via, civico, cap, provincia, citta) " +
			"VALUES (?, ?, ?, ?, ?, ?)";

	@Override
	public Indirizzo getIndirizzo(UtenteGenerico utente) {
		Indirizzo indirizzoTrovato = null;

		try (Connection conn = DatabaseManager.getConnection();
			 PreparedStatement ps = conn.prepareStatement(QUERY_GET_INDIRIZZO)) {

			ps.setString(1, utente.getUser_name()); 

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					indirizzoTrovato = new Indirizzo();
					indirizzoTrovato.setIdIndirizzo(rs.getString("id_indirizzo"));
					indirizzoTrovato.setVia(rs.getString("via"));
					indirizzoTrovato.setCivico(rs.getString("civico"));
					indirizzoTrovato.setCap(rs.getString("cap"));
					indirizzoTrovato.setProvincia(rs.getString("provincia"));
					indirizzoTrovato.setCitta(rs.getString("citta"));
				}
			}

		} catch (SQLException e) {
			System.err.println("Errore recupero indirizzo: " + e.getMessage());
		}

		return indirizzoTrovato;
	}

	@Override
	public boolean inserisciIndirizzo(Indirizzo indirizzo) {
		try (Connection conn = DatabaseManager.getConnection();
			 PreparedStatement ps = conn.prepareStatement(QUERY_INSERT_INDIRIZZO)) {

			ps.setString(1, indirizzo.getIdIndirizzo());
			ps.setString(2, indirizzo.getVia());
			ps.setString(3, indirizzo.getCivico());
			ps.setString(4, indirizzo.getCap());
			ps.setString(5, indirizzo.getProvincia());
			ps.setString(6, indirizzo.getCitta());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Errore inserimento indirizzo: " + e.getMessage());
			return false;
		}
	}
}