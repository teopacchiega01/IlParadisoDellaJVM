package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.GestoreAccount;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Assistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.Forum;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.Marketplace;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.ForumView;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.FrameMarketplace;

/**
 * @author Persy
 */
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
		addListenersLogin();
		addListenersModificaPagamento();
		this.view.setVisible(true);
	}
	
	private void addListenersHomePanel() {
		
		view.getHomePanel().getRegistrati_staff_butt().addActionListener(e -> view.mostraRegistrazioneStaff());
		view.getHomePanel().getRegistrati_utente_butt().addActionListener(e -> view.mostraRegistrazioneUtente());

		view.getHomePanel().getLogin_butt().addActionListener(e -> {
			if(model.getUtenteLoggato() == null) {
				 view.mostraLogin();
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
			view.getHomePanel().setVisibilitaModificaPagamento(false); 
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
			
			FrameMarketplace fm = view.creaFrameMarketplace() ;
			Marketplace mp = Marketplace.getInstance();
//			System.out.println("Utente loggato nella home: "+model.getUtenteLoggato().getUser_name());
			mp.setUtente_loggato(model.getUtenteLoggato());
//			System.out.println("Utente loggato nel marketplace: "+mp.getUtente_loggato().getUser_name());
			new ControllerMarketplace(mp, fm);
			
			this.view.setVisible(false);
			fm.setVisible(true);
		});
		
		view.getHomePanel().getForum_butt().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Forum f = Forum.getInstance();
				
				f.setU(model.getUtenteLoggato());
				
				ForumView fv = view.creaForumFrame();
				
				new ControllerForum(f, fv);
				
				fv.setVisible(true);
				view.setVisible(false);
				
			}
		});


		view.getHomePanel().getModificaPagamentoButt().addActionListener(e -> {
			view.mostraModificaPagamento();
		});
	}
	
	private void addListenersRegistrazioneUtentePanel() {
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
			String titolare_carta = view.getRegUtentePanel().getTitolareCarta();
			LocalDate scadenza_carta = view.getRegUtentePanel().getScadenzaCarta();
			String cvv_carta = view.getRegUtentePanel().getCvvCarta();
			
			boolean successo = model.registraUtenteGenerico(nome, cognome, username, email, password, via, civico, citta, provincia, cap, titolare_carta, scadenza_carta, cvv_carta);

			if(successo) {
				view.getRegUtentePanel().pulisciCampi();
				view.getHomePanel().setLabelOut("Registrazione completata! Ora puoi fare il Login.");
				view.mostraHome();
			} else {
				view.getRegUtentePanel().setLabelOut("Errore: Username o Email già presenti.");
			}
		});
	}
	
	private void addListenersRegistrazioneStaffPanel() {
		view.getRegStaffPanel().getIndietro_butt().addActionListener(e -> {
			view.getRegStaffPanel().pulisciCampi();
			view.mostraHome();
		});
		
		view.getRegStaffPanel().getRegistrati_butt().addActionListener(e -> {
			if(!view.getRegStaffPanel().validaCampi()) return;

			String email = view.getRegStaffPanel().getEmail();
			if (!email.endsWith("@staff.it")) {
				view.getRegStaffPanel().setLabelOut("Errore: La mail dello staff deve terminare con @staff.it");
				return; 
			}

			String nome = view.getRegStaffPanel().getNome();
			String cognome = view.getRegStaffPanel().getCognome();
			String username = view.getRegStaffPanel().getUsername();
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
	
	private void addListenersLogin() {
		view.getLoginPanel().getBtnTornaHome().addActionListener(e -> view.mostraHome());
	
		view.getLoginPanel().getBtnEffettuaLogin().addActionListener(e -> {
			String email = view.getLoginPanel().getTxtEmail().getText();
			String pw = new String(view.getLoginPanel().getTxtPassword().getPassword());
			
			boolean successo = model.loginUtente(email, pw);
			if(!successo) {
				System.out.println("Login fallito");
				view.getLoginPanel().getLblMessaggio().setText("Login fallito: email o password errate");
			} else {
				view.getLoginPanel().getLblMessaggio().setText(" "); 
				view.getLoginPanel().getTxtEmail().setText("");   
				view.getLoginPanel().getTxtPassword().setText("");
				
				view.getHomePanel().setLabelUtenteLoggato(model.getUtenteLoggato().getUser_name());
				view.getHomePanel().setVisibilitaLogin(false);
				view.getHomePanel().setVisibilitaLogout(true);
				view.getHomePanel().setVisibilitaRegistrati(false);
				view.getHomePanel().setLabelOut("Benvenuto, " + model.getUtenteLoggato().getNome() + "!");
				

				if (!model.getUtenteLoggato().isStaff()) {
					view.getHomePanel().setVisibilitaModificaPagamento(true);
				} else {
					view.getHomePanel().setVisibilitaModificaPagamento(false);
				}
				
				view.mostraHome(); 
			}
		});
	}
	

	private void addListenersModificaPagamento() {
		view.getModificaPagamentoPanel().getIndietro_butt().addActionListener(e -> {
			view.getModificaPagamentoPanel().pulisciCampi();
			view.getModificaPagamentoPanel().setLabelOut(" ");
			view.mostraHome();
		});

		view.getModificaPagamentoPanel().getAggiorna_butt().addActionListener(e -> {
			if (!view.getModificaPagamentoPanel().validaCampi()) return;

			String numero = view.getModificaPagamentoPanel().getNumeroCarta();
			LocalDate scadenza = view.getModificaPagamentoPanel().getScadenzaCarta();
			String cvv = view.getModificaPagamentoPanel().getCvvCarta();

			boolean successo = model.aggiornaPagamentoUtenteGenerico(numero, scadenza, cvv);

			if (successo) {
				view.getModificaPagamentoPanel().pulisciCampi();
				view.getModificaPagamentoPanel().setLabelOut(" ");
				view.getHomePanel().setLabelOut("Metodo di pagamento aggiornato!");
				System.out.println("Profilo Aggiornato:\n" + model.getUtenteLoggato());
				view.mostraHome();
			} else {
				view.getModificaPagamentoPanel().setLabelOut("Errore durante l'aggiornamento. Riprova.");
			}
		});
	}
	
	private void inizializzaInterfacciaHome() {
		if(model.getUtenteLoggato() != null) {
			view.getHomePanel().setLabelUtenteLoggato(model.getUtenteLoggato().getUser_name());
			view.getHomePanel().setVisibilitaLogin(false);
			view.getHomePanel().setVisibilitaLogout(true);
			view.getHomePanel().setVisibilitaRegistrati(false);
			

			if(!model.getUtenteLoggato().isStaff()) {
				view.getHomePanel().setVisibilitaModificaPagamento(true);
			}
		}
	}

	public GestoreAccount getModel() { return model; }
	public void setModel(GestoreAccount model) { this.model = model; }
	public HomeFrame getView() { return view; }
	public void setView(HomeFrame view) { this.view = view; }
}