package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.GestoreAccount;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Assistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Stato;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy.Ricerca;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;
/**
 * @author Persy
 */
public class ControllerAssistenza {
	
	private FrameAssistenza view; 
	private Assistenza model;
	private Timer chatUpdater;

	public ControllerAssistenza(FrameAssistenza view, Assistenza model) {
		this.view = view;
		this.model = model;
		this.model.caricaTicketUtenteLoggato();
		inizializzaInterfaccia();
		this.view.setVisible(true);
	}
	
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

		view.getHomePanelUtente().getVai_a_ticket_butt().addActionListener(e -> {
		    Ticket ticketSelezionato = view.getHomePanelUtente().getTicketSelezionato();
		    if (ticketSelezionato != null) {
		        
		        if (ticketSelezionato.getGestore() == null) {
		            view.getHomePanelUtente().setLabelOutUtente("Il ticket è in attesa di assegnazione. Attendi uno Staff.");
		            return; 
		        }

		        caricaChatUtente();
		    }
		});
		view.getTicketUtentePanel().getInvia_messaggio_utente().addActionListener(e -> gestisciInvioMessaggioUtente());
		
		view.getHomePanelUtente().getTornaHomeButt().addActionListener(e ->{
			view.getHomePanelUtente().setLabelOutUtente("Logout effettuato con successo. Arrivederci!");
			
			
			GestoreAccount gest = GestoreAccount.getInstance();
			HomeFrame hf = view.creaHomeFrame();
			view.setVisible(false);
			hf.mostraHome();
			new ControllerHome(gest, hf);
			
			
		});
		
		
		
		view.getTicketUtentePanel().getIndietro_butt().addActionListener(e -> {
			chatUpdater.start(); 
			view.mostraHomeUtente();
		});

		view.getTicketUtentePanel().getCerca_butt().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ticket t = view.getHomePanelUtente().getTicketSelezionato();
				
			
				Ricerca tipo = view.getTicketUtentePanel().getTipoRicercaSelezionata();
				String parametro = view.getTicketUtentePanel().getParametroRicerca();

				if (t != null && !parametro.isEmpty()) {
					chatUpdater.stop(); 
					String risultati = model.eseguiRicercaSuTicket(t.getId_ticket(), tipo, parametro);
					view.getTicketUtentePanel().pulisciChat();
					view.getTicketUtentePanel().setConversazioneTicket("--- RISULTATI RICERCA ---\n\n" + risultati);
				}
			}
		});

		view.getTicketUtentePanel().getReset_ricerca_butt().addActionListener(e -> {
			view.getTicketUtentePanel().resetCampoRicerca();
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
		
		String testo = view.getTicketUtentePanel().getTestoMessaggioDaInviare(); 
		Ticket t = view.getHomePanelUtente().getTicketSelezionato();
		if (t != null && !testo.isEmpty()) {
			if (model.creaMessaggio(testo, t.getId_ticket())) {
				view.getTicketUtentePanel().pulisciInput();
				view.getTicketUtentePanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());			
			}
		}
	}

	
	
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
		
		

		view.getHomePanelStaff().getTornaHomeButt().addActionListener(e ->{
			view.getHomePanelStaff().setLabelOutStaff("Logout effettuato con successo. Arrivederci!");
			GestoreAccount gen = GestoreAccount.getInstance();
			gen.logout();
			HomeFrame hf = view.creaHomeFrame();
			view.setVisible(false);
			new ControllerHome(gen, hf);
			
			
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
				

				Ricerca tipo = view.getTicketStaffPanel().getTipoRicercaSelezionata();
				String parametro = view.getTicketStaffPanel().getParametroRicerca();

				if (t != null && !parametro.isEmpty()) {
					chatUpdater.stop(); 
					String risultati = model.eseguiRicercaSuTicket(t.getId_ticket(), tipo, parametro);
					view.getTicketStaffPanel().pulisciChat();
					view.getTicketStaffPanel().setConversazioneTicket("--- RISULTATI RICERCA ---\n\n" + risultati);
				}
			}
		});

		view.getTicketStaffPanel().getReset_ricerca_butt().addActionListener(e -> {
			view.getTicketStaffPanel().resetCampoRicerca();
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
		String testo = view.getTicketStaffPanel().getTestoMessaggioDaInviare();
		Ticket t = view.getHomePanelStaff().getTicketSelezionato();
		if (t != null && !testo.isEmpty()) {
			if (model.creaMessaggio(testo, t.getId_ticket())) {
				view.getTicketStaffPanel().pulisciInput();
				view.getTicketStaffPanel().setConversazioneTicket(t.getCronologiaMessaggiFormattata());
			}
		}
	}

	
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