package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.Ordine;

public class BlackFridaySaleStrategy implements ISaleStrategy {
	private final static double SCONTO = 0.3;
	@Override
	public double getTotaleElaborato(Ordine ordine_da_elaborare) {
		return ordine_da_elaborare.getPrezzo_totale()-ordine_da_elaborare.getPrezzo_totale()*SCONTO;
	}

}
