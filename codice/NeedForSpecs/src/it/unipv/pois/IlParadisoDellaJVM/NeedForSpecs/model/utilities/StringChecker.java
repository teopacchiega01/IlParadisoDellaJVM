package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.regex.Pattern;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;

/**
 * @author Persy
 */
public class StringChecker {

	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	
	public static String formattaSingoloMessaggio(String autore, LocalDateTime dataPubblicazione, String testo) {
		String dataFormattata = "Data Sconosciuta";
		if (dataPubblicazione != null) {
			dataFormattata = dataPubblicazione.format(FORMATO_DATA);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(autore)
		  .append(" - ")
		  .append(dataFormattata)
		  .append(":\n") 
		  .append(testo)
		  .append("\n\n");
		  
		return sb.toString();
	}

	public static String pulisciInput(String inputGrezzo) {
		if (inputGrezzo == null) {
			return "";
		}
		return inputGrezzo.trim(); 
	}
	
	public static String formattaNomeUtente(String nomeGrezzo) {
		if (nomeGrezzo == null || nomeGrezzo.trim().isEmpty()) {
			return "UtenteSconosciuto";
		}
	
		String pulito = nomeGrezzo.trim();
		return pulito.substring(0, 1).toUpperCase() + pulito.substring(1).toLowerCase();
	}
	
	public static boolean validaStringa(String input, String regex) {
		if (input == null || input.isBlank() || regex == null) {
			return false;
		}
		return Pattern.matches(regex, input.trim());
	}
	
	
	public static LocalDate convertiInLocalDate(String data, String formato) {
		String dataPulita = pulisciInput(data);
		if (dataPulita.isEmpty()) {
			return null;
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
			return LocalDate.parse(dataPulita, formatter);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	public static LocalDate convertiScadenzaCarta(String scadenza) {
		String scadenzaPulita = pulisciInput(scadenza);
		if (scadenzaPulita.isEmpty()) {
			return null;
		}
		try {
			DateTimeFormatter formatter = new DateTimeFormatterBuilder()
					.appendPattern("MM/yy")
					.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
					.toFormatter();
			return LocalDate.parse(scadenzaPulita, formatter);
		} catch (DateTimeParseException e) {
			return null;
		}
	}
	
	public static String formattaListaMessaggi(ArrayList<Messaggio> listaMessaggi) {
	    StringBuilder sb = new StringBuilder();
	    
	    for (Messaggio m : listaMessaggi) {
	        sb.append(formattaSingoloMessaggio(
	            m.getAutore().getUser_name(), 
	            m.getData_pubblicazione(), 
	            m.getTesto()
	        ));
	    }
	    
	    return sb.toString();
	}
}