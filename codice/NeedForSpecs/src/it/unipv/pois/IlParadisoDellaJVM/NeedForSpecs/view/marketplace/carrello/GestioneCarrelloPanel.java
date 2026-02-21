package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.carrello;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GestioneCarrelloPanel extends JPanel {
	private JButton btnLogin, btnEliminaLista, btnEffettuaOrdine, btnTornaMarketplace;
    private JList<String> listAnnunci;
    private JTextField txtPrezzoTotale;
    private JLabel lblMessaggio;

    public GestioneCarrelloPanel() {
        setLayout(new BorderLayout(10, 10));

        // NORD
        JPanel pnlBottoniTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnLogin = new JButton("LOGIN");
        pnlBottoniTop.add(btnLogin);
        add(pnlBottoniTop, BorderLayout.NORTH);

        // CENTRO
        JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
        
        listAnnunci = new JList<>();
        pnlCentro.add(new JScrollPane(listAnnunci), BorderLayout.CENTER);
        
        JPanel pnlAzioniLista = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEliminaLista = new JButton("ELIMINA DAL CARRELLO");
        pnlAzioniLista.add(btnEliminaLista);
        pnlCentro.add(pnlAzioniLista, BorderLayout.SOUTH);
        
        add(pnlCentro, BorderLayout.CENTER);

        // SUD
        JPanel pnlSud = new JPanel(new GridLayout(4, 1, 5, 5));
        
        JPanel pnlPrezzo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlPrezzo.add(new JLabel("PREZZO TOTALE:"));
        txtPrezzoTotale = new JTextField(15);
        txtPrezzoTotale.setEditable(false);
        pnlPrezzo.add(txtPrezzoTotale);
        
        JPanel pnlOrdine = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnEffettuaOrdine = new JButton("EFFETTUA ORDINE");
        pnlOrdine.add(btnEffettuaOrdine);
        
        lblMessaggio = new JLabel(" ", SwingConstants.CENTER);
        
        JPanel pnlTorna = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnTornaMarketplace = new JButton("TORNA AL MARKETPLACE");
        pnlTorna.add(btnTornaMarketplace);
        
        pnlSud.add(pnlPrezzo);
        pnlSud.add(pnlOrdine);
        pnlSud.add(lblMessaggio);
        pnlSud.add(pnlTorna);
        
        add(pnlSud, BorderLayout.SOUTH);
    }

    // --- GETTER ---
    public JButton getBtnTornaMarketplace() { return btnTornaMarketplace; }
	public JButton getBtnLogin() { return btnLogin; }
	public JButton getBtnEliminaLista() { return btnEliminaLista; }
	public JButton getBtnEffettuaOrdine() { return btnEffettuaOrdine; }
	public JList<String> getListAnnunci() { return listAnnunci; }
	public JTextField getTxtPrezzoTotale() { return txtPrezzoTotale; }
	public JLabel getLblMessaggio() { return lblMessaggio; }
    
}
