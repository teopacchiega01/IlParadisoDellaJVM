package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view;

import javax.swing.*;
import java.awt.*;

/**
 * @author Persy
 */
public class LoginPanel extends JPanel {

    private JButton btnTornaHome;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnEffettuaLogin;
    private JLabel lblMessaggio;

    public LoginPanel() {
       
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

     
        JPanel pnlNord = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnTornaHome = new JButton("TORNA ALLA HOME");
        pnlNord.add(btnTornaHome);
        add(pnlNord, BorderLayout.NORTH);

      
        JPanel pnlCentro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.anchor = GridBagConstraints.EAST; 
        pnlCentro.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; 
        txtEmail = new JTextField(20);
        pnlCentro.add(txtEmail, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        pnlCentro.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        txtPassword = new JPasswordField(20); 
        pnlCentro.add(txtPassword, gbc);

       
        gbc.gridx = 0; gbc.gridy = 2; 
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER;
        
       
        gbc.insets = new Insets(30, 10, 10, 10); 
        btnEffettuaLogin = new JButton("EFFETTUA IL LOGIN");
        pnlCentro.add(btnEffettuaLogin, gbc);

        add(pnlCentro, BorderLayout.CENTER);

    
        JPanel pnlSud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblMessaggio = new JLabel(" ");
        lblMessaggio.setForeground(Color.RED); 
        pnlSud.add(lblMessaggio);
        add(pnlSud, BorderLayout.SOUTH);
    }


    public JButton getBtnTornaHome() { return btnTornaHome; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JButton getBtnEffettuaLogin() { return btnEffettuaLogin; }
    public JLabel getLblMessaggio() { return lblMessaggio; }
}