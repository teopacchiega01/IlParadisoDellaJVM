package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Commento;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.adapter.CommentoAdapter;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.adapter.PostAdapter;

public class ForumView extends JFrame {

	private JLabel labelMessaggi;
	private JPanel containerCentrale;
	private ForumPanel postView;

	public ForumView(){
		super("Il Paradiso della JVM - Forum");
		// Dimensioni aumentate per gestire meglio il layout
		this.setSize(1100, 850); 
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Layout principale del Frame
		this.setLayout(new BorderLayout());

		// Questo pannello ospiterà i contenuti che cambiano
		containerCentrale = new JPanel(new BorderLayout());
		postView = new ForumPanel();
		containerCentrale.add(postView, BorderLayout.CENTER);

		// Label dei messaggi: ESTERNA al container centrale
		labelMessaggi = new JLabel(" ", SwingConstants.CENTER);
		labelMessaggi.setFont(new Font("Arial", Font.BOLD, 15));
		labelMessaggi.setOpaque(true);
		labelMessaggi.setBackground(new Color(230, 230, 230));
		labelMessaggi.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		labelMessaggi.setPreferredSize(new java.awt.Dimension(1000, 30));

		// Montaggio finale
		this.add(containerCentrale, BorderLayout.CENTER);
		this.add(labelMessaggi, BorderLayout.SOUTH);
	}

	// Metodo fondamentale: cambia solo il centro, non il fondo!
	public void cambiaFinestra(JPanel p) {
		containerCentrale.removeAll();
		containerCentrale.add(p, BorderLayout.CENTER);
		containerCentrale.revalidate();
		containerCentrale.repaint();
	}

	public void mostraErrore(String messaggio) {
		labelMessaggi.setForeground(Color.RED);
		labelMessaggi.setText("✘ " + messaggio);
	}

	public void mostraSuccesso(String messaggio) {
		labelMessaggi.setForeground(new Color(0, 120, 0)); 
		labelMessaggi.setText("✔ " + messaggio);
	}

	public void pulisciMessaggi() {
		labelMessaggi.setText(" ");
	}

	public ForumPanel getPostView() { return postView; }
	public CreaPostPanel creaPost() { return new CreaPostPanel(); }
	public ApriPostPanel apriPost() { return new ApriPostPanel(); }
	public CreaCommentoPanel creaCommento() { return new CreaCommentoPanel(); }
	public ApriCommentoPanel apriCommento() {
		
		return new ApriCommentoPanel();
	}
	public PostAdapter adaptPost(ArrayList<Post> p) { return new PostAdapter(p); }
	public CommentoAdapter adaptCommenti(ArrayList<Commento> c) { return new CommentoAdapter(c); }
}