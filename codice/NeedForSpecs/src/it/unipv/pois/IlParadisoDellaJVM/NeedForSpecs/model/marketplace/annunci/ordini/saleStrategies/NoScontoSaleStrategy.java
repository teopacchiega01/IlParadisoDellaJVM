package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.Ordine;

public class NoScontoSaleStrategy implements ISaleStrategy {
	
	@Override
	public double getTotaleElaborato(Ordine ordine_da_elaborare) {
		return ordine_da_elaborare.getPrezzo_totale();
	}

}
