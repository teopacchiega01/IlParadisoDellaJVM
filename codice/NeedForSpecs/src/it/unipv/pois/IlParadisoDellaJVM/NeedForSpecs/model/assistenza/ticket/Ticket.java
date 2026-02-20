package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.assistenza.ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteGenerico;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.UtenteStaff;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Messaggio;



/*
 * @author: Persy
 */

public class Ticket {

	private String id_ticket;
	private UtenteGenerico richiedente_assistenza;
	private UtenteStaff gestore;
	private Stato stato_ticket;
	private ArrayList<Messaggio> conversazione;
	
	
	
	
	

	
	public Ticket(String id_ticket, UtenteGenerico richiedente_assistenza, UtenteStaff gestore, Stato stato_ticket,
			ArrayList<Messaggio> conversazione) {
		super();
		this.id_ticket = id_ticket;
		this.richiedente_assistenza = richiedente_assistenza;
		this.gestore = gestore;
		this.stato_ticket = stato_ticket;
		this.conversazione = conversazione;
	}



	public Ticket(UtenteGenerico richiedente_assistenza, UtenteStaff gestore, Stato stato_ticket,
			ArrayList<Messaggio> conversazione) {
		super();
		this.id_ticket = generaIdTicket();
		this.richiedente_assistenza = richiedente_assistenza;
		this.gestore = gestore;
		this.stato_ticket = stato_ticket;
		this.conversazione = conversazione;
	}



	public Ticket() {
		super();
		conversazione = new ArrayList<>();
	}



	public String getId_ticket() {
		return id_ticket;
	}


	public void setId_ticket(String id_ticket) {
		this.id_ticket = id_ticket;
	}






	public UtenteGenerico getRichiedente_assistenza() {
		return richiedente_assistenza;
	}


	public void setRichiedente_assistenza(UtenteGenerico richiedente_assistenza) {
		this.richiedente_assistenza = richiedente_assistenza;
	}


	public UtenteStaff getGestore() {
		return gestore;
	}


	public void setGestore(UtenteStaff gestore) {
		this.gestore = gestore;
	}

	public Stato getStato_ticket() {
		return stato_ticket;
	}

	public void setStato_ticket(Stato stato_ticket) {
		this.stato_ticket = stato_ticket;
	}
	public ArrayList<Messaggio> getConversazione() {
		return conversazione;
	}


	public void setConversazione(ArrayList<Messaggio> conversazione) {
		this.conversazione = conversazione;
	}
	public void aggiungiMessaggioAllaConversazione(Messaggio msg) {
			
			conversazione.add(msg);
			System.out.println("Messaggio aggiunto alla conversazione");
			
	}
	
	
	public void agguiungiMessessaggiAllaConversazione(ArrayList<Messaggio> messaggi) {
		for(Messaggio m : messaggi) {
			aggiungiMessaggioAllaConversazione(m);
		}
	}
		
	public Messaggio creaMessaggio(Utente autore,String testo) {
		Messaggio msg = new Messaggio(autore, testo, LocalDateTime.now(), this);
		System.out.println("messaggio creato con successo..");
		aggiungiMessaggioAllaConversazione(msg);
		return msg;
		
		
	}
	
	
	public ArrayList<Messaggio> cercaCercaMessaggiDaAutore(Utente autore) {
		ArrayList<Messaggio> messaggi = new ArrayList<>(); 
	
		for(Messaggio m : conversazione) {
			if(m.getAutore().getUser_name().equals(autore.getUser_name()))
				messaggi.add(m);
		}
		return messaggi;
	}
	
	// Usare le collection per implementare un compare che controlla le stringhe usando le ragex 
	public ArrayList<Messaggio> cercaMessaggiDataParola(String parola){
		ArrayList<Messaggio> messaggi = new ArrayList<>();
		if(!parola.isBlank() ) {
			for(Messaggio m : conversazione) {
				if(m.getTesto().contains(parola)) 
					messaggi.add(m);
				
			}
		}else {
			System.err.println("Parola non valida");
		}
		return messaggi;
	}
	
	public Messaggio getUltimoMessaggio() {
	
		return conversazione.getLast();
	
		
	}
	private void ordinaConversazionePerData() {

	    Collections.sort(conversazione); 
	}

	public String getCronologiaMessaggiFormattata() {
	    if (conversazione == null || conversazione.isEmpty()) {
	        return "Nessun messaggio presente.\n";
	    }
	    ordinaConversazionePerData();
	    
	    StringBuilder cronologia = new StringBuilder();
	    for (Messaggio m : conversazione) {
	        cronologia.append(m.getAutore().getUser_name())
	                  .append(": ")
	                  .append(m.getTesto())
	                  .append("\n\n");
	    }
	    
	    return cronologia.toString();
	}
	
	
	/*
	 * Generazione ID ticket tramite la stringa Ticket e un numero casuale a 4 cifre. La scelta di avere 10 caratteri massimi è un vincolo del camppo id nel db 
	 */
	private String generaIdTicket() {
		
		String ticket_id = "Ticket"+(int)(Math.random()*10000);
		return ticket_id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id_ticket + " - Stato: " + this.stato_ticket + " - Responsabile: "+this.gestore.getNome_utente()+ " - Utente Richiedente "+this.richiedente_assistenza.getNome_utente();	
	}
	
}
