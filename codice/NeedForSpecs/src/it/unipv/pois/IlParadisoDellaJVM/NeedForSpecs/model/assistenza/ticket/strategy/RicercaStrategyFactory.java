package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket.strategy;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class RicercaStrategyFactory {

	private static RicercaStrategyFactory instance;
	
	private RicercaStrategyFactory() {}
	
	public static RicercaStrategyFactory getInstance() {
		if (instance == null) {
			instance = new RicercaStrategyFactory();
		}
		return instance;
	}

	public IRicercaMessaggiStrategy getStrategy(Ricerca tipo_ricerca) {
		
		IRicercaMessaggiStrategy strategy = null;
		Properties p = new Properties(); 
		String strategyClassName;			
		
		try {
			p.load(new FileInputStream("properties/properties"));
			strategyClassName = p.getProperty(tipo_ricerca.name());
			
			if (strategyClassName != null) {
				Constructor<?> c = Class.forName(strategyClassName).getConstructor();
				strategy = (IRicercaMessaggiStrategy) c.newInstance();				
			} else {
				System.err.println("Nessuna classe configurata per il tipo: " + tipo_ricerca.name());
			}
			
		} catch (Exception e) {
			System.err.println("Errore fatale durante l'istanziazione della strategy: " + e.getMessage());
			e.printStackTrace();
		}
		
		return strategy;
	}
}