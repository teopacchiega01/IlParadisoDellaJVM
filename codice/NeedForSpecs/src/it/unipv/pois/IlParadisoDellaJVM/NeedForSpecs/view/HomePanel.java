package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomePanel extends JPanel {

	private JLabel label_utente_loggato;
	private JButton logout_butt;
	private JButton login_butt;
	private JButton registrati_utente_butt;
	private JButton registrati_staff_butt;
	
	private JLabel label_out;
	
	private JButton marketplace_butt;
	private JButton assistenza_butt;
	private JButton forum_butt;

	public HomePanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel pannelloNord = new JPanel(new BorderLayout());
		
		label_utente_loggato = new JLabel("👤 Utente: Ospite");
		label_utente_loggato.setFont(new Font("Arial", Font.ITALIC, 14));
		
		logout_butt = new JButton("Logout");
		login_butt = new JButton("Login");
		registrati_utente_butt = new JButton("Registrazione Utente");
		registrati_staff_butt = new JButton("Registrazione Staff");
		
		JPanel nordEst = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		nordEst.add(registrati_utente_butt);
		nordEst.add(registrati_staff_butt);
		nordEst.add(login_butt);
		nordEst.add(logout_butt);
		
		pannelloNord.add(label_utente_loggato, BorderLayout.WEST);
		pannelloNord.add(nordEst, BorderLayout.EAST);

		label_out = new JLabel("Benvenuto nel Paradiso della JVM!", SwingConstants.CENTER);
		label_out.setFont(new Font("Arial", Font.BOLD, 16));
		
		JPanel pannelloSud = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
		
		marketplace_butt = new JButton("MarketPlace");
		assistenza_butt = new JButton("Assistenza");
		forum_butt = new JButton("Forum");
		
		pannelloSud.add(marketplace_butt);
		pannelloSud.add(assistenza_butt);
		pannelloSud.add(forum_butt);

		this.add(pannelloNord, BorderLayout.NORTH);
		this.add(label_out, BorderLayout.CENTER);
		this.add(pannelloSud, BorderLayout.SOUTH);
	}

	public void setLabelOut(String messaggio) {
		this.label_out.setText(messaggio);
	}
	
	public void setLabelUtenteLoggato(String username) {
		this.label_utente_loggato.setText("👤 Utente: " + username);
	}
	
	public void setVisibilitaLogin(boolean visibile) {
		this.login_butt.setVisible(visibile);
	}

	public void setVisibilitaLogout(boolean visibile) {
		this.logout_butt.setVisible(visibile);
	}

	public void setVisibilitaRegistrati(boolean visibile) {
		this.registrati_utente_butt.setVisible(visibile);
		this.registrati_staff_butt.setVisible(visibile);
	}

	public JButton getLogout_butt() { return logout_butt; }
	public JButton getLogin_butt() { return login_butt; }
	public JButton getRegistrati_utente_butt() { return registrati_utente_butt; }
	public JButton getRegistrati_staff_butt() { return registrati_staff_butt; }
	public JButton getMarketplace_butt() { return marketplace_butt; }
	public JButton getAssistenza_butt() { return assistenza_butt; }
	public JButton getForum_butt() { return forum_butt; }
}
