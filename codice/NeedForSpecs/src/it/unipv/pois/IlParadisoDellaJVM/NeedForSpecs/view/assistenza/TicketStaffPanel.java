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

public class TicketStaffPanel extends JPanel {
	
	private JLabel out_ticket_staff;
	private JTextArea conversazione_staff; 
	private JTextArea testo_messaggio_staff;
	private JButton invia_messaggio_staff;
	private JButton indietro_butt;

	public TicketStaffPanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		out_ticket_staff = new JLabel("Stato Chat: Attiva");
		
		conversazione_staff = new JTextArea();
		conversazione_staff.setEditable(false);
		conversazione_staff.setLineWrap(true);
		conversazione_staff.setWrapStyleWord(true);
		
		testo_messaggio_staff = new JTextArea(3, 20);
		testo_messaggio_staff.setLineWrap(true);
		testo_messaggio_staff.setWrapStyleWord(true);
		
		invia_messaggio_staff = new JButton("Invia Messaggio");
		indietro_butt = new JButton("< Torna alla Home");
		
		JPanel nord = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nord.add(indietro_butt);
		nord.add(out_ticket_staff);
		
		JScrollPane scrollConversazione = new JScrollPane(conversazione_staff);
		
		JPanel sud = new JPanel(new BorderLayout());
		sud.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		JScrollPane scrollInput = new JScrollPane(testo_messaggio_staff);
		
		sud.add(scrollInput, BorderLayout.CENTER);
		sud.add(invia_messaggio_staff, BorderLayout.EAST);
		
		this.add(nord, BorderLayout.NORTH);
		this.add(scrollConversazione, BorderLayout.CENTER);
		this.add(sud, BorderLayout.SOUTH);
	}

	public boolean isPannelloVisibile() {
	    return this.isShowing();
	}
	public void setConversazioneTicket(String cronologia) {
		this.conversazione_staff.setText(cronologia); 
	}
	
	public void setTitoloTicket(String titolo) {
		this.out_ticket_staff.setText(titolo);
	}
	public void aggiungiMessaggio(String mittente, String testo) {
		conversazione_staff.append(mittente + ": " + testo + "\n\n");
	}
	
	
	public void pulisciInput() {
		testo_messaggio_staff.setText("");
	}
	
	public void pulisciChat() {
		conversazione_staff.setText("");
	}
	public void setLabeOutTicketStaff(String text) {
		
		out_ticket_staff.setText(text);
	}

	public JLabel getOut_ticket_staff() {
		return out_ticket_staff;
	}

	public JTextArea getConversazione_staff() {
		return conversazione_staff;
	}

	public JTextArea getTesto_messaggio_staff() {
		return testo_messaggio_staff;
	}

	public JButton getInvia_messaggio_staff() {
		return invia_messaggio_staff;
	}

	public JButton getIndietro_butt() {
		return indietro_butt;
	}
}

