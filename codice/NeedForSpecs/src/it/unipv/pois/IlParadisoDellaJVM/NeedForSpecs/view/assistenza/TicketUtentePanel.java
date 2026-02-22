package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy.Ricerca;

/*
 * @author: Persy
 */

public class TicketUtentePanel extends JPanel {
	
	private JLabel out_ticket_utente;
	private JTextArea conversazione_utente; 
	private JTextArea testo_messaggio_utente;
	private JButton invia_messaggio_utente;
	private JButton indietro_butt;
	
	// Nuovi attributi per la ricerca
	private JComboBox<Ricerca> combo_ricerca;
	private JTextField testo_ricerca;
	private JButton cerca_butt;
	private JButton reset_ricerca_butt;

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
		
		// Inizializzazione componenti ricerca
		combo_ricerca = new JComboBox<>(Ricerca.values());
		testo_ricerca = new JTextField(15);
		cerca_butt = new JButton("Cerca");
		reset_ricerca_butt = new JButton("Reset");
		
		// PANNELLO NORD: diviso tra comandi base (sinistra) e ricerca (destra)
		JPanel nord = new JPanel(new BorderLayout());
		
		JPanel nordOvest = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nordOvest.add(indietro_butt);
		nordOvest.add(out_ticket_utente);
		
		JPanel nordEst = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		nordEst.add(new JLabel("Cerca per:"));
		nordEst.add(combo_ricerca);
		nordEst.add(testo_ricerca);
		nordEst.add(cerca_butt);
		nordEst.add(reset_ricerca_butt);
		
		nord.add(nordOvest, BorderLayout.WEST);
		nord.add(nordEst, BorderLayout.EAST);
		
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
	
	// Nota: qui restituiamo la stringa grezza per poi pulirla nel Controller con StringChecker
	public String getTestoUtente() {
		return testo_messaggio_utente.getText(); 
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
	
	// GETTER
	public JLabel getOut_ticket_utente() { return out_ticket_utente; }
	public JTextArea getConversazione_utente() { return conversazione_utente; }
	public JTextArea getTesto_messaggio_utente() { return testo_messaggio_utente; }
	public JButton getInvia_messaggio_utente() { return invia_messaggio_utente; }
	public JButton getIndietro_butt() { return indietro_butt; }
	
	// GETTER PER LA RICERCA
	public JComboBox<Ricerca> getCombo_ricerca() { return combo_ricerca; }
	public JTextField getTesto_ricerca() { return testo_ricerca; }
	public JButton getCerca_butt() { return cerca_butt; }
	public JButton getReset_ricerca_butt() { return reset_ricerca_butt; }
}