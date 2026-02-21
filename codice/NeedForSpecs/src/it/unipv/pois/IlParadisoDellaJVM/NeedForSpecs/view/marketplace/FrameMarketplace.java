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


public class FrameMarketplace extends JFrame {

    private CardLayout cardLayout;
    private JPanel pannelloPrincipale;

    // --- I 7 PANNELLI DEL PROGETTO ---
    
    // Marketplace
    private MarketplaceGuestPanel mkGuestPanel;
    private MarketplaceUserPanel mkUserPanel;
    private MarketplaceStaffPanel mkStaffPanel;
    
    // Configuratore
    private ConfiguratoreGuestPanel confGuestPanel;
    private ConfiguratoreUserPanel confUserPanel;
    
    // Altri
    private AggiuntaAnnuncioPanel aggiuntaAnnuncioPanel;
    private GestioneCarrelloPanel carrelloPanel;

    public FrameMarketplace() {
        super("Il Paradiso della JVM - Hardware & Build");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra nello schermo

        // Inizializzo il CardLayout
        cardLayout = new CardLayout();
        pannelloPrincipale = new JPanel(cardLayout);

        // 1. Inizializzo le istanze di tutti i pannelli
        mkGuestPanel = new MarketplaceGuestPanel();
        mkUserPanel = new MarketplaceUserPanel();
        mkStaffPanel = new MarketplaceStaffPanel();
        
        confGuestPanel = new ConfiguratoreGuestPanel();
        confUserPanel = new ConfiguratoreUserPanel();
        
        aggiuntaAnnuncioPanel = new AggiuntaAnnuncioPanel();
        carrelloPanel = new GestioneCarrelloPanel();

        // 2. Aggiungo i pannelli al "mazzo di carte" con i loro ID univoci
        pannelloPrincipale.add(mkGuestPanel, "MK_GUEST");
        pannelloPrincipale.add(mkUserPanel, "MK_USER");
        pannelloPrincipale.add(mkStaffPanel, "MK_STAFF");
        
        pannelloPrincipale.add(confGuestPanel, "CONF_GUEST");
        pannelloPrincipale.add(confUserPanel, "CONF_USER");
        
        pannelloPrincipale.add(aggiuntaAnnuncioPanel, "AGGIUNTA_ANNUNCIO");
        pannelloPrincipale.add(carrelloPanel, "CARRELLO");

        add(pannelloPrincipale);
    }

    // --- METODO JOLLY PER CAMBIARE SCHERMATA (Consigliato per il Controller) ---
    public void cambiaSchermata(String nomeSchermata) {
        cardLayout.show(pannelloPrincipale, nomeSchermata);
    }

    // --- METODI SPECIFICI PER CAMBIARE SCHERMATA (Opzionali) ---
    public void mostraMarketplaceGuest() { cardLayout.show(pannelloPrincipale, "MK_GUEST"); }
    public void mostraMarketplaceUser() { cardLayout.show(pannelloPrincipale, "MK_USER"); }
    public void mostraMarketplaceStaff() { cardLayout.show(pannelloPrincipale, "MK_STAFF"); }
    
    public void mostraConfiguratoreGuest() { cardLayout.show(pannelloPrincipale, "CONF_GUEST"); }
    public void mostraConfiguratoreUser() { cardLayout.show(pannelloPrincipale, "CONF_USER"); }
    
    public void mostraAggiuntaAnnuncio() { cardLayout.show(pannelloPrincipale, "AGGIUNTA_ANNUNCIO"); }
    public void mostraCarrello() { cardLayout.show(pannelloPrincipale, "CARRELLO"); }

    // --- GETTER PER I PANNELLI (Fondamentali per i Controller) ---
    public MarketplaceGuestPanel getMkGuestPanel() { return mkGuestPanel; }
    public MarketplaceUserPanel getMkUserPanel() { return mkUserPanel; }
    public MarketplaceStaffPanel getMkStaffPanel() { return mkStaffPanel; }
    
    public ConfiguratoreGuestPanel getConfGuestPanel() { return confGuestPanel; }
    public ConfiguratoreUserPanel getConfUserPanel() { return confUserPanel; }
    
    public AggiuntaAnnuncioPanel getAggiuntaAnnuncioPanel() { return aggiuntaAnnuncioPanel; }
    public GestioneCarrelloPanel getCarrelloPanel() { return carrelloPanel; }
}