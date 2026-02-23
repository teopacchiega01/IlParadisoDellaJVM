package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;

public class OrdinamentoAutore implements ForumStrategy {

	@Override
	public void ordinamento(ArrayList<Post> p) {

		p.sort(new Comparator<Post>() {

			@Override
			public int compare(Post p1, Post p2) {

				String autore1;
				String autore2;
				
				if (p1.getAutore() != null) {

					autore1 = getAutore(p1);
				} else {

					autore1 = ""; 
					
				}

				
				if (p2.getAutore() != null) {
					
					autore2 = getAutore(p2);
					
				} else {
					
					autore2 = "";
					
				}

				return autore1.compareToIgnoreCase(autore2);
			}
		});
	}
	
	private String getAutore(Post p) {
		
		return p.getAutore().getUser_name();
		
	}
}