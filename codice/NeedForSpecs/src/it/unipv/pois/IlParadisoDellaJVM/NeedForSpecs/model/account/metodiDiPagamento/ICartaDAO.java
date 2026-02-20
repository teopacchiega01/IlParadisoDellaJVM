package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;

public interface ICartaDAO {
	public Carta getCarta(UtenteGenerico utente);
	public boolean inserisciCarta(Carta carta_da_inserire);
}
