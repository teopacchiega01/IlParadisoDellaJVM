package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MarketplaceStaffPanel extends JPanel {
    
    private JButton btnLogout, btnForum, btnCarrello;
    private JTextField txtCerca;
    private JButton btnCerca;
    private JList<String> listComponenti;
    private JButton btnMostraInfo, btnAggiungiCarrello, btnRimuoviAnnuncio;
    private JTextArea txtAreaInfo;
    private JButton btnVaiConfiguratore;
    private JLabel lblMessaggio;

    public MarketplaceStaffPanel() {
        setLayout(new BorderLayout(10, 10));

        // --- NORD ---
        JPanel pnlNord = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel pnlBottoniTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btnLogout = new JButton("LOGOUT");
        btnForum = new JButton("FORUM");
        btnCarrello = new JButton("CARRELLO");
        pnlBottoniTop.add(btnLogout); pnlBottoniTop.add(btnForum); pnlBottoniTop.add(btnCarrello);
        
        JPanel pnlRicerca = new JPanel(new FlowLayout());
        txtCerca = new JTextField(30);
        btnCerca = new JButton("CERCA");
        pnlRicerca.add(txtCerca); pnlRicerca.add(btnCerca);
        
        pnlNord.add(pnlBottoniTop); pnlNord.add(pnlRicerca);
        add(pnlNord, BorderLayout.NORTH);

        // --- CENTRO ---
        JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
        listComponenti = new JList<>();
        JScrollPane scrollLista = new JScrollPane(listComponenti);
        scrollLista.setPreferredSize(new Dimension(250, 0));
        pnlCentro.add(scrollLista, BorderLayout.WEST);
        
        JPanel pnlInfo = new JPanel(new BorderLayout(5, 5));
        JPanel pnlBottoniLista = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnMostraInfo = new JButton("MOSTRA INFO");
        btnAggiungiCarrello = new JButton("AGGIUNGI AL CARRELLO");
        
        // IL BOTTONE ESCLUSIVO DELLO STAFF
        btnRimuoviAnnuncio = new JButton("RIMUOVI ANNUNCIO");
        btnRimuoviAnnuncio.setBackground(Color.RED);
        btnRimuoviAnnuncio.setForeground(Color.WHITE);
        
        pnlBottoniLista.add(btnMostraInfo); 
        pnlBottoniLista.add(btnAggiungiCarrello);
        pnlBottoniLista.add(btnRimuoviAnnuncio);
        
        txtAreaInfo = new JTextArea();
        txtAreaInfo.setEditable(false);
        pnlInfo.add(pnlBottoniLista, BorderLayout.NORTH);
        pnlInfo.add(new JScrollPane(txtAreaInfo), BorderLayout.CENTER);
        
        pnlCentro.add(pnlInfo, BorderLayout.CENTER);
        add(pnlCentro, BorderLayout.CENTER);

        // --- SUD ---
        JPanel pnlSud = new JPanel(new BorderLayout());
        JPanel pnlBottoniSud = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnVaiConfiguratore = new JButton("VAI AL CONFIGURATORE");
        pnlBottoniSud.add(btnVaiConfiguratore); // Niente "Aggiungi Annuncio" per lo staff
        
        lblMessaggio = new JLabel("Pannello di Amministrazione Attivo.", SwingConstants.CENTER);
        lblMessaggio.setForeground(Color.RED);
        
        pnlSud.add(pnlBottoniSud, BorderLayout.NORTH);
        pnlSud.add(lblMessaggio, BorderLayout.SOUTH);
        add(pnlSud, BorderLayout.SOUTH);
    }

    public JButton getBtnLogout() { return btnLogout; }
    public JButton getBtnForum() { return btnForum; }
    public JButton getBtnCarrello() { return btnCarrello; }
    public JTextField getTxtCerca() { return txtCerca; }
    public JButton getBtnCerca() { return btnCerca; }
    public JList<String> getListComponenti() { return listComponenti; }
    public JButton getBtnMostraInfo() { return btnMostraInfo; }
    public JButton getBtnAggiungiCarrello() { return btnAggiungiCarrello; }
    public JButton getBtnRimuoviAnnuncio() { return btnRimuoviAnnuncio; }
    public JTextArea getTxtAreaInfo() { return txtAreaInfo; }
    public JButton getBtnVaiConfiguratore() { return btnVaiConfiguratore; }
    public JLabel getLblMessaggio() { return lblMessaggio; }
}