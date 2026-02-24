package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.gestioneAnnunci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.marketplace.prodotti.Prodotto;

/**
 * @author teopacchiega
 */

public class AggiuntaAnnuncioPanel extends JPanel {
	
	private JButton btnLogout, btnCarrello;
	private JList<Prodotto> listProdotti; 
	
	private JTextField txtPrezzo;
	private JButton btnAggiungiAnnuncio;
	private JLabel lblMessaggio;
	private JButton btnTornaMarketplace;

	public AggiuntaAnnuncioPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel pnlBottoniTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		btnLogout = new JButton("LOGOUT");
		btnCarrello = new JButton("CARRELLO");
		pnlBottoniTop.add(btnLogout); 
		pnlBottoniTop.add(btnCarrello);
		add(pnlBottoniTop, BorderLayout.NORTH);

		JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
		pnlCentro.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

		JPanel pnlLista = new JPanel(new BorderLayout(5, 5));
		pnlLista.add(new JLabel("Seleziona il prodotto che vuoi vendere:"), BorderLayout.NORTH);
		
		listProdotti = new JList<>();
		listProdotti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		JScrollPane scrollLista = new JScrollPane(listProdotti);
		pnlLista.add(scrollLista, BorderLayout.CENTER);

		JPanel pnlAzione = new JPanel();
		pnlAzione.setLayout(new BoxLayout(pnlAzione, BoxLayout.Y_AXIS));
		
		JPanel pnlPrezzo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlPrezzo.add(new JLabel("Prezzo di vendita (€):"));
		txtPrezzo = new JTextField(10);
		pnlPrezzo.add(txtPrezzo);
		
		JPanel pnlAggiungi = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnAggiungiAnnuncio = new JButton("PUBBLICA ANNUNCIO");
		pnlAggiungi.add(btnAggiungiAnnuncio);

		pnlAzione.add(Box.createVerticalStrut(10));
		pnlAzione.add(pnlPrezzo);
		pnlAzione.add(pnlAggiungi);

		pnlCentro.add(pnlLista, BorderLayout.CENTER);
		pnlCentro.add(pnlAzione, BorderLayout.SOUTH);
		
		add(pnlCentro, BorderLayout.CENTER);

		JPanel pnlSud = new JPanel(new BorderLayout());
		lblMessaggio = new JLabel(" ", SwingConstants.CENTER);
		lblMessaggio.setForeground(Color.RED); 
		
		JPanel pnlTorna = new JPanel(new FlowLayout());
		btnTornaMarketplace = new JButton("TORNA AL MARKETPLACE");
		pnlTorna.add(btnTornaMarketplace);
		
		pnlSud.add(lblMessaggio, BorderLayout.NORTH);
		pnlSud.add(pnlTorna, BorderLayout.SOUTH);
		add(pnlSud, BorderLayout.SOUTH);
	}

	public JButton getBtnTornaMarketplace() { return btnTornaMarketplace; }
	public JButton getBtnLogout() { return btnLogout; }
	public JButton getBtnCarrello() { return btnCarrello; }
	public JList<Prodotto> getListProdotti() { return listProdotti; }
	public JButton getBtnAggiungiAnnuncio() { return btnAggiungiAnnuncio; }
	public JTextField getTxtPrezzo() { return txtPrezzo; }
	public JLabel getLblMessaggio() { return lblMessaggio; }
}