package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy;


import java.util.ArrayList;

import java.util.List;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.StringChecker;

/**
 * @author Persy
 */

public class CercaAutoreStrategy implements IRicercaMessaggiStrategy {
	/**
	 * REGEX_AUTORE: Valida l'username o il nome dell'autore.
	 * "^[a-zA-Z]"    : Deve iniziare obbligatoriamente con una lettera.
	 * "[a-zA-Z0-9]*$" : Può essere seguito da zero o più lettere o numeri. 
	 * (Niente spazi, niente caratteri speciali).
	 */
	private static final String REGEX_AUTORE = "^[a-zA-Z][a-zA-Z0-9]*$";

	@Override
	public List<Messaggio> cerca(List<Messaggio> conversazione, String nome_autore) {
		List<Messaggio> filtrati = new ArrayList<>();
		
		
		if (!StringChecker.validaStringa(nome_autore, REGEX_AUTORE)) {
			System.err.println("Input non valido! Usa solo lettere e numeri.");
			return filtrati; 
		}
		
		if (conversazione != null && !conversazione.isEmpty()) {
			String autoreCercato = nome_autore.toLowerCase().trim();
			for (Messaggio m : conversazione) {
				String nomeMittente = m.getAutore().getUser_name().toLowerCase();
				if (nomeMittente.contains(autoreCercato)) {
					filtrati.add(m);
				}
			}
		} else {
			System.err.println("La conversazione è vuota, impossibile cercare.");
		}
		
		return filtrati;
	}
}