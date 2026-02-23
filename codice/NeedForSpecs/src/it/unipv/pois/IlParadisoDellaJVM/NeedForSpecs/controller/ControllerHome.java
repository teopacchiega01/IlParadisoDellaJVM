package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;

import java.time.LocalDate;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.GestoreAccount;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Assistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;

public class ControllerHome {
	
	private GestoreAccount model;
	private HomeFrame view; 

	public ControllerHome(GestoreAccount model, HomeFrame view) {
		this.model = model;
		this.view = view;
		inizializzaInterfacciaHome();
		addListenersHomePanel();
		addListenersRegistrazioneUtentePanel();
		addListenersRegistrazioneStaffPanel();
		this.view.setVisible(true);
	}
	
	public void addListenersHomePanel() {
		
		view.getHomePanel().getRegistrati_staff_butt().addActionListener(e -> view.mostraRegistrazioneStaff());
		
		view.getHomePanel().getRegistrati_utente_butt().addActionListener(e -> view.mostraRegistrazioneUtente());

		view.getHomePanel().getLogin_butt().addActionListener(e -> {
			if(model.getUtenteLoggato() == null) {
				// TODO: view.mostraLogin();
			} else {
				view.getHomePanel().setLabelOut("Sei già loggato");
			}
		});
		
		view.getHomePanel().getLogout_butt().addActionListener(e -> {
			model.logout();
			view.getHomePanel().setLabelOut("Logout effettuato con successo. Arrivederci!");
			view.getHomePanel().setLabelUtenteLoggato("Ospite");
			view.getHomePanel().setVisibilitaLogin(true);
			view.getHomePanel().setVisibilitaLogout(false);
			view.getHomePanel().setVisibilitaRegistrati(true);
		});
		
		view.getHomePanel().getAssistenza_butt().addActionListener(e -> {
			Utente u = model.getUtenteLoggato();
			if(u != null) {
				FrameAssistenza fa = view.creaFrameAssistenza();
				Assistenza ass = Assistenza.getInstance();
				ass.setUtente_loggato(u);
				view.setVisible(false);
				new ControllerAssistenza(fa, ass);
			} else {
				view.getHomePanel().setLabelOut("Devi loggarti per accedere all'assistenza");
			}
		});
		
		view.getHomePanel().getMarketplace_butt().addActionListener(e -> {
			// TODO
		});
	}
	
	public void addListenersRegistrazioneUtentePanel() {
		
		view.getRegUtentePanel().getIndietro_butt().addActionListener(e -> {
			view.getRegUtentePanel().pulisciCampi();
			view.mostraHome();
		});
		
		view.getRegUtentePanel().getRegistrati_butt().addActionListener(e -> {
			
			if(!view.getRegUtentePanel().validaCampi()) return;

			String nome = view.getRegUtentePanel().getNome();
			String cognome = view.getRegUtentePanel().getCognome();
			String username = view.getRegUtentePanel().getUsername();
			String email = view.getRegUtentePanel().getEmail();
			String password = view.getRegUtentePanel().getPassword();
			String via = view.getRegUtentePanel().getVia();
			String civico = view.getRegUtentePanel().getCivico();
			String citta = view.getRegUtentePanel().getCitta();
			String cap = view.getRegUtentePanel().getCap();
			String provincia = view.getRegUtentePanel().getProvincia();
			String titolareCarta = view.getRegUtentePanel().getTitolareCarta();
			LocalDate scadenzaCarta = view.getRegUtentePanel().getScadenzaCarta();
			String cvvCarta = view.getRegUtentePanel().getCvvCarta();
			
			boolean successo = model.registraUtenteGenerico(nome, cognome, username, email, password, via, civico, citta, provincia, cap, titolareCarta, scadenzaCarta, cvvCarta);

			if(successo) {
				view.getRegUtentePanel().pulisciCampi();
				view.getHomePanel().setLabelOut("Registrazione completata! Ora puoi fare il Login.");
				view.mostraHome();
			} else {
				view.getRegUtentePanel().setLabelOut("Errore: Username o Email già presenti.");
			}
		});
	}
	
	public void addListenersRegistrazioneStaffPanel() {
		
		view.getRegStaffPanel().getIndietro_butt().addActionListener(e -> {
			view.getRegStaffPanel().pulisciCampi();
			view.mostraHome();
		});
		
		view.getRegStaffPanel().getRegistrati_butt().addActionListener(e -> {
			
			if(!view.getRegStaffPanel().validaCampi()) return;

			String nome = view.getRegStaffPanel().getNome();
			String cognome = view.getRegStaffPanel().getCognome();
			String username = view.getRegStaffPanel().getUsername();
			String email = view.getRegStaffPanel().getEmail();
			String password = view.getRegStaffPanel().getPassword();

			boolean successo = model.registraStaff(nome, cognome, username, email, password);

			if(successo) {
				view.getRegStaffPanel().pulisciCampi();
				view.getHomePanel().setLabelOut("Staff registrato con successo!");
				view.mostraHome();
			} else {
				view.getRegStaffPanel().setLabelOut("Errore durante la registrazione dello Staff.");
			}
		});
	}
	
	public void inizializzaInterfacciaHome() {
		if(model.getUtenteLoggato() != null) {
			view.getHomePanel().setLabelUtenteLoggato(model.getUtenteLoggato().getUser_name());
		}
	}

	public GestoreAccount getModel() {
		return model;
	}

	public void setModel(GestoreAccount model) {
		this.model = model;
	}

	public HomeFrame getView() {
		return view;
	}

	public void setView(HomeFrame view) {
		this.view = view;
	}
	
	
}