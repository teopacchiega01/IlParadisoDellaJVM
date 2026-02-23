package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.adapter;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Commento;

public class CommentoAdapter extends AbstractTableModel {

	private ArrayList<Commento> commenti; 

	private final String[] colonne = {"Autore", "Testo Commento"};

	public CommentoAdapter(ArrayList<Commento> commenti) {
		this.commenti = commenti;
	}

	@Override
	public int getRowCount() {
		return commenti.size();
	}

	@Override
	public int getColumnCount() {
		return colonne.length;
	}

	@Override
	public String getColumnName(int n) {
		return colonne[n];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Commento c = commenti.get(rowIndex);

		switch (columnIndex) {
		case 0:

			return c.getAutore().getUser_name();
			
		case 1:

			return c.getTesto();
			
		default: 
			return null;
		}
	}
	

	public Commento getCommentoAt(int rowIndex) {
		return commenti.get(rowIndex);
	}
	
	public ArrayList<Commento> getListaCommenti() {
		return this.commenti; 
	}

}