package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs;

import javax.swing.JFrame;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller.ControllerAssistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Assistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.Persistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.Forum;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.ForumView;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;

public class main {

	public static void main(String[] args) {
		
		Persistenza scelta = Persistenza.MYSQL_DB;
		DAOFactory factory = DAOFactory.getPersistenceFactory(scelta);
		
		// 1. Creo gli utenti simulati
		UtenteGenerico utenteSimulato = new UtenteGenerico("GiuliaBianchi", "giulia.b@email.com", "qwerty", "Giulia", "Bianchi");
		UtenteStaff luca = new UtenteStaff("AdminTech_Luca", "admin.luca@paradiso.com", "adminpass", "Luca", "Verdi");

		
		
		Assistenza ass = new Assistenza(factory, utenteSimulato); 
		FrameAssistenza frame = new FrameAssistenza();
		new ControllerAssistenza(frame, ass);

//		
//		Assistenza ass = new Assistenza(factory, luca); 
//		
//		FrameAssistenza frame = new FrameAssistenza();
//		 new ControllerAssistenza(frame, ass);
//		
		

//		Forum f = Forum.getInstance(factory);
//		
//		ForumView fFrame = new ForumView(f);
//		fFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		fFrame.setVisible(true);
	}
}