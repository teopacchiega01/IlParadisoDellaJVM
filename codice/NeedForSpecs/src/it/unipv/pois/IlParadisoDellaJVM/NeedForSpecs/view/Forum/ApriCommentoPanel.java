package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

/**
 *
 * * @author teomiraldi
 * 
 */

public class ApriCommentoPanel extends JPanel {

	private JTextArea txtCommentoPadre;
	private JTable tabellaRisposte;
	private JButton btnTornaIndietro;
	private JButton btnRispondi;
	private JButton btnElimina;
	private JButton btnApriRisposta;  

	public ApriCommentoPanel() {
		this.setLayout(new BorderLayout(20, 20));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnTornaIndietro = new JButton("⬅ Torna Indietro");
		topPanel.add(btnTornaIndietro);
		this.add(topPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new BorderLayout(0, 15));

		txtCommentoPadre = new JTextArea(5, 20);
		txtCommentoPadre.setEditable(false);
		txtCommentoPadre.setLineWrap(true);
		txtCommentoPadre.setWrapStyleWord(true);
		txtCommentoPadre.setFont(new Font("Arial", Font.ITALIC, 16));
		txtCommentoPadre.setBorder(BorderFactory.createTitledBorder("Stai leggendo il commento di:"));
		centerPanel.add(new JScrollPane(txtCommentoPadre), BorderLayout.NORTH);

		tabellaRisposte = new JTable();
		tabellaRisposte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabellaRisposte.setRowHeight(40);

		JScrollPane scrollRisposte = new JScrollPane(tabellaRisposte);
		scrollRisposte.setBorder(BorderFactory.createTitledBorder("Risposte a questo commento"));
		centerPanel.add(scrollRisposte, BorderLayout.CENTER);

		this.add(centerPanel, BorderLayout.CENTER);

		JPanel bottoniPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnRispondi = new JButton("Rispondi al commento in alto");
		btnApriRisposta = new JButton("Apri Risposta Selezionata");
		btnElimina = new JButton("Elimina Risposta Selezionata");

		bottoniPanel.add(btnElimina);
		bottoniPanel.add(btnApriRisposta);
		bottoniPanel.add(btnRispondi);

		this.add(bottoniPanel, BorderLayout.SOUTH);
	}

	public JTextArea getTxtCommentoPadre() { 

		return txtCommentoPadre; 

	}


	public JTable getTabellaRisposte() { 

		return tabellaRisposte; 

	}

	public JButton getBtnTornaIndietro() { 

		return btnTornaIndietro; 

	}

	public JButton getBtnRispondi() {

		return btnRispondi; 

	}

	public JButton getBtnElimina() { 

		return btnElimina; 

	}

	public JButton getBtnApriRisposta() { 

		return btnApriRisposta;

	}



}