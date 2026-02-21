package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.Ordine;

public interface ISaleStrategy {
	double getTotaleElaborato(Ordine ordine_da_elaborare);
}
