package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.Ordine;

/**
* @author teopacchiega
*/

public interface ISaleStrategy {
	public double getTotaleElaborato(Ordine ordine_da_elaborare);
}
