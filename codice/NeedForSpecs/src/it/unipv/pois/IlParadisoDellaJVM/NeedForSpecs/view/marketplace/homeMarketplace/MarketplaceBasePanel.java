package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.homeMarketplace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;

public abstract class MarketplaceBasePanel extends JPanel {

    // Componenti Fissi (Comuni a tutti)
    protected JButton btnLoginLogout, btnTornaHome;
    protected JList<Prodotto> listTuttiProdotti;
//    protected JButton btnCercaAnnunci;
    protected JList<Annuncio> listAnnunciProdottoSelezionato;
//    protected JButton btnMostraInfo;
    protected JTextArea txtAreaInfoAnnuncio;
    protected JButton btnAccediConfiguratore;
    protected JLabel lblMessaggio;

    // Componenti Variabili (Istanzati e gestiti dalle sottoclassi)
    protected JButton btnCarrello;
    protected JButton btnAggiungiCarrello;
    protected JButton btnRimuoviAnnuncio;
    protected JButton btnAggiungiAnnuncio;

    // Pannelli "Contenitore" per l'iniezione dinamica
    protected JPanel pnlTopRight;
    protected JPanel pnlInfoActions;
    protected JPanel pnlBottomLeft;

    public MarketplaceBasePanel() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(15, 15, 15, 15));
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        // Inizializzazione bottoni fissi
        btnLoginLogout = new JButton(); // Il testo lo decide la sottoclasse
        btnTornaHome = new JButton("TORNA ALLA HOME");
        
        listTuttiProdotti = new JList<>();
//        btnCercaAnnunci = new JButton("CERCA ANNUNCI");
        
        listAnnunciProdottoSelezionato = new JList<>();
//        btnMostraInfo = new JButton("MOSTRA INFO");
        
        txtAreaInfoAnnuncio = new JTextArea(8, 30);
        txtAreaInfoAnnuncio.setEditable(false);
        
        btnAccediConfiguratore = new JButton("ACCEDI AL CONFIGURATORE");
        
        lblMessaggio = new JLabel(" ", SwingConstants.CENTER);
        lblMessaggio.setForeground(Color.RED);

        // Inizializzazione dei pannelli "iniettabili"
        pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pnlInfoActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlBottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        // Aggiungo subito il bottone "Mostra Info" al suo pannellino
//        pnlInfoActions.add(btnMostraInfo);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;

        // --- RIGA 1: Header ---
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        
        gbc.gridx = 0; gbc.weightx = 0.3;
        JPanel pnlTopLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlTopLeft.add(btnLoginLogout);
        add(pnlTopLeft, gbc);
        
        gbc.gridx = 1; gbc.weightx = 0.4;
        JPanel pnlTopCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlTopCenter.add(btnTornaHome);
        add(pnlTopCenter, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.3;
        add(pnlTopRight, gbc); // Qui le sottoclassi metteranno il carrello (se previsto)

        // --- RIGA 2: Ricerca Prodotti ---
        gbc.gridy = 1;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.weightx = 0.7; gbc.weighty = 0.1;
        add(new JScrollPane(listTuttiProdotti), gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 1; gbc.weightx = 0.3; gbc.weighty = 0.0;
//        add(btnCercaAnnunci, gbc);

        // --- RIGA 3: Catalogo Annunci e Info ---
        gbc.gridy = 2; gbc.weighty = 0.6;
        
        gbc.gridx = 0; gbc.gridwidth = 1; gbc.weightx = 0.3;
        JPanel pnlListaAnnunci = new JPanel(new BorderLayout());
        pnlListaAnnunci.setBorder(BorderFactory.createTitledBorder("Catalogo annunci"));
        pnlListaAnnunci.add(new JScrollPane(listAnnunciProdottoSelezionato), BorderLayout.CENTER);
        add(pnlListaAnnunci, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 0.7;
        JPanel pnlInfoContainer = new JPanel(new BorderLayout(5, 5));
        pnlInfoContainer.add(pnlInfoActions, BorderLayout.NORTH); // Qui vanno Mostra Info + (Aggiungi Carrello / Rimuovi Annuncio)
        pnlInfoContainer.add(new JScrollPane(txtAreaInfoAnnuncio), BorderLayout.CENTER);
        add(pnlInfoContainer, gbc);

        // --- RIGA 4: Bottoni Inferiori ---
        gbc.gridy = 3; gbc.weighty = 0.0;
        
        gbc.gridx = 0; gbc.gridwidth = 1; gbc.weightx = 0.5;
        add(pnlBottomLeft, gbc); // Qui va l'Aggiungi Annuncio (se previsto)
        
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 0.5;
        JPanel pnlBottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pnlBottomRight.add(btnAccediConfiguratore);
        add(pnlBottomRight, gbc);

        // --- RIGA 5: Messaggi ---
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 3;
        add(lblMessaggio, gbc);
    }

    // Getter Fissi (comuni a tutti)
    public JButton getBtnLoginLogout() { return btnLoginLogout; }
    public JButton getBtnTornaHome() { return btnTornaHome; }
    public JList<Prodotto> getListTuttiProdotti() { return listTuttiProdotti; }
//    public JButton getBtnCercaAnnunci() { return btnCercaAnnunci; }
    public JList<Annuncio> getListAnnunciProdottoSelezionato() { return listAnnunciProdottoSelezionato; }
//    public JButton getBtnMostraInfo() { return btnMostraInfo; }
    public JTextArea getTxtAreaInfoAnnuncio() { return txtAreaInfoAnnuncio; }
    public JButton getBtnAccediConfiguratore() { return btnAccediConfiguratore; }
    public JLabel getLblMessaggio() { return lblMessaggio; }
}