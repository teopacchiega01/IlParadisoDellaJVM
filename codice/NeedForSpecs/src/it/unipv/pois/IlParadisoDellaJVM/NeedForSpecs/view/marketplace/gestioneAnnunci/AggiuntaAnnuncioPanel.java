package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.gestioneAnnunci;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AggiuntaAnnuncioPanel extends JPanel {
	private JButton btnLogin, btnCarrello, btnCerca, btnAggiungiAnnuncio, btnTornaMarketplace;
    private JTextField txtCerca, txtPrezzo;
    private JLabel lblMessaggio;

    public AggiuntaAnnuncioPanel() {
        setLayout(new BorderLayout(10, 10));

        // NORD
        JPanel pnlBottoniTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btnLogin = new JButton("LOGIN");
        btnCarrello = new JButton("CARRELLO");
        pnlBottoniTop.add(btnLogin); pnlBottoniTop.add(btnCarrello);
        add(pnlBottoniTop, BorderLayout.NORTH);

        // CENTRO (Form per inserire l'annuncio)
        // Usiamo un GridBagLayout (o un pannello con BoxLayout) per centrare gli elementi
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));
        pnlCentro.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // Margini larghi per centrare

        JPanel pnlRicerca = new JPanel(new FlowLayout());
        pnlRicerca.add(new JLabel("Prodotto da vendere:"));
        txtCerca = new JTextField(20);
        btnCerca = new JButton("CERCA PRODOTTO DB");
        pnlRicerca.add(txtCerca); pnlRicerca.add(btnCerca);

        JPanel pnlPrezzo = new JPanel(new FlowLayout());
        pnlPrezzo.add(new JLabel("Prezzo di vendita (€):"));
        txtPrezzo = new JTextField(10);
        pnlPrezzo.add(txtPrezzo);

        JPanel pnlAggiungi = new JPanel(new FlowLayout());
        btnAggiungiAnnuncio = new JButton("PUBBLICA ANNUNCIO");
        pnlAggiungi.add(btnAggiungiAnnuncio);

        pnlCentro.add(pnlRicerca);
        pnlCentro.add(Box.createVerticalStrut(20));
        pnlCentro.add(pnlPrezzo);
        pnlCentro.add(Box.createVerticalStrut(30));
        pnlCentro.add(pnlAggiungi);
        
        add(pnlCentro, BorderLayout.CENTER);

        // SUD
        JPanel pnlSud = new JPanel(new BorderLayout());
        lblMessaggio = new JLabel(" ", SwingConstants.CENTER);
        
        JPanel pnlTorna = new JPanel(new FlowLayout());
        btnTornaMarketplace = new JButton("TORNA AL MARKETPLACE");
        pnlTorna.add(btnTornaMarketplace);
        
        pnlSud.add(lblMessaggio, BorderLayout.NORTH);
        pnlSud.add(pnlTorna, BorderLayout.SOUTH);
        add(pnlSud, BorderLayout.SOUTH);
    }

    // --- GETTER ---
    public JButton getBtnTornaMarketplace() { return btnTornaMarketplace; }
	public JButton getBtnLogin() { return btnLogin; }
	public JButton getBtnCarrello() { return btnCarrello; }
	public JButton getBtnCerca() { return btnCerca; }
	public JButton getBtnAggiungiAnnuncio() { return btnAggiungiAnnuncio; }
	public JTextField getTxtCerca() { return txtCerca; }
	public JTextField getTxtPrezzo() { return txtPrezzo; }
	public JLabel getLblMessaggio() { return lblMessaggio; }
    
}
