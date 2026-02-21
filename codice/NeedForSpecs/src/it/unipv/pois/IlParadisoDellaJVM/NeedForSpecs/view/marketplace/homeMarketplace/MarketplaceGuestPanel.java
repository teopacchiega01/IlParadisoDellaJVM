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

public class MarketplaceGuestPanel extends JPanel {
    
    private JButton btnLogin, btnForum;
    private JTextField txtCerca;
    private JButton btnCerca;
    private JList<String> listComponenti;
    private JButton btnMostraInfo;
    private JTextArea txtAreaInfo;
    private JButton btnVaiConfiguratore;
    private JLabel lblMessaggio;

    public MarketplaceGuestPanel() {
        setLayout(new BorderLayout(10, 10));

        // --- NORD ---
        JPanel pnlNord = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel pnlBottoniTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btnLogin = new JButton("LOGIN");
        btnForum = new JButton("FORUM");
        pnlBottoniTop.add(btnLogin); pnlBottoniTop.add(btnForum);
        
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
        pnlBottoniLista.add(btnMostraInfo);
        
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
        pnlBottoniSud.add(btnVaiConfiguratore);
        
        lblMessaggio = new JLabel("Effettua il login per acquistare o vendere prodotti.", SwingConstants.CENTER);
        lblMessaggio.setForeground(Color.RED);
        
        pnlSud.add(pnlBottoniSud, BorderLayout.NORTH);
        pnlSud.add(lblMessaggio, BorderLayout.SOUTH);
        add(pnlSud, BorderLayout.SOUTH);
    }

    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnForum() { return btnForum; }
    public JTextField getTxtCerca() { return txtCerca; }
    public JButton getBtnCerca() { return btnCerca; }
    public JList<String> getListComponenti() { return listComponenti; }
    public JButton getBtnMostraInfo() { return btnMostraInfo; }
    public JTextArea getTxtAreaInfo() { return txtAreaInfo; }
    public JButton getBtnVaiConfiguratore() { return btnVaiConfiguratore; }
    public JLabel getLblMessaggio() { return lblMessaggio; }
}