package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities;

import java.security.SecureRandom;
/**
 * @author teopacchiega
 */

public class GeneratoreId {
    private static final SecureRandom random = new SecureRandom();

    private GeneratoreId() {
        throw new UnsupportedOperationException("Questa è una classe di utilità e non può essere istanziata.");
    }

    
    public static String generaId(int dimensione) {
        if (dimensione <= 0) {
            throw new IllegalArgumentException("La dimensione dell'ID deve essere maggiore di 0");
        }

        StringBuilder id = new StringBuilder(dimensione);
        id.append(random.nextInt(9) + 1);
        for (int i = 0; i < dimensione - 1; i++) {
            id.append(random.nextInt(10));
        }

        return id.toString();
    }
	
}
