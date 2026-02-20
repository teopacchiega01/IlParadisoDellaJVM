package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameAssistenza extends JFrame {

	private CardLayout cardLayout;
	private JPanel contenitorePrincipale;
	
	private HomePanelStaff homePanelStaff;
	private TicketStaffPanel ticketStaffPanel;
	
	private HomePanelUtente homePanelUtente;
	private TicketUtentePanel ticketUtentePanel;

	public FrameAssistenza() {
		super("Il Paradiso della JVM - Assistenza");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		cardLayout = new CardLayout();
		contenitorePrincipale = new JPanel(cardLayout);
		
		homePanelStaff = new HomePanelStaff();
		ticketStaffPanel = new TicketStaffPanel();
		homePanelUtente = new HomePanelUtente();
		ticketUtentePanel = new TicketUtentePanel();
		
		contenitorePrincipale.add(homePanelStaff, "HOME_STAFF");
		contenitorePrincipale.add(ticketStaffPanel, "CHAT_STAFF");
		contenitorePrincipale.add(homePanelUtente, "HOME_UTENTE");
		contenitorePrincipale.add(ticketUtentePanel, "CHAT_UTENTE");
		
		this.add(contenitorePrincipale);
	}

	public void mostraHomeStaff() {
		cardLayout.show(contenitorePrincipale, "HOME_STAFF");
	}
	
	public void mostraChatStaff() {
		cardLayout.show(contenitorePrincipale, "CHAT_STAFF");
	}
	
	public void mostraHomeUtente() {
		cardLayout.show(contenitorePrincipale, "HOME_UTENTE");
	}
	
	public void mostraChatUtente() {
		cardLayout.show(contenitorePrincipale, "CHAT_UTENTE");
	}

	public HomePanelStaff getHomePanelStaff() {
		return homePanelStaff;
	}

	public TicketStaffPanel getTicketStaffPanel() {
		return ticketStaffPanel;
	}

	public HomePanelUtente getHomePanelUtente() {
		return homePanelUtente;
	}

	public TicketUtentePanel getTicketUtentePanel() {
		return ticketUtentePanel;
	}
}