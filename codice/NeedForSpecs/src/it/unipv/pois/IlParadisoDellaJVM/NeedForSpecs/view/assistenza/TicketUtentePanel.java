package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;


/*
 * @author: Persy
 */

public class TicketUtentePanel extends JPanel {
	
	private JLabel out_ticket_utente;
	private JTextArea conversazione_utente; 
	private JTextArea testo_messaggio_utente;
	private JButton invia_messaggio_utente;
	private JButton indietro_butt;

	public TicketUtentePanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		out_ticket_utente = new JLabel("Dettaglio Ticket");
		
		conversazione_utente = new JTextArea();
		conversazione_utente.setEditable(false);
		conversazione_utente.setLineWrap(true);
		conversazione_utente.setWrapStyleWord(true);
		
		testo_messaggio_utente = new JTextArea(3, 20);
		testo_messaggio_utente.setLineWrap(true);
		testo_messaggio_utente.setWrapStyleWord(true);
		
		invia_messaggio_utente = new JButton("Invia Messaggio");
		indietro_butt = new JButton("< Torna alla Home");
		
		JPanel nord = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nord.add(indietro_butt);
		nord.add(out_ticket_utente);
		
		JScrollPane scrollConversazione = new JScrollPane(conversazione_utente);
		
		JPanel sud = new JPanel(new BorderLayout());
		sud.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		JScrollPane scrollInput = new JScrollPane(testo_messaggio_utente);
		
		sud.add(scrollInput, BorderLayout.CENTER);
		sud.add(invia_messaggio_utente, BorderLayout.EAST);
		
		this.add(nord, BorderLayout.NORTH);
		this.add(scrollConversazione, BorderLayout.CENTER);
		this.add(sud, BorderLayout.SOUTH);
	}
	
	
	
	public boolean isPannelloVisibile() {
	    return this.isShowing();
	}
	public String getTestoUtente() {
		String testo = testo_messaggio_utente.getText();
	
		return testo.trim();
	}
	
	public void setConversazioneTicket(String cronologia) {
			this.conversazione_utente.setText(cronologia);
	}
		
		
		
	public void setTitoloTicket(String titolo) {
			this.out_ticket_utente.setText(titolo);
	}

	public void aggiungiMessaggio(String mittente, String testo) {
		conversazione_utente.append(mittente + ": " + testo + "\n\n");
	}
	
	public void pulisciInput() {
		testo_messaggio_utente.setText("");
	}
	
	public void pulisciChat() {
		conversazione_utente.setText("");
	}

	public void setLabelOutUtente(String text) {
		out_ticket_utente.setText(text);
	}
	
	public JLabel getOut_ticket_utente() {
		return out_ticket_utente;
	}

	public JTextArea getConversazione_utente() {
		return conversazione_utente;
	}

	public JTextArea getTesto_messaggio_utente() {
		return testo_messaggio_utente;
	}

	public JButton getInvia_messaggio_utente() {
		return invia_messaggio_utente;
	}

	public JButton getIndietro_butt() {
		return indietro_butt;
	}
}