package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Properties;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;

//	@author teopacchiega

public class ProdottiFactory {
	private static Properties p;

	static {
		p = new Properties();
		try {
			p.load(new FileInputStream("properties/properties"));
		} catch (Exception e) {
			System.err.println("Impossibile caricare il file properties. " + e.getMessage());
		}
	}


	public static Prodotto creaComponente(double prezzo, String marca, String modello, TipoComponente tipo, ArrayList<String> valori_specifiche, int potenza) {

		Componente c = new Componente(prezzo, marca, modello, tipo, valori_specifiche, potenza);

		return c;
	}

	public static Prodotto creaBuild(String nomeBuild) {

		Build b = new Build(nomeBuild);

		return b;
	}


	public static Prodotto getProdotto(TipologiaProdotto tipo_prodotto_scelto) {
		Prodotto prod = null;
		String nome_classe_prodotto;			
		try {
			nome_classe_prodotto = p.getProperty(tipo_prodotto_scelto.name());
			Constructor c = Class.forName(nome_classe_prodotto).getConstructor();
			prod = (Prodotto)c.newInstance();				
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			prod = null;
		}
		return prod;
	}


}
