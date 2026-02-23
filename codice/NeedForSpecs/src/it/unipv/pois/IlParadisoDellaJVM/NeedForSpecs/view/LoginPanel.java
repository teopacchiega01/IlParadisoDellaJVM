package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JButton btnTornaHome;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnEffettuaLogin;
    private JLabel lblMessaggio;

    public LoginPanel() {
        // Uso un BorderLayout generale per piazzare la Home in alto, il form in mezzo e i messaggi in basso
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- NORD: Pulsante Torna alla Home ---
        // Lo metto in un FlowLayout per mantenerlo centrato in alto
        JPanel pnlNord = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnTornaHome = new JButton("TORNA ALLA HOME");
        pnlNord.add(btnTornaHome);
        add(pnlNord, BorderLayout.NORTH);

        // --- CENTRO: Form di Login ---
        // Il GridBagLayout è perfetto per allineare le etichette con le caselle di testo
        JPanel pnlCentro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spazio tra i componenti

        // Riga 1: Email
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.anchor = GridBagConstraints.EAST; // Allinea l'etichetta a destra
        pnlCentro.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Allinea il campo a sinistra
        txtEmail = new JTextField(20);
        pnlCentro.add(txtEmail, gbc);

        // Riga 2: Password
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        pnlCentro.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        txtPassword = new JPasswordField(20); // Nasconde i caratteri digitati!
        pnlCentro.add(txtPassword, gbc);

        // Riga 3: Bottone Login
        gbc.gridx = 0; gbc.gridy = 2; 
        gbc.gridwidth = 2; // Fa occupare al bottone lo spazio di due colonne
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Aggiungo un piccolo margine superiore al bottone per staccarlo dai textfield
        gbc.insets = new Insets(30, 10, 10, 10); 
        btnEffettuaLogin = new JButton("EFFETTUA IL LOGIN");
        pnlCentro.add(btnEffettuaLogin, gbc);

        add(pnlCentro, BorderLayout.CENTER);

        // --- SUD: Messaggi di Errore ---
        JPanel pnlSud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblMessaggio = new JLabel(" "); // Inizialmente vuoto
        lblMessaggio.setForeground(Color.RED); // Gli errori si mettono in rosso!
        pnlSud.add(lblMessaggio);
        add(pnlSud, BorderLayout.SOUTH);
    }

    // --- GETTER ---
    public JButton getBtnTornaHome() { return btnTornaHome; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JButton getBtnEffettuaLogin() { return btnEffettuaLogin; }
    public JLabel getLblMessaggio() { return lblMessaggio; }
}