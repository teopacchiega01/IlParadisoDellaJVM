package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

// IMPORTANTE: Aggiunto l'import del GestoreAccount!
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.GestoreAccount;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Commento;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.Forum;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.ForumException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy.Ordinamento;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.ApriCommentoPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.ApriPostPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.CreaCommentoPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.CreaPostPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.ForumPanel;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.ForumView;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.adapter.CommentoAdapter;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.Forum.adapter.PostAdapter;

public class ControllerForum {

	private Forum model;
	private ForumPanel fPanel; 
	private ForumView view;    

	public ControllerForum(Forum model, ForumView view) {
		this.model = model;
		this.view = view;

		this.fPanel = view.getForumPanel(); 

		aggiornaTabella();

		addListeners(model.getU());
	}

	public void addListeners(Utente u) {

		fPanel.getApriPost().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				gestisciAperturaPost(u);
			}
		});

		fPanel.getCreaPost().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				gestisciCreazionePost(u);
			}
		});

		fPanel.getOrdina().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				gestisciOrdinamento();
			}
		});

		fPanel.getCercaPost().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				gestisciRicerca();
			}
		});

		if (u != null) {

			fPanel.getEliminaPost().setVisible(true);
			fPanel.getEliminaPost().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					gestisciEliminazionePost(u); 
				}

			});

		} else {

			fPanel.getEliminaPost().setVisible(false);
		}


		fPanel.getTornaAllaHome().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				view.pulisciMessaggi();
				view.dispose(); 

				GestoreAccount ga = GestoreAccount.getInstance();

				if (u != null) {

					ga.setUtenteLoggato(u);
				}

				new ControllerHome(ga, view.getHomeFrame());

			}
		});
	}


	private void aggiornaTabella() {
		try {

			ArrayList<Post> listaPost = model.inizializzaForum();
			PostAdapter adapter = view.adaptPost(listaPost);
			fPanel.getTabellaPost().setModel(adapter);

		} catch (ForumException e) {

			e.printStackTrace();
		}
	}

	private void gestisciAperturaPost(Utente u) {

		view.pulisciMessaggi(); 

		int rigaSelezionata = fPanel.getTabellaPost().getSelectedRow();

		if (rigaSelezionata == -1) {
			view.mostraErrore("Devi prima selezionare un post da aprire!");
			return;
		}

		PostAdapter adapter = (PostAdapter) fPanel.getTabellaPost().getModel();
		Post postDaAprire = adapter.getPostAt(rigaSelezionata);

		ApriPostPanel apriPost = view.apriPost();

		apriPost.getlTitolo().setText(postDaAprire.getTitolo());
		apriPost.getlSottotitolo().setText(postDaAprire.getSottotitoloSicuro());
		apriPost.gettTesto().setText(postDaAprire.getTesto());
		apriPost.gettTesto().setCaretPosition(0);

		try {

			ArrayList<Commento> listaCommenti = model.getCommenti(postDaAprire);
			CommentoAdapter commentoAdapter = view.adaptCommenti(listaCommenti);
			apriPost.getTabellaCommenti().setModel(commentoAdapter);

			boolean ciSonoCommenti = !listaCommenti.isEmpty();
			apriPost.getApriCommento().setVisible(ciSonoCommenti);

		} catch (ForumException ex) {

			ex.printStackTrace();
		}

		//PULIZIA LISTENER
		ActionListener[] listenerCrea = apriPost.getCreaCommento().getActionListeners();
		for (int i = 0; i < listenerCrea.length; i++) {
			apriPost.getCreaCommento().removeActionListener(listenerCrea[i]);
		}

		ActionListener[] listenerApri = apriPost.getApriCommento().getActionListeners();
		for (int i = 0; i < listenerApri.length; i++) {
			apriPost.getApriCommento().removeActionListener(listenerApri[i]);
		}

		ActionListener[] listenerElimina = apriPost.getEliminaCommento().getActionListeners();
		for (int i = 0; i < listenerElimina.length; i++) {
			apriPost.getEliminaCommento().removeActionListener(listenerElimina[i]);
		}

		ActionListener[] listenerIndietro = apriPost.getTornaIndietro().getActionListeners();
		for (int i = 0; i < listenerIndietro.length; i++) {
			apriPost.getTornaIndietro().removeActionListener(listenerIndietro[i]);
		}




		apriPost.getCreaCommento().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				gestisciCreazioneCommento(apriPost, postDaAprire, u);
			}
		});

		apriPost.getApriCommento().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int riga = apriPost.getTabellaCommenti().getSelectedRow();

				if (riga == -1) {

					view.mostraErrore("Devi prima selezionare un commento!");

					return;
				}

				CommentoAdapter adapter = (CommentoAdapter) apriPost.getTabellaCommenti().getModel();
				Commento commento = adapter.getCommentoAt(riga);

				/* * Passiamo un'azione di Callback (Runnable) come "biglietto di ritorno".
				 * Quando l'utente premerà "Indietro" dal pannello delle risposte, 
				 * questo blocco di codice verrà eseguito per:
				 * 1. Scaricare nuovamente i commenti dal Model (per mostrare eventuali modifiche).
				 * 2. Aggiornare la tabella della vista padre (apriPost).
				 * 3. Riportare in primo piano la finestra originale.
				 * Questo approccio rispetta l'MVC perché disaccoppia le due viste:
				 * la vista figlia non ha bisogno di sapere da chi è stata chiamata.
				 * 
				 * Runnable: Pacchetto di istruzioni passabile ad un metodo come fosse un oggetto
				 */
				espandiCommento(commento, postDaAprire, u, new Runnable() {
					
					@Override
					public void run() {

						try {

							apriPost.getTabellaCommenti().setModel(view.adaptCommenti(model.getCommenti(postDaAprire)));

						} catch (Exception ex) {

							ex.printStackTrace();
						}

						view.cambiaFinestra(apriPost);
					}
				});

				espandiCommento(commento, postDaAprire, u, new Runnable() {
					@Override
					public void run() {
						
						try {

							apriPost.getTabellaCommenti().setModel(view.adaptCommenti(model.getCommenti(postDaAprire)));

						} catch (Exception ex) {

							ex.printStackTrace();
						}

						view.cambiaFinestra(apriPost);
					}
				});
			}
		});

		apriPost.getEliminaCommento().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				gestisciEliminazioneCommento(apriPost, postDaAprire, u);
			}
		});

		apriPost.getTornaIndietro().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				view.pulisciMessaggi();
				aggiornaTabella();
				view.cambiaFinestra(fPanel); 

			}
		});

		view.cambiaFinestra(apriPost);
	}

	private void gestisciCreazionePost(Utente u) {

		view.pulisciMessaggi();
		CreaPostPanel creaPost = view.creaPost();
		view.cambiaFinestra(creaPost);

		creaPost.getCrea().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String titolo = creaPost.getTxtTitolo().getText().trim();
				String testo = creaPost.getTxtTesto().getText();
				String sottotitolo = creaPost.getTxtSottotitolo().getText();

				try {

					model.creaPost(u, testo, titolo, sottotitolo);

					creaPost.getTxtTitolo().setBorder(BorderFactory.createLineBorder(Color.GRAY));
					aggiornaTabella();
					view.cambiaFinestra(fPanel);
					
					//mette in coda l'output del messaggio di risposta
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {

							view.mostraSuccesso("Post creato con successo!");
						}
					});

				} catch (IllegalArgumentException ex) {

					creaPost.getTxtTitolo().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					view.mostraErrore(ex.getMessage());

				} catch (ForumException ex) {

					view.mostraErrore("Errore nel database.");
				}
			}
		});

		creaPost.getAnnulla().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				view.pulisciMessaggi();
				view.cambiaFinestra(fPanel);
				aggiornaTabella();
			}
		});
	}

	private void gestisciEliminazionePost(Utente u) {

		view.pulisciMessaggi();

		int riga = fPanel.getTabellaPost().getSelectedRow();

		if (riga == -1) {

			view.mostraErrore("Seleziona prima un post!");

			return;
		}

		PostAdapter adapter = (PostAdapter) fPanel.getTabellaPost().getModel();
		Post p = adapter.getPostAt(riga);

		if (!model.puoModificareOEliminare(u, p)) {

			view.mostraErrore("Non hai i permessi per eliminare questo contenuto!");

			return;
		}

		try {

			model.eliminaPost(p);
			aggiornaTabella();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					view.mostraSuccesso("Post eliminato!");
				}
			});

		} catch (ForumException ex) {

			view.mostraErrore("Errore durante l'eliminazione.");
		}
	}

	private void gestisciEliminazioneCommento(ApriPostPanel apriPost, Post postPadre, Utente u) {

		view.pulisciMessaggi();

		int riga = apriPost.getTabellaCommenti().getSelectedRow();

		if (riga == -1) {

			view.mostraErrore("Devi prima selezionare un commento da eliminare!");

			return;
		}

		CommentoAdapter adapter = (CommentoAdapter) apriPost.getTabellaCommenti().getModel();
		Commento commentoDaEliminare = adapter.getCommentoAt(riga);

		if (!model.puoModificareOEliminare(u, commentoDaEliminare)) {

			view.mostraErrore("Non hai i permessi per eliminare questo contenuto!");

			return; 
		}

		try {

			model.eliminaCommento(commentoDaEliminare);
			ArrayList<Commento> listaAggiornata = model.getCommenti(postPadre);
			CommentoAdapter nuovoAdapter = view.adaptCommenti(listaAggiornata);
			apriPost.getTabellaCommenti().setModel(nuovoAdapter);

			if (listaAggiornata.isEmpty()) {

				apriPost.getApriCommento().setVisible(false);
			}

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {

					view.mostraSuccesso("Commento eliminato con successo!");
				}
			});

		} catch (ForumException ex) {

			ex.printStackTrace();

			view.mostraErrore("Errore di eliminazione dal database.");
		}
	}

	private void gestisciCreazioneCommento(ApriPostPanel apriPost, Post postPadre, Utente u) {

		view.pulisciMessaggi();

		CreaCommentoPanel creaCommentoView = view.creaCommento();
		view.cambiaFinestra(creaCommentoView);

		creaCommentoView.getBtnPubblica().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String testo = creaCommentoView.getTxtTesto().getText();

				try {

					model.creaCommento(u, testo, postPadre, postPadre);

					creaCommentoView.getTxtTesto().setBorder(BorderFactory.createLineBorder(Color.GRAY));
					ArrayList<Commento> listaAggiornata = model.getCommenti(postPadre);
					CommentoAdapter nuovoAdapter = view.adaptCommenti(listaAggiornata);
					apriPost.getTabellaCommenti().setModel(nuovoAdapter);

					apriPost.getApriCommento().setVisible(true);
					view.cambiaFinestra(apriPost);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							view.mostraSuccesso("Commento pubblicato!");
						}
					});

				} catch (IllegalArgumentException ex) {

					creaCommentoView.getTxtTesto().setBorder(BorderFactory.createLineBorder(Color.RED));
					view.mostraErrore(ex.getMessage());

				} catch (ForumException ex) {

					ex.printStackTrace();
					view.mostraErrore("Errore durante la pubblicazione.");
					view.cambiaFinestra(apriPost);
				}
			}
		});

		creaCommentoView.getBtnAnnulla().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				view.cambiaFinestra(apriPost);
			}
		});
	}

	private void gestisciRicerca() {

		view.pulisciMessaggi();
		String parolaCercata = fPanel.getCercaPost().getText().trim();

		parolaCercata = fPanel.getTitoloPost().getText().trim();

		try {

			ArrayList<Post> postTrovati = model.cercaPostPerTitolo(parolaCercata);
			fPanel.getTabellaPost().setModel(view.adaptPost(postTrovati));

			if (postTrovati.isEmpty()) {

				view.mostraErrore("Nessun post trovato con questo titolo.");

			}

		} catch (ForumException ex) {

			view.mostraErrore("Errore durante la ricerca.");
		}
	}

	private void espandiCommento(Commento commentoDaAprire, Post postPadre, Utente u, Runnable azioneIndietro) {

		view.pulisciMessaggi();

		ApriCommentoPanel apriCommentoView = view.apriCommento();

		apriCommentoView.getTxtCommentoPadre().setText(commentoDaAprire.getTesto());
		apriCommentoView.getTxtCommentoPadre().setCaretPosition(0);

		String nomeAutore = commentoDaAprire.getNomeAutoreVisibile();
		apriCommentoView.getTxtCommentoPadre().setBorder(BorderFactory.createTitledBorder("Autore: " + nomeAutore));

		aggiornaTabellaRisposte(apriCommentoView, postPadre, commentoDaAprire);

		apriCommentoView.getBtnTornaIndietro().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				view.pulisciMessaggi();
				azioneIndietro.run();
			}
		});

		apriCommentoView.getBtnRispondi().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (u == null) {

					view.mostraErrore("Devi effettuare il login per rispondere!");

					return;
				}
				gestisciCreazioneRisposta(apriCommentoView, postPadre, commentoDaAprire, u);
			}
		});

		apriCommentoView.getBtnApriRisposta().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int riga = apriCommentoView.getTabellaRisposte().getSelectedRow();

				if (riga == -1) {

					view.mostraErrore("Seleziona prima una risposta dalla tabella!");

					return;
				}

				CommentoAdapter adapter = (CommentoAdapter) apriCommentoView.getTabellaRisposte().getModel();
				Commento sottoRisposta = adapter.getCommentoAt(riga);

				espandiCommento(sottoRisposta, postPadre, u, new Runnable() {
					@Override
					public void run() {

						aggiornaTabellaRisposte(apriCommentoView, postPadre, commentoDaAprire);
						view.cambiaFinestra(apriCommentoView);
					}
				});
			}
		});

		if (u != null) {

			apriCommentoView.getBtnElimina().setVisible(true);
			apriCommentoView.getBtnElimina().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					int riga = apriCommentoView.getTabellaRisposte().getSelectedRow();

					if (riga == -1) {

						view.mostraErrore("Devi selezionare una risposta da eliminare!");

						return;
					}

					CommentoAdapter adapter = (CommentoAdapter) apriCommentoView.getTabellaRisposte().getModel();
					Commento rispostaDaEliminare = adapter.getCommentoAt(riga);

					if (!model.puoModificareOEliminare(u, rispostaDaEliminare)) {

						view.mostraErrore("Non hai i permessi per eliminare questa risposta!");

						return; 
					}

					boolean successo = model.eliminaCommento(rispostaDaEliminare);

					if (successo) {

						aggiornaTabellaRisposte(apriCommentoView, postPadre, commentoDaAprire);

						SwingUtilities.invokeLater(new Runnable() {
							public void run() { view.mostraSuccesso("Risposta eliminata!"); }
						});

					} else {

						view.mostraErrore("Errore di eliminazione.");
					}
				}
			});

		} else {

			apriCommentoView.getBtnElimina().setVisible(false);
		}

		view.cambiaFinestra(apriCommentoView);
	}

	private void gestisciCreazioneRisposta(ApriCommentoPanel vistaPadre, Post postPadre, Commento commentoPadre, Utente u) {

		view.pulisciMessaggi();
		CreaCommentoPanel creaRispostaView = view.creaCommento();
		view.cambiaFinestra(creaRispostaView);

		creaRispostaView.getBtnPubblica().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String testo = creaRispostaView.getTxtTesto().getText();

				try {

					model.creaCommento(u, testo, postPadre, commentoPadre);

					creaRispostaView.getTxtTesto().setBorder(BorderFactory.createLineBorder(Color.GRAY));
					aggiornaTabellaRisposte(vistaPadre, postPadre, commentoPadre);
					view.cambiaFinestra(vistaPadre);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {

							view.mostraSuccesso("Risposta pubblicata!");
						}
					});

				} catch (IllegalArgumentException ex) {

					creaRispostaView.getTxtTesto().setBorder(BorderFactory.createLineBorder(Color.RED));

					view.mostraErrore(ex.getMessage());

				} catch (ForumException ex) {

					view.mostraErrore("Errore durante la pubblicazione della risposta.");
				}
			}
		});

		creaRispostaView.getBtnAnnulla().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				view.cambiaFinestra(vistaPadre);
			}
		});
	}

	private void aggiornaTabellaRisposte(ApriCommentoPanel panelView, Post postPadre, Commento commentoPadre) {

		try {

			ArrayList<Commento> risposte = model.getCommentiDiCommenti(postPadre, commentoPadre);
			CommentoAdapter adapter = view.adaptCommenti(risposte);
			panelView.getTabellaRisposte().setModel(adapter);

		} catch (ForumException e) {

			e.printStackTrace();
		}
	}

	private void gestisciOrdinamento() {

		view.pulisciMessaggi();
		int indiceScelto = fPanel.getComboOrdinamento().getSelectedIndex();

		Ordinamento tipoEnum = Ordinamento.values()[indiceScelto];

		try {

			ArrayList<Post> postOrdinati = model.ordinaPost(tipoEnum);
			fPanel.getTabellaPost().setModel(new PostAdapter(postOrdinati));

		} catch (ForumException ex) {

			view.mostraErrore("Errore durante l'ordinamento: " + ex.getMessage());
		}
	}
}