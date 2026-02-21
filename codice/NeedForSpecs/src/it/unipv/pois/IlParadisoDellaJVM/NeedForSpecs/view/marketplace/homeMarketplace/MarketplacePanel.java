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

public class MarketplacePanel extends JPanel {
    
    // Bottoni Navigazione
    private JButton btnLogin, btnForum, btnCarrello;
    // Ricerca
    private JTextField txtCerca;
    private JButton btnCerca;
    // Area Centrale
    private JList<String> listComponenti;
    private JButton btnMostraInfo, btnAggiungiCarrello;
    private JTextArea txtAreaInfo;
    // Area Inferiore
    private JButton btnAggiungiAnnuncio, btnVaiConfiguratore;
    private JLabel lblMessaggio;

    public MarketplacePanel() {
        setLayout(new BorderLayout(10, 10)); // Margini di 10px

        // --- NORD: Navigazione e Ricerca ---
        JPanel pnlNord = new JPanel(new GridLayout(2, 1, 5, 5));
        
        JPanel pnlBottoniTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btnLogin = new JButton("LOGIN");
        btnForum = new JButton("FORUM");
        btnCarrello = new JButton("CARRELLO");
        pnlBottoniTop.add(btnLogin); pnlBottoniTop.add(btnForum); pnlBottoniTop.add(btnCarrello);
        
        JPanel pnlRicerca = new JPanel(new FlowLayout());
        txtCerca = new JTextField(30);
        btnCerca = new JButton("CERCA");
        pnlRicerca.add(txtCerca); pnlRicerca.add(btnCerca);
        
        pnlNord.add(pnlBottoniTop);
        pnlNord.add(pnlRicerca);
        add(pnlNord, BorderLayout.NORTH);

        // --- CENTRO: Lista, Azioni e Info ---
        JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
        
        listComponenti = new JList<>(); // Il controller riempirà questa lista col DefaultListModel
        JScrollPane scrollLista = new JScrollPane(listComponenti);
        scrollLista.setPreferredSize(new Dimension(250, 0));
        pnlCentro.add(scrollLista, BorderLayout.WEST);
        
        JPanel pnlInfo = new JPanel(new BorderLayout(5, 5));
        JPanel pnlBottoniLista = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnMostraInfo = new JButton("MOSTRA INFO");
        btnAggiungiCarrello = new JButton("AGGIUNGI AL CARRELLO");
        pnlBottoniLista.add(btnMostraInfo); pnlBottoniLista.add(btnAggiungiCarrello);
        
        txtAreaInfo = new JTextArea();
        txtAreaInfo.setEditable(false);
        pnlInfo.add(pnlBottoniLista, BorderLayout.NORTH);
        pnlInfo.add(new JScrollPane(txtAreaInfo), BorderLayout.CENTER);
        
        pnlCentro.add(pnlInfo, BorderLayout.CENTER);
        add(pnlCentro, BorderLayout.CENTER);

        // --- SUD: Bottoni inferiori e Messaggi ---
        JPanel pnlSud = new JPanel(new BorderLayout());
        
        JPanel pnlBottoniSud = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAggiungiAnnuncio = new JButton("AGGIUNGI ANNUNCIO");
        btnVaiConfiguratore = new JButton("VAI AL CONFIGURATORE");
        pnlBottoniSud.add(btnAggiungiAnnuncio); pnlBottoniSud.add(btnVaiConfiguratore);
        
        lblMessaggio = new JLabel(" ", SwingConstants.CENTER); // Spazio vuoto iniziale
        lblMessaggio.setForeground(Color.RED);
        
        pnlSud.add(pnlBottoniSud, BorderLayout.NORTH);
        pnlSud.add(lblMessaggio, BorderLayout.SOUTH);
        add(pnlSud, BorderLayout.SOUTH);
    }

    // --- GETTER ---
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnForum() { return btnForum; }
    public JButton getBtnCarrello() { return btnCarrello; }
    public JTextField getTxtCerca() { return txtCerca; }
    public JButton getBtnCerca() { return btnCerca; }
    public JList<String> getListComponenti() { return listComponenti; }
    public JButton getBtnMostraInfo() { return btnMostraInfo; }
    public JButton getBtnAggiungiCarrello() { return btnAggiungiCarrello; }
    public JTextArea getTxtAreaInfo() { return txtAreaInfo; }
    public JButton getBtnAggiungiAnnuncio() { return btnAggiungiAnnuncio; }
    public JButton getBtnVaiConfiguratore() { return btnVaiConfiguratore; }
    public JLabel getLblMessaggio() { return lblMessaggio; }
}