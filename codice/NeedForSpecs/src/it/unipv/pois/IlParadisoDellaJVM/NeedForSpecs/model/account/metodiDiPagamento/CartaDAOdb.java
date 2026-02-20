package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;

//	@Author teopacchiega

public class CartaDAOdb implements ICartaDAO {
	private static final String QUERY_GET_CARTA = "SELECT * FROM Carta WHERE numero_carta = ?";
    private static final String QUERY_INSERT_CARTA = "INSERT INTO Carta (numero_carta, data_scadenza, cvv) VALUES (?, ?, ?)";
    
	@Override
	public Carta getCarta(UtenteGenerico utente) {
		Carta cartaTrovata = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(QUERY_GET_CARTA)) {

            // Come prima, presumo che l'utente abbia l'ID della carta salvato al suo interno
            ps.setString(1, utente.getMetodo_di_pagamento().getNumeroCarta());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cartaTrovata = new Carta();
                    cartaTrovata.setNumeroCarta(rs.getString("numero_carta"));
                    
                    // CONVERSIONE DA SQL A JAVA:
                    // Prendo il java.sql.Date dal Resultset e lo converto subito nel nostro amato LocalDate
                    cartaTrovata.setDataScadenza(rs.getDate("data_scadenza").toLocalDate());
                    
                    cartaTrovata.setCvv(rs.getString("cvv"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore durante il recupero della carta: " + e.getMessage());
        }

        return cartaTrovata;
	}

	@Override
	public boolean inserisciCarta(Carta carta_da_inserire) {
		try (Connection conn = DatabaseManager.getConnection();
	             PreparedStatement ps = conn.prepareStatement(QUERY_INSERT_CARTA)) {

	            ps.setString(1, carta_da_inserire.getNumeroCarta());
	            
	            // CONVERSIONE DA JAVA A SQL:
	            // Trasformo il LocalDate in java.sql.Date prima di metterlo nella query!
	            ps.setDate(2, Date.valueOf(carta_da_inserire.getDataScadenza()));
	            
	            ps.setString(3, carta_da_inserire.getCvv());

	            int righeModificate = ps.executeUpdate();
	            return righeModificate > 0;

	        } catch (SQLException e) {
	            System.err.println("Errore durante l'inserimento della carta: " + e.getMessage());
	            return false;
	        }
	}

}
