package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento;

import java.security.SecureRandom;
import java.time.LocalDate;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.GeneratoreId;

public class Carta {
	private final static int DIM_N_CARTA = 20;
	private String numeroCarta;
    private LocalDate dataScadenza;
    private String cvv;

    // Costruttore vuoto
    public Carta() {
    }

    // Costruttore con parametri (il numero_carta viene generato in automatico)
    public Carta(LocalDate dataScadenza, String cvv) {
        this.numeroCarta = GeneratoreId.generaId(DIM_N_CARTA);
        this.dataScadenza = dataScadenza;
        this.cvv = cvv;
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

}
