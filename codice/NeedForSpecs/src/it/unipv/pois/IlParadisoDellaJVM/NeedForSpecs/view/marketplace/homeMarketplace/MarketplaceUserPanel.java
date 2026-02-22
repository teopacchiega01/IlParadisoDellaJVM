package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace;

import javax.swing.JButton;

public class MarketplaceUserPanel extends MarketplaceBasePanel {

    public MarketplaceUserPanel() {
        super();
        btnLoginLogout.setText("LOGOUT");

        // 1. Inietto il Carrello in alto a destra
        btnCarrello = new JButton("CARRELLO");
        pnlTopRight.add(btnCarrello);

        // 2. Inietto Aggiungi al Carrello di fianco a Mostra Info
        btnAggiungiCarrello = new JButton("AGGIUNGI AL CARRELLO");
        pnlInfoActions.add(btnAggiungiCarrello);

        // 3. Inietto Aggiungi Annuncio in basso a sinistra
        btnAggiungiAnnuncio = new JButton("AGGIUNGI ANNUNCIO");
        pnlBottomLeft.add(btnAggiungiAnnuncio);
    }

    // Getter specifici per l'User
    public JButton getBtnCarrello() { return btnCarrello; }
    public JButton getBtnAggiungiCarrello() { return btnAggiungiCarrello; }
    public JButton getBtnAggiungiAnnuncio() { return btnAggiungiAnnuncio; }
}
