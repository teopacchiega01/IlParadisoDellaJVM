package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;

/**
 *
 * * @author teomiraldi
 * 
 */

public class OrdinamentoAlfabetico implements ForumStrategy {

	@Override
	public void ordinamento(ArrayList<Post> p) {
		
		p.sort(new Comparator<Post>() {
			
			@Override
			public int compare(Post p1, Post p2) {
				
				if (p1.getTitolo() == null || p2.getTitolo() == null) {
					
					return 0;
					
				}

				return p1.getTitolo().compareToIgnoreCase(p2.getTitolo());
			}
		});
	}
}