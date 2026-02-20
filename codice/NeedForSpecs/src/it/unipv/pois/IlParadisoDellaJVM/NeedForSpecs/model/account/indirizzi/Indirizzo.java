package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.indirizzi;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.GeneratoreId;

public class Indirizzo {
	private static final int DIM_ID_INDIRIZZO = 20;
	private String idIndirizzo;
    private String via;
    private String civico;
    private String cap;
    private String provincia;
    private String citta;

    // Costruttore vuoto
    public Indirizzo() {
    }

    // Costruttore con parametri (l'ID viene generato in automatico)
    public Indirizzo(String via, String civico, String cap, String provincia, String citta) {
        this.idIndirizzo = GeneratoreId.generaId(DIM_ID_INDIRIZZO);
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.provincia = provincia;
        this.citta = citta;
    }


    public String getIdIndirizzo() {
        return idIndirizzo;
    }

    public void setIdIndirizzo(String idIndirizzo) {
        this.idIndirizzo = idIndirizzo;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

}
