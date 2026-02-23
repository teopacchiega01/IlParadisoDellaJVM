package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.GestoreAccount;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.Marketplace;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.FrameMarketplace;

//	@Author teopacchiega

public class ControllerMarketplace {
	private Marketplace model;
	private FrameMarketplace view;

	public ControllerMarketplace(Marketplace model, FrameMarketplace view) {
		super();
		this.model = model;
		this.view = view;
		inizializzaInterfaccia();
	}


	private void inizializzaInterfaccia() {
		aggiornaHomeMarketplace();
		inizializzaListeners();
	}

	private void aggiornaHomeMarketplace() {
		if(model.getUtente_loggato()==null) {
			view.mostraMarketplaceGuest();
		}else if(model.getUtente_loggato().isStaff()) {
			view.mostraMarketplaceStaff();
		}else {
			view.mostraMarketplaceUser();
		}
	}

	private void aggiornaConfiguratore() {
		if(model.getUtente_loggato()==null) {
			view.mostraConfiguratoreGuest();
		}else {
			view.mostraConfiguratoreUser();
		}
	}

	private void popolaListaProdotti() {
		DefaultListModel<Prodotto> modelProdotti = new DefaultListModel<>();

		for (Prodotto p : model.getProdotti()) {
			modelProdotti.addElement(p);
		}

		view.getMarketplaceGuestPanel().getListTuttiProdotti().setModel(modelProdotti);
		view.getMarketplaceUserPanel().getListTuttiProdotti().setModel(modelProdotti);
		view.getMarketplaceStaffPanel().getListTuttiProdotti().setModel(modelProdotti);
	}

	private void popolaListaAnnunci(Prodotto prodottoSelezionato) {
		DefaultListModel<Annuncio> modelAnnunci = new DefaultListModel<>();

		for (Annuncio a : model.getAnnunci()) {
			if (a.getProdotto_in_vendita().getId_prodotto().equals(prodottoSelezionato.getId_prodotto())) {
				modelAnnunci.addElement(a);
			}
		}

		view.getMarketplaceGuestPanel().getListAnnunciProdottoSelezionato().setModel(modelAnnunci);
		view.getMarketplaceUserPanel().getListAnnunciProdottoSelezionato().setModel(modelAnnunci);
		view.getMarketplaceStaffPanel().getListAnnunciProdottoSelezionato().setModel(modelAnnunci);
	}

	private Prodotto getProdottoSelezionato() {
		if (view.getMarketplaceGuestPanel().isShowing()) return view.getMarketplaceGuestPanel().getListTuttiProdotti().getSelectedValue();
		if (view.getMarketplaceUserPanel().isShowing()) return view.getMarketplaceUserPanel().getListTuttiProdotti().getSelectedValue();
		if (view.getMarketplaceStaffPanel().isShowing()) return view.getMarketplaceStaffPanel().getListTuttiProdotti().getSelectedValue();
		return null;
	}

	private Annuncio getAnnuncioSelezionato() {
		if (view.getMarketplaceGuestPanel().isShowing()) return view.getMarketplaceGuestPanel().getListAnnunciProdottoSelezionato().getSelectedValue();
		if (view.getMarketplaceUserPanel().isShowing()) return view.getMarketplaceUserPanel().getListAnnunciProdottoSelezionato().getSelectedValue();
		if (view.getMarketplaceStaffPanel().isShowing()) return view.getMarketplaceStaffPanel().getListAnnunciProdottoSelezionato().getSelectedValue();
		return null;
	}

	private TipoComponente getTipoSelezionato() {
		if (view.getConfiguratoreGuestPanel().isShowing()) return view.getConfiguratoreGuestPanel().getListTipiComponente().getSelectedValue();
		if (view.getConfiguratoreUserPanel().isShowing()) return view.getConfiguratoreUserPanel().getListTipiComponente().getSelectedValue();
		return null;
	}

	private Componente getComponenteCatalogoSelezionato() {
		if (view.getConfiguratoreGuestPanel().isShowing()) return view.getConfiguratoreGuestPanel().getListComponentiDisponibili().getSelectedValue();
		if (view.getConfiguratoreUserPanel().isShowing()) return view.getConfiguratoreUserPanel().getListComponentiDisponibili().getSelectedValue();
		return null;
	}

	private Componente getComponenteBuildSelezionato() {
		if (view.getConfiguratoreGuestPanel().isShowing()) return view.getConfiguratoreGuestPanel().getListComponentiBuild().getSelectedValue();
		if (view.getConfiguratoreUserPanel().isShowing()) return view.getConfiguratoreUserPanel().getListComponentiBuild().getSelectedValue();
		return null;
	}

