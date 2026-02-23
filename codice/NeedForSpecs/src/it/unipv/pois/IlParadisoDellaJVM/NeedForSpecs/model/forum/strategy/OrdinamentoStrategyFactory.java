package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class OrdinamentoStrategyFactory {

	private static OrdinamentoStrategyFactory instance;

	public static OrdinamentoStrategyFactory getInstance() {
		if (instance == null) {
			instance = new OrdinamentoStrategyFactory();
		}
		return instance;
	}

	public ForumStrategy getStrategy(Ordinamento r) {

		ForumStrategy strategy = null;

		Properties p = new Properties();

		String nomeClasse;

		try {

			p.load(new FileInputStream("properties/properties"));
			nomeClasse = p.getProperty(r.name());

			if(nomeClasse != null) {

				Constructor<?> c = Class.forName(nomeClasse).getConstructor();

				strategy = (ForumStrategy) c.newInstance();

			}else {

				System.err.println("Classe " + r.name() + "non trovata");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Errore creazione istanza" + e.getMessage());
		}

		return strategy;

	}



	// Traduttore da stringa a strategt
	public ForumStrategy StringaToStrategy(String sceltaCombo) {

		Ordinamento o;

		switch (sceltaCombo) {
		case "Più Recenti":
			
			o = Ordinamento.PIU_RECENTI;
			break;
			
		case "Alfabetico (Titolo)":
			
			o = Ordinamento.TITOLO;
			break;
			
		case "Autore":
			
			o = Ordinamento.AUTORE;
			break;
			
		default:
			o = Ordinamento.PIU_RECENTI;
			break;
		}

		
		return getStrategy(o);
	}




}