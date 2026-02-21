package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.adapter.*;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.Forum;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.ForumException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.ForumPanelView;


public class ControllerForum {

	private Forum model;            
	private ForumPanelView view;     
	private HomeFrame mainFrame;


	public ControllerForum(Forum model, ForumPanelView view, HomeFrame mainFrame, Utente u) {
		super();
		this.model = model;
		this.view = view;
		this.mainFrame = mainFrame;

		try {

			ArrayList<Post> post = model.inizializzaForum();

		} catch (ForumException e) {

			JOptionPane.showMessageDialog(
					view, 
					"Impossibile caricare i post: " + e.getMessage(), 
					"Errore Database", 
					JOptionPane.ERROR_MESSAGE
					);
		}
		
		


	}



}