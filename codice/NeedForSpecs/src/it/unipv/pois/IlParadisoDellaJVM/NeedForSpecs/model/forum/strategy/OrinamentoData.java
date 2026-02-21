package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy;

import java.util.ArrayList;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;

public class OrinamentoData implements ForumStrategy{

	@Override
	public void ordinamento(ArrayList<Post> p) {
		
		p.sort((p1, p2) -> {
			
            if (p1.getData_pubblicazione() == null || p2.getData_pubblicazione() == null) {
            	
            	return 0;
            
            }
            
            //inversione p2 con p1 per ottenere data recente
            return p2.getData_pubblicazione().compareTo(p1.getData_pubblicazione());
        });
		
		
	}

}
