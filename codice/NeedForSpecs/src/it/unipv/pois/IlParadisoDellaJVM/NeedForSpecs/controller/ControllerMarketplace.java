package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.GestoreAccount;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.Marketplace;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.TipologiaProdotto;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.FrameMarketplace;

/**
 * @author teopacchiega
 */

public class ControllerMarketplace {
	private Marketplace model;
	private FrameMarketplace view;

	public ControllerMarketplace(Marketplace model, FrameMarketplace view) {
		super();
		this.model = model;
		this.view = view;
		view.setVisible(true);
		inizializzaInterfaccia();
	}


	private void inizializzaInterfaccia() {
		aggiornaHomeMarketplace();
		inizializzaListeners();
	}

	private void aggiornaHomeMarketplace() {
		//		System.out.println("metodo aggiornaHomeMarketplace - utente: "+model.getUtente_loggato().getUser_name());
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
		DefaultListModel<Prodotto> model_prodotti = new DefaultListModel<>();

		for (Prodotto p : model.getProdotti()) {
			model_prodotti.addElement(p);
		}

		view.getMarketplaceGuestPanel().getListTuttiProdotti().setModel(model_prodotti);
		view.getMarketplaceUserPanel().getListTuttiProdotti().setModel(model_prodotti);
		view.getMarketplaceStaffPanel().getListTuttiProdotti().setModel(model_prodotti);
	}

	private void popolaListaAnnunci(Prodotto prodottoSelezionato) {
		DefaultListModel<Annuncio> model_annunci = new DefaultListModel<>();

		for (Annuncio a : model.getAnnunci()) {
			if (a.getProdotto_in_vendita().getId_prodotto().equals(prodottoSelezionato.getId_prodotto())) {
				model_annunci.addElement(a);
			}
		}

		view.getMarketplaceGuestPanel().getListAnnunciProdottoSelezionato().setModel(model_annunci);
		view.getMarketplaceUserPanel().getListAnnunciProdottoSelezionato().setModel(model_annunci);
		view.getMarketplaceStaffPanel().getListAnnunciProdottoSelezionato().setModel(model_annunci);
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

	private Annuncio getElementoCarrelloSelezionato() {
		if (view.getCarrelloPanel().isShowing()) return view.getCarrelloPanel().getListAnnunci().getSelectedValue();
		return null;
	}

	private Prodotto getProdottoSelezionatoInAggiuntaAnnuncio() {
		if (view.getAggiuntaAnnuncioPanel().isShowing()) return view.getAggiuntaAnnuncioPanel().getListProdotti().getSelectedValue();
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


	// =========================================================================================================
	// HOME MARKETPLACE

	private void addListenersComuniHomeMarketplace() {
		gestisciListenersReattiviMarketplace();

		ActionListener torna_alla_home_di_need_for_specs = e -> {
			view.dispose();
			GestoreAccount new_model = GestoreAccount.getInstance();
			new_model.setUtenteLoggato(model.getUtente_loggato());
			ControllerHome ch = new ControllerHome(new_model, view.getHomeFrame());

			System.out.println("Ritorno alla home");
		};

		view.getMarketplaceGuestPanel().getBtnTornaHome().addActionListener(torna_alla_home_di_need_for_specs);
		view.getMarketplaceUserPanel().getBtnTornaHome().addActionListener(torna_alla_home_di_need_for_specs);
		view.getMarketplaceStaffPanel().getBtnTornaHome().addActionListener(torna_alla_home_di_need_for_specs);

	}

	private void addListenersUserHomeMarketplace() {

		// LOGOUT
		view.getMarketplaceUserPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				model.setUtente_loggato(null);
				aggiornaHomeMarketplace();
				System.out.println("Logout");

			}
		});

