package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.Assistenza;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.eccezioni.TicketNotFoundException;

	/**
	 * Classe di Test per verificare la logica del Manager Assistenza.
	 * @author Persy
	 */
	class AssistenzaTest {

		private Assistenza assistenza;
		private UtenteGenerico utente_generico;
		private UtenteStaff utente_staff;

		@BeforeEach
		void setUp() throws Exception {
			//Azzero l'istanza della classe assistenza
			Field instanceField = Assistenza.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, null);

			
			assistenza = Assistenza.getInstance();
			
			
			utente_generico = new UtenteGenerico();
			utente_generico.setUser_name("MarioTest");
			
			utente_staff = new UtenteStaff();
			utente_staff.setUser_name("AdminTest");
		}

		@Test
		void testSingletonInstance() {
		
			Assistenza a1 = Assistenza.getInstance();
			Assistenza a2 = Assistenza.getInstance();
			
			assertSame(a1, a2, "Il pattern Singleton deve restituire sempre la stessa identica istanza in memoria");
		}

		@Test
		void testVisualizzaTicketInesistenteLanciaEccezione() {
			
			assertThrows(TicketNotFoundException.class, () -> {
				assistenza.visualizzaTicketDaId("ID_FALSO_123");
			}, "Deve lanciare TicketNotFoundException se l'ID non è presente in memoria");
		}

		@Test
		void testApriTicketSenzaUtenteFallisce() {
			
			assistenza.setUtente_loggato(null);
			
			boolean risultato = assistenza.apriTicket();
			assertFalse(risultato, "Il ticket non deve essere aperto se non c'è nessun utente loggato");
		}

		@Test
		void testApriTicketConStaffFallisce() {
			
			assistenza.setUtente_loggato(utente_staff);
			
			boolean risultato = assistenza.apriTicket();
			assertFalse(risultato, "Lo Staff non deve poter aprire nuovi ticket di assistenza");
		}
		
		@Test
		void testChiudiTicketInesistenteFallisce() {
			
			boolean risultato = assistenza.chiudiTicket("TicketFantasma");
			assertFalse(risultato, "Non deve poter chiudere un ticket che non esiste nella sessione");
		}

		@Test
		void testListaTicketInizialmenteVuota() {
			
			ArrayList<Ticket> caricati = assistenza.getTuttiITicketCaricati();
			assertTrue(caricati.isEmpty(), "All'avvio, la lista dei ticket caricati deve essere vuota");
		}
		
		@Test
		void testImpostaERecuperaUtenteLoggato() {
			
			assistenza.setUtente_loggato(utente_generico);
			assertEquals(utente_generico, assistenza.getUtente_loggato(), "L'utente loggato recuperato deve essere quello impostato");
		}
}