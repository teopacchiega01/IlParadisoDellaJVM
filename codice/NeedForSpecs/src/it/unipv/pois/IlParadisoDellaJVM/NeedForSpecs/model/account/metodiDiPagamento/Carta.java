package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento;

import java.security.SecureRandom;
import java.time.LocalDate;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.GeneratoreId;


/**
 * @author teopacchiega
 */
public class Carta {
	private final static int DIM_N_CARTA = 20;
	private String numero_carta;
    private LocalDate data_scadenza;
    private String cvv;


    public Carta() {
    }


    public Carta(LocalDate data_scadenza, String cvv) {
        this.numero_carta = GeneratoreId.generaId(DIM_N_CARTA);
        this.data_scadenza = data_scadenza;
        this.cvv = cvv;
    }

    
   
	public Carta(String numeroCarta, LocalDate data_scadenza, String cvv) {
		super();
		this.numero_carta = numeroCarta;
		this.data_scadenza = data_scadenza;
		this.cvv = cvv;
	}

	public String getNumeroCarta() {
        return numero_carta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numero_carta = numeroCarta;
    }

    public LocalDate getDataScadenza() {
        return data_scadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.data_scadenza = dataScadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

}
