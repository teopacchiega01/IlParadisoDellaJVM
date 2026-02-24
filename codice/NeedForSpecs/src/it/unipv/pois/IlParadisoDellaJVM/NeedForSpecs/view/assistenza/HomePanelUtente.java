package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;

/**
 * @author persy
 */

public class HomePanelUtente extends JPanel {
	private JLabel label_benvenuto; 
	private JLabel risultati_home_utente;
	private JComboBox<Ticket> ticket_combo;
	private JButton crea_ticket_butt;
	private JButton vai_a_ticket_butt;
	private JButton logout_butt;
	
	public HomePanelUtente() {
		this.setLayout(new BorderLayout());
		label_benvenuto = new JLabel("Benvenuto, seleziona l'operazione che vuoi eseguire tramite gli strumenti dell'interfaccia");
		risultati_home_utente = new JLabel(" ");
		ticket_combo = new JComboBox<Ticket>();
		crea_ticket_butt = new JButton("Crea ticket");
		vai_a_ticket_butt = new JButton("Vai a ticket selezionato");
		logout_butt = new JButton("Logout");
		
		JPanel nord  = new JPanel(new BorderLayout());
		JPanel nordLabels = new JPanel(new java.awt.GridLayout(2, 1)); 
		nordLabels.add(label_benvenuto);
		nordLabels.add(risultati_home_utente);
		nord.add(logout_butt, BorderLayout.WEST);
		nord.add(nordLabels, BorderLayout.CENTER);
		
		JPanel centro = new JPanel();
		JPanel sud = new JPanel();
		
		centro.add(new JLabel("Seleziona Ticket:"));
		centro.add(ticket_combo);
		centro.add(vai_a_ticket_butt);
		
		sud.add(crea_ticket_butt);
		
		add(nord, BorderLayout.NORTH);
		add(centro, BorderLayout.CENTER);
		add(sud, BorderLayout.SOUTH);
	}

	public void setLabelOutUtente(String text) {
		risultati_home_utente.setText(text);
	}
	
	public void inizializzaTicketCombo(ArrayList<Ticket> tickets) {
		ticket_combo.removeAllItems();
		for(Ticket t : tickets) {
			ticket_combo.addItem(t);
		}
	}

	public Ticket getTicketSelezionato() {
		return (Ticket)ticket_combo.getSelectedItem();
	}
	
	public JLabel getLabel_benvenuto() {
		return label_benvenuto;
	}

	public void setLabel_benvenuto(JLabel label_benvenuto) {
		this.label_benvenuto = label_benvenuto;
	}

	public JLabel getRisultati_home_utentef() {
		return risultati_home_utente;
	}

	public void setRisultati_home_utente(JLabel risultati_home_staff) {
		this.risultati_home_utente = risultati_home_staff;
	}

	public JComboBox<Ticket> getTicket_combo() {
		return ticket_combo;
	}

	public void setTicket_combo(JComboBox<Ticket> ticket_combo) {
		this.ticket_combo = ticket_combo;
	}

	public JButton getCrea_ticket_butt() {
		return crea_ticket_butt;
	}

	public void setCrea_ticket_butt(JButton crea_ticket_butt) {
		this.crea_ticket_butt = crea_ticket_butt;
	}

	public JButton getVai_a_ticket_butt() {
		return vai_a_ticket_butt;
	}

	public void setVai_a_ticket_butt(JButton vai_a_ticket_butt) {
		this.vai_a_ticket_butt = vai_a_ticket_butt;
	}

	public JButton getLogout_butt() {
		return logout_butt;
	}

	public void setLogout_butt(JButton logout_butt) {
		this.logout_butt = logout_butt;
	}
}