package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.Forum;

class ForumTest {

	private Forum forum;
	private Utente autorePost;
	private Utente utenteNormale;
	private Utente utenteStaff;
	private Post postDiTest;

	@BeforeEach
	void setUp() {
		
		forum = Forum.getInstance();

		autorePost = new UtenteGenerico(); 
		autorePost.setUser_name("autore");

		utenteNormale = new UtenteGenerico(); 
		utenteNormale.setUser_name("nonAutore");

		utenteStaff = new UtenteStaff(); 
		utenteStaff.setUser_name("Admin");

		postDiTest = new Post(autorePost, "Contenuto", LocalDateTime.now(), "Titolo", "Sottotitolo");
		
	}

	@Test
	void testSingletonInstance() {

		Forum altraIstanza = Forum.getInstance();
		
		//verifica che l'istanza forum sia ugualle all'altraIstanza
		assertSame(forum, altraIstanza, "Forum dovrebbe essere un Singleton (stessa istanza).");
	}

	@Test
	void testPuoModificareOEliminare_OspiteNegato() {
		
		//permesso di modifica o eliminazione
		boolean permesso = forum.puoModificareOEliminare(null, postDiTest);
		assertFalse(permesso, "Un utente nullo (non loggato) NON deve avere i permessi.");
	}

	@Test
	void testPuoModificareOEliminare_AutoreConcesso() {

		boolean permesso = forum.puoModificareOEliminare(autorePost, postDiTest);
		assertTrue(permesso, "L'autore originale del post DEVE avere i permessi.");
	}

	@Test
	void testPuoModificareOEliminare_AltroUtenteNegato() {
		
		boolean permesso = forum.puoModificareOEliminare(utenteNormale, postDiTest);
		assertFalse(permesso, "Un utente diverso dall'autore NON deve avere i permessi.");
	}

	@Test
	void testPuoModificareOEliminare_StaffConcesso() {

		boolean permesso = forum.puoModificareOEliminare(utenteStaff, postDiTest);
		assertTrue(permesso, "Lo staff DEVE sempre avere i permessi di modifica/eliminazione.");
	}

	@Test
	void testCreaPost_LanciaEccezioneSeTitoloVuoto() {

		assertThrows(IllegalArgumentException.class, () -> {
			forum.creaPost(autorePost, "Testo valido", "", "Sottotitolo");
		}, "Dovrebbe lanciare un'eccezione se il titolo è una stringa vuota.");
	}

	@Test
	void testCreaCommento_LanciaEccezioneSeTestoNullo() {

		assertThrows(IllegalArgumentException.class, () -> {
			forum.creaCommento(autorePost, null, postDiTest, postDiTest);
		}, "Dovrebbe lanciare un'eccezione se il testo del commento è nullo.");
	}
}