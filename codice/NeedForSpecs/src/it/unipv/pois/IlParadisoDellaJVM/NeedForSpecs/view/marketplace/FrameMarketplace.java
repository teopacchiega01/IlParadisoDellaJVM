package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace; 

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.LoginPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.carrello.GestioneCarrelloPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.configuratore.ConfiguratoreGuestPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.configuratore.ConfiguratoreUserPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.gestioneAnnunci.AggiuntaAnnuncioPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace.MarketplaceGuestPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace.MarketplaceStaffPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace.MarketplaceUserPanel;

/**
 * @author teopacchiega
 */

public class FrameMarketplace extends JFrame {

	private CardLayout card_layout;
	private JPanel mk_main_panel;

	private MarketplaceGuestPanel mk_guest_panel;
	private MarketplaceUserPanel mk_user_panel;
	private MarketplaceStaffPanel mk_staff_panel;
	private ConfiguratoreGuestPanel conf_guest_panel;
	private ConfiguratoreUserPanel conf_user_panel;
	private AggiuntaAnnuncioPanel aggiunta_annuncio_panel;
	private GestioneCarrelloPanel carrello_panel;
	private LoginPanel login_panel;

	public FrameMarketplace() {
		super("Il Paradiso della JVM - Hardware & Build");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		card_layout = new CardLayout();
		mk_main_panel = new JPanel(card_layout);

		mk_guest_panel = new MarketplaceGuestPanel();
		mk_user_panel = new MarketplaceUserPanel();
		mk_staff_panel = new MarketplaceStaffPanel();
		conf_guest_panel = new ConfiguratoreGuestPanel();
		conf_user_panel = new ConfiguratoreUserPanel();
		aggiunta_annuncio_panel = new AggiuntaAnnuncioPanel();
		carrello_panel = new GestioneCarrelloPanel();
		login_panel = new LoginPanel();

		mk_main_panel.add(mk_guest_panel, PannelliMarketplace.MK_GUEST.name());
		mk_main_panel.add(mk_user_panel, PannelliMarketplace.MK_USER.name());
		mk_main_panel.add(mk_staff_panel, PannelliMarketplace.MK_STAFF.name());
		mk_main_panel.add(conf_guest_panel, PannelliMarketplace.CONF_GUEST.name());
		mk_main_panel.add(conf_user_panel, PannelliMarketplace.CONF_USER.name());
		mk_main_panel.add(aggiunta_annuncio_panel, PannelliMarketplace.AGGIUNTA_ANNUNCIO.name());
		mk_main_panel.add(carrello_panel, PannelliMarketplace.CARRELLO.name());
		mk_main_panel.add(login_panel, PannelliMarketplace.LOGIN.name());

		add(mk_main_panel);
	}

	public void mostraMarketplaceGuest() { 
		card_layout.show(mk_main_panel, PannelliMarketplace.MK_GUEST.name());
		System.out.println("Mostro "+PannelliMarketplace.MK_GUEST.name());
	}
	public void mostraMarketplaceUser() { 
		card_layout.show(mk_main_panel, PannelliMarketplace.MK_USER.name()); 
		System.out.println("Mostro "+PannelliMarketplace.MK_USER.name());
	}
	public void mostraMarketplaceStaff() { 
		card_layout.show(mk_main_panel, PannelliMarketplace.MK_STAFF.name()); 
		System.out.println("Mostro "+PannelliMarketplace.MK_STAFF.name());
	}
	public void mostraConfiguratoreGuest() { 
		card_layout.show(mk_main_panel, PannelliMarketplace.CONF_GUEST.name()); 
		System.out.println("Mostro "+PannelliMarketplace.CONF_GUEST.name());
	}
	public void mostraConfiguratoreUser() { 
		card_layout.show(mk_main_panel, PannelliMarketplace.CONF_USER.name()); 
		System.out.println("Mostro "+PannelliMarketplace.CONF_USER.name());
	}
	public void mostraAggiuntaAnnuncio() { 
		card_layout.show(mk_main_panel, PannelliMarketplace.AGGIUNTA_ANNUNCIO.name());
		System.out.println("Mostro "+PannelliMarketplace.AGGIUNTA_ANNUNCIO.name());
	}
	public void mostraCarrello() { 
		card_layout.show(mk_main_panel, PannelliMarketplace.CARRELLO.name());
		System.out.println("Mostro "+PannelliMarketplace.CARRELLO.name()); 
	}
	public void mostraLogin() { 
		card_layout.show(mk_main_panel, PannelliMarketplace.LOGIN.name());
		System.out.println("Mostro "+PannelliMarketplace.LOGIN.name()); 
	}
	public void cambiaSchermata(PannelliMarketplace schermata) { 
		card_layout.show(mk_main_panel, schermata.name()); 
		System.out.println("Mostro "+schermata.name());
	}

	public MarketplaceGuestPanel getMarketplaceGuestPanel() { return mk_guest_panel; }
	public MarketplaceUserPanel getMarketplaceUserPanel() { return mk_user_panel; }
	public MarketplaceStaffPanel getMarketplaceStaffPanel() { return mk_staff_panel; }
	public ConfiguratoreGuestPanel getConfiguratoreGuestPanel() { return conf_guest_panel; }
	public ConfiguratoreUserPanel getConfiguratoreUserPanel() { return conf_user_panel; }
	public AggiuntaAnnuncioPanel getAggiuntaAnnuncioPanel() { return aggiunta_annuncio_panel; }
	public GestioneCarrelloPanel getCarrelloPanel() { return carrello_panel; }
	public LoginPanel getLoginPanel() { return login_panel; }

	public HomeFrame getHomeFrame() { return new HomeFrame(); }
}