	private void inizializzaListeners() {
		addListenersHomeMarketplace();
		addListenersConfiguratore();
		addListenersGestioneAnnunci();
		addListenersCarrello();
		gestisciListenersReattiviCarrello();
		gestisciListenersReattiviConfiguratore();
		gestisciListenersReattiviMarketplace();
		gestisciListenersReattiviAggiuntaAnnuncio();
	}

	private void addListenersHomeMarketplace() {
		addListenersComuniHomeMarketplace();
		addListenersUserHomeMarketplace();
		addListenersGuestHomeMarketplace();
		addListenersStaffHomeMarketplace();
	}

	private void addListenersConfiguratore() {
		addListenersComuniConfiguratore();
		addListenersUserConfiguratore();
		addListenersGuestConfiguratore();
	}


	// --- Home del Marketplace

	private void addListenersComuniHomeMarketplace() {
		gestisciListenersReattiviMarketplace();


		// PULSANTE TORNA ALLA HOME
		ActionListener tornaAllaHomeDiNeedForSpecs = e -> {
			view.dispose();
			GestoreAccount new_model = GestoreAccount.getInstance();
			new_model.setUtenteLoggato(model.getUtente_loggato());
			ControllerHome ch = new ControllerHome(new_model, view.getHomeFrame());
			//			ch.getView().setVisible(true);
			System.out.println("Ritorno alla home");
		};

		view.getMarketplaceGuestPanel().getBtnTornaHome().addActionListener(tornaAllaHomeDiNeedForSpecs);
		view.getMarketplaceUserPanel().getBtnTornaHome().addActionListener(tornaAllaHomeDiNeedForSpecs);
		view.getMarketplaceStaffPanel().getBtnTornaHome().addActionListener(tornaAllaHomeDiNeedForSpecs);

	}

