package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Build;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.ComponentiException.TipoErrore;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.AspettiTecnici;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;

public class BuildTest {

    private Build buildTest;
    private Componente mockMobo;
    private Componente mockCpuCompatibile;
    private Componente mockCpuIncompatibile;

    @BeforeEach
    public void setUp() {
        // 1. Inizializzo una build vuota prima di ogni test
        buildTest = new Build(0.0, "PC Test");

        // 2. Creo dei componenti fittizi (mock) per i test
        
        // MOBO finta: Socket AM5
        mockMobo = new Componente(150.0, "Asus", "TUF", TipoComponente.MOBO, 40) {
            @Override
            public EnumMap<AspettiTecnici, String> getScheda_tecnica() {
                EnumMap<AspettiTecnici, String> st = new EnumMap<>(AspettiTecnici.class);
                st.put(AspettiTecnici.SOCKET_CPU, "AM5");
                st.put(AspettiTecnici.N_MODULI_RAM, "4");
                st.put(AspettiTecnici.N_SLOT_PCIE, "2");
                return st;
            }
        };

        // CPU finta COMPATIBILE: Socket AM5
        mockCpuCompatibile = new Componente(300.0, "AMD", "Ryzen 7", TipoComponente.CPU, 105) {
            @Override
            public EnumMap<AspettiTecnici, String> getScheda_tecnica() {
                EnumMap<AspettiTecnici, String> st = new EnumMap<>(AspettiTecnici.class);
                st.put(AspettiTecnici.SOCKET_CPU, "AM5");
                return st;
            }
        };

        // CPU finta INCOMPATIBILE: Socket LGA1700
        mockCpuIncompatibile = new Componente(300.0, "Intel", "Core i5", TipoComponente.CPU, 65) {
            @Override
            public EnumMap<AspettiTecnici, String> getScheda_tecnica() {
                EnumMap<AspettiTecnici, String> st = new EnumMap<>(AspettiTecnici.class);
                st.put(AspettiTecnici.SOCKET_CPU, "LGA1700");
                return st;
            }
        };
    }

    @Test
    public void testCostruttoreInizializzaMappa() {
        assertNotNull(buildTest.getComponenti(), "La mappa dei componenti non deve essere null");
        assertEquals(0, buildTest.getNumeroTotaleComponenti(), "Una build nuova deve avere 0 componenti");
        
        // Verifica che tutte le chiavi dell'enum siano state inizializzate con liste vuote
        for (TipoComponente tipo : TipoComponente.values()) {
            assertNotNull(buildTest.getComponenti().get(tipo), "La lista per " + tipo + " non deve essere null");
        }
    }

    @Test
    public void testAggiuntaPrimoComponenteNonMoboLanciaEccezione() {
        // Tento di aggiungere una CPU prima della MOBO
        ComponentiException eccezione = assertThrows(ComponentiException.class, () -> {
            buildTest.aggiungiComponente(mockCpuCompatibile);
        });

        assertEquals(TipoErrore.SCHEDA_MADRE_MANCANTE, eccezione.getTipo_errore());
    }

    @Test
    public void testAggiuntaMoboSuccesso() {
        try {
            boolean risultato = buildTest.aggiungiComponente(mockMobo);
            assertTrue(risultato, "L'aggiunta della MOBO dovrebbe restituire true");
            assertEquals(1, buildTest.getComponenti().get(TipoComponente.MOBO).size(), "Dovrebbe esserci 1 MOBO nella lista");
            assertEquals(150.0, buildTest.getPrezzo(), "Il prezzo totale dovrebbe essersi aggiornato");
        } catch (ComponentiException e) {
            fail("Non doveva lanciare eccezione per la prima MOBO");
        }
    }

    @Test
    public void testAggiuntaDoppiaMoboLanciaEccezione() {
        try {
            buildTest.aggiungiComponente(mockMobo); // Aggiungo la prima (dovrebbe andare)
        } catch (ComponentiException e) {
            fail("La prima aggiunta non doveva fallire");
        }

        // Tento di aggiungere la seconda
        ComponentiException eccezione = assertThrows(ComponentiException.class, () -> {
            buildTest.aggiungiComponente(mockMobo);
        });

        assertEquals(TipoErrore.SCHEDE_MADRI_MULTIPLE, eccezione.getTipo_errore());
    }

    @Test
    public void testAggiuntaCpuCompatibileSuccesso() {
        try {
            buildTest.aggiungiComponente(mockMobo);
            boolean risultato = buildTest.aggiungiComponente(mockCpuCompatibile);
            
            assertTrue(risultato, "L'aggiunta di una CPU compatibile dovrebbe restituire true");
            assertEquals(2, buildTest.getNumeroTotaleComponenti(), "La build dovrebbe avere 2 pezzi (Mobo + Cpu)");
            assertEquals(450.0, buildTest.getPrezzo(), "Il prezzo totale dovrebbe essere la somma di Mobo e Cpu");
        } catch (ComponentiException e) {
            fail("Non doveva lanciare eccezione per una CPU compatibile: " + e.getMessage());
        }
    }

    @Test
    public void testAggiuntaCpuIncompatibileLanciaEccezione() {
        try {
            buildTest.aggiungiComponente(mockMobo);
        } catch (ComponentiException e) {
            fail("L'aggiunta della Mobo non doveva fallire");
        }

        ComponentiException eccezione = assertThrows(ComponentiException.class, () -> {
            buildTest.aggiungiComponente(mockCpuIncompatibile);
        });

        assertEquals(TipoErrore.SOCKET_CPU_INCOMPATIBILE, eccezione.getTipo_errore());
    }

    @Test
    public void testRimuoviMoboFallisceSeCiSonoAltriComponenti() {
        try {
            buildTest.aggiungiComponente(mockMobo);
            buildTest.aggiungiComponente(mockCpuCompatibile);
        } catch (ComponentiException e) {
            fail("Impostazione iniziale fallita");
        }

        // Tento di rimuovere la Mobo (non dovrebbe lasciarmelo fare perché c'è la CPU attaccata)
        boolean risultato = buildTest.rimuoviComponente(mockMobo);
        
        assertFalse(risultato, "Non dovrebbe essere possibile rimuovere la MOBO se ci sono altri componenti");
        assertEquals(1, buildTest.getComponenti().get(TipoComponente.MOBO).size(), "La MOBO dovrebbe essere ancora lì");
    }
}