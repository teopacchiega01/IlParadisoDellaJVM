package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DatabaseManager;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.AspettiTecnici;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;

/**
 * @author teopacchiega
 */
public class ProdottoDAOdb implements IProdottoDAO {
	
	private static final String QUERY_GET_COMPONENTI =  "SELECT P.id_prodotto, P.prezzo, C.marca, C.modello, C.potenza_richiesta, C.tipo_componente, A.aspetto_tecnico, A.valore "
			+ "FROM Prodotto AS P JOIN Componente AS C ON P.id_prodotto = C.id_prodotto "
			+ "LEFT JOIN Aspetto_Tecnico AS A ON P.id_prodotto = A.id_prodotto "
			+ "ORDER BY P.id_prodotto;";
	
	
			
	private static final String QUERY_GET_BUILD = "SELECT P.id_prodotto, P.prezzo, B.nome "
			+ "FROM Prodotto AS P "
			+ "JOIN Build AS B ON P.id_prodotto = B.id_prodotto "
			+ "ORDER BY P.id_prodotto;";
			
	private static final String QUERY_POPOLA_BUILD = "SELECT id_componente FROM Composizione WHERE id_build = ?";
	
	private static final String QUERY_INSERIMENTO_PRODOTTO = "INSERT INTO Prodotto (id_prodotto, prezzo, tipo) VALUES (?, ?, ?)";
	private static final String QUERY_INSERIMENTO_COMPONENTE = "INSERT INTO Componente (id_prodotto, marca, modello, tipo_componente, potenza_richiesta) VALUES (?, ?, ?, ?, ?)";
	private static final String QUERY_INSERIMENTO_ASPETTO_TECNICO = "INSERT INTO Aspetto_Tecnico (id_prodotto, aspetto_tecnico, valore) VALUES (?, ?, ?)";
	
	private static final String QUERY_INSERIMENTO_BUILD = "INSERT INTO Build (id_prodotto, nome) VALUES (?, ?)";
	private static final String QUERY_INSERIMENTO_COMPOSIZIONE = "INSERT INTO Composizione (id_build, id_componente) VALUES (?, ?)";
	
	@Override
	public Prodotto getProdottoFromId(String id_prodotto) {
		ArrayList<Prodotto> catalogo = getComponenti(); 
	    
	    for (Prodotto p : catalogo) {
	        if (p.getId_prodotto().equals(id_prodotto)) {
	            return p;
	        }
	    }
	    
	    ArrayList<Prodotto> build = getBuild();
	    for (Prodotto p : build) {
	        if (p.getId_prodotto().equals(id_prodotto)) {
	        	popolaBuild((Build)p, catalogo);
	            return p;
	        }
	    }
	    
	    return null;
	}

	@Override
	public ArrayList<Prodotto> getProdotti() {
		ArrayList<Prodotto> catalogo = new ArrayList<>();
		ArrayList<Prodotto> componenti = getComponenti();
		ArrayList<Prodotto> build = getBuild();

		for (Prodotto p : build) {
			if (p.getTipologia() == TipologiaProdotto.BUILD) {
				Build b = (Build) p;
				popolaBuild(b, componenti);
			}
		}

		catalogo.addAll(componenti);
		catalogo.addAll(build);

		return catalogo;
	}

	public ArrayList<Prodotto> getComponenti() {
		ArrayList<Prodotto> listaRisultato = new ArrayList<>();

		try (Connection conn = DatabaseManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(QUERY_GET_COMPONENTI);
				ResultSet rs = ps.executeQuery()) {

			String idCorrente = null;
			double prezzoCorrente = 0;
			String marcaCorrente = null;
			String modelloCorrente = null;
			int potenzaCorrente = 0;
			TipoComponente tipoCorrente = null;

			HashMap<String, String> specificheAccumulate = new HashMap<>();

			while (rs.next()) {
				String idLetto = rs.getString("id_prodotto");

				if (idCorrente != null && !idCorrente.equals(idLetto)) {
					Prodotto pezzoFinito = costruisciComponenteConFactory(
							idCorrente, prezzoCorrente, marcaCorrente, modelloCorrente, 
							potenzaCorrente, tipoCorrente, specificheAccumulate
							);
					listaRisultato.add(pezzoFinito);
					specificheAccumulate.clear();
				}

				idCorrente = idLetto;
				prezzoCorrente = rs.getDouble("prezzo");
				marcaCorrente = rs.getString("marca");
				modelloCorrente = rs.getString("modello");
				potenzaCorrente = rs.getInt("potenza_richiesta");
				tipoCorrente = TipoComponente.valueOf(rs.getString("tipo_componente"));

				String nomeAspetto = rs.getString("aspetto_tecnico");
				String valoreAspetto = rs.getString("valore");
				specificheAccumulate.put(nomeAspetto, valoreAspetto);
			}

			if (idCorrente != null) {
				Prodotto ultimoPezzo = costruisciComponenteConFactory(
						idCorrente, prezzoCorrente, marcaCorrente, modelloCorrente, 
						potenzaCorrente, tipoCorrente, specificheAccumulate
						);
				listaRisultato.add(ultimoPezzo);
			}

		} catch (Exception e) {
			System.err.println("Errore Database (Componenti): " + e.getMessage());
			e.printStackTrace();
		}

		return listaRisultato;
	}
	
	public ArrayList<Prodotto> getBuild() {
		ArrayList<Prodotto> listaBuild = new ArrayList<>();

		try (Connection conn = DatabaseManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(QUERY_GET_BUILD);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String id = rs.getString("id_prodotto");
				double prezzo = rs.getDouble("prezzo");
				String nome = rs.getString("nome");

				Prodotto nuovaBuild = ProdottiFactory.creaBuild(nome);
				nuovaBuild.setPrezzo(prezzo);
				nuovaBuild.setId_prodotto(id);

				listaBuild.add(nuovaBuild);
			}

		} catch (Exception e) {
			System.err.println("Errore DAO Build: " + e.getMessage());
		}

