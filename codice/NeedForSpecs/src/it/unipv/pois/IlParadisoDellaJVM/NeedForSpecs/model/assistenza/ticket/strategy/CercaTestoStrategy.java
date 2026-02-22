package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy;

import java.util.ArrayList;
import java.util.List;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utili.StringChecker;

public class CercaTestoStrategy implements IRicercaMessaggiStrategy {

	
	private static final String REGEX_TESTO = "^[a-zA-Z0-9][a-zA-Z0-9\\s\\.,?!'\"]*$";

	@Override
	public List<Messaggio> cerca(List<Messaggio> conversazione, String frase) {
		ArrayList<Messaggio> messaggi_filtrati = new ArrayList<>();
		
		if (!StringChecker.validaStringa(frase, REGEX_TESTO)) {
			System.err.println("Testo non valido! Evita caratteri speciali vietati.");
			return messaggi_filtrati; 
		}

		if (conversazione != null && !conversazione.isEmpty()) {
			String parolaCercata = frase.toLowerCase().trim();
			for (Messaggio m : conversazione) {
				if (m.getTesto().toLowerCase().contains(parolaCercata)) {
					messaggi_filtrati.add(m);
				}
			}
		} else {
			System.err.println("La conversazione è vuota, impossibile cercare.");
		}
		
		return messaggi_filtrati;
	}	
}
