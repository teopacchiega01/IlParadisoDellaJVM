package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view;

import javax.swing.JFrame;
<<<<<<< HEAD
import javax.swing.JPanel;
import java.awt.CardLayout;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.FrameMarketplace;


public class HomeFrame extends JFrame {

	public final static String HOME_PANEL = "HOME";
	public final static String REG_UTENTE_PANEL = "REG_UTENTE";
	public final static String REG_STAFF_PANEL = "REG_STAFF";

	private CardLayout cardLayout;
	private JPanel mainPanel;

	private HomePanel homePanel;
	private RegistrazioneUtentePanel regUtentePanel;
	private RegistrazioneStaffPanel regStaffPanel;
=======

public class HomeFrame extends JFrame{
>>>>>>> branch 'main' of https://github.com/teopacchiega01/IlParadisoDellaJVM.git

	public HomeFrame() {
		super("Il Paradiso della JVM - Home Principale");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		homePanel = new HomePanel();
		regUtentePanel = new RegistrazioneUtentePanel();
		regStaffPanel = new RegistrazioneStaffPanel();
		
		mainPanel.add(homePanel, HOME_PANEL);
		mainPanel.add(regUtentePanel, REG_UTENTE_PANEL);
		mainPanel.add(regStaffPanel, REG_STAFF_PANEL);
		
		this.add(mainPanel);
	}
	
	public void mostraPannello(String nomePannello) {
		cardLayout.show(mainPanel, nomePannello);
	}

	public HomePanel getHomePanel() {
		return homePanel;
	}

	public RegistrazioneUtentePanel getRegUtentePanel() {
		return regUtentePanel;
	}

	public RegistrazioneStaffPanel getRegStaffPanel() {
		return regStaffPanel;
	}

	public FrameAssistenza creaFrameAssistenza() {
		return new FrameAssistenza();
	}

	public FrameMarketplace creaFrameMarketplace() {
		return new FrameMarketplace();
	}
	public void mostraHome() {
		cardLayout.show(mainPanel, HOME_PANEL);
	}
	
	public void mostraRegistrazioneUtente() {
		cardLayout.show(mainPanel, REG_UTENTE_PANEL);
	}
	
	public void mostraRegistrazioneStaff() {
		cardLayout.show(mainPanel, REG_STAFF_PANEL);
	}

	// TODO: togliere il commento
	/*
	public FrameForum creaFrameForum() {
		return new FrameForum();
	}
	*/
}