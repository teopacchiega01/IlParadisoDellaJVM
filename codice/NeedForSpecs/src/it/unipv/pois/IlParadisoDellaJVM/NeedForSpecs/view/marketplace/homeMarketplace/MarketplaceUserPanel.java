package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace;

import javax.swing.JButton;

/**
 * @author teopacchiega
 */

public class MarketplaceUserPanel extends MarketplaceBasePanel {
	private JButton btnGestioneAnnunci;
	
    public MarketplaceUserPanel() {
        super();
        btnLoginLogout.setText("LOGOUT");

        btnCarrello = new JButton("CARRELLO");
        pnlTopRight.add(btnCarrello);

        btnAggiungiCarrello = new JButton("AGGIUNGI AL CARRELLO");
        pnlInfoActions.add(btnAggiungiCarrello);

        btnAggiungiAnnuncio = new JButton("AGGIUNGI ANNUNCIO");
        pnlBottomLeft.add(btnAggiungiAnnuncio);
        
        btnGestioneAnnunci = new JButton("GESTIONE ANNUNCI");
        pnlBottomLeft.add(btnGestioneAnnunci);
    }

    public JButton getBtnCarrello() { return btnCarrello; }
    public JButton getBtnAggiungiCarrello() { return btnAggiungiCarrello; }
    public JButton getBtnAggiungiAnnuncio() { return btnAggiungiAnnuncio; }
    public JButton getBtnGestioneAnnunci() { return btnGestioneAnnunci; }
}
