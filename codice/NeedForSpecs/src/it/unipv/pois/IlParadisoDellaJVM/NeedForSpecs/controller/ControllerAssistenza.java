package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Assistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Stato;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;

public class ControllerAssistenza {
	
	private FrameAssistenza view; 
	private Assistenza model;
	private Timer chatUpdater;
	
	// ==========================================================
	// 1. COSTRUTTORE PER UTENTE GENERICO
	// ==========================================================
	public ControllerAssistenza(FrameAssistenza view, Assistenza model, UtenteGenerico utente) {
		this.view = view;
		this.model = model;
		
		// Inizializzazione dati
		ArrayList<Ticket> tickets = model.inizializzaTicketDaUtenteGenerico(utente);
		view.getHomePanelUtente().inizializzaTicketCombo(tickets);
		
		addListenersUtente(utente);
		
		view.mostraHomeUtente();
		view.setVisible(true);
		avviaTimerSincronizzazioneUtente();
	}

	// ==========================================================
	// 2. COSTRUTTORE PER STAFF
	// ==========================================================
	public ControllerAssistenza(FrameAssistenza view, Assistenza model, UtenteStaff staff) {
	    this.view = view;
	    this.model = model;
	    
	    // PRIMA: Assegniamo allo staff i ticket che non hanno ancora un gestore
	    model.assegnaTicketToGestore(staff); 
	    
	    // DOPO: Carichiamo la lista (che ora includerà anche quelli appena assegnati)
	    ArrayList<Ticket> tickets = model.inizializzaTicketDaUtenteStaff(staff);
	    view.getHomePanelStaff().inizializzaTicketCombo(tickets);
	    
	    addListenersStaff(staff);
	    
	    view.mostraHomeStaff(); // Adesso mostrerà la home giusta
	    view.setVisible(true);
	    avviaTimerSincronizzazioneStaff();
	}

	// ==========================================================
	// METODI LOGICA UTENTE (Incluso Creazione Ticket)
	// ==========================================================
	private void addListenersUtente(UtenteGenerico utente) {
		// Bottone CREA TICKET
		view.getHomePanelUtente().getCrea_ticket_butt().addActionListener(e -> {
			boolean creato = model.apriTicket(utente);
			if(creato) {
				// Ricarico la combo per mostrare il nuovo ticket (in stato IN_ASSEGNAZIONE)
				view.getHomePanelUtente().inizializzaTicketCombo(model.inizializzaTicketDaUtenteGenerico(utente));
				view.getHomePanelUtente().setLabelOutUtente("Ticket creato con successo!");
			}
		});

		view.getHomePanelUtente().getVai_a_ticket_butt().addActionListener(e -> mostraChatUtente());
		
		view.getTicketUtentePanel().getInvia_messaggio_utente().addActionListener(e -> gestisciInvioMessaggioUtente(utente));
		
		view.getTicketUtentePanel().getIndietro_butt().addActionListener(e -> view.mostraHomeUtente());
	}
	
	private void mostraChatUtente() {
		Ticket t = view.getHomePanelUtente().getTicketSelezionato();
		if (t != null) {
			view.getTicketUtentePanel().pulisciChat();
			view.getTicketUtentePanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());
			view.mostraChatUtente();
		}
	}

	private void gestisciInvioMessaggioUtente(UtenteGenerico utente) {
	    String testo = view.getTicketUtentePanel().getTestoUtente(); 
	    Ticket t = view.getHomePanelUtente().getTicketSelezionato();
	    if (t != null && !testo.isEmpty()) {
	        if (model.creaMessaggio(utente, testo, t.getId_ticket())) {
	            view.getTicketUtentePanel().pulisciInput();
	        }
	    }
	}

	// ==========================================================
	// METODI LOGICA STAFF (Cambio Stato e Chiusura)
	// ==========================================================
	private void addListenersStaff(UtenteStaff staff) {
		// Bottone VAI A TICKET
		view.getHomePanelStaff().getVai_a_ticket_butt().addActionListener(e -> mostraChatStaff());

		// Bottone CAMBIA STATO
		view.getHomePanelStaff().getCambia_stato_butt().addActionListener(e -> {
			Ticket t = view.getHomePanelStaff().getTicketSelezionato();
			Stato s = view.getHomePanelStaff().getStatoSelezionato();
			if(t != null && s != null) {
				model.cambioStatoTicket(t.getId_ticket(), s);
				view.getHomePanelStaff().setLabelOutStaff("Stato aggiornato a: " + s);
				// Aggiorno la combo per riflettere il cambio (es: nel toString)
				view.getHomePanelStaff().repaint(); 
			}
		});

		// Bottone CHIUDI TICKET
		view.getHomePanelStaff().getChiudi_ticket_butt().addActionListener(e -> {
			Ticket t = view.getHomePanelStaff().getTicketSelezionato();
			if(t != null) {
				if(model.chiudiTicket(t.getId_ticket())) {
					// Ricarico la lista dei ticket attivi dello staff
					view.getHomePanelStaff().inizializzaTicketCombo(model.inizializzaTicketDaUtenteStaff(staff));
					view.getHomePanelStaff().setLabelOutStaff("Ticket " + t.getId_ticket() + " chiuso.");
				}
			}
		});

		view.getTicketStaffPanel().getInvia_messaggio_staff().addActionListener(e -> gestisciInvioMessaggioStaff(staff));
		
		view.getTicketStaffPanel().getIndietro_butt().addActionListener(e -> view.mostraHomeStaff());
	}

	private void mostraChatStaff() {
		Ticket t = view.getHomePanelStaff().getTicketSelezionato();
		if (t != null) {
			view.getTicketStaffPanel().pulisciChat();
			view.getTicketStaffPanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());
			view.mostraChatStaff();
		}
	}

	private void gestisciInvioMessaggioStaff(UtenteStaff staff) {
		String testo = view.getTicketStaffPanel().getTesto_messaggio_staff().getText().trim();
		Ticket t = view.getHomePanelStaff().getTicketSelezionato();
		if (t != null && !testo.isEmpty()) {
			if (model.creaMessaggio(staff, testo, t.getId_ticket())) {
				view.getTicketStaffPanel().pulisciInput();
			}
		}
	}

	// ==========================================================
	// TIMER E SINCRONIZZAZIONE (Separati per sicurezza grafica)
	// ==========================================================
	private void avviaTimerSincronizzazioneUtente() {
	    chatUpdater = new Timer(3000, e -> {
	        if (view.getTicketUtentePanel().isShowing()) {
	            Ticket t = view.getHomePanelUtente().getTicketSelezionato();
	            if (t != null) {
	                model.aggiornaConversazioneDatoTicket(t.getId_ticket());
	                view.getTicketUtentePanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());
	            }
	        }
	    });
	    chatUpdater.start();
	}

	private void avviaTimerSincronizzazioneStaff() {
	    chatUpdater = new Timer(3000, e -> {
	        if (view.getTicketStaffPanel().isShowing()) {
	            Ticket t = view.getHomePanelStaff().getTicketSelezionato();
	            if (t != null) {
	                model.aggiornaConversazioneDatoTicket(t.getId_ticket());
	                view.getTicketStaffPanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());
	            }
	        }
	    });
	    chatUpdater.start();
	}
}