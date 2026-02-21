package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies;

public class SuperScontiCompositeSaleStrategy extends CompositeSaleStrategy {

	public SuperScontiCompositeSaleStrategy() {
		super();
		super.addStrategy(new BlackFridaySaleStrategy());
		super.addStrategy(new DieciPercSaleStrategy());
	}

}
