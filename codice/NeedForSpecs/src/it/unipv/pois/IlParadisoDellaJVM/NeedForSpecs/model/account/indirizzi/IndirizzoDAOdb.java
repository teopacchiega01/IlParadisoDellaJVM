package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;

//	@Author teopacchiega

public class IndirizzoDAOdb implements IIndirizzoDAO {
	private static final String QUERY_GET_INDIRIZZO = "SELECT * FROM Indirizzo WHERE id_indirizzo = ?";
	private static final String QUERY_INSERT_INDIRIZZO = "INSERT INTO Indirizzo (id_indirizzo, via, civico, cap, provincia, citta) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";

	@Override
	public Indirizzo getIndirizzo(UtenteGenerico utente) {
		Indirizzo indirizzoTrovato = null;

		try (Connection conn = DatabaseManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(QUERY_GET_INDIRIZZO)) {

			// Presumo che l'utente sappia qual è l'ID del suo indirizzo!
			ps.setString(1, utente.getInd_di_spedizione().getIdIndirizzo()); 

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					// Uso il costruttore vuoto e i setter per non sovrascrivere l'ID con uno nuovo generato a caso!
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
			System.err.println("Errore durante il recupero dell'indirizzo: " + e.getMessage());
		}

		return indirizzoTrovato;
	}

	@Override
	public boolean inserisciIndirizzo(Indirizzo indirizzo_da_inserire) {
		try (Connection conn = DatabaseManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(QUERY_INSERT_INDIRIZZO)) {

			ps.setString(1, indirizzo_da_inserire.getIdIndirizzo());
			ps.setString(2, indirizzo_da_inserire.getVia());
			ps.setString(3, indirizzo_da_inserire.getCivico());
			ps.setString(4, indirizzo_da_inserire.getCap());
			ps.setString(5, indirizzo_da_inserire.getProvincia());
			ps.setString(6, indirizzo_da_inserire.getCitta());

			int righeModificate = ps.executeUpdate();
			return righeModificate > 0;

		} catch (SQLException e) {
			System.err.println("Errore durante l'inserimento dell'indirizzo: " + e.getMessage());
			return false;
		}
	}

}
