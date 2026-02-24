package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.time.LocalDate;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.utilities.StringChecker;

public class ModificaPagamentoPanel extends JPanel {

    private JTextField numero_carta_field;
    private JTextField scadenza_carta_field;
    private JTextField cvv_carta_field;
    
    private JButton aggiorna_butt;
    private JButton indietro_butt;
    private JLabel label_out;

    public ModificaPagamentoPanel() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // NORD: Titolo e feedback
        JPanel nord = new JPanel(new GridLayout(2, 1, 0, 10));
        nord.add(new JLabel("Modifica Metodo di Pagamento", SwingConstants.CENTER));
        label_out = new JLabel(" ", SwingConstants.CENTER);
        nord.add(label_out);

        // CENTRO: Form
        JPanel centro = new JPanel(new GridLayout(3, 2, 10, 15));
        centro.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        numero_carta_field = new JTextField();
        scadenza_carta_field = new JTextField();
        cvv_carta_field = new JTextField();

        centro.add(new JLabel("Nuovo Numero Carta (16 cifre):")); 
        centro.add(numero_carta_field);
        centro.add(new JLabel("Scadenza (MM/AA):")); 
        centro.add(scadenza_carta_field);
        centro.add(new JLabel("CVV:")); 
        centro.add(cvv_carta_field);

        // SUD: Bottoni
        JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        aggiorna_butt = new JButton("Aggiorna Dati");
        indietro_butt = new JButton("< Torna al Profilo");
        
        sud.add(indietro_butt);
        sud.add(aggiorna_butt);

        this.add(nord, BorderLayout.NORTH);
        this.add(centro, BorderLayout.CENTER);
        this.add(sud, BorderLayout.SOUTH);
    }

    public void setLabelOut(String text) { label_out.setText(text); }
    
    public void pulisciCampi() {
        numero_carta_field.setText("");
        scadenza_carta_field.setText("");
        cvv_carta_field.setText("");
    }

    public boolean validaCampi() {
        if (getNumeroCarta().isEmpty() || getCvvCarta().isEmpty() || getScadenzaCarta() == null) {
            setLabelOut("Errore: Compila tutti i campi correttamente!");
            return false;
        }
        return true;
    }

    public String getNumeroCarta() { return StringChecker.pulisciInput(numero_carta_field.getText()); }
    public LocalDate getScadenzaCarta() { return StringChecker.convertiScadenzaCarta(scadenza_carta_field.getText()); }
    public String getCvvCarta() { return StringChecker.pulisciInput(cvv_carta_field.getText()); }

    public JButton getAggiorna_butt() { return aggiorna_butt; }
    public JButton getIndietro_butt() { return indietro_butt; }
}