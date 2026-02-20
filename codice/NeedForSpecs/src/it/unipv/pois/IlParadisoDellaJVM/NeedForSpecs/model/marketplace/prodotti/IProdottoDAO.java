package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti;

import java.util.ArrayList;

//	@Author teopacchiega

public interface IProdottoDAO {
	public Prodotto getProdottoFromId(String id_prodotto);
	public ArrayList<Prodotto> getProdotti();
	public boolean inserisciProdotto(Prodotto prodotto_da_inserire);
}
