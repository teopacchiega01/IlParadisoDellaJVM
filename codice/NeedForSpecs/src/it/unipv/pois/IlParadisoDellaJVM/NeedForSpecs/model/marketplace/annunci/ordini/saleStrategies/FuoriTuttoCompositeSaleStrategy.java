package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies;

public class FuoriTuttoCompositeSaleStrategy extends CompositeSaleStrategy {
	
	
	public FuoriTuttoCompositeSaleStrategy() {
		super();
		super.addStrategy(new BlackFridaySaleStrategy());
		super.addStrategy(new NoIvaSaleStrategy());
	}

}
