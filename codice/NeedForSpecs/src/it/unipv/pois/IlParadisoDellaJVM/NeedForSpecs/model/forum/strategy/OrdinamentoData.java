package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;

/**
 *
 * * @author teomiraldi
 * 
 */

public class OrdinamentoData implements ForumStrategy {

	@Override
	public void ordinamento(ArrayList<Post> p) {
		
		p.sort(new Comparator<Post>() {
			
			@Override
			public int compare(Post p1, Post p2) {
				
				if(p1.getData_pubblicazione() == null || p2.getData_pubblicazione() == null) {
					
					return 0;
				}

				return p2.getData_pubblicazione().compareTo(p1.getData_pubblicazione());
			}
		});
	}
}