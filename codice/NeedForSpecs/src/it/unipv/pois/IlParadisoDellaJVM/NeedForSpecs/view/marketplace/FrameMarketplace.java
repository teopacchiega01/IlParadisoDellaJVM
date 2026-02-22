package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace; 

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.carrello.GestioneCarrelloPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.configuratore.ConfiguratoreGuestPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.configuratore.ConfiguratoreUserPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.gestioneAnnunci.AggiuntaAnnuncioPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace.MarketplaceGuestPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace.MarketplaceStaffPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace.MarketplaceUserPanel;

// @Author teopacchiega

public class FrameMarketplace extends JFrame {

    private CardLayout cardLayout;
    private JPanel mkMainPanel;
    
    private MarketplaceGuestPanel mkGuestPanel;
    private MarketplaceUserPanel mkUserPanel;
    private MarketplaceStaffPanel mkStaffPanel;
    private ConfiguratoreGuestPanel confGuestPanel;
    private ConfiguratoreUserPanel confUserPanel;
    private AggiuntaAnnuncioPanel aggiuntaAnnuncioPanel;
    private GestioneCarrelloPanel carrelloPanel;

    public FrameMarketplace() {
        super("Il Paradiso della JVM - Hardware & Build");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mkMainPanel = new JPanel(cardLayout);

        mkGuestPanel = new MarketplaceGuestPanel();
        mkUserPanel = new MarketplaceUserPanel();
        mkStaffPanel = new MarketplaceStaffPanel();
        confGuestPanel = new ConfiguratoreGuestPanel();
        confUserPanel = new ConfiguratoreUserPanel();
        aggiuntaAnnuncioPanel = new AggiuntaAnnuncioPanel();
        carrelloPanel = new GestioneCarrelloPanel();

        mkMainPanel.add(mkGuestPanel, PannelliMarketplace.MK_GUEST.name());
        mkMainPanel.add(mkUserPanel, PannelliMarketplace.MK_USER.name());
        mkMainPanel.add(mkStaffPanel, PannelliMarketplace.MK_STAFF.name());
        mkMainPanel.add(confGuestPanel, PannelliMarketplace.CONF_GUEST.name());
        mkMainPanel.add(confUserPanel, PannelliMarketplace.CONF_USER.name());
        mkMainPanel.add(aggiuntaAnnuncioPanel, PannelliMarketplace.AGGIUNTA_ANNUNCIO.name());
        mkMainPanel.add(carrelloPanel, PannelliMarketplace.CARRELLO.name());

        add(mkMainPanel);
    }

    // --- METODI PER CAMBIARE SCHERMATA ---
    public void mostraMarketplaceGuest() { cardLayout.show(mkMainPanel, PannelliMarketplace.MK_GUEST.name()); }
    public void mostraMarketplaceUser() { cardLayout.show(mkMainPanel, PannelliMarketplace.MK_USER.name()); }
    public void mostraMarketplaceStaff() { cardLayout.show(mkMainPanel, PannelliMarketplace.MK_STAFF.name()); }
    public void mostraConfiguratoreGuest() { cardLayout.show(mkMainPanel, PannelliMarketplace.CONF_GUEST.name()); }
    public void mostraConfiguratoreUser() { cardLayout.show(mkMainPanel, PannelliMarketplace.CONF_USER.name()); }
    public void mostraAggiuntaAnnuncio() { cardLayout.show(mkMainPanel, PannelliMarketplace.AGGIUNTA_ANNUNCIO.name()); }
    public void mostraCarrello() { cardLayout.show(mkMainPanel, PannelliMarketplace.CARRELLO.name()); }
    public void cambiaSchermata(PannelliMarketplace schermata) { cardLayout.show(mkMainPanel, schermata.name()); }

    // --- GETTER ---
    public MarketplaceGuestPanel getMkGuestPanel() { return mkGuestPanel; }
    public MarketplaceUserPanel getMkUserPanel() { return mkUserPanel; }
    public MarketplaceStaffPanel getMkStaffPanel() { return mkStaffPanel; }
    public ConfiguratoreGuestPanel getConfGuestPanel() { return confGuestPanel; }
    public ConfiguratoreUserPanel getConfUserPanel() { return confUserPanel; }
    public AggiuntaAnnuncioPanel getAggiuntaAnnuncioPanel() { return aggiuntaAnnuncioPanel; }
    public GestioneCarrelloPanel getCarrelloPanel() { return carrelloPanel; }
}