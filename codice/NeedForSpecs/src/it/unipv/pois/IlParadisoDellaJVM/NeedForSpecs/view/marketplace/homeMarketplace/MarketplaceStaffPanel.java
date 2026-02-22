package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace;

import java.awt.Color;

import javax.swing.JButton;

public class MarketplaceStaffPanel extends MarketplaceBasePanel {

    public MarketplaceStaffPanel() {
        super();
        btnLoginLogout.setText("LOGOUT");

        // NO btnCarrello
        // NO btnAggiungiAnnuncio

        // Inietto Rimuovi Annuncio di fianco a Mostra Info (al posto di Aggiungi al Carrello)
        btnRimuoviAnnuncio = new JButton("RIMUOVI ANNUNCIO");
        btnRimuoviAnnuncio.setBackground(Color.RED);
        btnRimuoviAnnuncio.setForeground(Color.WHITE);
        pnlInfoActions.add(btnRimuoviAnnuncio);
    }

    // Getter specifico per lo Staff
    public JButton getBtnRimuoviAnnuncio() { return btnRimuoviAnnuncio; }
}
