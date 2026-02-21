package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.Forum;

public class ForumView extends JFrame {
	
	private CardLayout cardLayout;
	
	private ForumPanelView postView;


	public ForumView(Forum f){
		
		super("Il Paradiso della JVM - Forum");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		cardLayout = new CardLayout();
		
		postView = new ForumPanelView();
		
		add(postView);
		

	}







}