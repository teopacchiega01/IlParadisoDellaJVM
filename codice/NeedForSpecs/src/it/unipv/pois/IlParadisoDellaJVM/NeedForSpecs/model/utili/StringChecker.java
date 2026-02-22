package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utili;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

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
}