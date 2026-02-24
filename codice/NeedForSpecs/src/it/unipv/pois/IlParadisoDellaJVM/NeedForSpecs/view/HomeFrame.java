package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view;

import javax.swing.JFrame;

import javax.swing.JPanel;
import java.awt.CardLayout;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.ForumView;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.FrameMarketplace;

/**
 * @author Persy
 */
public class HomeFrame extends JFrame {

	public final static String HOME_PANEL = "HOME";
	public final static String REG_UTENTE_PANEL = "REG_UTENTE";
	public final static String REG_STAFF_PANEL = "REG_STAFF";
	public final static String LOGIN_PANEL = "LOGIN";
	public final static String MODIFICA_PAGAMENTO_PANEL = "MODIFICA_PAGAMENTO"; 

	private CardLayout cardLayout;
	private JPanel mainPanel;

	private HomePanel homePanel;
	private RegistrazioneUtentePanel regUtentePanel;
	private RegistrazioneStaffPanel regStaffPanel;
	private LoginPanel loginPanel; 
	private ModificaPagamentoPanel modificaPagamentoPanel;
	
	public HomeFrame() {
		super("NeedForSpecs - Home");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		homePanel = new HomePanel();
		regUtentePanel = new RegistrazioneUtentePanel();
		regStaffPanel = new RegistrazioneStaffPanel();
		loginPanel = new LoginPanel();
		modificaPagamentoPanel = new ModificaPagamentoPanel(); 
		
		mainPanel.add(homePanel, HOME_PANEL);
		mainPanel.add(regUtentePanel, REG_UTENTE_PANEL);
		mainPanel.add(regStaffPanel, REG_STAFF_PANEL);
		mainPanel.add(loginPanel, LOGIN_PANEL);
		mainPanel.add(modificaPagamentoPanel, MODIFICA_PAGAMENTO_PANEL); 
		
		this.add(mainPanel);
	}
	
	public void mostraPannello(String nomePannello) {
		cardLayout.show(mainPanel, nomePannello);
	}

	public HomePanel getHomePanel() { return homePanel; }
	public RegistrazioneUtentePanel getRegUtentePanel() { return regUtentePanel; }
	public RegistrazioneStaffPanel getRegStaffPanel() { return regStaffPanel; }
	public LoginPanel getLoginPanel() { return loginPanel; }
	public ModificaPagamentoPanel getModificaPagamentoPanel() { return modificaPagamentoPanel; }

	public void setLoginPanel(LoginPanel loginPanel) { this.loginPanel = loginPanel; }

	public FrameAssistenza creaFrameAssistenza() { return new FrameAssistenza(); }
	public FrameMarketplace creaFrameMarketplace() { return new FrameMarketplace(); }
	public ForumView creaForumFrame() { return new ForumView(); }
	
	
	public void mostraHome() { cardLayout.show(mainPanel, HOME_PANEL); }
	public void mostraRegistrazioneUtente() { cardLayout.show(mainPanel, REG_UTENTE_PANEL); }
	public void mostraRegistrazioneStaff() { cardLayout.show(mainPanel, REG_STAFF_PANEL); }
	public void mostraLogin() { cardLayout.show(mainPanel, LOGIN_PANEL); }
	public void mostraModificaPagamento() { cardLayout.show(mainPanel, MODIFICA_PAGAMENTO_PANEL); } 
}