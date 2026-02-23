package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy;

import java.util.ArrayList;
import java.util.List;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.StringChecker;

public class CercaAutoreStrategy implements IRicercaMessaggiStrategy {
	private static final String REGEX_AUTORE = "^[a-zA-Z][a-zA-Z0-9]*$";

	@Override
	public List<Messaggio> cerca(List<Messaggio> conversazione, String nomeAutore) {
		List<Messaggio> filtrati = new ArrayList<>();
		
		
		if (!StringChecker.validaStringa(nomeAutore, REGEX_AUTORE)) {
			System.err.println("Input non valido! Usa solo lettere e numeri.");
			return filtrati; 
		}
		
		if (conversazione != null && !conversazione.isEmpty()) {
			String autoreCercato = nomeAutore.toLowerCase().trim();
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