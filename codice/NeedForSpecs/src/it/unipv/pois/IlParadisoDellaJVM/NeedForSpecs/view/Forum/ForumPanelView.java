package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum;

import javax.swing.*;
import java.awt.*;

public class ForumPanelView extends JPanel {

    private JButton btnTornaHome;
    private JButton btnNuovoPost;
    private JButton btnApriPost; // Nuovo bottone per espandere il post selezionato!
    private JTable tabellaPost;  // La tua lista interattiva
    
    private JComboBox<String> comboOrdinamento;
    private JButton btnOrdina;

    public ForumPanelView() {
        this.setLayout(new BorderLayout()); 

        //TOP: bottoni generali
        JPanel pannelloTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTornaHome = new JButton("Torna alla Home");
        btnNuovoPost = new JButton("Scrivi un Post");
        pannelloTop.add(btnTornaHome);
        pannelloTop.add(btnNuovoPost);
        this.add(pannelloTop, BorderLayout.NORTH);

        //CENTER: lista interattiva (Tabella) dentro uno ScrollPane
        tabellaPost = new JTable();
        tabellaPost.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Selezioni un post alla volta
        
        // Il JScrollPane è vitale: se hai 100 post, fa apparire la barra di scorrimento!
        JScrollPane scrollPane = new JScrollPane(tabellaPost);
        this.add(scrollPane, BorderLayout.CENTER);

        //BOTTOM: bottone per "Espandere" il post e cambiare finestra
        JPanel pannelloBot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnApriPost = new JButton("Espandi Post Selezionato 📖");
        pannelloBot.add(btnApriPost);
        this.add(pannelloBot, BorderLayout.SOUTH);
        
        String[] opzioni = {"Meno Recenti", "Alfabetico (Titolo)", "Autore"};
        comboOrdinamento = new JComboBox<>(opzioni);
        btnOrdina = new JButton("Ordina");
        
        pannelloTop.add(new JLabel("Ordina per: "));
        pannelloTop.add(comboOrdinamento);
        pannelloTop.add(btnOrdina);
    }


    public JButton getBtnTornaHome() {
    	
    	return btnTornaHome; 
    	
    }
    
    
    public JButton getBtnNuovoPost() {
    	
    	return btnNuovoPost;
    	
    }
    
    
    public JButton getBtnApriPost() { 
    	
    	return btnApriPost; 
    
    }
    
    
    public JTable getTabellaPost() {
    	
    	return tabellaPost;
    	
    }
    
    
    public JComboBox<String> getComboOrdinamento() {
    	
    	return comboOrdinamento; 
    	
    }
    
    
    public JButton getBtnOrdina() { 
    	
    	return btnOrdina; 
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
