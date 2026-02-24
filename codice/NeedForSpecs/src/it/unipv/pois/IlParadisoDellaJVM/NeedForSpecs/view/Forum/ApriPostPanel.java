package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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

public class ApriPostPanel extends JPanel {

	private JLabel lTitolo;
	private JLabel lSottotitolo;
	private JTextArea tTesto;
	private JTable tabellaCommenti;  
	private JButton creaCommento;
	private JButton eliminaCommento;
	private JButton tornaIndietro;
	private JButton apriCommento;

	public ApriPostPanel() {

		this.setLayout(new GridLayout(1, 2, 20, 0));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel leftPanel = new JPanel(new BorderLayout(0, 15));

		
		JPanel topLefPanel = new JPanel(new BorderLayout());

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		tornaIndietro = new JButton("⬅ INDIETRO");
		btnPanel.add(tornaIndietro);
		topLefPanel.add(btnPanel, BorderLayout.NORTH);

		JPanel titoliPanel = new JPanel();
		titoliPanel.setLayout(new BoxLayout(titoliPanel, BoxLayout.Y_AXIS)); 
		titoliPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0)); 

		lTitolo = new JLabel("Titolo");
		lTitolo.setFont(new Font("Arial", Font.BOLD, 24));

		lSottotitolo = new JLabel("Sottotitolo");
		lSottotitolo.setFont(new Font("Arial", Font.ITALIC, 16));

		titoliPanel.add(lTitolo);
		titoliPanel.add(Box.createVerticalStrut(5)); 
		titoliPanel.add(lSottotitolo);
		topLefPanel.add(titoliPanel, BorderLayout.CENTER);

		leftPanel.add(topLefPanel, BorderLayout.NORTH);


		tTesto = new JTextArea();
		tTesto.setEditable(false); 
		tTesto.setLineWrap(true);
		tTesto.setWrapStyleWord(true);
		tTesto.setFont(new Font("Arial", Font.PLAIN, 14));
		tTesto.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JScrollPane scrollTesto = new JScrollPane(tTesto);
		leftPanel.add(scrollTesto, BorderLayout.CENTER);


		JPanel rightPanel = new JPanel(new BorderLayout(0, 10));

		JLabel lCommenti = new JLabel("Lista commenti");
		lCommenti.setFont(new Font("Arial", Font.BOLD, 18));
		rightPanel.add(lCommenti, BorderLayout.NORTH);

		tabellaCommenti = new JTable();
		tabellaCommenti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollCommenti = new JScrollPane(tabellaCommenti);
		rightPanel.add(scrollCommenti, BorderLayout.CENTER);


		JPanel bottoniDestraPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		creaCommento = new JButton("Crea Commento");
		apriCommento = new JButton("Apri Commento");
		eliminaCommento = new JButton("Elimina Commento");


		bottoniDestraPanel.add(creaCommento);
		bottoniDestraPanel.add(apriCommento);
		bottoniDestraPanel.add(eliminaCommento);

		rightPanel.add(bottoniDestraPanel, BorderLayout.SOUTH);

		this.add(leftPanel);
		this.add(rightPanel);
	}

	public JLabel getlTitolo() { 
		
		return lTitolo; 
		
	}
	
	public JLabel getlSottotitolo() { 
		
		return lSottotitolo; 
		
	}
	
	public JTextArea gettTesto() { 
		
		return tTesto; 
		
	}
	
	public JTable getTabellaCommenti() { 
		
		return tabellaCommenti; 
		
	}
	
	public JButton getCreaCommento() { 
		
		return creaCommento;
		
	}
	
	public JButton getEliminaCommento() { 
		
		return eliminaCommento;
		
	}
	
	public JButton getTornaIndietro() { 
		
		return tornaIndietro; 
		
	}
	
	public JButton getApriCommento() { 
		
		return apriCommento; 
		
	}
	

	
	
	
	
	
}