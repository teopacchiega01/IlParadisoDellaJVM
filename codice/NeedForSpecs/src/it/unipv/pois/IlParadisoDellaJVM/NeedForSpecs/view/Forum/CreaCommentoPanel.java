package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CreaCommentoPanel extends JPanel {

	private JTextArea txtTesto;
	private JButton btnPubblica;
	private JButton btnAnnulla;

	public CreaCommentoPanel() {
		
		this.setLayout(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		
		JLabel lblTitolo = new JLabel("Scrivi il tuo commento");
		lblTitolo.setFont(new Font("Arial", Font.BOLD, 20));
		this.add(lblTitolo, BorderLayout.NORTH);

		txtTesto = new JTextArea();
		txtTesto.setLineWrap(true); // Va a capo in automatico...
		txtTesto.setWrapStyleWord(true); // ...senza spezzare le parole a metà!
		txtTesto.setFont(new Font("Arial", Font.PLAIN, 14));

		JScrollPane scrollTesto = new JScrollPane(txtTesto);
		this.add(scrollTesto, BorderLayout.CENTER);

	
		JPanel bottoniPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Allineati a destra

		btnAnnulla = new JButton("Annulla");
		btnPubblica = new JButton("Pubblica Commento");

		bottoniPanel.add(btnAnnulla);
		bottoniPanel.add(btnPubblica);

		this.add(bottoniPanel, BorderLayout.SOUTH);
	}

	public JTextArea getTxtTesto() { 
		return txtTesto; 
	}

	public JButton getBtnPubblica() { 
		return btnPubblica; 
	}

	public JButton getBtnAnnulla() { 
		return btnAnnulla; 
	}
}
