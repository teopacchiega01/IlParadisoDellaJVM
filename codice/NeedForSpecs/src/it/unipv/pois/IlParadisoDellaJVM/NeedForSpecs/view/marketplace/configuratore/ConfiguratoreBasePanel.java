package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.configuratore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.Componente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.componenti.enums.TipoComponente;

/**
 * @author teopacchiega
 */

public abstract class ConfiguratoreBasePanel extends JPanel {

    protected JButton btnLoginLogout, btnTornaMarketplace, btnCarrello;
    protected JList<TipoComponente> listTipiComponente;
    protected JButton btnAggiungiTipoAllaBuild;

    protected JList<Componente> listComponentiDisponibili;
    protected JButton AggiungiComponenteAllaBuild;
    protected JTextArea txtAreaInfoCatalogo;

  
    protected JList<Componente> listComponentiBuild;
    protected JButton btnRimuoviDaBuild;
    protected JTextArea txtAreaInfoBuild;

    protected JLabel lblMessaggio;

    public ConfiguratoreBasePanel() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        initComponents();
        layoutComponents();
    }

    protected void initComponents() {
        btnLoginLogout = new JButton();
        btnTornaMarketplace = new JButton("TORNA AL MARKETPLACE");
        btnCarrello = new JButton("CARRELLO");


        listTipiComponente = new JList<TipoComponente>();
        btnAggiungiTipoAllaBuild = new JButton("AGGIUNGI ALLA BUILD");

        listComponentiDisponibili = new JList<Componente>();

        txtAreaInfoCatalogo = new JTextArea(5, 20);
        txtAreaInfoCatalogo.setEditable(false);

        listComponentiBuild = new JList<Componente>();
        btnRimuoviDaBuild = new JButton("RIMUOVI DALLA BUILD");
        txtAreaInfoBuild = new JTextArea(5, 20);
        txtAreaInfoBuild.setEditable(false);

        lblMessaggio = new JLabel(" ");
        lblMessaggio.setForeground(Color.RED);
    }

    protected void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(btnLoginLogout, gbc);
        gbc.gridx = 1;
        add(btnTornaMarketplace, gbc);
        gbc.gridx = 2;
        add(btnCarrello, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        add(new JScrollPane(listTipiComponente), gbc);
        gbc.gridx = 2; gbc.gridwidth = 1;
        add(btnAggiungiTipoAllaBuild, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0.3; gbc.weighty = 0.4; gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(listComponentiDisponibili), gbc);

        JPanel pnlInfoCatalogo = new JPanel(new BorderLayout(5, 5));
        JPanel pnlBtnInfoCat = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlBtnInfoCat.add(Box.createHorizontalStrut(5));
        pnlInfoCatalogo.add(pnlBtnInfoCat, BorderLayout.NORTH);
        pnlInfoCatalogo.add(new JScrollPane(txtAreaInfoCatalogo), BorderLayout.CENTER);

        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 0.7;
        add(pnlInfoCatalogo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.weightx = 0.3; gbc.weighty = 0.4;
        add(new JScrollPane(listComponentiBuild), gbc);

        JPanel pnlInfoBuild = new JPanel(new BorderLayout(5, 5));
        JPanel pnlBtnInfoBuild = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlBtnInfoBuild.add(Box.createHorizontalStrut(5));
        pnlBtnInfoBuild.add(btnRimuoviDaBuild);
        pnlInfoBuild.add(pnlBtnInfoBuild, BorderLayout.NORTH);
        pnlInfoBuild.add(new JScrollPane(txtAreaInfoBuild), BorderLayout.CENTER);

        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 0.7;
        add(pnlInfoBuild, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3; gbc.weightx = 1.0; gbc.weighty = 0.1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lblMessaggio, gbc);
    }

    public JButton getBtnLoginLogout() { return btnLoginLogout; }
    public JButton getBtnTornaMarketplace() { return btnTornaMarketplace; }
    public JButton getBtnCarrello() { return btnCarrello; }
	public JList<TipoComponente> getListTipiComponente() { return listTipiComponente; }
	public JButton getBtnAggiungiTipoAllaBuild() { return btnAggiungiTipoAllaBuild; }
	public JList<Componente> getListComponentiDisponibili() { return listComponentiDisponibili; }
	public JTextArea getTxtAreaInfoCatalogo() { return txtAreaInfoCatalogo; }
	public JList<Componente> getListComponentiBuild() { return listComponentiBuild; }
	public JButton getBtnRimuoviDaBuild() { return btnRimuoviDaBuild; }
	public JTextArea getTxtAreaInfoBuild() { return txtAreaInfoBuild; }
	public JLabel getLblMessaggio() { return lblMessaggio; }
    
}