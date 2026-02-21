package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.ordini.saleStrategies;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class SaleFactory {
	private ISaleStrategy sale_strategy;
	private final String PROPERTYNAME="sale.strategy.class.name";
	
	
	public ISaleStrategy getDiscountStrategy() {
		
		if(sale_strategy==null) {

			Properties p = new Properties(System.getProperties());
			String DiscountClassName;
			
			try {
				p.load(new FileInputStream("properties/properties"));
				DiscountClassName=p.getProperty(PROPERTYNAME);
				
				Constructor c = Class.forName(DiscountClassName).getConstructor();
				sale_strategy=(ISaleStrategy)c.newInstance();
				
			} catch (Exception e) {
				e.printStackTrace();
				sale_strategy=null;
			}
			
		}
		
		return sale_strategy;
	}
	
	
	
	
}
