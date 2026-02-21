package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy;


import java.util.List;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;

public interface IRicercaMessaggiStrategy {
	
	List<Messaggio> cerca(List<Messaggio> conversazione, String elemento_ricerca);

}
