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
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.StringChecker;

/**
 * @author Persy
 */
public class TicketStaffPanel extends JPanel {
	
	private JLabel out_ticket_staff;
	private JTextArea conversazione_staff; 
	private JTextArea testo_messaggio_staff;
	private JButton invia_messaggio_staff;
	private JButton indietro_butt;
	
	
	private JComboBox<Ricerca> combo_ricerca;
	private JTextField testo_ricerca;
	private JButton cerca_butt;
	private JButton reset_ricerca_butt;

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
		
	
		combo_ricerca = new JComboBox<>(Ricerca.values());
		testo_ricerca = new JTextField(15);
		cerca_butt = new JButton("Cerca");
		reset_ricerca_butt = new JButton("Reset");
		
		
		JPanel nord = new JPanel(new BorderLayout());
		
		JPanel nordOvest = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nordOvest.add(indietro_butt);
		nordOvest.add(out_ticket_staff);
		
		JPanel nordEst = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		nordEst.add(new JLabel("Cerca per:"));
		nordEst.add(combo_ricerca);
		nordEst.add(testo_ricerca);
		nordEst.add(cerca_butt);
		nordEst.add(reset_ricerca_butt);
		
		nord.add(nordOvest, BorderLayout.WEST);
		nord.add(nordEst, BorderLayout.EAST);
		
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

	
	public String getTestoMessaggioDaInviare() {
		return StringChecker.pulisciInput(testo_messaggio_staff.getText());
	}
	
	public Ricerca getTipoRicercaSelezionata() {
		return (Ricerca) combo_ricerca.getSelectedItem();
	}
	
	public String getParametroRicerca() {
		return StringChecker.pulisciInput(testo_ricerca.getText());
	}
	
	public void resetCampoRicerca() {
		testo_ricerca.setText("");
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


	public JButton getInvia_messaggio_staff() { return invia_messaggio_staff; }
	public JButton getIndietro_butt() { return indietro_butt; }
	public JButton getCerca_butt() { return cerca_butt; }
	public JButton getReset_ricerca_butt() { return reset_ricerca_butt; }
}