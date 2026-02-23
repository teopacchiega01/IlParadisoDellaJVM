package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs;

import javax.swing.JFrame;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller.ControllerAssistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller.ControllerHome;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.GestoreAccount;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Assistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.Persistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.Forum;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.ForumView;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;

public class main {

	public static void main(String[] args) {
		
		
		
		// 1. Creo gli utenti simulati
		UtenteGenerico utenteSimulato = new UtenteGenerico("GiuliaBianchi", "giulia.b@email.com", "qwerty", "Giulia", "Bianchi");
		UtenteStaff luca = new UtenteStaff("AdminTech_Luca", "admin.luca@paradiso.com", "adminpass", "Luca", "Verdi");

		HomeFrame hf = new HomeFrame();
		GestoreAccount ga = GestoreAccount.getInstance();
		ga.setUtenteLoggato(utenteSimulato);
		new ControllerHome(ga, hf);
		
		
////		
//		Assistenza ass = Assistenza.getInstance();
//		ass.setUtente_loggato(utenteSimulato);
//		FrameAssistenza frame = new FrameAssistenza();
//		new ControllerAssistenza(frame, ass);

//		
//		Assistenza ass = Assistenza.getInstance();
//		ass.setUtente_loggato(luca);
//		FrameAssistenza frame = new FrameAssistenza();
//		new ControllerAssistenza(frame, ass);
//		
		

//		Forum f = Forum.getInstance(factory);
//		
//		ForumView fFrame = new ForumView(f);
//		fFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		fFrame.setVisible(true);
	}
}