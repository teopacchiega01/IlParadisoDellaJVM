package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;

public class CartaDAOdb implements ICartaDAO {

	private static final String QUERY_GET_CARTA = 
			"SELECT c.* FROM Carta c " +
			"JOIN UtenteGenerico ug ON c.numero_carta = ug.numero_carta " +
			"WHERE ug.user_name = ?";
	
	private static final String QUERY_INSERT_CARTA = 
			"INSERT INTO Carta (numero_carta, data_scadenza, cvv) VALUES (?, ?, ?)";

	@Override
	public Carta getCarta(UtenteGenerico utente) {
		Carta cartaTrovata = null;

		try (Connection conn = DatabaseManager.getConnection();
			 PreparedStatement ps = conn.prepareStatement(QUERY_GET_CARTA)) {

			ps.setString(1, utente.getUser_name());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					cartaTrovata = new Carta();
					cartaTrovata.setNumeroCarta(rs.getString("numero_carta"));
					cartaTrovata.setDataScadenza(rs.getDate("data_scadenza").toLocalDate());
					cartaTrovata.setCvv(rs.getString("cvv"));
				}
			}

		} catch (SQLException e) {
			System.err.println("Errore recupero carta: " + e.getMessage());
		}

		return cartaTrovata;
	}

	@Override
	public boolean inserisciCarta(Carta carta) {
		try (Connection conn = DatabaseManager.getConnection();
			 PreparedStatement ps = conn.prepareStatement(QUERY_INSERT_CARTA)) {

			ps.setString(1, carta.getNumeroCarta());
			ps.setDate(2, Date.valueOf(carta.getDataScadenza()));
			ps.setString(3, carta.getCvv());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Errore inserimento carta: " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean aggiornaCartaUtente(String username, Carta nuovaCarta) {
		// TODO Auto-generated method stub
		String queryInsertCarta = "INSERT IGNORE INTO Carta (numero_carta, data_scadenza, cvv) VALUES (?, ?, ?)";
	    String queryUpdateUtente = "UPDATE UtenteGenerico SET numero_carta = ? WHERE user_name = ?";

	    try (Connection conn = DatabaseManager.getConnection()) {
	        conn.setAutoCommit(false); 
	        try (PreparedStatement ps1 = conn.prepareStatement(queryInsertCarta)) {
	            ps1.setString(1, nuovaCarta.getNumeroCarta());
	            ps1.setDate(2, java.sql.Date.valueOf(nuovaCarta.getDataScadenza()));
	            ps1.setString(3, nuovaCarta.getCvv());
	            ps1.executeUpdate();
	        }
	        try (PreparedStatement ps2 = conn.prepareStatement(queryUpdateUtente)) {
	            ps2.setString(1, nuovaCarta.getNumeroCarta());
	            ps2.setString(2, username);
	            ps2.executeUpdate();
	        }

	        conn.commit();
	        return true;

	    } catch (SQLException e) {
	        System.err.println("Errore aggiornamento carta: " + e.getMessage());
	        return false;
	    }
	}
}