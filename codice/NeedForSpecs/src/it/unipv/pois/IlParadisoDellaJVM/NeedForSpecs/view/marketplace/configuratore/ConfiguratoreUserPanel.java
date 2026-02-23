package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.configuratore;

import javax.swing.*;
import java.awt.*;

public class ConfiguratoreUserPanel extends ConfiguratoreBasePanel {

    private JTextField txtNomeBuild;
    private JButton btnSalvaBuild;

    public ConfiguratoreUserPanel() {
        super();
        btnLoginLogout.setText("LOGOUT");
        initUserComponents();
        layoutUserComponents();
    }

    private void initUserComponents() {
        txtNomeBuild = new JTextField(15);
        btnSalvaBuild = new JButton("SALVA BUILD");
    }

    private void layoutUserComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 4; // Inseriamo nella riga 4, prima del messaggio

        // Nome Build
        gbc.gridx = 1; gbc.weightx = 0.0;
        add(new JLabel("Nome build:"), gbc);
        gbc.gridx = 2; gbc.weightx = 0.5;
        add(txtNomeBuild, gbc);

        // Salva Build
        gbc.gridy = 6;
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        add(btnSalvaBuild, gbc);

        // Spostiamo il messaggio alla fine
        remove(lblMessaggio);
        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 3;
        add(lblMessaggio, gbc);
        revalidate();
        repaint();
    }

    public JTextField getTxtNomeBuild() { return txtNomeBuild; }
    public JButton getBtnSalvaBuild() { return btnSalvaBuild; }
}
