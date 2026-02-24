package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.IUtenteDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.IProdottoDAO;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;

//	@Author teopacchiega

public class AnnuncioDAOdb implements IAnnuncioDAO {
	private final static String QUERY_GET_ANNUNCIO_FROM_UD = "SELECT A.id_annuncio, A.id_utente_venditore, A.id_prodotto, A.prezzo,  "
			+ "FROM Annuncio AS A JOIN Utente AS U ON A.id_utente_venditore = U.user_name"
			+ "JOIN Prodotto AS P ON A.id_prodotto = P.id_prodotto"
			+ "WHERE id_annuncio=? "
			+ "AND A.id_ordine IS NULL;";
	private final static String QUERY_GET_ANNUNCI = "SELECT id_annuncio, id_utente_venditore, id_prodotto, prezzo "
			+ "FROM Annuncio "
			+ "WHERE id_ordine IS NULL;";
	private final static String QUERY_INSERIMENTO = "INSERT INTO Annuncio (id_annuncio, id_utente_venditore, id_prodotto, prezzo) "
			+ "VALUES (?, ?, ?, ?);";
	private final static String QUERY_RIMOZIONE = "DELETE FROM Annuncio WHERE id_annuncio = ?;";

	private IUtenteDAO utente_dao;
	private IProdottoDAO prodotto_dao;




	public AnnuncioDAOdb() {
		super();
		this.utente_dao = DAOFactory.getInstance().getUtenteDAO();
		this.prodotto_dao = DAOFactory.getInstance().getProdottoDAO();
	}

	@Override
	public Annuncio getAnnuncioFromId(String id_annuncio) {

		Connection conn = DatabaseManager.getConnection();
		PreparedStatement pr_stat;
		ResultSet res_set;
		Annuncio annuncio_trovato = new Annuncio();

		try {
			pr_stat = conn.prepareStatement(QUERY_GET_ANNUNCIO_FROM_UD);

			pr_stat.setString(1, id_annuncio);

			res_set = pr_stat.executeQuery();

			while(res_set.next()) {
				String id_annuncio_trovato = res_set.getString("id_annuncio");
				String id_utente_venditore_trovato = res_set.getString("id_utente_venditore");
				String id_prodotto_trovato = res_set.getString("id_prodotto");
				double prezzo_trovato = res_set.getDouble("prezzo");
				String id_ordine_trovato = res_set.getString("id_ordine");

				UtenteGenerico utente_trovato = (UtenteGenerico)utente_dao.getUtenteFromId(id_utente_venditore_trovato);
				Prodotto prodotto_trovato = prodotto_dao.getProdottoFromId(id_prodotto_trovato);
				annuncio_trovato.setId_annuncio(id_annuncio_trovato);
				annuncio_trovato.setVenditore(utente_trovato);
				annuncio_trovato.setPrezzo(prezzo_trovato);
				return annuncio_trovato;
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
		ArrayList<Annuncio> lista_annunci = new ArrayList<>();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement pr_stat = null;
		ResultSet res_set = null;

		try {
			pr_stat = conn.prepareStatement(QUERY_GET_ANNUNCI);
			res_set = pr_stat.executeQuery();

			while(res_set.next()) {
				String id_annuncio_trovato = res_set.getString("id_annuncio");
				String id_utente_venditore_trovato = res_set.getString("id_utente_venditore");
				String id_prodotto_trovato = res_set.getString("id_prodotto");
				double prezzo_trovato = res_set.getDouble("prezzo");

				Annuncio annuncio = new Annuncio();
				annuncio.setId_annuncio(id_annuncio_trovato);
				annuncio.setPrezzo(prezzo_trovato);

				UtenteGenerico venditore = (UtenteGenerico)utente_dao.getUtenteFromId(id_utente_venditore_trovato);
				Prodotto prodotto = prodotto_dao.getProdottoFromId(id_prodotto_trovato);
				annuncio.setVenditore(venditore);
				annuncio.setProdotto_in_vendita(prodotto);

				lista_annunci.add(annuncio);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (res_set != null) res_set.close(); } catch (Exception e) {};
			try { if (pr_stat != null) pr_stat.close(); } catch (Exception e) {};
		}

		DatabaseManager.closeConnection(conn);
		return lista_annunci;
	}

	@Override
	public boolean inserisciAnnuncio(Annuncio annuncio_da_inserire) {
		boolean inserito_con_successo = false;
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement pr_stat = null;

		try {
			pr_stat = conn.prepareStatement(QUERY_INSERIMENTO);

			pr_stat.setString(1, annuncio_da_inserire.getId_annuncio());
			pr_stat.setString(2, annuncio_da_inserire.getVenditore().getUser_name()); 
			pr_stat.setString(3, annuncio_da_inserire.getProdotto_in_vendita().getId_prodotto());
			pr_stat.setDouble(4, annuncio_da_inserire.getPrezzo());
			int righeModificate = pr_stat.executeUpdate();

			if (righeModificate > 0) {
				inserito_con_successo = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (pr_stat != null) pr_stat.close(); } catch (Exception e) {};
		}

		DatabaseManager.closeConnection(conn);
		return inserito_con_successo;
	}

	@Override
	public boolean rimuoviAnnuncio(Annuncio annuncio_da_rimuovere) {

		if (annuncio_da_rimuovere == null || annuncio_da_rimuovere.getId_annuncio() == null) {
			return false;
		}

		boolean rimosso_con_successo = false;
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement pr_stat = null;

		try {
			pr_stat = conn.prepareStatement(QUERY_RIMOZIONE);
			pr_stat.setString(1, annuncio_da_rimuovere.getId_annuncio());
			int righe_eliminate = pr_stat.executeUpdate();
			if (righe_eliminate > 0) {
				rimosso_con_successo = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (pr_stat != null) pr_stat.close(); } catch (Exception e) {};
		}

		DatabaseManager.closeConnection(conn);
		return rimosso_con_successo;
	}
}

