package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;

//	@Author teopacchiega

public class OrdineDAOdb implements IOrdineDAO {
	// Costanti per le query (pulizia estrema!)
    private static final String QUERY_INSERT_ORDINE = "INSERT INTO Ordine (id_ordine, id_utente_acquirente) VALUES (?, ?)";
    // Questa query aggiorna l'annuncio inserendo l'ID dell'ordine, segnandolo di fatto come "Venduto"
    private static final String QUERY_UPDATE_ANNUNCIO = "UPDATE Annuncio SET id_ordine = ? WHERE id_annuncio = ?";

    @Override
    public boolean inserisciOrdine(Ordine ordine_da_inserire) {
        
        Connection conn = null;

        try {
            conn = DatabaseManager.getConnection();
            
            // 1. INIZIO TRANSAZIONE: Disabilito l'autocommit perché tocco due tabelle diverse!
            conn.setAutoCommit(false);

            // --- FASE 1: INSERISCO L'ORDINE ---
            try (PreparedStatement psOrdine = conn.prepareStatement(QUERY_INSERT_ORDINE)) {
                
                // NOTA: Assumo che tu abbia aggiunto un getIdOrdine() alla classe Ordine!
                psOrdine.setString(1, ordine_da_inserire.getId_ordine()); 
                
                // Recupero lo username dell'acquirente
                psOrdine.setString(2, ordine_da_inserire.getAcquirente().getUser_name());
                
                psOrdine.executeUpdate();
            }

            // --- FASE 2: AGGIORNO GLI ANNUNCI (I prodotti acquistati) ---
            ArrayList<Annuncio> prodottiComprati = ordine_da_inserire.getProdotti_acquistati();
            
            // Controllo di sicurezza: l'ordine deve avere almeno un prodotto
            if (prodottiComprati != null && !prodottiComprati.isEmpty()) {
                
                try (PreparedStatement psAnnuncio = conn.prepareStatement(QUERY_UPDATE_ANNUNCIO)) {
                    
                    for (Annuncio annuncio : prodottiComprati) {
                        // Setto l'ID dell'ordine appena creato
                        psAnnuncio.setString(1, ordine_da_inserire.getId_ordine());
                        
                        // Cerco l'annuncio specifico tramite il suo ID
                        psAnnuncio.setString(2, annuncio.getId_annuncio()); 
                        
                        // Accodo la query nel Batch!
                        psAnnuncio.addBatch();
                    }
                    
                    // Eseguo tutte le UPDATE degli annunci in un colpo solo
                    psAnnuncio.executeBatch();
                }
            } else {
                // Se provano a inserire un ordine vuoto, blocco tutto
                throw new SQLException("Impossibile creare un ordine senza prodotti.");
            }

            // 3. TUTTO OK: Salvo definitivamente nel database!
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Errore critico durante l'acquisto. Transazione annullata: " + e.getMessage());
            
            // ROLLBACK: Se l'aggiornamento degli annunci fallisce, cancello anche l'ordine!
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Errore nel rollback: " + ex.getMessage());
                }
            }
            return false;
            
        } finally {
            // Ripristino la connessione
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close(); // Da togliere se usi un Connection Pool gestito altrove
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
