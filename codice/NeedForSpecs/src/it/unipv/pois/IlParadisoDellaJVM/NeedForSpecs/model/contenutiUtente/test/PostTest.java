package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Commento;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;

class PostTest {

	private Post post;
	private UtenteGenerico utenteTest;

	@BeforeEach
	void setUp() {

		utenteTest = new UtenteGenerico(); 
		utenteTest.setUser_name("UtenteTest");
		
		post = new Post(utenteTest, "Testo del post", LocalDateTime.now(), "Titolo Post", null);
	}

	@Test
	void testGetSottotitoloSicuro_ConSottotitoloNullo() {
		// Act
		String risultato = post.getSottotitoloSicuro();
		
		assertEquals("", risultato, "Se il sottotitolo è null, deve restituire una stringa vuota per non far crashare la View.");
	}

	@Test
	void testGetSottotitoloSicuro_ConSottotitoloPresente() {

		post.setSottotitolo("Sottotitolo reale");
		
		String risultato = post.getSottotitoloSicuro();
		
		assertEquals("Sottotitolo reale", risultato, "Se il sottotitolo è valorizzato, deve restituirlo intatto.");
	}

	@Test
	void testAggiungiCommento_Valido() {

		Commento commentoValido = new Commento(utenteTest, "Ottimo post!", LocalDateTime.now(), post, post);
		
		post.aggiungiCommento(commentoValido);
		
		assertEquals(1, post.getCommenti().size(), "La lista dei commenti deve contenere esattamente un elemento.");
		assertTrue(post.getCommenti().contains(commentoValido), "Il commento aggiunto deve essere presente nella lista.");
	}

	@Test
	void testAggiungiCommento_LanciaEccezioneSeNullo() {

		assertThrows(IllegalArgumentException.class, () -> {
			post.aggiungiCommento(null);
		}, "Dovrebbe lanciare IllegalArgumentException se si tenta di aggiungere un commento nullo.");
	}
}
