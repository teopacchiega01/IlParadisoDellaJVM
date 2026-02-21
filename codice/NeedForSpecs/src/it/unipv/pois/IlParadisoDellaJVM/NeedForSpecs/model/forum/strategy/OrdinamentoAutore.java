package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;

public class OrdinamentoAutore implements ForumStrategy{

	@Override
	public void ordinamento(ArrayList<Post> p) {

		p.sort((p1, p2) -> {
			
			if (p1.getAutore() == null || p2.getAutore() == null) {
				
				return 0;
				
			}

			String autore1 = p1.getAutore().getUser_name();
			String autore2 = p2.getAutore().getUser_name();

			return autore1.compareToIgnoreCase(autore2);
		});

	}

}
