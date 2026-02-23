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

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.StringChecker;

public class RegistrazioneUtentePanel extends JPanel {

	private JLabel titolo_label;
	private JLabel label_out;
	
	private JTextField nome_field;
	private JTextField cognome_field;
	private JTextField username_field;
	private JTextField email_field;
	private JPasswordField password_field;
	
	private JTextField via_field;
	private JTextField civico_field;
	private JTextField citta_field;
	private JTextField provincia_field;
	private JTextField cap_field;
	
	private JTextField titolare_carta_field;
	private JTextField scadenza_carta_field;
	private JTextField cvv_carta_field;
	
	private JButton registrati_butt;
	private JButton indietro_butt;

	public RegistrazioneUtentePanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

		JPanel nord = new JPanel(new GridLayout(2, 1, 0, 5));
		titolo_label = new JLabel("Registrazione Utente", SwingConstants.CENTER);
		label_out = new JLabel(" ", SwingConstants.CENTER);
		nord.add(titolo_label);
		nord.add(label_out);

		JPanel centro = new JPanel(new GridLayout(15, 2, 10, 5));
		centro.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		
		nome_field = new JTextField();
		cognome_field = new JTextField();
		username_field = new JTextField();
		email_field = new JTextField();
		password_field = new JPasswordField();
		
		via_field = new JTextField();
		civico_field = new JTextField();
		citta_field = new JTextField();
		provincia_field = new JTextField();
		cap_field = new JTextField();
		
		titolare_carta_field = new JTextField();
		scadenza_carta_field = new JTextField();
		cvv_carta_field = new JTextField();

		centro.add(new JLabel("Nome:")); centro.add(nome_field);
		centro.add(new JLabel("Cognome:")); centro.add(cognome_field);
		centro.add(new JLabel("Username:")); centro.add(username_field);
		centro.add(new JLabel("Email:")); centro.add(email_field);
		centro.add(new JLabel("Password:")); centro.add(password_field);
		
		centro.add(new JLabel("--- INDIRIZZO DI SPEDIZIONE ---")); centro.add(new JLabel(""));
		centro.add(new JLabel("Via:")); centro.add(via_field);
		centro.add(new JLabel("Civico:")); centro.add(civico_field);
		centro.add(new JLabel("Città:")); centro.add(citta_field);
		centro.add(new JLabel("Provincia:")); centro.add(provincia_field);
		centro.add(new JLabel("CAP:")); centro.add(cap_field);
		
		centro.add(new JLabel("--- METODO DI PAGAMENTO ---")); centro.add(new JLabel(""));
		centro.add(new JLabel("Titolare Carta:")); centro.add(titolare_carta_field);
		centro.add(new JLabel("Scadenza (MM/AA):")); centro.add(scadenza_carta_field);
		centro.add(new JLabel("CVV:")); centro.add(cvv_carta_field);

		JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		registrati_butt = new JButton("Registrati");
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
		nome_field.setText(""); cognome_field.setText("");
		username_field.setText(""); email_field.setText("");
		password_field.setText(""); via_field.setText("");
		civico_field.setText(""); citta_field.setText("");
		provincia_field.setText(""); cap_field.setText("");
		titolare_carta_field.setText(""); scadenza_carta_field.setText("");
		cvv_carta_field.setText("");
	}
	
	public boolean validaCampi() {
		if (getNome().isEmpty() || getCognome().isEmpty() || getUsername().isEmpty() || 
			getEmail().isEmpty() || getPassword().isEmpty() || getVia().isEmpty() || 
			getCivico().isEmpty() || getCitta().isEmpty() || getCap().isEmpty() || 
			getProvincia().isEmpty() || getTitolareCarta().isEmpty() || 
			getCvvCarta().isEmpty() || getScadenzaCarta() == null) {
			
			setLabelOut("Errore: Compila tutti i campi correttamente!");
			return false;
		}
		return true;
	}
	public String getNome() { return StringChecker.pulisciInput(nome_field.getText()); }
	public String getCognome() { return StringChecker.pulisciInput(cognome_field.getText()); }
	public String getUsername() { return StringChecker.pulisciInput(username_field.getText()); }
	public String getEmail() { return StringChecker.pulisciInput(email_field.getText()); }
	public String getPassword() { return StringChecker.pulisciInput(new String(password_field.getPassword())); }
	
	public String getVia() { return StringChecker.pulisciInput(via_field.getText()); }
	public String getCivico() { return StringChecker.pulisciInput(civico_field.getText()); }
	public String getCitta() { return StringChecker.pulisciInput(citta_field.getText()); }
	public String getProvincia() { return StringChecker.pulisciInput(provincia_field.getText()); }
	public String getCap() { return StringChecker.pulisciInput(cap_field.getText()); }
	
	public String getTitolareCarta() { return StringChecker.pulisciInput(titolare_carta_field.getText()); }
	public java.time.LocalDate getScadenzaCarta() { return StringChecker.convertiScadenzaCarta(scadenza_carta_field.getText()); }
	public String getCvvCarta() { return StringChecker.pulisciInput(cvv_carta_field.getText()); }

	public JButton getRegistrati_butt() { return registrati_butt; }
	public JButton getIndietro_butt() { return indietro_butt; }

}