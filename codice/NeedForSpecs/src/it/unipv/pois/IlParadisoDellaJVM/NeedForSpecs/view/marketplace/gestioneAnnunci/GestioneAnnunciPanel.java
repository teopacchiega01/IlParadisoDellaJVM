package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.gestioneAnnunci;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;

public class GestioneAnnunciPanel extends JPanel {

    private JButton btnLogout;
    private JList<Annuncio> listAnnunci;
    private JButton btnEliminaAnnuncio;
    private JTextArea txtInfoAnnuncio;
    private JLabel lblMessaggioErrore;
    private JButton btnTornaMarketplace;

    public GestioneAnnunciPanel() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        btnLogout = new JButton("LOGOUT");
        
        listAnnunci = new JList<>();
        
        btnEliminaAnnuncio = new JButton("ELIMINA ANNUNCIO");
        
        txtInfoAnnuncio = new JTextArea(8, 40);
        txtInfoAnnuncio.setEditable(false);
        txtInfoAnnuncio.setLineWrap(true);
        txtInfoAnnuncio.setWrapStyleWord(true);
        txtInfoAnnuncio.setOpaque(false); 
        
        lblMessaggioErrore = new JLabel(" ");
        lblMessaggioErrore.setForeground(Color.RED);
        lblMessaggioErrore.setHorizontalAlignment(JLabel.CENTER);
        
        btnTornaMarketplace = new JButton("TORNA AL MARKETPLACE");
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; 
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        add(btnLogout, gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 1;
        gbc.gridwidth = 2; 
        add(new JLabel("ANNUNCI PUBBLICATI:"), gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5; 
        gbc.fill = GridBagConstraints.BOTH; 
        add(new JScrollPane(listAnnunci), gbc); 
        
        gbc.gridx = 1; 
        gbc.weightx = 0.0; 
        gbc.weighty = 0.0; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHEAST; 
        add(btnEliminaAnnuncio, gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5; 
        gbc.fill = GridBagConstraints.BOTH;
        
        JScrollPane scrollInfo = new JScrollPane(txtInfoAnnuncio);
        scrollInfo.setBorder(new TitledBorder("Informazioni sull'annuncio selezionato"));
        scrollInfo.setOpaque(false);
        scrollInfo.getViewport().setOpaque(false);
        add(scrollInfo, gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 4;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lblMessaggioErrore, gbc);
        
        gbc.gridx = 0; 
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnTornaMarketplace, gbc);
    }

    public JButton getBtnLogout() { return btnLogout; }
    public JList<Annuncio> getListAnnunci() { return listAnnunci; }
    public JButton getBtnEliminaAnnuncio() { return btnEliminaAnnuncio; }
    public JTextArea getTxtInfoAnnuncio() { return txtInfoAnnuncio; }
    public JLabel getLblMessaggioErrore() { return lblMessaggioErrore; }
    public JButton getBtnTornaMarketplace() { return btnTornaMarketplace; }
}
