package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ForumPanel extends JPanel {

	private JButton tornaAllaHome, creaPost, apriPost, ordina, eliminaPost, cercaPost;
	private JTable tabellaPost;  
	private JTextField titoloPost;
	
	private JComboBox<String> comboOrdinamento; 

	public ForumPanel() {
		this.setLayout(new BorderLayout()); 

		// TOP: Navigazione e Ordinamento
		JPanel pannelloTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tornaAllaHome = new JButton("Torna alla Home");
		creaPost = new JButton("Scrivi un Post");
		
		String[] opzioni = {"Più Recenti", "Alfabetico (Titolo)", "Autore"};
		comboOrdinamento = new JComboBox<>(opzioni);
		
		ordina = new JButton("Ordina");
		titoloPost = new JTextField(15);
		cercaPost = new JButton("Cerca");

		pannelloTop.add(tornaAllaHome);
		pannelloTop.add(creaPost);
		pannelloTop.add(comboOrdinamento);
		pannelloTop.add(ordina);
		pannelloTop.add(titoloPost);
		pannelloTop.add(cercaPost);
		this.add(pannelloTop, BorderLayout.NORTH);

		// CENTER: Tabella
		tabellaPost = new JTable();
		tabellaPost.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(new JScrollPane(tabellaPost), BorderLayout.CENTER);

		// BOTTOM: Azioni
		JPanel pannelloBot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		apriPost = new JButton("Espandi Post");
		eliminaPost = new JButton("Elimina Post");
		eliminaPost.setVisible(false); 

		pannelloBot.add(apriPost);
		pannelloBot.add(eliminaPost);
		this.add(pannelloBot, BorderLayout.SOUTH);
	}

	public JButton getTornaAllaHome() { 
		return tornaAllaHome; 
	}
	
	public JButton getCreaPost() { 
		return creaPost; 
	}
	
	public JButton getApriPost() { 
		return apriPost; 
	}
	
	public JTable getTabellaPost() {
		return tabellaPost;
	}
	
	public JTextField getTitoloPost() {
		return titoloPost;
	}
	
	public JButton getCercaPost() {
		return cercaPost;
	}
	
	// IMPORTANTE: Ora il getter restituisce una JComboBox<String>
	public JComboBox<String> getComboOrdinamento() {
		return comboOrdinamento;
	}
	
	public JButton getOrdina() { 
		return ordina; 
	}
	
	public JButton getEliminaPost() { 
		return eliminaPost; 
	}
}