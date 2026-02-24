package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.configuratore;

import javax.swing.*;
import java.awt.*;

/**
 * @author teopacchiega
 */
public class ConfiguratoreUserPanel extends ConfiguratoreBasePanel {

    private JTextField txtNomeBuild;
    private JButton btnSalvaBuild, btnCarrello;

    public ConfiguratoreUserPanel() {
        super();
        btnLoginLogout.setText("LOGOUT");
        initUserComponents();
        layoutUserComponents();
    }

    private void initUserComponents() {
        txtNomeBuild = new JTextField(15);
        btnSalvaBuild = new JButton("SALVA BUILD");
        btnCarrello = new JButton("CARRELLO");
    }

    private void layoutUserComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridy = 0; 
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        add(btnCarrello, gbc);

        gbc.gridy = 4; 
        
        gbc.gridx = 1; 
        gbc.weightx = 0.0;
        add(new JLabel("Nome build:"), gbc);
        
        gbc.gridx = 2; 
        gbc.weightx = 0.5;
        add(txtNomeBuild, gbc);

        gbc.gridy = 6;
        gbc.gridx = 1; 
        gbc.gridwidth = 2; 
        gbc.weightx = 1.0;
        add(btnSalvaBuild, gbc);

        remove(lblMessaggio);
        gbc.gridy = 7; 
        gbc.gridx = 0; 
        gbc.gridwidth = 3;
        add(lblMessaggio, gbc);
        
        revalidate();
        repaint();
    }

    public JTextField getTxtNomeBuild() { return txtNomeBuild; }
    public JButton getBtnSalvaBuild() { return btnSalvaBuild; }
    public JButton getBtnCarrello() { return btnCarrello; }
}