		// ACCEDI AL CONFIGURATORE
		view.getMarketplaceUserPanel().getBtnAccediConfiguratore().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraConfiguratoreUser();
				System.out.println("Passo al configuratore");
			}
		});

		// GESTIONE CARRELLO
		view.getMarketplaceUserPanel().getBtnCarrello().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraCarrello();
				System.out.println("Passo al carrello");

			}
		});

		// AGGIUNGI AL CARRELLO
		view.getMarketplaceUserPanel().getBtnAggiungiCarrello().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Annuncio nuovo_acquisto = getAnnuncioSelezionato();
				if(model.aggiungiAlCarrello((UtenteGenerico)model.getUtente_loggato(), nuovo_acquisto)) {
					System.out.println("Annuncio aggiunto al carrello");
				}else{
					System.out.println("Impossibile aggiungere annuncio al carrello");
				};

			}
		});

		// PASSA AD AGGIUNGI ANNUNCIO
		view.getMarketplaceUserPanel().getBtnAggiungiAnnuncio().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				view.mostraAggiuntaAnnuncio();
			}
		});
	}

	private void addListenersGuestHomeMarketplace() {

		// LOGIN
		view.getMarketplaceGuestPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HomeFrame hf = view.getHomeFrame();
				GestoreAccount new_model = GestoreAccount.getInstance();
				new_model.setUtenteLoggato(model.getUtente_loggato());
				new ControllerHome(new_model, hf);
				view.dispose();
				System.out.println("Torno alla home per il login");
			}
		});

		// ACCEDI CONFIGURATORE
		view.getMarketplaceGuestPanel().getBtnAccediConfiguratore().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraConfiguratoreGuest();
				System.out.println("Passo al configuratore");
			}
		});



	}

	private void addListenersStaffHomeMarketplace() {

		// LOGOUT
		view.getMarketplaceStaffPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				aggiornaHomeMarketplace();
				System.out.println("Logout");
			}
		});


		// RIMUOVI ANNUNCIO
		view.getMarketplaceStaffPanel().getBtnRimuoviAnnuncio().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Annuncio annuncio_da_rimuovere = getAnnuncioSelezionato();
				if (annuncio_da_rimuovere != null) {

					if (model.rimuoviAnnuncio(annuncio_da_rimuovere)) {
						view.getMarketplaceStaffPanel().getTxtAreaInfoAnnuncio().setText("");
						Prodotto prodottoAttuale = getProdottoSelezionato();
						if (prodottoAttuale != null) {
							popolaListaAnnunci(prodottoAttuale);
						}
					} else {
						view.getMarketplaceStaffPanel().getLblMessaggio().setText("Errore di sistema: impossibile rimuovere l'annuncio.");
					}

				} else {
					view.getMarketplaceStaffPanel().getLblMessaggio().setText("Errore: seleziona un annuncio prima di cliccare su Rimuovi!");
				}

			}
		});


	}

	private void gestisciListenersReattiviMarketplace() {

		javax.swing.event.ListSelectionListener reazioneSelezionaProdotto = e -> {
			if (!e.getValueIsAdjusting()) {
				Prodotto p = getProdottoSelezionato();
				if (p != null) {
					popolaListaAnnunci(p); 
					view.getMarketplaceGuestPanel().getTxtAreaInfoAnnuncio().setText("");
					view.getMarketplaceUserPanel().getTxtAreaInfoAnnuncio().setText("");
					view.getMarketplaceStaffPanel().getTxtAreaInfoAnnuncio().setText("");
				}
			}
		};
		view.getMarketplaceGuestPanel().getListTuttiProdotti().addListSelectionListener(reazioneSelezionaProdotto);
		view.getMarketplaceUserPanel().getListTuttiProdotti().addListSelectionListener(reazioneSelezionaProdotto);
		view.getMarketplaceStaffPanel().getListTuttiProdotti().addListSelectionListener(reazioneSelezionaProdotto);


		javax.swing.event.ListSelectionListener reazione_seleziona_annuncio = e -> {
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
		view.getMarketplaceGuestPanel().getListAnnunciProdottoSelezionato().addListSelectionListener(reazione_seleziona_annuncio);
		view.getMarketplaceUserPanel().getListAnnunciProdottoSelezionato().addListSelectionListener(reazione_seleziona_annuncio);
		view.getMarketplaceStaffPanel().getListAnnunciProdottoSelezionato().addListSelectionListener(reazione_seleziona_annuncio);

	}

	// =========================================================================================================
	// CONFIGURATORE

	private void addListenersComuniConfiguratore() {
		gestisciListenersReattiviConfiguratore();
		ActionListener torna_alla_home_del_marketplace = e -> {
			aggiornaHomeMarketplace();
			model.resetBuild();
			System.out.println("Ritorno alla home del marketplace");
		};

		view.getConfiguratoreGuestPanel().getBtnTornaMarketplace().addActionListener(torna_alla_home_del_marketplace);
		view.getConfiguratoreUserPanel().getBtnTornaMarketplace().addActionListener(torna_alla_home_del_marketplace);


		ActionListener aggiungi_alla_build = e -> {
			System.out.println("Aggiungo componente alla build");
			try {
				model.getBuild_configuratore().aggiungiComponente(getComponenteCatalogoSelezionato());
			} catch (ComponentiException exc) {
				view.getConfiguratoreGuestPanel().getLblMessaggio().setText("ERRORE: "+exc.getMessage());
				view.getConfiguratoreUserPanel().getLblMessaggio().setText("ERRORE: "+exc.getMessage());
				exc.printStackTrace();
			}

		};

		ActionListener rimuovi_dalla_build = e -> {
			System.out.println("Rimuovo componente dalla build");
			model.getBuild_configuratore().rimuoviComponente(getComponenteCatalogoSelezionato());
		};


	}

	private void addListenersUserConfiguratore() {


		// LOGOUT
		view.getConfiguratoreUserPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				aggiornaHomeMarketplace();
				System.out.println("Logout");
			}
		});

		// SALVA BUILD
		view.getConfiguratoreUserPanel().getBtnSalvaBuild().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nome_build = view.getConfiguratoreUserPanel().getTxtNomeBuild().toString();
				model.getBuild_configuratore().setNome(nome_build);
				model.getBuild_configuratore().aggiornaPrezzo();
				model.salvaBuildConfiguratore();
			}
		});


		// PASSA A CARRELLO
		view.getConfiguratoreUserPanel().getBtnCarrello().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraCarrello();

			}
		});
	}

	private void addListenersGuestConfiguratore() {
		//		view.getConfiguratoreGuestPanel().getBtn

		view.getConfiguratoreGuestPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HomeFrame hf = view.getHomeFrame();
				GestoreAccount new_model = GestoreAccount.getInstance();
				new_model.setUtenteLoggato(model.getUtente_loggato());
				new ControllerHome(new_model, hf);
				view.dispose();
				System.out.println("Torno alla home per il login");
			}
		});

	}

	private void popolaListaCatalogoConfiguratore(TipoComponente tipoScelto) {
		DefaultListModel<Componente> model_catalogo = new DefaultListModel<>();

		for (Prodotto p : model.getProdotti()) {
			if (p.getTipologia()==TipologiaProdotto.COMPONENTE) {
				Componente c = (Componente) p;
				if (c.getTipo() == tipoScelto) {
					model_catalogo.addElement(c);
				}
			}
		}


		view.getConfiguratoreGuestPanel().getListComponentiDisponibili().setModel(model_catalogo);
		view.getConfiguratoreUserPanel().getListComponentiDisponibili().setModel(model_catalogo);
	}

	private void gestisciListenersReattiviConfiguratore() {


		javax.swing.event.ListSelectionListener reazione_tipo_componente = e -> {
			if (!e.getValueIsAdjusting()) {
				TipoComponente tipo = getTipoSelezionato();
				if (tipo != null) {
					popolaListaCatalogoConfiguratore(tipo); 
					view.getConfiguratoreGuestPanel().getTxtAreaInfoCatalogo().setText("");
					view.getConfiguratoreUserPanel().getTxtAreaInfoCatalogo().setText("");
				}
			}
		};

		view.getConfiguratoreGuestPanel().getListTipiComponente().addListSelectionListener(reazione_tipo_componente);
		view.getConfiguratoreUserPanel().getListTipiComponente().addListSelectionListener(reazione_tipo_componente);


		javax.swing.event.ListSelectionListener reazione_componente_catalogo = e -> {
			if (!e.getValueIsAdjusting()) {
				Componente c = getComponenteCatalogoSelezionato();
				if (c != null) {
					String info = c.getInfoProdotto();

					view.getConfiguratoreGuestPanel().getTxtAreaInfoCatalogo().setText(info);
					view.getConfiguratoreUserPanel().getTxtAreaInfoCatalogo().setText(info);
				}
			}
		};

		view.getConfiguratoreGuestPanel().getListComponentiDisponibili().addListSelectionListener(reazione_componente_catalogo);
		view.getConfiguratoreUserPanel().getListComponentiDisponibili().addListSelectionListener(reazione_componente_catalogo);


		javax.swing.event.ListSelectionListener reazione_componente_build = e -> {
			if (!e.getValueIsAdjusting()) {
				Componente c = getComponenteBuildSelezionato();
				if (c != null) {
					String info = c.getInfoProdotto();

					view.getConfiguratoreGuestPanel().getTxtAreaInfoBuild().setText(info);
					view.getConfiguratoreUserPanel().getTxtAreaInfoBuild().setText(info);
				}
			}
		};

		view.getConfiguratoreGuestPanel().getListComponentiBuild().addListSelectionListener(reazione_componente_build);
		view.getConfiguratoreUserPanel().getListComponentiBuild().addListSelectionListener(reazione_componente_build);
	}

	// =========================================================================================================
	// GESTIONE ANNUNCI

	private void addListenersGestioneAnnunci() {

		// LOGOUT
		view.getAggiuntaAnnuncioPanel().getBtnLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				aggiornaHomeMarketplace();
				System.out.println("Logout");
			}
		});

		// CARRELLO
		view.getAggiuntaAnnuncioPanel().getBtnCarrello().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraCarrello();
				System.out.println("Passo al carrello");
			}
		});

		//AGGIUNTA ANNUNCIo
		view.getAggiuntaAnnuncioPanel().getBtnAggiungiAnnuncio().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Prodotto prod_annuncio = getProdottoSelezionato();
				double prezzo = Double.parseDouble(view.getAggiuntaAnnuncioPanel().getTxtPrezzo().getSelectedText());

			}
		});

		view.getAggiuntaAnnuncioPanel().getBtnTornaMarketplace().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				aggiornaHomeMarketplace();
				System.out.println("Torno alla home marketplace");
			}
		});

	}

	private void gestisciListenersReattiviAggiuntaAnnuncio() {

		javax.swing.event.ListSelectionListener reazione_seleziona_prodotto_da_vendere = e -> {
			if (!e.getValueIsAdjusting()) {
				Prodotto prodotto_selezionato = view.getAggiuntaAnnuncioPanel().getListProdotti().getSelectedValue();
				if (prodotto_selezionato != null) {
					view.getAggiuntaAnnuncioPanel().getLblMessaggio().setForeground(java.awt.Color.BLUE);
					view.getAggiuntaAnnuncioPanel().getLblMessaggio().setText("Hai selezionato: " + prodotto_selezionato.toString()); 
				} else {
					view.getAggiuntaAnnuncioPanel().getLblMessaggio().setText(" ");
				}
			}
		};

		view.getAggiuntaAnnuncioPanel().getListProdotti().addListSelectionListener(reazione_seleziona_prodotto_da_vendere);
	}

	// =========================================================================================================
	// CARRELLO

	private void addListenersCarrello() {

		// PULSANTE LOGOUT
		view.getCarrelloPanel().getBtnLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				aggiornaHomeMarketplace();
				System.out.println("Logout");
			}
		});

		// PULSANTE TORNA ALLA HOME
		view.getCarrelloPanel().getBtnTornaMarketplace().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				aggiornaHomeMarketplace();
				System.out.println("Torno alla home del marketplace");
			}
		});

		view.getCarrelloPanel().getBtnEffettuaOrdine().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.effettuaOrdine((UtenteGenerico)model.getUtente_loggato());
			}
		});

		view.getCarrelloPanel().getBtnEliminaLista().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.rimuoviElementoDalCarrello((UtenteGenerico)model.getUtente_loggato(), getElementoCarrelloSelezionato());
			}
		});

		// PULSANTE RIMUOVI DAL CARRELLO
		//		view.getCarrelloPanel().getBtn
		//		view.getCarrelloPanel().getBtnLogout()

	}

	private void gestisciListenersReattiviCarrello() {

		javax.swing.event.ListSelectionListener reazione_seleziona_carrello = e -> {
			if (!e.getValueIsAdjusting()) {
				Annuncio annuncio_selezionato = view.getCarrelloPanel().getListAnnunci().getSelectedValue();

				if (annuncio_selezionato != null) {
					String info = annuncio_selezionato.toString();
					view.getCarrelloPanel().getLblMessaggio().setText(info);
				} else {
					view.getCarrelloPanel().getLblMessaggio().setText("");
				}
			}
		};


		view.getCarrelloPanel().getListAnnunci().addListSelectionListener(reazione_seleziona_carrello);
	}




	//	private void addListenersLogin() {
	//
	//		view.getLoginPanel().getBtnTornaHome().addActionListener(new ActionListener() {
	//
	//			@Override
	//			public void actionPerformed(ActionEvent e) {
	//				// TODO Auto-generated method stub
	//				view.dispose();
	//				GestoreAccount new_model = GestoreAccount.getInstance();
	//				ControllerHome ch = new ControllerHome(new_model, view.getHomeFrame());
	//				ch.getView().setVisible(true);
	//				System.out.println("Torno alla home");
	//			}
	//		});
	//
	//
	//	}




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
