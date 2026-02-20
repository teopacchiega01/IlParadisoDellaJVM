package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller.ControllerAssistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Assistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.DAOFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db.Persistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.assistenza.FrameAssistenza;

public class main {

	public static void main(String[] args) {
		
		 
		
		Persistenza scelta =  Persistenza.MYSQL_DB;
		
		DAOFactory factory = DAOFactory.getPersistenceFactory(scelta);
		
		Assistenza ass  = new Assistenza(factory);
		
		FrameAssistenza frame = new FrameAssistenza();
        
		
        //frame.mostraHomeStaff(); 
        
       //frame.mostraChatStaff();
       // frame.mostraHomeUtente();
        
        frame.setVisible(true);
        UtenteGenerico utenteSimulato = new UtenteGenerico("GiuliaBianchi", "giulia.b@email.com", "qwerty", "Giulia", "Bianchi");
        UtenteStaff luca = new UtenteStaff("AdminTech_Luca", "admin.luca@paradiso.com", "adminpass", "Luca", "Verdi");
		
        //new ControllerAssistenza(frame, ass, utenteSimulato);
      
        new ControllerAssistenza(frame, ass, luca);
	}
	
}