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

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.annunci.Annuncio;

/**
 * @author teopacchiega
 */


public class GestioneCarrelloPanel extends JPanel {
	
	private JButton btnLogout;
	
	private JButton btnEliminaLista;
	private JList<Annuncio> listAnnunci;
	
	private JButton btnEffettuaOrdine, btnTornaMarketplace;
    private JTextField txtPrezzoTotale;
    private JLabel lblMessaggio;

    public GestioneCarrelloPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlBottoniTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnLogout = new JButton("LOGOUT");
        pnlBottoniTop.add(btnLogout);
        add(pnlBottoniTop, BorderLayout.NORTH);

        JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
        listAnnunci = new JList<>();
        pnlCentro.add(new JScrollPane(listAnnunci), BorderLayout.CENTER);
        JPanel pnlAzioniLista = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEliminaLista = new JButton("ELIMINA DAL CARRELLO");
        pnlAzioniLista.add(btnEliminaLista);
        pnlCentro.add(pnlAzioniLista, BorderLayout.SOUTH);
        add(pnlCentro, BorderLayout.CENTER);

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

    public JButton getBtnTornaMarketplace() { return btnTornaMarketplace; }
	public JButton getBtnLogout() { return btnLogout; }
	public JButton getBtnEliminaLista() { return btnEliminaLista; }
	public JButton getBtnEffettuaOrdine() { return btnEffettuaOrdine; }
	public JList<Annuncio> getListAnnunci() { return listAnnunci; }
	public JTextField getTxtPrezzoTotale() { return txtPrezzoTotale; }
	public JLabel getLblMessaggio() { return lblMessaggio; }
    
}
