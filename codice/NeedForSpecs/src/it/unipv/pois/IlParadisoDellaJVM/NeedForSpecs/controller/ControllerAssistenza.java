package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Assistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Stato;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy.Ricerca;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utili.StringChecker;

public class ControllerAssistenza {
	
	private FrameAssistenza view; 
	private Assistenza model;
	private Timer chatUpdater;

	// COSTRUTTORE UNICO A 2 PARAMETRI (MVC PURO)
	public ControllerAssistenza(FrameAssistenza view, Assistenza model) {
		this.view = view;
		this.model = model;
		
		// 1. Il model carica i ticket usando la sua logica interna (isStaff)
		this.model.caricaTicketUtenteLoggato();
		
		// 2. Il controller instrada la grafica chiamando un metodo separato e pulito
		inizializzaInterfaccia();
		
		this.view.setVisible(true);
	}
	
	// ==========================================================
	// METODO DI ROUTING GRAFICO
	// ==========================================================
	private void inizializzaInterfaccia() {
		Utente u = model.getUtente_loggato();
		ArrayList<Ticket> tickets = model.getTuttiITicketCaricati();
		
		if (u.isStaff()) {
			view.getHomePanelStaff().inizializzaTicketCombo(tickets);
			addListenersStaff();
			view.mostraHomeStaff(); 
			avviaTimerSincronizzazioneStaff();
		} else {
			view.getHomePanelUtente().inizializzaTicketCombo(tickets);
			addListenersUtente();
			view.mostraHomeUtente();
			avviaTimerSincronizzazioneUtente();
		}
	}
	
