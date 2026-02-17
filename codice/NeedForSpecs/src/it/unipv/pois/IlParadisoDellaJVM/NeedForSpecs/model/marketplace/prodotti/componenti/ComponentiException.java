package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti;

//@author teopacchiega

public class ComponentiException extends Exception {
	public enum TipoErrore{
		SCHEDA_MADRE_MANCANTE,
		SCHEDE_MADRI_MULTIPLE,
		SOCKET_CPU_INCOMPATIBILE,
		CPU_MULTIPLE,
		TIPO_RAM_INCOMPATIBILE,
		SLOT_RAM_INSUFFICIENTI,
		SLOT_PCIE_INCOMPATIBILE,
		SLOT_PCIE_INSUFFICIENTI,
		POTENZA_INSUFFICIENTE,
		PSU_MULTIPLI
	}
	
	private final TipoErrore tipo_errore;

	public ComponentiException(String messaggio, TipoErrore tipo_errore) {
		super(messaggio);
		this.tipo_errore = tipo_errore;
	}

	public TipoErrore getTipo_errore() {
		return tipo_errore;
	}
	
	
	
	
}
