package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;

//	@Author teopacchiega

public class OrdineDAOdb implements IOrdineDAO {
	private static final String QUERY_INSERT_ORDINE = "INSERT INTO Ordine (id_ordine, id_utente_acquirente) VALUES (?, ?)";
	private static final String QUERY_UPDATE_ANNUNCIO = "UPDATE Annuncio SET id_ordine = ? WHERE id_annuncio = ?";

	@Override
	public boolean inserisciOrdine(Ordine ordine_da_inserire) {

		Connection conn = null;
		try {
			conn = DatabaseManager.getConnection();
			conn.setAutoCommit(false);
			try (PreparedStatement ps_ordine = conn.prepareStatement(QUERY_INSERT_ORDINE)) {
				ps_ordine.setString(1, ordine_da_inserire.getId_ordine()); 
				ps_ordine.setString(2, ordine_da_inserire.getAcquirente().getUser_name());
				ps_ordine.executeUpdate();
			}
			ArrayList<Annuncio> prodotti_comprati = ordine_da_inserire.getProdotti_acquistati();
			if (prodotti_comprati != null && !prodotti_comprati.isEmpty()) {
				try (PreparedStatement ps_annuncio = conn.prepareStatement(QUERY_UPDATE_ANNUNCIO)) {
					for (Annuncio annuncio : prodotti_comprati) {
						ps_annuncio.setString(1, ordine_da_inserire.getId_ordine());
						ps_annuncio.setString(2, annuncio.getId_annuncio()); 
						ps_annuncio.addBatch();
					}

					ps_annuncio.executeBatch();
				}
			} else {
				throw new SQLException("Impossibile creare un ordine senza prodotti.");
			}

			conn.commit();
			return true;

		} catch (SQLException e) {
			System.err.println("Errore critico durante l'acquisto. Transazione annullata: " + e.getMessage());
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					System.err.println("Errore nel rollback: " + ex.getMessage());
				}
			}
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
