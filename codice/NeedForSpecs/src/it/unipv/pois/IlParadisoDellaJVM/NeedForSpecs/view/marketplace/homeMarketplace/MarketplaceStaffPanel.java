package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace;

import java.awt.Color;

import javax.swing.JButton;

/**
 * @author teopacchiega
 */

public class MarketplaceStaffPanel extends MarketplaceBasePanel {

    public MarketplaceStaffPanel() {
        super();
        btnLoginLogout.setText("LOGOUT");

       
        btnRimuoviAnnuncio = new JButton("RIMUOVI ANNUNCIO");
        btnRimuoviAnnuncio.setBackground(Color.RED);
        btnRimuoviAnnuncio.setOpaque(true);
        btnRimuoviAnnuncio.setBorderPainted(false);
        btnRimuoviAnnuncio.setForeground(Color.BLACK);
        pnlInfoActions.add(btnRimuoviAnnuncio);
    }

    public JButton getBtnRimuoviAnnuncio() { return btnRimuoviAnnuncio; }
}
