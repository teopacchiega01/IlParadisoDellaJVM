package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;

public class CercaDataStrategy implements IRicercaMessaggiStrategy {

	@Override
	public List<Messaggio> cerca(List<Messaggio> conversazione, String data) {
		// TODO Auto-generated method stub
		ArrayList<Messaggio> messaggi_filtrati = new ArrayList<>();
		LocalDate data_utente =  LocalDate.parse(data);
		
		
		if(!conversazione.isEmpty()) {
			for(Messaggio m : conversazione) {
				if(data_utente.equals(m.getData_pubblicazione().toLocalDate()))
					messaggi_filtrati.add(m);
				
			}
		}
		
		return messaggi_filtrati;
	}

}