	/*==========================================================
	 * LOGICA UTENTE GENERICO
	 *==========================================================
	 */
	private void addListenersUtente() {
		view.getHomePanelUtente().getCrea_ticket_butt().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean creato = model.apriTicket();
				if(creato) {
					view.getHomePanelUtente().inizializzaTicketCombo(model.getTuttiITicketCaricati());
					view.getHomePanelUtente().setLabelOutUtente("Ticket creato con successo!");
				}
			}
		});

		view.getHomePanelUtente().getVai_a_ticket_butt().addActionListener(e -> caricaChatUtente());
		view.getTicketUtentePanel().getInvia_messaggio_utente().addActionListener(e -> gestisciInvioMessaggioUtente());
		
		view.getTicketUtentePanel().getIndietro_butt().addActionListener(e -> {
			chatUpdater.start(); 
			view.mostraHomeUtente();
		});

		view.getTicketUtentePanel().getCerca_butt().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ticket t = view.getHomePanelUtente().getTicketSelezionato();
				Ricerca tipo = (Ricerca) view.getTicketUtentePanel().getCombo_ricerca().getSelectedItem();
				String parametro = StringChecker.pulisciInput(view.getTicketUtentePanel().getTesto_ricerca().getText());

				if (t != null && !parametro.isEmpty()) {
					chatUpdater.stop(); 
					String risultati = model.eseguiRicercaSuTicket(t.getId_ticket(), tipo, parametro);
					view.getTicketUtentePanel().pulisciChat();
					view.getTicketUtentePanel().setConversazioneTicket("--- RISULTATI RICERCA ---\n\n" + risultati);
				}
			}
		});

		view.getTicketUtentePanel().getReset_ricerca_butt().addActionListener(e -> {
			view.getTicketUtentePanel().getTesto_ricerca().setText("");
			caricaChatUtente();
			chatUpdater.start(); 
		});
	}
	
	private void caricaChatUtente() {
		Ticket t = view.getHomePanelUtente().getTicketSelezionato();
		if (t != null) {
			view.getTicketUtentePanel().pulisciChat();
			view.getTicketUtentePanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());
			view.mostraChatUtente();
		}
	}

	private void gestisciInvioMessaggioUtente() {
		String testo = StringChecker.pulisciInput(view.getTicketUtentePanel().getTestoUtente()); 
		Ticket t = view.getHomePanelUtente().getTicketSelezionato();
		if (t != null && !testo.isEmpty()) {
			if (model.creaMessaggio(testo, t.getId_ticket())) {
				view.getTicketUtentePanel().pulisciInput();
			}
		}
	}

	/*==========================================================
	 * LOGICA STAFF
	 *==========================================================
	 */
	private void addListenersStaff() {
		view.getHomePanelStaff().getVai_a_ticket_butt().addActionListener(e -> caricaChatStaff());
		
		view.getHomePanelStaff().getCambia_stato_butt().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ticket t = view.getHomePanelStaff().getTicketSelezionato();
				Stato s = view.getHomePanelStaff().getStatoSelezionato();
				if(t != null && s != null) {
					model.cambioStatoTicket(t.getId_ticket(), s);
					view.getHomePanelStaff().setLabelOutStaff("Stato aggiornato a: " + s);
					view.getHomePanelStaff().aggiornaGrafica(); 
				}
			}
		});
		
		view.getHomePanelStaff().getChiudi_ticket_butt().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ticket t = view.getHomePanelStaff().getTicketSelezionato();
				if(t != null) {
					if(model.chiudiTicket(t.getId_ticket())) {
						view.getHomePanelStaff().inizializzaTicketCombo(model.getTuttiITicketCaricati());
						view.getHomePanelStaff().setLabelOutStaff("Ticket " + t.getId_ticket() + " chiuso.");
						view.getTicketStaffPanel().setLabeOutTicketStaff("Stato Chat: " + t.getStato_ticket());
						view.getTicketUtentePanel().setLabelOutUtente("Stato Chat " + t.getStato_ticket());
					}
				}
			}
		});

		view.getTicketStaffPanel().getInvia_messaggio_staff().addActionListener(e -> gestisciInvioMessaggioStaff());
		
		view.getTicketStaffPanel().getIndietro_butt().addActionListener(e -> {
			chatUpdater.start(); 
			view.mostraHomeStaff();
		});

		view.getTicketStaffPanel().getCerca_butt().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ticket t = view.getHomePanelStaff().getTicketSelezionato();
				Ricerca tipo = (Ricerca) view.getTicketStaffPanel().getCombo_ricerca().getSelectedItem();
				String parametro = StringChecker.pulisciInput(view.getTicketStaffPanel().getTesto_ricerca().getText());

				if (t != null && !parametro.isEmpty()) {
					chatUpdater.stop(); 
					String risultati = model.eseguiRicercaSuTicket(t.getId_ticket(), tipo, parametro);
					view.getTicketStaffPanel().pulisciChat();
					view.getTicketStaffPanel().setConversazioneTicket("--- RISULTATI RICERCA ---\n\n" + risultati);
				}
			}
		});

		view.getTicketStaffPanel().getReset_ricerca_butt().addActionListener(e -> {
			view.getTicketStaffPanel().getTesto_ricerca().setText("");
			caricaChatStaff();
			chatUpdater.start(); 
		});
	}

	private void caricaChatStaff() {
		Ticket t = view.getHomePanelStaff().getTicketSelezionato();
		if (t != null) {
			view.getTicketStaffPanel().pulisciChat();
			view.getTicketStaffPanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());
			view.mostraChatStaff();
		}
	}

	private void gestisciInvioMessaggioStaff() {
		String testo = StringChecker.pulisciInput(view.getTicketStaffPanel().getTesto_messaggio_staff().getText());
		Ticket t = view.getHomePanelStaff().getTicketSelezionato();
		if (t != null && !testo.isEmpty()) {
			if (model.creaMessaggio(testo, t.getId_ticket())) {
				view.getTicketStaffPanel().pulisciInput();
			}
		}
	}

	// ==========================================================
	// 				AGGIORNAMENTO DELLA CHAT (Timer)
	// ==========================================================
	private void avviaTimerSincronizzazioneUtente() {
		chatUpdater = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (view.getTicketUtentePanel().isPannelloVisibile()) {
					Ticket t = view.getHomePanelUtente().getTicketSelezionato();
					if (t != null) {
						model.aggiornaConversazioneDatoTicket(t.getId_ticket());
						view.getTicketUtentePanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());
					}
				}
			}
		});
		chatUpdater.start();
	}

	private void avviaTimerSincronizzazioneStaff() {
		chatUpdater = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (view.getTicketStaffPanel().isPannelloVisibile()) {
					Ticket t = view.getHomePanelStaff().getTicketSelezionato();
					if (t != null) {
						model.aggiornaConversazioneDatoTicket(t.getId_ticket());
						view.getTicketStaffPanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());
					}
				}
			}
		});
		chatUpdater.start();
	}
}