	private void addListenersUserHomeMarketplace() {

		// PULSANTE LOGOUT
		view.getMarketplaceUserPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.dispose();
				model.setUtente_loggato(null);
				aggiornaHomeMarketplace();
				System.out.println("Logout e torno alla home");
			}
		});

		// PULSANTE ACCEDI AL CONFIGURATORE
		view.getMarketplaceUserPanel().getBtnAccediConfiguratore().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraConfiguratoreUser();
				System.out.println("Passo al configuratore");
			}
		});

		// PULSANTE CARRELLO
		view.getMarketplaceUserPanel().getBtnCarrello().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraCarrello();
				System.out.println("Passo al carrello");

			}
		});
	}

	private void addListenersGuestHomeMarketplace() {

		// PULSANTE LOGIN
		view.getMarketplaceUserPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.getLoginPanel().getTxtEmail().setText("");
				view.getLoginPanel().getTxtPassword().setText("");
				view.getLoginPanel().getLblMessaggio().setText("");
				view.mostraLogin();
			}
		});

		// PULSANTE ACCEDI AL CONFIGURATORE
		view.getMarketplaceGuestPanel().getBtnAccediConfiguratore().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraConfiguratoreGuest();
				System.out.println("Passo al configuratore");
			}
		});

	}

	private void addListenersStaffHomeMarketplace() {

		// PULSANTE LOGOUT
		view.getMarketplaceStaffPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				aggiornaHomeMarketplace();
				System.out.println("Logout");
			}
		});

		// PULSANTE RIMUOVI
		view.getMarketplaceStaffPanel().getBtnRimuoviAnnuncio().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Annuncio annuncio_da_rimuovere = getAnnuncioSelezionato();
				if (annuncio_da_rimuovere != null) {

					if (model.rimuoviAnnuncio(annuncio_da_rimuovere)) {
						view.getMarketplaceStaffPanel().getLblMessaggio().setText("Annuncio eliminato con successo!");
						view.getMarketplaceStaffPanel().getTxtAreaInfoAnnuncio().setText("");
						// Aggiorno la lista annunci
						Prodotto prodottoAttuale = getProdottoSelezionato();
						if (prodottoAttuale != null) {
							popolaListaAnnunci(prodottoAttuale);
						}
					} else {
						view.getMarketplaceStaffPanel().getLblMessaggio().setText("Errore di sistema: impossibile rimuovere l'annuncio.");
					}

				} else {
					// Se l'utente clicca il bottone senza aver selezionato nulla nella JList
					view.getMarketplaceStaffPanel().getLblMessaggio().setText("Errore: seleziona un annuncio prima di cliccare su Rimuovi!");
				}

			}
		});

	}

	private void gestisciListenersReattiviMarketplace() {

		// Quando clicco un Prodotto, la seconda lista (quella degli Annunci) si riempie all'istante
		javax.swing.event.ListSelectionListener reazioneSelezionaProdotto = e -> {
			// getValueIsAdjusting() evita che l'evento parta due volte (quando premi il clic e quando lo rilasci)
			if (!e.getValueIsAdjusting()) {
				Prodotto p = getProdottoSelezionato();
				if (p != null) {
					popolaListaAnnunci(p); 
					// Svuoto la JTextArea per fare pulizia al cambio prodotto:
					view.getMarketplaceGuestPanel().getTxtAreaInfoAnnuncio().setText("");
					view.getMarketplaceUserPanel().getTxtAreaInfoAnnuncio().setText("");
					view.getMarketplaceStaffPanel().getTxtAreaInfoAnnuncio().setText("");
				}
			}
		};
		view.getMarketplaceGuestPanel().getListTuttiProdotti().addListSelectionListener(reazioneSelezionaProdotto);
		view.getMarketplaceUserPanel().getListTuttiProdotti().addListSelectionListener(reazioneSelezionaProdotto);
		view.getMarketplaceStaffPanel().getListTuttiProdotti().addListSelectionListener(reazioneSelezionaProdotto);


		// Quando clicco un Annuncio, la JTextArea si riempie di dettagli all'istante
		javax.swing.event.ListSelectionListener reazioneSelezionaAnnuncio = e -> {
			if (!e.getValueIsAdjusting()) {
				Annuncio a = getAnnuncioSelezionato();
				if (a != null) {
					String dettagli = a.toString(); 
					view.getMarketplaceGuestPanel().getTxtAreaInfoAnnuncio().setText(dettagli);
					view.getMarketplaceUserPanel().getTxtAreaInfoAnnuncio().setText(dettagli);
					view.getMarketplaceStaffPanel().getTxtAreaInfoAnnuncio().setText(dettagli);
				}
			}
		};
		view.getMarketplaceGuestPanel().getListAnnunciProdottoSelezionato().addListSelectionListener(reazioneSelezionaAnnuncio);
		view.getMarketplaceUserPanel().getListAnnunciProdottoSelezionato().addListSelectionListener(reazioneSelezionaAnnuncio);
		view.getMarketplaceStaffPanel().getListAnnunciProdottoSelezionato().addListSelectionListener(reazioneSelezionaAnnuncio);

	}

	// --- Configuratore

	private void addListenersComuniConfiguratore() {
		gestisciListenersReattiviConfiguratore();

		// TORNA ALLA HOME DEL MARKETPLACE
		ActionListener torna_alla_home_del_marketplace = e -> {
			aggiornaHomeMarketplace();
			model.resetBuild();
			System.out.println("Ritorno alla home del marketplace");
		};

		view.getConfiguratoreGuestPanel().getBtnTornaMarketplace().addActionListener(torna_alla_home_del_marketplace);
		view.getConfiguratoreUserPanel().getBtnTornaMarketplace().addActionListener(torna_alla_home_del_marketplace);

		// AGGIUNGI ALLA BUILD
		ActionListener aggiungi_alla_build = e -> {
			System.out.println("Aggiungo componente alla build");
			try {
				model.getBuild_configuratore().aggiungiComponente(getComponenteCatalogoSelezionato());
			} catch (ComponentiException exc) {
				// TODO Auto-generated catch block
				view.getConfiguratoreGuestPanel().getLblMessaggio().setText("ERRORE: "+exc.getMessage());
				view.getConfiguratoreUserPanel().getLblMessaggio().setText("ERRORE: "+exc.getMessage());
				exc.printStackTrace();
			}

		};

		// RIMUOVI DALLA BUILD
		ActionListener rimuovi_dalla_build = e -> {
			System.out.println("Rimuovo componente dalla build");
			model.getBuild_configuratore().rimuoviComponente(getComponenteCatalogoSelezionato());
		};


	}

	private void addListenersUserConfiguratore() {

		// PULSANTE LOGOUT
		view.getConfiguratoreUserPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				view.mostraMarketplaceGuest();
				System.out.println("Logout");
			}
		});

		// PULSANTE SALVA BUILD
		view.getConfiguratoreUserPanel().getBtnSalvaBuild().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nome_build = view.getConfiguratoreUserPanel().getTxtNomeBuild().toString();
				model.getBuild_configuratore().setNome(nome_build);
				model.getBuild_configuratore().aggiornaPrezzo();
				model.salvaBuildConfiguratore();
			}
		});


		// PULSANTE CARRELLO
		view.getConfiguratoreUserPanel().getBtnCarrello().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraCarrello();

			}
		});
	}

	private void addListenersGuestConfiguratore() {

		// PULSANTE LOGIN
		view.getConfiguratoreGuestPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.getLoginPanel().getTxtEmail().setText("");
				view.getLoginPanel().getTxtPassword().setText("");
				view.getLoginPanel().getLblMessaggio().setText("");
				view.mostraLogin();
			}
		});

	}

	private void popolaListaCatalogoConfiguratore(TipoComponente tipoScelto) {
		DefaultListModel<Componente> modelCatalogo = new DefaultListModel<>();

		// Ciclo tutti i prodotti del modello
		for (Prodotto p : model.getProdotti()) {
			// Controllo se il prodotto è effettivamente un Componente e se è del tipo giusto
			if (p instanceof Componente) {
				Componente c = (Componente) p;
				if (c.getTipo() == tipoScelto) {
					modelCatalogo.addElement(c);
				}
			}
		}

		// Inietto il modello pieno in entrambi i pannelli
		view.getConfiguratoreGuestPanel().getListComponentiDisponibili().setModel(modelCatalogo);
		view.getConfiguratoreUserPanel().getListComponentiDisponibili().setModel(modelCatalogo);
	}

	private void gestisciListenersReattiviConfiguratore() {

		// --- AZIONE 1: CLIC SUL TIPO (es. "CPU") -> SI POPOLA IL CATALOGO ---
		javax.swing.event.ListSelectionListener reazioneTipoComponente = e -> {
			if (!e.getValueIsAdjusting()) {
				TipoComponente tipo = getTipoSelezionato();
				if (tipo != null) {
					popolaListaCatalogoConfiguratore(tipo); // Riempie il catalogo!

					// Faccio pulizia nella TextArea del catalogo
					view.getConfiguratoreGuestPanel().getTxtAreaInfoCatalogo().setText("");
					view.getConfiguratoreUserPanel().getTxtAreaInfoCatalogo().setText("");
				}
			}
		};

		view.getConfiguratoreGuestPanel().getListTipiComponente().addListSelectionListener(reazioneTipoComponente);
		view.getConfiguratoreUserPanel().getListTipiComponente().addListSelectionListener(reazioneTipoComponente);


		// --- AZIONE 2: CLIC NEL CATALOGO -> SI RIEMPIE LA TEXTAREA CON LE INFO ---
		javax.swing.event.ListSelectionListener reazioneComponenteCatalogo = e -> {
			if (!e.getValueIsAdjusting()) {
				Componente c = getComponenteCatalogoSelezionato();
				if (c != null) {
					String info = c.getInfoProdotto(); // O il metodo che hai per le info

					view.getConfiguratoreGuestPanel().getTxtAreaInfoCatalogo().setText(info);
					view.getConfiguratoreUserPanel().getTxtAreaInfoCatalogo().setText(info);
				}
			}
		};

		view.getConfiguratoreGuestPanel().getListComponentiDisponibili().addListSelectionListener(reazioneComponenteCatalogo);
		view.getConfiguratoreUserPanel().getListComponentiDisponibili().addListSelectionListener(reazioneComponenteCatalogo);


		// --- AZIONE 3: CLIC NELLA BUILD INFERIORE -> SI RIEMPIE L'ALTRA TEXTAREA ---
		javax.swing.event.ListSelectionListener reazioneComponenteBuild = e -> {
			if (!e.getValueIsAdjusting()) {
				Componente c = getComponenteBuildSelezionato();
				if (c != null) {
					String info = c.getInfoProdotto();

					view.getConfiguratoreGuestPanel().getTxtAreaInfoBuild().setText(info);
					view.getConfiguratoreUserPanel().getTxtAreaInfoBuild().setText(info);
				}
			}
		};

		view.getConfiguratoreGuestPanel().getListComponentiBuild().addListSelectionListener(reazioneComponenteBuild);
		view.getConfiguratoreUserPanel().getListComponentiBuild().addListSelectionListener(reazioneComponenteBuild);
	}

	// --- Gestione annunci

	private void addListenersGestioneAnnunci() {
		// PULSANTE LOGOUT
		view.getAggiuntaAnnuncioPanel().getBtnLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				view.mostraMarketplaceGuest();
				System.out.println("Logout");
			}
		});


		view.getAggiuntaAnnuncioPanel().getBtnCarrello().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraCarrello();
				System.out.println("Passo al carrello");
			}
		});


		view.getAggiuntaAnnuncioPanel().getBtnAggiungiAnnuncio().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Prodotto prod_annuncio = getProdottoSelezionato();
				double prezzo = Double.parseDouble(view.getAggiuntaAnnuncioPanel().getTxtPrezzo().getSelectedText());

			}
		});

	}

	private void gestisciListenersReattiviAggiuntaAnnuncio() {

		javax.swing.event.ListSelectionListener reazioneSelezionaProdottoDaVendere = e -> {
			// Solito controllo per evitare che l'evento scatti due volte
			if (!e.getValueIsAdjusting()) {

				// Pesco il prodotto dalla lista del pannello
				Prodotto prodottoSelezionato = view.getAggiuntaAnnuncioPanel().getListProdotti().getSelectedValue();

				if (prodottoSelezionato != null) {
					// Do un feedback visivo all'utente (magari mettendo il testo in blu o nero invece che rosso errore)
					view.getAggiuntaAnnuncioPanel().getLblMessaggio().setForeground(java.awt.Color.BLUE);

					// Assumo che Prodotto abbia un metodo getNome() o getModello()
					view.getAggiuntaAnnuncioPanel().getLblMessaggio().setText("Hai selezionato: " + prodottoSelezionato.toString()); 
				} else {
					// Se la selezione viene annullata, svuoto il messaggio
					view.getAggiuntaAnnuncioPanel().getLblMessaggio().setText(" ");
				}
			}
		};

		// Aggancio l'ascoltatore alla lista
		view.getAggiuntaAnnuncioPanel().getListProdotti().addListSelectionListener(reazioneSelezionaProdottoDaVendere);
	}


	// --- Carrello 

	private void addListenersCarrello() {

		// PULSANTE LOGOUT
		view.getCarrelloPanel().getBtnLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				view.mostraMarketplaceGuest();
				System.out.println("Logout");
			}
		});

	}

	private void gestisciListenersReattiviCarrello() {

		javax.swing.event.ListSelectionListener reazioneSelezionaCarrello = e -> {
			// Evito il doppio scatto dell'evento
			if (!e.getValueIsAdjusting()) {

				// Pesco l'annuncio direttamente dalla JList del carrello
				Annuncio annuncioSelezionato = view.getCarrelloPanel().getListAnnunci().getSelectedValue();

				if (annuncioSelezionato != null) {
					// Scrivo le info nella JTextArea del carrello
					String info = annuncioSelezionato.toString(); // O il metodo che formatta i dettagli
					view.getCarrelloPanel().getLblMessaggio().setText(info);
				} else {
					// Se non c'è nulla di selezionato (es. dopo una rimozione), svuoto l'area
					view.getCarrelloPanel().getLblMessaggio().setText("");
				}
			}
		};

		// Aggancio l'ascoltatore alla lista del carrello
		view.getCarrelloPanel().getListAnnunci().addListSelectionListener(reazioneSelezionaCarrello);
	}


	// --- Login

	private void addListenersLogin() {

		view.getLoginPanel().getBtnTornaHome().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				view.dispose();
				GestoreAccount new_model = GestoreAccount.getInstance();
				ControllerHome ch = new ControllerHome(new_model, view.getHomeFrame());
				ch.getView().setVisible(true);
				System.out.println("Torno alla home");
			}
		});

//		view.getLoginPanel().getBtnEffettuaLogin().addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String email = view.getLoginPanel().getTxtEmail().getText();
//				String pw = new String(view.getLoginPanel().getTxtPassword().getPassword());
//				model.getUtente_loggato().login(email, pw);
//				if(model.getUtente_loggato()==null) {
//					System.out.println("Login fallito");
//					view.getLoginPanel().getLblMessaggio().setText("Login fallito: email o password errate");
//				}else {
//					System.out.println("Login riuscito");
//
//					if(model.getUtente_loggato().isStaff()) {
//						view.mostraMarketplaceStaff();
//					}else {
//						view.mostraMarketplaceUser();
//					}
//				}
//			}
//		});

	}


	// --- GETTER E SETTER

	public Marketplace getModel() {
		return model;
	}


	public void setModel(Marketplace model) {
		this.model = model;
	}


	public FrameMarketplace getView() {
		return view;
	}


	public void setView(FrameMarketplace view) {
		this.view = view;
	}




}
