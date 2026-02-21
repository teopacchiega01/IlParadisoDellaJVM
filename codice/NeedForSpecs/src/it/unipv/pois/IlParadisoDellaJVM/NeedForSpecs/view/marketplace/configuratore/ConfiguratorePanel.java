package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.marketplace.configuratore;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ConfiguratorePanel extends JPanel {
	
	private JButton btnLogin, btnTornaMarketplace, btnForum, btnCarrello;
	private JTextField txtCerca;
	private JButton btnCerca;
	private JList<String> listCatalogo, listBuild;
	private JButton btnMostraInfo, btnAggiungiBuild, btnRimuovi, btnSvuotaBuild, btnSalvaBuild;
	private JTextArea txtAreaInfo;
	private JTextField txtNomeBuild, txtPrezzoBuild;
	private JLabel lblMessaggio;

	public ConfiguratorePanel() {
		setLayout(new BorderLayout(10, 10));

		// NORD
		JPanel pnlNord = new JPanel(new GridLayout(2, 1));
		JPanel pnlBottoniTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		btnLogin = new JButton("LOGIN");
		btnTornaMarketplace = new JButton("TORNA AL MARKETPLACE");
		btnForum = new JButton("FORUM");
		btnCarrello = new JButton("CARRELLO");
		pnlBottoniTop.add(btnLogin); pnlBottoniTop.add(btnTornaMarketplace); pnlBottoniTop.add(btnForum); pnlBottoniTop.add(btnCarrello);

		JPanel pnlRicerca = new JPanel(new FlowLayout());
		txtCerca = new JTextField(30);
		btnCerca = new JButton("CERCA");
		pnlRicerca.add(txtCerca); pnlRicerca.add(btnCerca);

		pnlNord.add(pnlBottoniTop); pnlNord.add(pnlRicerca);
		add(pnlNord, BorderLayout.NORTH);

		// CENTRO (Diviso in due: Sopra il catalogo, Sotto la build attuale)
		JPanel pnlCentro = new JPanel();
		pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));

		// Centro-Top: Catalogo e Info
		JPanel pnlCatalogo = new JPanel(new BorderLayout(5, 5));
		listCatalogo = new JList<>();
		pnlCatalogo.add(new JScrollPane(listCatalogo), BorderLayout.WEST);

		JPanel pnlInfo = new JPanel(new BorderLayout());
		JPanel pnlAzioniCat = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnMostraInfo = new JButton("MOSTRA INFO");
		btnAggiungiBuild = new JButton("AGGIUNGI ALLA BUILD");
		pnlAzioniCat.add(btnMostraInfo); pnlAzioniCat.add(btnAggiungiBuild);
		txtAreaInfo = new JTextArea(5, 20);
		txtAreaInfo.setEditable(false);
		pnlInfo.add(pnlAzioniCat, BorderLayout.NORTH);
		pnlInfo.add(new JScrollPane(txtAreaInfo), BorderLayout.CENTER);
		pnlCatalogo.add(pnlInfo, BorderLayout.CENTER);

		// Centro-Bottom: Lista Build Corrente
		JPanel pnlLaMiaBuild = new JPanel(new BorderLayout(5, 5));
		pnlLaMiaBuild.setBorder(BorderFactory.createTitledBorder("Componenti nella Build"));
		listBuild = new JList<>();
		pnlLaMiaBuild.add(new JScrollPane(listBuild), BorderLayout.CENTER);

		JPanel pnlAzioniBuild = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnRimuovi = new JButton("RIMUOVI");
		btnSvuotaBuild = new JButton("SVUOTA BUILD");
		pnlAzioniBuild.add(btnRimuovi); pnlAzioniBuild.add(btnSvuotaBuild);
		pnlLaMiaBuild.add(pnlAzioniBuild, BorderLayout.EAST);

		pnlCentro.add(pnlCatalogo);
		pnlCentro.add(Box.createVerticalStrut(15)); // Spazio
		pnlCentro.add(pnlLaMiaBuild);
		add(pnlCentro, BorderLayout.CENTER);

		// SUD: Dati finali e Salvataggio
		JPanel pnlSud = new JPanel(new GridLayout(4, 1, 5, 5));

		JPanel pnlDati = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		pnlDati.add(new JLabel("NOME BUILD:"));
		txtNomeBuild = new JTextField(15);
		pnlDati.add(txtNomeBuild);
		pnlDati.add(new JLabel("PREZZO TOTALE:"));
		txtPrezzoBuild = new JTextField(10);
		txtPrezzoBuild.setEditable(false);
		pnlDati.add(txtPrezzoBuild);

		btnSalvaBuild = new JButton("SALVA BUILD");
		JPanel pnlSalva = new JPanel(new FlowLayout());
		pnlSalva.add(btnSalvaBuild);

		lblMessaggio = new JLabel(" ", SwingConstants.CENTER);

		pnlSud.add(pnlDati);
		pnlSud.add(pnlSalva);
		pnlSud.add(lblMessaggio);
		add(pnlSud, BorderLayout.SOUTH);
	}

	// --- GETTER ---
	public JButton getBtnTornaMarketplace() { return btnTornaMarketplace; }
	public JButton getBtnLogin() { return btnLogin; }
	public JButton getBtnForum() { return btnForum; }
	public JButton getBtnCarrello() { return btnCarrello; }
	public JTextField getTxtCerca() { return txtCerca; }
	public JButton getBtnCerca() { return btnCerca; }
	public JList<String> getListCatalogo() { return listCatalogo; }
	public JList<String> getListBuild() { return listBuild; }
	public JButton getBtnMostraInfo() { return btnMostraInfo; }
	public JButton getBtnAggiungiBuild() { return btnAggiungiBuild; }
	public JButton getBtnRimuovi() { return btnRimuovi; }
	public JButton getBtnSvuotaBuild() { return btnSvuotaBuild; }
	public JButton getBtnSalvaBuild() { return btnSalvaBuild; }
	public JTextArea getTxtAreaInfo() { return txtAreaInfo; }
	public JTextField getTxtNomeBuild() { return txtNomeBuild; }
	public JTextField getTxtPrezzoBuild() { return txtPrezzoBuild; }
	public JLabel getLblMessaggio() { return lblMessaggio; }

}

