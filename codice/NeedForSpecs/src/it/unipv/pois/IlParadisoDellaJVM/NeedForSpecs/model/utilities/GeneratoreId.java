package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities;

import java.security.SecureRandom;

public class GeneratoreId {
	// Instanzio il generatore crittografico una volta sola
    private static final SecureRandom random = new SecureRandom();

    // Costruttore privato: impedisce a chiunque di fare "new GeneratoreId()"
    private GeneratoreId() {
        throw new UnsupportedOperationException("Questa è una classe di utilità e non può essere istanziata.");
    }

    
    public static String generaId(int dimensione) {
    	
        if (dimensione <= 0) {
            throw new IllegalArgumentException("La dimensione dell'ID deve essere maggiore di 0");
        }

        StringBuilder id = new StringBuilder(dimensione);

        // La prima cifra da 1 a 9 (evita che inizi con 0)
        id.append(random.nextInt(9) + 1);

        // Le restanti cifre da 0 a 9
        for (int i = 0; i < dimensione - 1; i++) {
            id.append(random.nextInt(10));
        }

        return id.toString();
    }
	
}
