package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller.ControllerHome;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.GestoreAccount;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;

public class main {

	public static void main(String[] args) {

		// UTENTI NEL DB
//		| AdminTech_Luca     | admin.luca@staff.com    | adminpass   | Luca   | Verdi   |
//		| anna_neri          | anna.neri@email.com     | hash_pw_012 | Anna   | Neri    |
//		| bidello_modulare   | paolo.limiti@email.com  | password1   | Paolo  | Limiti  |
//		| giulia_verdi       | giulia.verdi@email.com  | hash_pw_789 | Giulia | Verdi   |
//		| GiuliaBianchi      | giulia.b@email.com      | qwerty      | Giulia | Bianchi |
//		| luigi_bianchi      | luigi.bianchi@email.com | hash_pw_456 | Luigi  | Bianchi |
//		| mario_rossi88      | mario.rossi@email.com   | hash_pw_123 | Mario  | Rossi   |
//		| MarioRossi99       | mario.rossi2@email.com  | password123 | Mario  | Rossi   |
//		| paolo_gialli       | paolo.gialli@email.com  | hash_pw_345 | Paolo  | Gialli  |
//		| tarallo_perforante | pietro.smusi@staff.com  | 12345678    | Pietro | Smusi   |

		
		// ATTENZIONE
		// Controllare sulla classe DatabaseManager che sia
		// decommentato il link al db corretto per il proprio OS
		
		// UTENTI DI PROVA
//		UtenteGenerico utenteSimulato = new UtenteGenerico("GiuliaBianchi", "giulia.b@email.com", "qwerty", "Giulia", "Bianchi");
//		UtenteStaff luca = new UtenteStaff("AdminTech_Luca", "admin.luca@staff.it", "adminpass", "Luca", "Verdi");

		
		HomeFrame hf = new HomeFrame();
		GestoreAccount ga = GestoreAccount.getInstance();
//		ga.setUtenteLoggato(utenteSimulato);
		ga.setUtenteLoggato(null);
		new ControllerHome(ga, hf);


	}
}