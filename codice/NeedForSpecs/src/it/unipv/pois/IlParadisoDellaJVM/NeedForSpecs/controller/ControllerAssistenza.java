package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;



// @author Persy 

// implementare il controller che riceve un utente U e poi chiama i metodi in base alla tipologia 


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
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

	public ControllerAssistenza(FrameAssistenza view, Assistenza model, UtenteGenerico utente) {
		this.view = view;
		this.model = model;
		
		ArrayList<Ticket> tickets = model.inizializzaTicketDaUtenteGenerico(utente);
		view.getHomePanelUtente().inizializzaTicketCombo(tickets);
		
		addListenersUtente(utente);
		
		view.mostraHomeUtente();
		view.setVisible(true);
		avviaTimerSincronizzazioneUtente();
	}

	public ControllerAssistenza(FrameAssistenza view, Assistenza model, UtenteStaff staff) {
	    this.view = view;
	    this.model = model;
	    
	    model.assegnaTicketToGestore(staff); 
	    
	    ArrayList<Ticket> tickets = model.inizializzaTicketDaUtenteStaff(staff);
	    view.getHomePanelStaff().inizializzaTicketCombo(tickets);
	    
	    addListenersStaff(staff);
	    
	    view.mostraHomeStaff(); 
	    view.setVisible(true);
	    avviaTimerSincronizzazioneStaff();
	}
	public ControllerAssistenza(FrameAssistenza view, Assistenza model) {
		this.view = view;
		this.model = model;
//		addListenersUtenteAbs();
		
	}
	
	/*==========================================================
	 * 	LOGICA UTENTE
	 *==========================================================
	 */
	
	
//	public void addListenersUtenteAbs(){
//		Utente loggato = model.getUtente_loggato();
//		if(loggato.Metodo) {
//			UtenteGenerico utente = (UtenteGenerico) loggato;
//			ArrayList<Ticket> tickets = model.inizializzaTicketDaUtenteGenerico(utente);
//			view.getHomePanelUtente().inizializzaTicketCombo(tickets);
//			
//			addListenersUtente(utente);
//			
//			view.mostraHomeUtente();
//			view.setVisible(true);
//			avviaTimerSincronizzazioneUtente();
//		}else {
//			UtenteStaff staff = (UtenteStaff) loggato;
//			model.assegnaTicketToGestore(staff); 
//
//			ArrayList<Ticket> tickets = model.inizializzaTicketDaUtenteStaff(staff);
//			view.getHomePanelStaff().inizializzaTicketCombo(tickets);
//
//			addListenersStaff(staff);
//
//			view.mostraHomeStaff(); 
//			view.setVisible(true);
//			avviaTimerSincronizzazioneStaff();
//		}
//	}
//	
	private void addListenersUtente(UtenteGenerico utente) {
		
		//
		view.getHomePanelUtente().getCrea_ticket_butt().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean creato = model.apriTicket(utente);
				if(creato) {
					view.getHomePanelUtente().inizializzaTicketCombo(model.inizializzaTicketDaUtenteGenerico(utente));
					view.getHomePanelUtente().setLabelOutUtente("Ticket creato con successo!");
				}
			}
		});


		view.getHomePanelUtente().getVai_a_ticket_butt().addActionListener(e -> caricaChatUtente());
		view.getTicketUtentePanel().getInvia_messaggio_utente().addActionListener(e -> gestisciInvioMessaggioUtente(utente));
		view.getTicketUtentePanel().getIndietro_butt().addActionListener(e -> view.mostraHomeUtente());
	}
	
	private void caricaChatUtente() {
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

	/*==========================================================
	 * LOGICA STAFF
	 *==========================================================
	 */

	private void addListenersStaff(UtenteStaff staff) {
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
						ArrayList<Ticket> ticket_aggiornati = model.inizializzaTicketDaUtenteStaff(staff);
						view.getHomePanelStaff().inizializzaTicketCombo(ticket_aggiornati);
						view.getHomePanelStaff().setLabelOutStaff("Ticket " + t.getId_ticket() + " chiuso.");
						view.getTicketStaffPanel().setLabeOutTicketStaff("Stato Chat: " + t.getStato_ticket());
						view.getTicketUtentePanel().setLabelOutUtente("Stato Chat " + t.getStato_ticket());
					}
				}
			}
		});

		view.getTicketStaffPanel().getInvia_messaggio_staff().addActionListener(e -> gestisciInvioMessaggioStaff(staff));
		view.getTicketStaffPanel().getIndietro_butt().addActionListener(e -> view.mostraHomeStaff());
	}

	private void caricaChatStaff() {
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
	// 				AGGIORNAMENTO DELLA CHAT  
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