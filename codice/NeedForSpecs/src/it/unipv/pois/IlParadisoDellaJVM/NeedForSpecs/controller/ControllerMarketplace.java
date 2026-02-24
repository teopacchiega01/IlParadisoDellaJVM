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




	private void addListenersComuniHomeMarketplace() {
		gestisciListenersReattiviMarketplace();


		
		ActionListener tornaAllaHomeDiNeedForSpecs = e -> {
			view.dispose();
			GestoreAccount new_model = GestoreAccount.getInstance();
			new_model.setUtenteLoggato(model.getUtente_loggato());
			ControllerHome ch = new ControllerHome(new_model, view.getHomeFrame());
			
			System.out.println("Ritorno alla home");
		};

		view.getMarketplaceGuestPanel().getBtnTornaHome().addActionListener(tornaAllaHomeDiNeedForSpecs);
		view.getMarketplaceUserPanel().getBtnTornaHome().addActionListener(tornaAllaHomeDiNeedForSpecs);
		view.getMarketplaceStaffPanel().getBtnTornaHome().addActionListener(tornaAllaHomeDiNeedForSpecs);

	}

	private void addListenersUserHomeMarketplace() {

		
		view.getMarketplaceUserPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.dispose();
				model.setUtente_loggato(null);
				aggiornaHomeMarketplace();
				System.out.println("Logout e torno alla home");
			}
		});


		view.getMarketplaceUserPanel().getBtnAccediConfiguratore().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraConfiguratoreUser();
				System.out.println("Passo al configuratore");
			}
		});

		
		view.getMarketplaceUserPanel().getBtnCarrello().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraCarrello();
				System.out.println("Passo al carrello");

			}
		});
	}

	private void addListenersGuestHomeMarketplace() {

		
		view.getMarketplaceUserPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.getLoginPanel().getTxtEmail().setText("");
				view.getLoginPanel().getTxtPassword().setText("");
				view.getLoginPanel().getLblMessaggio().setText("");
				view.mostraLogin();
			}
		});

	
		view.getMarketplaceGuestPanel().getBtnAccediConfiguratore().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraConfiguratoreGuest();
				System.out.println("Passo al configuratore");
			}
		});

	}

	private void addListenersStaffHomeMarketplace() {

		
		view.getMarketplaceStaffPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				aggiornaHomeMarketplace();
				System.out.println("Logout");
			}
		});

		
		view.getMarketplaceStaffPanel().getBtnRimuoviAnnuncio().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Annuncio annuncio_da_rimuovere = getAnnuncioSelezionato();
				if (annuncio_da_rimuovere != null) {

					if (model.rimuoviAnnuncio(annuncio_da_rimuovere)) {
						view.getMarketplaceStaffPanel().getLblMessaggio().setText("Annuncio eliminato con successo!");
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

		view.getConfiguratoreUserPanel().getBtnLoginLogout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setUtente_loggato(null);
				view.mostraMarketplaceGuest();
				System.out.println("Logout");
			}
		});

		view.getConfiguratoreUserPanel().getBtnSalvaBuild().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nome_build = view.getConfiguratoreUserPanel().getTxtNomeBuild().toString();
				model.getBuild_configuratore().setNome(nome_build);
				model.getBuild_configuratore().aggiornaPrezzo();
				model.salvaBuildConfiguratore();
			}
		});


		view.getConfiguratoreUserPanel().getBtnCarrello().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.mostraCarrello();

			}
		});
	}

	private void addListenersGuestConfiguratore() {

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

		for (Prodotto p : model.getProdotti()) {
			if (p instanceof Componente) {
				Componente c = (Componente) p;
				if (c.getTipo() == tipoScelto) {
					modelCatalogo.addElement(c);
				}
			}
		}

	
		view.getConfiguratoreGuestPanel().getListComponentiDisponibili().setModel(modelCatalogo);
		view.getConfiguratoreUserPanel().getListComponentiDisponibili().setModel(modelCatalogo);
	}

	private void gestisciListenersReattiviConfiguratore() {


		javax.swing.event.ListSelectionListener reazioneTipoComponente = e -> {
			if (!e.getValueIsAdjusting()) {
				TipoComponente tipo = getTipoSelezionato();
				if (tipo != null) {
					popolaListaCatalogoConfiguratore(tipo); 
					view.getConfiguratoreGuestPanel().getTxtAreaInfoCatalogo().setText("");
					view.getConfiguratoreUserPanel().getTxtAreaInfoCatalogo().setText("");
				}
			}
		};

		view.getConfiguratoreGuestPanel().getListTipiComponente().addListSelectionListener(reazioneTipoComponente);
		view.getConfiguratoreUserPanel().getListTipiComponente().addListSelectionListener(reazioneTipoComponente);


		javax.swing.event.ListSelectionListener reazioneComponenteCatalogo = e -> {
			if (!e.getValueIsAdjusting()) {
				Componente c = getComponenteCatalogoSelezionato();
				if (c != null) {
					String info = c.getInfoProdotto();

					view.getConfiguratoreGuestPanel().getTxtAreaInfoCatalogo().setText(info);
					view.getConfiguratoreUserPanel().getTxtAreaInfoCatalogo().setText(info);
				}
			}
		};

		view.getConfiguratoreGuestPanel().getListComponentiDisponibili().addListSelectionListener(reazioneComponenteCatalogo);
		view.getConfiguratoreUserPanel().getListComponentiDisponibili().addListSelectionListener(reazioneComponenteCatalogo);


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


	private void addListenersGestioneAnnunci() {
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
			if (!e.getValueIsAdjusting()) {
				Prodotto prodottoSelezionato = view.getAggiuntaAnnuncioPanel().getListProdotti().getSelectedValue();
				if (prodottoSelezionato != null) {
					view.getAggiuntaAnnuncioPanel().getLblMessaggio().setForeground(java.awt.Color.BLUE);
					view.getAggiuntaAnnuncioPanel().getLblMessaggio().setText("Hai selezionato: " + prodottoSelezionato.toString()); 
				} else {
					view.getAggiuntaAnnuncioPanel().getLblMessaggio().setText(" ");
				}
			}
		};

		view.getAggiuntaAnnuncioPanel().getListProdotti().addListSelectionListener(reazioneSelezionaProdottoDaVendere);
	}

 

	private void addListenersCarrello() {

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
			if (!e.getValueIsAdjusting()) {
				Annuncio annuncioSelezionato = view.getCarrelloPanel().getListAnnunci().getSelectedValue();

				if (annuncioSelezionato != null) {
					String info = annuncioSelezionato.toString();
					view.getCarrelloPanel().getLblMessaggio().setText(info);
				} else {
					view.getCarrelloPanel().getLblMessaggio().setText("");
				}
			}
		};

	
		view.getCarrelloPanel().getListAnnunci().addListSelectionListener(reazioneSelezionaCarrello);
	}


	

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


	}




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
