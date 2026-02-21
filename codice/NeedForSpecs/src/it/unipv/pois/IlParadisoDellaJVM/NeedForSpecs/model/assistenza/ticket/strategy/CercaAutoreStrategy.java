package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy;

import java.util.ArrayList;
import java.util.List;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;

public class CercaAutoreStrategy implements IRicercaMessaggiStrategy {

	@Override
	public List<Messaggio> cerca(List<Messaggio> conversazione, String user_name) {
		// TODO Auto-generated method stub
		ArrayList<Messaggio> messaggi_filtrati = new ArrayList<>();
		
		if(!conversazione.isEmpty()) {
			for(Messaggio m : conversazione) {
				Utente autore = m.getAutore();
				if(autore.getUser_name().equals(messaggi_filtrati))
					messaggi_filtrati.add(m);
			}	
		}
		
		return messaggi_filtrati;
	}

}
