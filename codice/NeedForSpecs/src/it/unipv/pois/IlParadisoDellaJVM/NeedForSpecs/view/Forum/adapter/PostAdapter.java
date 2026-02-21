package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.adapter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;

public class PostAdapter extends AbstractTableModel{

	private ArrayList<Post> post;
	private final String[] colonne = {"Titolo", "Autore", "Data Pubblicazione"};


	public PostAdapter(ArrayList<Post> post) {

		this.post = post;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return post.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colonne.length;
	}

	public String getColumnName(int n) {

		return colonne[n];

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Post p = post.get(rowIndex);

		switch (columnIndex) {

		case 0:

			return p.getTitolo();

		case 1:
			
			return p.getAutore().getUser_name();

		case 2: 
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			
			return p.getData_pubblicazione().format(formatter);
			
		default: 
			
			return null;



		}
	}
	
	//metodo che consente l'implementazione di espandi commento all'interno della tabella
	public Post getPostAt(int rowIndex) {
        return post.get(rowIndex);
    }
	
	
	
	

}
