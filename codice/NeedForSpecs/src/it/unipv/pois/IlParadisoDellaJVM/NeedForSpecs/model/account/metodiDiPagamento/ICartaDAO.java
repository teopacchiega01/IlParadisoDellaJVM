package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.metodiDiPagamento;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
/**
 * @author teopacchiega
 */

public interface ICartaDAO {
	public Carta getCarta(UtenteGenerico utente);
	public boolean inserisciCarta(Carta carta_da_inserire);
	public boolean aggiornaCartaUtente(String username, Carta nuovaCarta);
}
