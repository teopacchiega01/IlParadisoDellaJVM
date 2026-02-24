package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;

/**
 * @author Persy
 */
public class CercaDataStrategy implements IRicercaMessaggiStrategy {

	
	private static final DateTimeFormatter FORMATO_INPUT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public List<Messaggio> cerca(List<Messaggio> conversazione, String data_cercata) {
		List<Messaggio> filtrati = new ArrayList<>();
		if (conversazione != null && !conversazione.isEmpty() && !data_cercata.isBlank()) {
			try {
				LocalDate dataUtente = LocalDate.parse(data_cercata.trim(), FORMATO_INPUT);
				for (Messaggio m : conversazione) {
					if (m.getData_pubblicazione() != null) {
						LocalDate dataMessaggio = m.getData_pubblicazione().toLocalDate();
						if (dataUtente.equals(dataMessaggio)) {
							filtrati.add(m);
						}
					}
				}
			} catch (DateTimeParseException e) {
				System.err.println("Errore di ricerca: inserire la data nel formato dd/MM/yyyy (es. 20/02/2026)");
			}
		} else {
			System.err.println("Conversazione vuota o parametro di ricerca assente.");
		}
		
		return filtrati;
	}
}