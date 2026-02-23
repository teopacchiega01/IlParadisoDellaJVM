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

public class ApriCommentoPanel extends JPanel {

	private JTextArea txtCommentoPadre;
	private JTable tabellaRisposte;
	private JButton btnTornaIndietro;
	private JButton btnRispondi;
	private JButton btnElimina;
	private JButton btnApriRisposta; // Il nuovo bottone!

	public ApriCommentoPanel() {
		this.setLayout(new BorderLayout(20, 20));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// TOP: Bottone Indietro
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnTornaIndietro = new JButton("⬅ Torna Indietro");
		topPanel.add(btnTornaIndietro);
		this.add(topPanel, BorderLayout.NORTH);

		// CENTER: Testo del Commento Padre + Tabella Risposte
		JPanel centerPanel = new JPanel(new BorderLayout(0, 15));

		// 1. Il commento genitore
		txtCommentoPadre = new JTextArea(5, 20);
		txtCommentoPadre.setEditable(false);
		txtCommentoPadre.setLineWrap(true);
		txtCommentoPadre.setWrapStyleWord(true);
		txtCommentoPadre.setFont(new Font("Arial", Font.ITALIC, 16));
		txtCommentoPadre.setBorder(BorderFactory.createTitledBorder("Stai leggendo il commento di:"));
		centerPanel.add(new JScrollPane(txtCommentoPadre), BorderLayout.NORTH);

		// 2. La tabella delle risposte
		tabellaRisposte = new JTable();
		tabellaRisposte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabellaRisposte.setRowHeight(40); // Più altezza per leggere meglio!

		JScrollPane scrollRisposte = new JScrollPane(tabellaRisposte);
		scrollRisposte.setBorder(BorderFactory.createTitledBorder("Risposte a questo commento"));
		centerPanel.add(scrollRisposte, BorderLayout.CENTER);

		this.add(centerPanel, BorderLayout.CENTER);

		// BOTTOM: Bottoni Azione
		JPanel bottoniPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnRispondi = new JButton("Rispondi al commento in alto");
		btnApriRisposta = new JButton("Apri Risposta Selezionata");
		btnElimina = new JButton("Elimina Risposta Selezionata");

		bottoniPanel.add(btnElimina);
		bottoniPanel.add(btnApriRisposta);
		bottoniPanel.add(btnRispondi);

		this.add(bottoniPanel, BorderLayout.SOUTH);
	}

	public JTextArea getTxtCommentoPadre() { return txtCommentoPadre; }
	public JTable getTabellaRisposte() { return tabellaRisposte; }
	public JButton getBtnTornaIndietro() { return btnTornaIndietro; }
	public JButton getBtnRispondi() { return btnRispondi; }
	public JButton getBtnElimina() { return btnElimina; }
	public JButton getBtnApriRisposta() { return btnApriRisposta; }
}