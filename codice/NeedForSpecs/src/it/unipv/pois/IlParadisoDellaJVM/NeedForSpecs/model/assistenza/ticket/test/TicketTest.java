package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Stato;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.Ticket;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy.Ricerca;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;

/**
 * Classe di Test per verificare la logica di business dell'Entità Ticket.
 * @author Persy
 */
class TicketTest {

	private Ticket ticket_vuoto;
	private Ticket ticket_nuovo;
	private UtenteGenerico utente_fittizio;
	private UtenteStaff staff_fittizio;

	@BeforeEach
	void setUp() {

		utente_fittizio = new UtenteGenerico(); 
		staff_fittizio = new UtenteStaff();
		staff_fittizio.setUser_name("Admin_Mario"); 
		
		ticket_vuoto = new Ticket();
		ticket_nuovo = new Ticket(utente_fittizio, null, Stato.APERTO, new ArrayList<>());
	}

	@Test
	void testCreazioneIdAutomatico() {
		assertNotNull(ticket_nuovo.getId_ticket(), "L'ID del ticket non deve essere null dopo la creazione");
		assertTrue(ticket_nuovo.getId_ticket().startsWith("Ticket"), "L'ID dovrebbe iniziare con 'Ticket'");
	}

	@Test
	void testAggiungiMessaggioSenzaDuplicati() {
		
		Messaggio msg1 = new Messaggio();
		msg1.setId_contenuto_utente("MSG-001");
		
		Messaggio msgDuplicato = new Messaggio();
		msgDuplicato.setId_contenuto_utente("MSG-001");

		ticket_vuoto.aggiungiMessaggioAllaConversazione(msg1);
		assertEquals(1, ticket_vuoto.getConversazione().size(), "La conversazione dovrebbe avere 1 messaggio");

		ticket_vuoto.aggiungiMessaggioAllaConversazione(msgDuplicato);
		assertEquals(1, ticket_vuoto.getConversazione().size(), "La dimensione deve restare 1, il duplicato non deve essere inserito");
	}

	@Test
	void testCreaMessaggioAggiungeAllaConversazione() {
		Messaggio nuovoMsg = ticket_nuovo.creaMessaggio(utente_fittizio, "Ho un problema col PC");
		
		assertNotNull(nuovoMsg, "Il messaggio creato non deve essere null");
		assertEquals("Ho un problema col PC", nuovoMsg.getTesto(), "Il testo del messaggio deve coincidere");
		assertEquals(1, ticket_nuovo.getConversazione().size(), "Il messaggio deve essere stato aggiunto automaticamente alla lista");
		assertEquals(nuovoMsg, ticket_nuovo.getUltimoMessaggio(), "Il messaggio aggiunto deve essere l'ultimo della conversazione");
	}

	@Test
	void testToStringSenzaGestore() {
		
		String risultato = ticket_nuovo.toString();
		assertTrue(risultato.contains("In attesa di assegnazione"), "Il toString deve segnalare se non c'è gestore");
	}

	@Test
	void testToStringConGestore() {
		// Admin_Mario
		ticket_nuovo.setGestore(staff_fittizio); 
		String risultato = ticket_nuovo.toString();
		assertTrue(risultato.contains("Admin_Mario"), "Il toString deve includere il nome del gestore se presente");
		assertFalse(risultato.contains("In attesa di assegnazione"));
	}
	
	@Test
	void testGetCronologiaFormattataVuota() {
		String cronologia = ticket_vuoto.getCronologiaMessaggiFormattata();
		assertEquals("Nessun messaggio presente.\n", cronologia, "Deve restituire un avviso se non ci sono messaggi");
	}
	@Test
	void testStatoInizialeNonChiuso() {
		
		assertFalse(ticket_nuovo.getStato_ticket() == Stato.CHIUSO, "Un nuovo ticket non deve nascere già chiuso");
	}

	@Test
	void testToStringNonStampaNull() {

		String risultato = ticket_vuoto.toString();
		assertFalse(risultato.contains("null"), "Il toString formattato non deve mai mostrare la parola 'null' all'utente");
	}
	
	@Test
	void testRicercaMessaggiNonPertinenti() {

		ticket_nuovo.creaMessaggio(utente_fittizio, "Il mio computer non si accende");
	
		String risultatoRicerca = ticket_nuovo.ricercaEFormattaMessaggi("tastiera", Ricerca.TESTO);
		
		assertFalse(risultatoRicerca.contains("computer"), "La ricerca non deve restituire messaggi che non matchano il criterio");
	}
}