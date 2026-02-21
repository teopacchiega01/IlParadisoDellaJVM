package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;

public class OrdinamentoAlfabetico implements ForumStrategy {

	@Override
	public void ordinamento(ArrayList<Post> p) {

		p.sort((p1, p2) -> {

			if (p1.getTitolo() == null || p2.getTitolo() == null) {
				
				return 0;
			
			}

			return p1.getTitolo().compareToIgnoreCase(p2.getTitolo());

		});



	}

}
