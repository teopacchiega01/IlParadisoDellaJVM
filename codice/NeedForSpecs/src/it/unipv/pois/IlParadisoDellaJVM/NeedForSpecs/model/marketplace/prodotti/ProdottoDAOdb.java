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

// @Author teopacchiega

public class ProdottoDAOdb implements IProdottoDAO {
	private static final String QUERY_GET_COMPONENTI =  "SELECT P.id_prodotto, P.prezzo, C.marca, C.modello, C.potenza, C.tipo_componente, A.aspetto_tecnico, A.valore "
			+ "FROM Prodotti AS P JOIN Componente AS C ON P.id_prodotto = C.id_prodotto "
			+ "JOIN Aspetto_Tecnico AS A ON P.id_prodotto = A.id_prodotto "
			+ "WHERE P.tipo_prodotto = 'COMPONENTE' "
			+ "ORDER BY P.id_prodotto";
	private static final String QUERY_GET_BUILD = "SELECT P.id_prodotto, P.prezzo, B.nome "
			+ "FROM Prodotti AS P "
			+ "JOIN Build AS B ON P.id_prodotto = B.id_prodotto "
			+ "WHERE P.tipo_prodotto = 'BUILD'";
	private static final String QUERY_POPOLA_BUILD = "SELECT id_componente FROM Composizione_Build WHERE id_build = ?";
	private static final String QUERY_INSERIMENTO_PRODOTTO = "INSERT INTO Prodotto (id_prodotto, prezzo, tipo_prodotto) VALUES (?, ?, ?)";
	private static final String QUERY_INSERIMENTO_COMPONENTE = "INSERT INTO Componente (id_prodotto, marca, modello, tipo_componente) VALUES (?, ?, ?, ?)";
	private static final String QUERY_INSERIMENTO_ASPETTO_TECNICO = "INSERT INTO Aspetto_Tecnico (id_prodotto, aspetto, valore) VALUES (?, ?, ?)";
	
	@Override
	public Prodotto getProdottoFromId(String id_prodotto) {
		// 1. Chiamo il metodo che scarica tutto il database (Componenti + Build)
	    ArrayList<Prodotto> catalogo = getComponenti(); 
	    
	    // 2. Scorro il catalogo alla ricerca del mio ID
	    for (Prodotto p : catalogo) {
	        if (p.getId_prodotto().equals(id_prodotto)) {
	            return p; // Trovato! Lo restituisco subito e fermo il ciclo
	        }
	    }
	    
	    // 3. Se arrivo qui, significa che il ciclo è finito e l'ID non esiste
	    return null;
	}

	@Override
	public ArrayList<Prodotto> getProdotti() {
		// 1. Creo la lista generale (polimorfica!)
		ArrayList<Prodotto> catalogo = new ArrayList<>();

		// 2. Chiamo il metodo che recupera i componenti
		ArrayList<Prodotto> componenti = getComponenti();

		// 3. Chiamo il metodo che recupera le build
		ArrayList<Prodotto> build = getBuild();

		for (Prodotto p : build) {
			if (p.getTipologia() == TipologiaProdotto.BUILD) {
				Build b = (Build) p;

				// Chiamo il metodo che abbiamo appena creato
				popolaBuild(b, componenti);
			}
		}

		// 4. Unisco tutto! (Prima i componenti, poi le build, esattamente come hai chiesto)
		catalogo.addAll(componenti);
		catalogo.addAll(build);

		// Ritorno il super-catalogo pronto per essere mostrato nel Marketplace!
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

				// IL CUORE DELL'ALGORITMO: L'ID è cambiato? (E non è il primissimo giro?)
				if (idCorrente != null && !idCorrente.equals(idLetto)) {

					// 1. IL COMPONENTE PRECEDENTE È FINITO! LO COSTRUISCO.
					Prodotto pezzoFinito = costruisciComponenteConFactory(
							idCorrente, prezzoCorrente, marcaCorrente, modelloCorrente, 
							potenzaCorrente, tipoCorrente, specificheAccumulate
							);
					listaRisultato.add(pezzoFinito);

					// 2. Svuoto la mappa per prepararla ad accogliere il nuovo componente
					specificheAccumulate.clear();
				}

				// Aggiorno i dati base (sovrascrivere con gli stessi dati non fa danni)
				idCorrente = idLetto;
				prezzoCorrente = rs.getDouble("prezzo");
				marcaCorrente = rs.getString("marca");
				modelloCorrente = rs.getString("modello");
				potenzaCorrente = rs.getInt("potenza");
				tipoCorrente = TipoComponente.valueOf(rs.getString("tipo_componente"));

				// Aggiungo la specifica letta in questa precisa riga
				String nomeAspetto = rs.getString("aspetto");
				String valoreAspetto = rs.getString("valore");
				specificheAccumulate.put(nomeAspetto, valoreAspetto);
			}

