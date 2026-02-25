package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Commento;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;

class CommentoTest {

	private Post postPadre;
	private Commento commento;

	@BeforeEach
	void setUp() {

		UtenteGenerico utentePost = new UtenteGenerico();
		postPadre = new Post(utentePost, "Testo Post", LocalDateTime.now(), "Titolo", "Sottotitolo");
	}

	@Test
	void testGetNomeAutoreVisibile_UtenteValido() {

		UtenteGenerico autoreCommento = new UtenteGenerico();
		autoreCommento.setUser_name("Autore");
		
		commento = new Commento(autoreCommento, "testoCommento", LocalDateTime.now(), postPadre, postPadre);
		

		String risultato = commento.getNomeAutoreVisibile();
		
		// Assert
		assertEquals("Gigabyte99", risultato, "Deve restituire l'username corretto dell'utente.");
	}

	@Test
	void testGetNomeAutoreVisibile_UtenteNullo() {

		commento = new Commento(null, "Testo rimasto orfano", LocalDateTime.now(), postPadre, postPadre);
		
		String risultato = commento.getNomeAutoreVisibile();
		
		assertEquals("Sconosciuto", risultato, "Se l'autore è null, deve restituire 'Sconosciuto' per non far crashare la View.");
	}

	@Test
	void testGetNomeAutoreVisibile_UtenteSenzaUsername() {
		
		UtenteGenerico autoreSenzaNome = new UtenteGenerico();
		autoreSenzaNome.setUser_name(null);
		
		commento = new Commento(autoreSenzaNome, "Testo commento", LocalDateTime.now(), postPadre, postPadre);
		
		// Act
		String risultato = commento.getNomeAutoreVisibile();
		
		// Assert
		assertEquals("Sconosciuto", risultato, "Se l'autore esiste ma non ha uno username, deve restituire 'Sconosciuto'.");
	}
}