package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreaPostPanel extends JPanel {
	
	private JLabel lTitolo;
	private JLabel lSottotitolo;
	private JLabel lTesto;
	
	private JTextField txtTitolo;
	private JTextField txtSottotitolo;
	private JTextArea txtTesto;
	
	private JButton crea;
	private JButton annulla;
	
	
	public CreaPostPanel() {
		
		this.setLayout(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		lTitolo = new JLabel("Inserisci titolo");
		lSottotitolo = new JLabel("Inserisci sottotitolo");
		lTesto = new JLabel("Inserisci testo");
		
		txtTitolo = new JTextField(20);
		txtSottotitolo = new JTextField(20);
		
		txtTesto = new JTextArea(10, 20);
		txtTesto.setLineWrap(true);
		txtTesto.setWrapStyleWord(true);
		JScrollPane scrollTesto = new JScrollPane(txtTesto);
		
		crea = new JButton("Crea post");
		annulla = new JButton("Annulla");
		
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		//Riga 1: titolo
		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(lTitolo, gbc);
		
		gbc.gridx = 1; gbc.gridy = 0; 
		gbc.weightx = 1.0;
		formPanel.add(txtTitolo, gbc);
		
		//Riga 2: sottotitolo
		gbc.gridx = 0; gbc.gridy = 1;
		gbc.weightx = 0;
		formPanel.add(lSottotitolo, gbc);
		
		gbc.gridx = 1; gbc.gridy = 1;
		gbc.weightx = 1.0;
		formPanel.add(txtSottotitolo, gbc);
		
		//Riga 3: testo
		gbc.gridx = 0; gbc.gridy = 2; 
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		formPanel.add(lTesto, gbc);
		
		gbc.gridx = 1; 
		gbc.gridy = 2; 
		gbc.weightx = 1.0;
		gbc.weighty = 1.0; 
		gbc.fill = GridBagConstraints.BOTH;
		formPanel.add(scrollTesto, gbc);
		
		
		this.add(formPanel, BorderLayout.CENTER);

		
		JPanel bottoniPanel = new JPanel(new BorderLayout());
		bottoniPanel.add(annulla, BorderLayout.WEST);
		bottoniPanel.add(crea, BorderLayout.EAST);
		
		this.add(bottoniPanel, BorderLayout.SOUTH);
		
		
	}
	
	public JButton getCrea() {		
		return crea; 
		
	}
	
	public JButton getAnnulla() { 	
		return annulla; 
	
	}
	
	public JTextField getTxtTitolo() { 
		return txtTitolo; 
		
	}
	
	public JTextField getTxtSottotitolo() { 
		return txtSottotitolo; 
		
	}
	
	public JTextArea getTxtTesto() { 
		return txtTesto; 
		
	}
	
	
	
	

}
