package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy;

import java.util.ArrayList;
import java.util.List;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;

public class CercaTestoStrategy implements IRicercaMessaggiStrategy {

	@Override
	public List<Messaggio> cerca(List<Messaggio> conversazione,String frase) {
		// TODO Auto-generated method stub
		ArrayList<Messaggio> messaggi_filtrati = new ArrayList<>();
		if(conversazione.isEmpty() ) {
			for(Messaggio m : conversazione) {
				if(m.getTesto().contains(frase)) 
					messaggi_filtrati.add(m);
				
			}
		}else {
			System.err.println("Parola non valida");
		}
		return messaggi_filtrati;
	}
	
}