		return listaBuild;
	}

	public void popolaBuild(Build buildDaRiempire, ArrayList<Prodotto> catalogoComponenti) {

		try (Connection conn = DatabaseManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(QUERY_POPOLA_BUILD)) {

			ps.setString(1, buildDaRiempire.getId_prodotto());

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String idComponenteTrovato = rs.getString("id_componente");

					for (Prodotto prodotto : catalogoComponenti) {
						if (prodotto.getId_prodotto().equals(idComponenteTrovato) && 
								prodotto.getTipologia() == TipologiaProdotto.COMPONENTE) {

							Componente comp = (Componente) prodotto;
							try {
								buildDaRiempire.aggiungiComponente(comp);
							} catch (Exception e) {
								System.err.println("Errore di integrità nella Build dal DB: " + e.getMessage());
							}
							break; 
						}
					}
				}
			}

		} catch (Exception e) {
			System.err.println("Errore nel recupero dei componenti della build: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean inserisciProdotto(Prodotto prodotto_da_inserire) {
		Connection conn = null;

		try {
			conn = DatabaseManager.getConnection();
			conn.setAutoCommit(false); 

			try (PreparedStatement psProd = conn.prepareStatement(QUERY_INSERIMENTO_PRODOTTO)) {
				psProd.setString(1, prodotto_da_inserire.getId_prodotto());
				psProd.setDouble(2, prodotto_da_inserire.getPrezzo());
				psProd.setString(3, prodotto_da_inserire.getTipologia().name()); 
				psProd.executeUpdate();
			}

			if (prodotto_da_inserire.getTipologia() == TipologiaProdotto.COMPONENTE) {
				Componente comp = (Componente) prodotto_da_inserire;

				try (PreparedStatement psComp = conn.prepareStatement(QUERY_INSERIMENTO_COMPONENTE)) {
					psComp.setString(1, comp.getId_prodotto());
					psComp.setString(2, comp.getMarca());
					psComp.setString(3, comp.getModello());
					psComp.setString(4, comp.getTipo().name());
					psComp.setInt(5, comp.getPotenza()); 
					psComp.executeUpdate();
				}

				if (comp.getScheda_tecnica() != null && !comp.getScheda_tecnica().isEmpty()) {
					try (PreparedStatement psAspetto = conn.prepareStatement(QUERY_INSERIMENTO_ASPETTO_TECNICO)) {
						for (Map.Entry<AspettiTecnici, String> riga : comp.getScheda_tecnica().entrySet()) {
							psAspetto.setString(1, comp.getId_prodotto());
							psAspetto.setString(2, riga.getKey().name());
							psAspetto.setString(3, riga.getValue());
							psAspetto.addBatch();
						}
						psAspetto.executeBatch();
					}
				}

			} else if (prodotto_da_inserire.getTipologia() == TipologiaProdotto.BUILD) {
				Build build = (Build) prodotto_da_inserire;
				
				// Salvo la Build
				try (PreparedStatement psBuild = conn.prepareStatement(QUERY_INSERIMENTO_BUILD)) {
					psBuild.setString(1, build.getId_prodotto());
					psBuild.setString(2, build.getNome());
					psBuild.executeUpdate();
				}
				
				if (build.getComponenti() != null && !build.getComponenti().isEmpty()) {
					try (PreparedStatement psComp = conn.prepareStatement(QUERY_INSERIMENTO_COMPOSIZIONE)) {
						for (ArrayList<Componente> lista_componenti : build.getComponenti().values()) {
							for (Componente c : lista_componenti) {
								psComp.setString(1, build.getId_prodotto());
								psComp.setString(2, c.getId_prodotto());
								psComp.addBatch();
							}
						}
						psComp.executeBatch();
					}
				}
			}

			conn.commit();
			return true;

		} catch (SQLException e) {
			System.err.println("Errore durante l'inserimento. Annullamento in corso... " + e.getMessage());

			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					System.err.println("Errore catastrofico nel rollback: " + ex.getMessage());
				}
			}
			return false;

		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					DatabaseManager.closeConnection(conn); 
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Prodotto costruisciComponenteConFactory(String id, double prezzo, String marca, 
			String modello, int potenza, TipoComponente tipo, 
			Map<String, String> specificheDB) {

		ArrayList<String> arrayPerFactory = new ArrayList<>();

		switch (tipo) {
		case MOBO:
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.SOCKET_CPU.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.FORM_FACTOR_MOBO.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.TIPO_RAM.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.N_MODULI_RAM.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.TIPO_SLOT_PCIE.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.N_SLOT_PCIE.name()));
			break;
		case CPU:
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.SOCKET_CPU.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.FREQUENZA_CLOCK_CPU.name()));
			break;
		case GPU:
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.TIPO_SLOT_PCIE.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.N_SLOT_PCIE.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.VRAM_GB.name()));
			break;
		case RAM:
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.TIPO_RAM.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.FREQUENZA.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.N_MODULI_RAM.name()));
			arrayPerFactory.add(specificheDB.get(AspettiTecnici.DIM_SINGOLO_MODULO_RAM.name()));
			break;
		case PSU:
			arrayPerFactory = null;
			break;

		}

		Prodotto nuovoComponente = ProdottiFactory.creaComponente(prezzo, marca, modello, tipo, arrayPerFactory, potenza);
		nuovoComponente.setId_prodotto(id); 

		return nuovoComponente;
	}
}