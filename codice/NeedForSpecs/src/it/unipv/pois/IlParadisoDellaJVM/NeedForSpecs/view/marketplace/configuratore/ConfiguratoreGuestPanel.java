package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.configuratore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ConfiguratoreGuestPanel extends JPanel {
    
    // NORD: Navigazione (NO Logout, NO Carrello)
    private JButton btnLogin, btnTornaMarketplace, btnForum;
    private JTextField txtCerca;
    private JButton btnCerca;
    
    // CENTRO: Catalogo e Build Attuale
    private JList<String> listCatalogo, listBuild;
    private JButton btnMostraInfo, btnAggiungiBuild, btnRimuovi, btnSvuotaBuild;
    private JTextArea txtAreaInfo;
    
    // SUD: Nessun campo di salvataggio
    private JLabel lblMessaggio;

    public ConfiguratoreGuestPanel() {
        setLayout(new BorderLayout(10, 10));

        // --- NORD ---
        JPanel pnlNord = new JPanel(new GridLayout(2, 1));
        JPanel pnlBottoniTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        
        btnLogin = new JButton("LOGIN");
        btnTornaMarketplace = new JButton("TORNA AL MARKETPLACE");
        btnForum = new JButton("FORUM");
        
        pnlBottoniTop.add(btnLogin); 
        pnlBottoniTop.add(btnTornaMarketplace); 
        pnlBottoniTop.add(btnForum);
        
        JPanel pnlRicerca = new JPanel(new FlowLayout());
        txtCerca = new JTextField(30);
        btnCerca = new JButton("CERCA COMPONENTE");
        pnlRicerca.add(txtCerca); 
        pnlRicerca.add(btnCerca);
        
        pnlNord.add(pnlBottoniTop); 
        pnlNord.add(pnlRicerca);
        add(pnlNord, BorderLayout.NORTH);

        // --- CENTRO ---
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));

        // Centro-Top: Catalogo
        JPanel pnlCatalogo = new JPanel(new BorderLayout(5, 5));
        pnlCatalogo.setBorder(BorderFactory.createTitledBorder("Catalogo Componenti"));
        listCatalogo = new JList<>();
        pnlCatalogo.add(new JScrollPane(listCatalogo), BorderLayout.WEST);
        
        JPanel pnlInfo = new JPanel(new BorderLayout());
        JPanel pnlAzioniCat = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnMostraInfo = new JButton("MOSTRA INFO");
        btnAggiungiBuild = new JButton("AGGIUNGI ALLA BUILD");
        pnlAzioniCat.add(btnMostraInfo); 
        pnlAzioniCat.add(btnAggiungiBuild);
        
        txtAreaInfo = new JTextArea(5, 20);
        txtAreaInfo.setEditable(false);
        pnlInfo.add(pnlAzioniCat, BorderLayout.NORTH);
        pnlInfo.add(new JScrollPane(txtAreaInfo), BorderLayout.CENTER);
        pnlCatalogo.add(pnlInfo, BorderLayout.CENTER);

        // Centro-Bottom: Lista Build Corrente
        JPanel pnlLaMiaBuild = new JPanel(new BorderLayout(5, 5));
        pnlLaMiaBuild.setBorder(BorderFactory.createTitledBorder("Componenti nella Build (Modalità Prova)"));
        listBuild = new JList<>();
        pnlLaMiaBuild.add(new JScrollPane(listBuild), BorderLayout.CENTER);
        
        JPanel pnlAzioniBuild = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRimuovi = new JButton("RIMUOVI");
        btnSvuotaBuild = new JButton("SVUOTA BUILD");
        pnlAzioniBuild.add(btnRimuovi); 
        pnlAzioniBuild.add(btnSvuotaBuild);
        pnlLaMiaBuild.add(pnlAzioniBuild, BorderLayout.EAST);

        pnlCentro.add(pnlCatalogo);
        pnlCentro.add(Box.createVerticalStrut(15));
        pnlCentro.add(pnlLaMiaBuild);
        add(pnlCentro, BorderLayout.CENTER);

        // --- SUD ---
        // Essendo Guest, non può salvare nulla. Mostriamo solo un messaggio.
        JPanel pnlSud = new JPanel(new BorderLayout());
        lblMessaggio = new JLabel("Effettua il login per poter dare un nome e salvare la tua Build!", SwingConstants.CENTER);
        lblMessaggio.setForeground(Color.RED);
        lblMessaggio.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        pnlSud.add(lblMessaggio, BorderLayout.CENTER);
        add(pnlSud, BorderLayout.SOUTH);
    }

    // --- GETTER ---
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnTornaMarketplace() { return btnTornaMarketplace; }
    public JButton getBtnForum() { return btnForum; }
    public JTextField getTxtCerca() { return txtCerca; }
    public JButton getBtnCerca() { return btnCerca; }
    public JList<String> getListCatalogo() { return listCatalogo; }
    public JList<String> getListBuild() { return listBuild; }
    public JButton getBtnMostraInfo() { return btnMostraInfo; }
    public JButton getBtnAggiungiBuild() { return btnAggiungiBuild; }
    public JButton getBtnRimuovi() { return btnRimuovi; }
    public JButton getBtnSvuotaBuild() { return btnSvuotaBuild; }
    public JTextArea getTxtAreaInfo() { return txtAreaInfo; }
    public JLabel getLblMessaggio() { return lblMessaggio; }
}