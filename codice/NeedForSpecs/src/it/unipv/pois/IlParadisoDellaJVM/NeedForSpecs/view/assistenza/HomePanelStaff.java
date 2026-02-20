package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Stato;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;
/*
 * @author: Persy
 */


public class HomePanelStaff extends JPanel {
	private JLabel label_benvenuto; 
	private JLabel risultati_home_staff;
	private JComboBox<Ticket> ticket_combo;
	private JComboBox<Stato> stato_combo; 
	private JButton	cambia_stato_butt;
	private JButton chiudi_ticket_butt;
	private JButton vai_a_ticket_butt;

	
	public HomePanelStaff() {
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		label_benvenuto = new JLabel("Benvenuto, seleziona l'operazione che vuoi eseguire tramite gli strumenti dell'interfaccia");
		risultati_home_staff = new JLabel(" ");
		ticket_combo = new JComboBox<Ticket>();
		stato_combo = new JComboBox<Stato>(Stato.values());
		cambia_stato_butt = new JButton("Cambia stato ticket selezionato");
		chiudi_ticket_butt = new JButton("Chiudi ticket selezionato");
		vai_a_ticket_butt = new JButton("Vai a ticket selezionato");
		
		
		
		JPanel nord  = new JPanel();
		JPanel centro = new JPanel();
		JPanel sud = new JPanel();
		
		nord.add(label_benvenuto);
		nord.add(risultati_home_staff);
		centro.add(new JLabel("Seleziona Ticket:"));
		centro.add(ticket_combo);
		centro.add(vai_a_ticket_butt);
		sud.add(new JLabel("Nuovo Stato:"));
		sud.add(stato_combo);
		sud.add(cambia_stato_butt);
		sud.add(chiudi_ticket_butt);
		
		
		add(nord, BorderLayout.NORTH);
		add(centro, BorderLayout.CENTER);
		add(sud, BorderLayout.SOUTH);
		
	}

	
	public void aggiornaGrafica() {
	    this.repaint();
	}
	
	public void setLabelOutStaff(String text) {
		risultati_home_staff.setText(text);
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
	public Stato getStatoSelezionato() {
		return (Stato)stato_combo.getSelectedItem();
	}

	public JLabel getRisultati_home_staff() {
		return risultati_home_staff;
	}





	public void setRisultati_home_staff(JLabel risultati_home_staff) {
		this.risultati_home_staff = risultati_home_staff;
	}





	public JLabel getLabel_benvenuto() {
		return label_benvenuto;
	}


	public void setLabel_benvenuto(JLabel label_benvenuto) {
		this.label_benvenuto = label_benvenuto;
	}


	public JComboBox<Ticket> getTicket_combo() {
		return ticket_combo;
	}


	public void setTicket_combo(JComboBox<Ticket> ticket_combo) {
		this.ticket_combo = ticket_combo;
	}


	public JComboBox<Stato> getStato_combo() {
		return stato_combo;
	}


	public void setStato_combo(JComboBox<Stato> stato_combo) {
		this.stato_combo = stato_combo;
	}


	public JButton getCambia_stato_butt() {
		return cambia_stato_butt;
	}


	public void setCambia_stato_butt(JButton cambia_stato_butt) {
		this.cambia_stato_butt = cambia_stato_butt;
	}


	public JButton getChiudi_ticket_butt() {
		return chiudi_ticket_butt;
	}


	public void setChiudi_ticket_butt(JButton chiudi_ticket_butt) {
		this.chiudi_ticket_butt = chiudi_ticket_butt;
	}


	public JButton getVai_a_ticket_butt() {
		return vai_a_ticket_butt;
	}


	public void setVai_a_ticket_butt(JButton vai_a_ticket_butt) {
		this.vai_a_ticket_butt = vai_a_ticket_butt;
	}
	
	
	
	
	
	

}
