package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utili.StringChecker;

public class RegistrazioneStaffPanel extends JPanel {

	private JLabel titolo_label;
	private JLabel label_out;
	
	private JTextField nome_field;
	private JTextField cognome_field;
	private JTextField username_field;
	private JTextField email_field;
	private JPasswordField password_field;
	
	private JButton registrati_butt;
	private JButton indietro_butt;

	public RegistrazioneStaffPanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

		JPanel nord = new JPanel(new GridLayout(2, 1, 0, 10));
		titolo_label = new JLabel("Registrazione Nuovo Staff", SwingConstants.CENTER);
		label_out = new JLabel(" ", SwingConstants.CENTER);
		nord.add(titolo_label);
		nord.add(label_out);

		JPanel centro = new JPanel(new GridLayout(5, 2, 10, 10));
		centro.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		nome_field = new JTextField();
		cognome_field = new JTextField();
		username_field = new JTextField();
		email_field = new JTextField();
		password_field = new JPasswordField();

		centro.add(new JLabel("Nome:"));
		centro.add(nome_field);
		centro.add(new JLabel("Cognome:"));
		centro.add(cognome_field);
		centro.add(new JLabel("Username:"));
		centro.add(username_field);
		centro.add(new JLabel("Email:"));
		centro.add(email_field);
		centro.add(new JLabel("Password:"));
		centro.add(password_field);

		JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		registrati_butt = new JButton("Registra Staff");
		indietro_butt = new JButton("< Torna al Login");
		
		sud.add(indietro_butt);
		sud.add(registrati_butt);

		this.add(nord, BorderLayout.NORTH);
		this.add(centro, BorderLayout.CENTER);
		this.add(sud, BorderLayout.SOUTH);
	}

	public void setLabelOut(String text) {
		label_out.setText(text);
	}

	public void pulisciCampi() {
		nome_field.setText("");
		cognome_field.setText("");
		username_field.setText("");
		email_field.setText("");
		password_field.setText("");
	}
	public boolean validaCampi() {
		if (getNome().isEmpty() || getCognome().isEmpty() || getUsername().isEmpty() || 
			getEmail().isEmpty() || getPassword().isEmpty()) {
			
			setLabelOut("Errore: Compila tutti i campi!");
			return false;
		}
		return true;
	}

	public String getNome() { return StringChecker.pulisciInput(nome_field.getText()); }
	public String getCognome() { return StringChecker.pulisciInput(cognome_field.getText()); }
	public String getUsername() { return StringChecker.pulisciInput(username_field.getText()); }
	public String getEmail() { return StringChecker.pulisciInput(email_field.getText()); }
	public String getPassword() { return StringChecker.pulisciInput(new String(password_field.getPassword())); }

	public JButton getRegistrati_butt() { return registrati_butt; }
	public JButton getIndietro_butt() { return indietro_butt; }
}