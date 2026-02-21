package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.Ordine;

public abstract class CompositeSaleStrategy implements ISaleStrategy {
	private ArrayList<ISaleStrategy> sale_strats;
	
	protected CompositeSaleStrategy() {
		super();
		this.sale_strats = new ArrayList<ISaleStrategy>();
	}
	
	protected void addStrategy(ISaleStrategy s) {
		sale_strats.add(s);
	}
	
	protected ArrayList<ISaleStrategy> getStrategies(){
		return sale_strats;
	}

	public double getTotaleElaborato(Ordine ordine_da_elaborare) {
		double prezzo_finale = ordine_da_elaborare.getPrezzo_totale();
		for(ISaleStrategy sale_strat : sale_strats) {
			if(prezzo_finale<sale_strat.getTotaleElaborato(ordine_da_elaborare)) {
				prezzo_finale = sale_strat.getTotaleElaborato(ordine_da_elaborare);
			}
		}
		return prezzo_finale;
	}

}
