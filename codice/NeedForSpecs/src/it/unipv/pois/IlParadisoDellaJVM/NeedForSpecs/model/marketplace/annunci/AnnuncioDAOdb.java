package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;

//	@Author teopacchiega

public class AnnuncioDAOdb implements IAnnuncioDAO {

	@Override
	public Annuncio getAnnuncioFromId(String id_annuncio) {
		
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement pr_stat;
		ResultSet res_set;
		String query = "SELECT A.id_annuncio, A.id_utente_venditore, A.id_prodotto, A.prezzo,  "
				+ "FROM Annuncio AS A JOIN Utente AS U ON A.id_utente_venditore = U.user_name"
				+ "JOIN Prodotto AS P ON A.id_prodotto = P.id_prodotto"
				+ "WHERE id_annuncio=?;";	
		Annuncio annuncio_trovato;
		
		try {
			pr_stat = conn.prepareStatement(query);
			
			pr_stat.setString(1, id_annuncio);
			
			res_set = pr_stat.executeQuery();
			
			while(res_set.next()) {
				String id_annuncio_trovato = res_set.getString("id_annuncio");
				String id_utente_venditore_trovato = res_set.getString("id_utente_venditore");
				String id_prodotto_trovato = res_set.getString("id_prodotto");
				double prezzo_trovato = res_set.getDouble("prezzo");
				String id_ordine_trovato = res_set.getString("id_ordine");
				
				// getUtente
				// getProdotto
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		DatabaseManager.closeConnection(conn);
		return null;
	}

	@Override
	public ArrayList<Annuncio> getAnnunci() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean inserisciAnnuncio(Annuncio annuncio_da_inserire) {
		// TODO Auto-generated method stub
		return false;
	}

}
