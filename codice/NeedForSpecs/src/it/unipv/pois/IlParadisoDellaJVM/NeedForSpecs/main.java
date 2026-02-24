package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller.ControllerHome;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.GestoreAccount;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;

public class main {

	public static void main(String[] args) {



		// 1. Creo gli utenti simulati
		UtenteGenerico utenteSimulato = new UtenteGenerico("GiuliaBianchi", "giulia.b@email.com", "qwerty", "Giulia", "Bianchi");
		UtenteStaff luca = new UtenteStaff("AdminTech_Luca", "admin.luca@staff.it", "adminpass", "Luca", "Verdi");

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
		//		ForumView fFrame = new ForumView();
		//		fFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		fFrame.setVisible(true);
		//
		//		UtenteStaff teo = new UtenteStaff("admin_teo", "teo@needforspecs.it", "password123", "Teo", "Rossi");
		//		UtenteGenerico s = new UtenteGenerico("sbs", "s@gmail.it", "sbspw", "Simone", "Bassi");
		//		UtenteGenerico g = new UtenteGenerico("GerryS", "gs@gmail.com", "sbatti", "Gerry", "Scotti");
		//
		//		new ControllerForum(f, fFrame.getPostView(),fFrame, g);










	}
}