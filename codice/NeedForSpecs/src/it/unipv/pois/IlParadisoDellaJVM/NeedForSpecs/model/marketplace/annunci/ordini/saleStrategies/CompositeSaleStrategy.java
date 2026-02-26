package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.Ordine;

/**
* @author teopacchiega
*/

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
			double prezzo_scontato = sale_strat.getTotaleElaborato(ordine_da_elaborare); 
			if(prezzo_scontato<prezzo_finale) {
				prezzo_finale = prezzo_scontato;
			}
		}
		return prezzo_finale;
	}

}