			// FUORI DAL CICLO: Non dimentichiamoci di salvare L'ULTIMO componente letto!
			if (idCorrente != null) {
				Prodotto ultimoPezzo = costruisciComponenteConFactory(
						idCorrente, prezzoCorrente, marcaCorrente, modelloCorrente, 
						potenzaCorrente, tipoCorrente, specificheAccumulate
						);
				listaRisultato.add(ultimoPezzo);
			}

		} catch (Exception e) {
			System.err.println("Errore Database: " + e.getMessage());
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

				// Uso la tua Factory!
				Prodotto nuovaBuild = ProdottiFactory.creaBuild(nome, prezzo);

				// Imposto il vero ID del Database
				nuovaBuild.setId_prodotto(id);

				// TODO: Qui, se il tuo DB lo prevede, potresti dover fare un'altra query 
				// per estrarre i componenti di questa specifica build e aggiungerli!

				listaBuild.add(nuovaBuild);
			}

		} catch (Exception e) {
			System.err.println("Errore DAO Build: " + e.getMessage());
		}

		return listaBuild;
	}

	//TODO ricontrollare questo codice
	public void popolaBuild(Build buildDaRiempire, ArrayList<Prodotto> catalogoComponenti) {

		// 1. Query sulla tabella di associazione (sostituisci i nomi con quelli veri del tuo DB) già pronta come costante
		

		try (Connection conn = DatabaseManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(QUERY_POPOLA_BUILD)) {

			// Imposto l'ID della Build che sto analizzando
			ps.setString(1, buildDaRiempire.getId_prodotto());

			try (ResultSet rs = ps.executeQuery()) {

				// 2. Scorro tutti gli ID dei componenti che appartengono a questa Build
				while (rs.next()) {
					String idComponenteTrovato = rs.getString("id_componente");

					// 3. Cerco l'oggetto Componente corrispondente nel mio catalogo già caricato in memoria!
					for (Prodotto prodotto : catalogoComponenti) {

						// Controllo se l'ID corrisponde e mi assicuro che sia davvero un Componente
						if (prodotto.getId_prodotto().equals(idComponenteTrovato) && 
								prodotto.getTipologia() == TipologiaProdotto.COMPONENTE) {

							// 4. Casting sicuro e aggiunta alla Build!
							Componente comp = (Componente) prodotto;

							try {
								// Uso il metodo che avevi già creato tu in Build!
								buildDaRiempire.aggiungiComponente(comp);
							} catch (Exception e) {
								// Se per qualche motivo il DB ha salvato una build fisicamente impossibile, 
								// qui catturi la ComponentiException che avevi implementato
								System.err.println("Errore di integrità nella Build dal DB: " + e.getMessage());
							}

							break; // Trovato e aggiunto, passo al prossimo ID restituito dal DB
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

		String queryComponente = "INSERT INTO Componente (id_prodotto, marca, modello, tipo_componente) VALUES (?, ?, ?, ?)";
		String queryAspetto = "INSERT INTO Aspetto_Tecnico (id_prodotto, aspetto, valore) VALUES (?, ?, ?)";

		Connection conn = null;

		try {
			// 1. Prendo la connessione
			conn = DatabaseManager.getConnection();

			// 2. DISABILITO L'AUTO-COMMIT (INIZIO LA TRANSAZIONE)
			// Significa: "Non salvare nulla finché non te lo dico io!"
			conn.setAutoCommit(false); 

			// --- FASE 1: INSERIMENTO NELLA TABELLA PADRE (Prodotto) ---
			try (PreparedStatement psProd = conn.prepareStatement(QUERY_INSERIMENTO_PRODOTTO)) {
				psProd.setString(1, prodotto_da_inserire.getId_prodotto());
				psProd.setDouble(2, prodotto_da_inserire.getPrezzo());
				psProd.setString(3, prodotto_da_inserire.getTipologia().name()); // Es. "COMPONENTE"
				psProd.executeUpdate();
			}

			// --- FASE 2: INSERIMENTO NELLE TABELLE FIGLIE ---
			// Visto che il prof ha bannato instanceof, usiamo il tuo metodo polimorfico!
			if (prodotto_da_inserire.getTipologia() == TipologiaProdotto.COMPONENTE) {

				// Faccio il casting sicuro
				Componente comp = (Componente) prodotto_da_inserire;

				// Inserisco nella tabella Componente
				try (PreparedStatement psComp = conn.prepareStatement(QUERY_INSERIMENTO_COMPONENTE)) {
					psComp.setString(1, comp.getId_prodotto());
					psComp.setString(2, comp.getMarca());
					psComp.setString(3, comp.getModello());
					psComp.setString(4, comp.getTipo().name()); // Es. "CPU"
					psComp.executeUpdate();
				}

				// --- FASE 3: INSERIMENTO DELLA SCHEDA TECNICA (L'EnumMap!) ---
				if (comp.getScheda_tecnica() != null && !comp.getScheda_tecnica().isEmpty()) {

					try (PreparedStatement psAspetto = conn.prepareStatement(QUERY_INSERIMENTO_ASPETTO_TECNICO)) {

						// Scorro la mappa con il for-each magico di cui parlavamo
						for (Map.Entry<AspettiTecnici, String> riga : comp.getScheda_tecnica().entrySet()) {
							psAspetto.setString(1, comp.getId_prodotto());
							psAspetto.setString(2, riga.getKey().name());
							psAspetto.setString(3, riga.getValue());

							// INVECE di fare executeUpdate() 10 volte, accodo le query nel BATCH!
							psAspetto.addBatch();
						}

						// Le sparo tutte insieme nel DB in un colpo solo! Prestazioni top.
						psAspetto.executeBatch();
					}
				}

			} else if (prodotto_da_inserire.getTipologia() == TipologiaProdotto.BUILD) {
				// Qui in futuro farai la stessa cosa, ma con la query INSERT INTO Build
			}

			// 3. SE ARRIVIAMO QUI SENZA ERRORI, SALVIAMO TUTTO DEFINITIVAMENTE! (COMMIT)
			conn.commit();
			return true;

		} catch (SQLException e) {
			// 🚨 DISASTRO! Qualcosa è andato storto (es. chiave primaria duplicata).
			System.err.println("Errore durante l'inserimento. Annullamento in corso... " + e.getMessage());

			// 4. ROLLBACK: Annulla immediatamente qualsiasi operazione fatta in questa transazione!
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					System.err.println("Errore catastrofico nel rollback: " + ex.getMessage());
				}
			}
			return false;

		} finally {
			// 5. RIPRISTINO: Riattivo l'auto-commit per chi userà la connessione dopo di me
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close(); // Se non stai usando un connection pool che lo fa in automatico
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Prodotto costruisciComponenteConFactory(String id, double prezzo, String marca, 
			String modello, int potenza, TipoComponente tipo, 
			Map<String, String> specificheDB) {

		// Preparo l'ArrayList esatta che la tua Factory si aspetta!
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

		// Chiamo la TUA Factory!
		Prodotto nuovoComponente = ProdottiFactory.creaComponente(prezzo, marca, modello, tipo, arrayPerFactory, potenza);

		// Ripristino il VERO ID che c'era nel Database (altrimenti la factory ne genera uno nuovo)
		nuovoComponente.setId_prodotto(id); 

		return nuovoComponente;
	}

}
