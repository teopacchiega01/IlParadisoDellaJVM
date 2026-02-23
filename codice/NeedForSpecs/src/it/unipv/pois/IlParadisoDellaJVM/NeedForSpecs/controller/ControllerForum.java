package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.account.Utente;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Commento;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.contenutiUtente.Post;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.Forum;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.ForumException;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy.ForumStrategy;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum.strategy.OrdinamentoStrategyFactory;
import it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.view.HomeFrame;
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
	private ForumPanel view;
	private HomeFrame mainFrame;
	private ForumView forumFrame;

	public ControllerForum(Forum model, ForumPanel view, HomeFrame mainFrame, Utente utente) {
		this.model = model;
		this.view = view;
		this.mainFrame = mainFrame;
		aggiornaTabella();
		addListeners(utente);
	}

	public ControllerForum(Forum model, ForumPanel view, ForumView forumFrame, Utente utente) {
		this.model = model;
		this.view = view;
		this.forumFrame = forumFrame;
		aggiornaTabella();
		addListeners(utente);
	}

	public ControllerForum(Forum model, ForumPanel view, Utente utente) {
		this.model = model;
		this.view = view;
		aggiornaTabella();
	}

	private void aggiornaTabella() {
		try {
			ArrayList<Post> listaPost = model.inizializzaForum();
			System.out.println("NUMERO POST CARICATI: " + listaPost.size());
			PostAdapter adapter = forumFrame.adaptPost(listaPost);
			view.getTabellaPost().setModel(adapter);
		} catch (ForumException e) {
			e.printStackTrace();
		}
	}

	public void addListeners(Utente u) {

		view.getApriPost().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gestisciAperturaPost(u);
			}
		});

		view.getCreaPost().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gestisciCreazionePost(u);
			}
		});

		view.getOrdina().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gestisciOrdinamento();
			}
		});

		view.getCercaPost().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gestisciRicerca();

			}
		});

		if (u != null) {
			view.getEliminaPost().setVisible(true);
			view.getEliminaPost().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gestisciEliminazionePost(u); 
				}
			});
		} else {
			view.getEliminaPost().setVisible(false);
		}

		view.getTornaAllaHome().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// forumFrame.cambiaFinestra(HomeFrame);
			}
		});
	}

	private void gestisciAperturaPost(Utente u) {
		forumFrame.pulisciMessaggi(); 

		int rigaSelezionata = view.getTabellaPost().getSelectedRow();

		if (rigaSelezionata == -1) {
			forumFrame.mostraErrore("Devi prima selezionare un post da aprire!");
			return;
		}

		PostAdapter adapter = (PostAdapter) view.getTabellaPost().getModel();
		Post postDaAprire = adapter.getPostAt(rigaSelezionata);

		ApriPostPanel apriPost = forumFrame.apriPost();

		apriPost.getlTitolo().setText(postDaAprire.getTitolo());
		if (postDaAprire.getSottotitolo() != null) {
			apriPost.getlSottotitolo().setText(postDaAprire.getSottotitolo());
		} else {
			apriPost.getlSottotitolo().setText("");
		}
		apriPost.gettTesto().setText(postDaAprire.getTesto());
		apriPost.gettTesto().setCaretPosition(0);

		try {
			ArrayList<Commento> listaCommenti = model.getCommenti(postDaAprire);
			CommentoAdapter commentoAdapter = forumFrame.adaptCommenti(listaCommenti);
			apriPost.getTabellaCommenti().setModel(commentoAdapter);

			boolean ciSonoCommenti = !listaCommenti.isEmpty();
			apriPost.getApriCommento().setVisible(ciSonoCommenti);

		} catch (ForumException ex) {
			ex.printStackTrace();
		}

		for (ActionListener al : apriPost.getCreaCommento().getActionListeners()) apriPost.getCreaCommento().removeActionListener(al);
		for (ActionListener al : apriPost.getApriCommento().getActionListeners()) apriPost.getApriCommento().removeActionListener(al);
		for (ActionListener al : apriPost.getEliminaCommento().getActionListeners()) apriPost.getEliminaCommento().removeActionListener(al);
		for (ActionListener al : apriPost.getTornaIndietro().getActionListeners()) apriPost.getTornaIndietro().removeActionListener(al);

		apriPost.getCreaCommento().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gestisciCreazioneCommento(apriPost, postDaAprire, u);
			}
		});

		// MODIFICATO QUI: Avvia la logica a cascata!
		apriPost.getApriCommento().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int riga = apriPost.getTabellaCommenti().getSelectedRow();
				if (riga == -1) {
					forumFrame.mostraErrore("Devi prima selezionare un commento!");
					return;
				}
				CommentoAdapter adapter = (CommentoAdapter) apriPost.getTabellaCommenti().getModel();
				Commento commento = adapter.getCommentoAt(riga);

				espandiCommento(commento, postDaAprire, u, apriPost);
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
				forumFrame.pulisciMessaggi();
				aggiornaTabella();
				forumFrame.cambiaFinestra(view);
			}
		});

		forumFrame.cambiaFinestra(apriPost);
	}

	private void gestisciCreazionePost(Utente u) {
		forumFrame.pulisciMessaggi();
		CreaPostPanel creaPost = forumFrame.creaPost();
		forumFrame.cambiaFinestra(creaPost);

		creaPost.getCrea().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String titolo = creaPost.getTxtTitolo().getText().trim();
				if (titolo.isEmpty()) {
					creaPost.getTxtTitolo().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					forumFrame.mostraErrore("Devi inserire un titolo!");
					return;
				}

				try {
					model.creaPost(u, creaPost.getTxtTesto().getText(), LocalDateTime.now(), titolo, creaPost.getTxtSottotitolo().getText());
					aggiornaTabella();
					forumFrame.cambiaFinestra(view);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							forumFrame.mostraSuccesso("Post creato con successo!");
						}
					});

				} catch (Exception ex) {
					forumFrame.mostraErrore("Errore nel database.");
				}
			}
		});

		creaPost.getAnnulla().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				forumFrame.pulisciMessaggi();
				forumFrame.cambiaFinestra(view);
				aggiornaTabella();
			}
		});
	}

	private void gestisciEliminazionePost(Utente u) {
		forumFrame.pulisciMessaggi();
		int riga = view.getTabellaPost().getSelectedRow();
		if (riga == -1) {
			forumFrame.mostraErrore("Seleziona prima un post!");
			return;
		}

		PostAdapter adapter = (PostAdapter) view.getTabellaPost().getModel();
		Post p = adapter.getPostAt(riga);

		if (!u.isStaff() && !p.getAutore().getUser_name().equals(u.getUser_name())) {
			forumFrame.mostraErrore("Non puoi eliminare post altrui!");
			return;
		}

		try {
			model.eliminaPost(p);
			aggiornaTabella();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					forumFrame.mostraSuccesso("Post eliminato!");
				}
			});

		} catch (ForumException ex) {
			forumFrame.mostraErrore("Errore durante l'eliminazione.");
		}
	}

	private void gestisciEliminazioneCommento(ApriPostPanel apriPost, Post postPadre, Utente u) {
		forumFrame.pulisciMessaggi();

		int riga = apriPost.getTabellaCommenti().getSelectedRow();

		if (riga == -1) {
			forumFrame.mostraErrore("Devi prima selezionare un commento da eliminare!");
			return;
		}

		CommentoAdapter adapter = (CommentoAdapter) apriPost.getTabellaCommenti().getModel();
		Commento commentoDaEliminare = adapter.getCommentoAt(riga);

		boolean isStaff = u.isStaff();
		boolean isAutore = commentoDaEliminare.getAutore() != null && commentoDaEliminare.getAutore().getUser_name().equals(u.getUser_name());

		if (!isStaff && !isAutore) {
			forumFrame.mostraErrore("Non hai i permessi per eliminare il commento di un altro utente!");
			return; 
		}

		try {
			model.eliminaCommento(commentoDaEliminare);
			ArrayList<Commento> listaAggiornata = model.getCommenti(postPadre);
			CommentoAdapter nuovoAdapter = forumFrame.adaptCommenti(listaAggiornata);
			apriPost.getTabellaCommenti().setModel(nuovoAdapter);

			if (listaAggiornata.isEmpty()) {
				apriPost.getApriCommento().setVisible(false);
			}

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					forumFrame.mostraSuccesso("Commento eliminato con successo!");
				}
			});

		} catch (ForumException ex) {
			ex.printStackTrace();
			forumFrame.mostraErrore("Errore di eliminazione dal database.");
		}
	}

	private void gestisciCreazioneCommento(ApriPostPanel apriPost, Post postPadre, Utente u) {
		forumFrame.pulisciMessaggi();

		CreaCommentoPanel creaCommentoView = forumFrame.creaCommento();
		forumFrame.cambiaFinestra(creaCommentoView);

		creaCommentoView.getBtnPubblica().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String testo = creaCommentoView.getTxtTesto().getText();

				if (testo != null && !testo.trim().isEmpty()) {
					try {
						model.creaCommento(u, testo, LocalDateTime.now(), postPadre, postPadre);
						ArrayList<Commento> listaAggiornata = model.getCommenti(postPadre);
						CommentoAdapter nuovoAdapter = forumFrame.adaptCommenti(listaAggiornata);
						apriPost.getTabellaCommenti().setModel(nuovoAdapter);

						apriPost.getApriCommento().setVisible(true);
						forumFrame.cambiaFinestra(apriPost);

						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								forumFrame.mostraSuccesso("Commento pubblicato!");
							}
						});

					} catch (ForumException ex) {
						ex.printStackTrace();
						forumFrame.mostraErrore("Errore durante la pubblicazione.");
						forumFrame.cambiaFinestra(apriPost);
					}
				} else {
					creaCommentoView.getTxtTesto().setBorder(BorderFactory.createLineBorder(Color.RED));
				}
			}
		});

		creaCommentoView.getBtnAnnulla().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				forumFrame.cambiaFinestra(apriPost);
			}
		});
	}

	private void gestisciRicerca() {

		forumFrame.pulisciMessaggi();


		String parolaCercata = view.getTitoloPost().getText().trim().toLowerCase();

		try {

			ArrayList<Post> tuttiIPost = model.inizializzaForum();


			if (parolaCercata.isEmpty()) {
				PostAdapter adapter = forumFrame.adaptPost(tuttiIPost);
				view.getTabellaPost().setModel(adapter);
				return;
			}

			ArrayList<Post> postTrovati = new ArrayList<>();

			for (Post p : tuttiIPost) {

				if (p.getTitolo().toLowerCase().contains(parolaCercata)) {
					postTrovati.add(p);
				}
			}


			PostAdapter adapterRicerca = forumFrame.adaptPost(postTrovati);
			view.getTabellaPost().setModel(adapterRicerca);

			if (postTrovati.isEmpty()) {
				forumFrame.mostraErrore("Nessun post trovato con questo titolo.");
			}

		} catch (ForumException ex) {
			ex.printStackTrace();
			forumFrame.mostraErrore("Errore durante la ricerca.");
		}

	}

	// NUOVO METODO RICORSIVO PER ESPANDERE LE RISPOSTE
	private void espandiCommento(Commento commentoDaAprire, Post postPadre, Utente u, JPanel vistaPrecedente) {
		forumFrame.pulisciMessaggi();

		ApriCommentoPanel apriCommentoView = forumFrame.apriCommento();

		apriCommentoView.getTxtCommentoPadre().setText(commentoDaAprire.getTesto());
		apriCommentoView.getTxtCommentoPadre().setCaretPosition(0);
		String nomeAutore = (commentoDaAprire.getAutore() != null) ? commentoDaAprire.getAutore().getUser_name() : "Sconosciuto";
		apriCommentoView.getTxtCommentoPadre().setBorder(BorderFactory.createTitledBorder("Stai leggendo il commento di: " + nomeAutore));

		aggiornaTabellaRisposte(apriCommentoView, postPadre, commentoDaAprire);

		apriCommentoView.getBtnTornaIndietro().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				forumFrame.pulisciMessaggi();
				forumFrame.cambiaFinestra(vistaPrecedente);

				// Rinfresca la vista precedente
				if (vistaPrecedente instanceof ApriPostPanel) {
					try {
						((ApriPostPanel) vistaPrecedente).getTabellaCommenti().setModel(forumFrame.adaptCommenti(model.getCommenti(postPadre)));
					} catch (Exception ex) {}
				}
			}
		});

		apriCommentoView.getBtnRispondi().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (u == null) {
					forumFrame.mostraErrore("Devi effettuare il login per rispondere!");
					return;
				}
				gestisciCreazioneRisposta(apriCommentoView, postPadre, commentoDaAprire, u);
			}
		});

		// NAVIGAZIONE A CASCATA
		apriCommentoView.getBtnApriRisposta().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int riga = apriCommentoView.getTabellaRisposte().getSelectedRow();
				if (riga == -1) {
					forumFrame.mostraErrore("Seleziona prima una risposta dalla tabella!");
					return;
				}
				CommentoAdapter adapter = (CommentoAdapter) apriCommentoView.getTabellaRisposte().getModel();
				Commento sottoRisposta = adapter.getCommentoAt(riga);

				// Richiama se stesso per scendere di livello
				espandiCommento(sottoRisposta, postPadre, u, apriCommentoView);
			}
		});

		if (u != null) {
			apriCommentoView.getBtnElimina().setVisible(true);
			apriCommentoView.getBtnElimina().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int riga = apriCommentoView.getTabellaRisposte().getSelectedRow();
					if (riga == -1) {
						forumFrame.mostraErrore("Devi selezionare una risposta da eliminare!");
						return;
					}
					CommentoAdapter adapter = (CommentoAdapter) apriCommentoView.getTabellaRisposte().getModel();
					Commento rispostaDaEliminare = adapter.getCommentoAt(riga);

					boolean isStaff = u.isStaff();
					boolean isAutore = rispostaDaEliminare.getAutore() != null && rispostaDaEliminare.getAutore().getUser_name().equals(u.getUser_name());

					if (!isStaff && !isAutore) {
						forumFrame.mostraErrore("Non hai i permessi per eliminare questa risposta!");
						return; 
					}

					boolean successo = model.eliminaCommento(rispostaDaEliminare);

					if (successo) {
						aggiornaTabellaRisposte(apriCommentoView, postPadre, commentoDaAprire);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() { forumFrame.mostraSuccesso("Risposta eliminata!"); }
						});
					} else {
						forumFrame.mostraErrore("Errore di eliminazione.");
					}
				}
			});
		} else {
			apriCommentoView.getBtnElimina().setVisible(false);
		}

		forumFrame.cambiaFinestra(apriCommentoView);
	}

	private void gestisciCreazioneRisposta(ApriCommentoPanel vistaPadre, Post postPadre, Commento commentoPadre, Utente u) {
		forumFrame.pulisciMessaggi();
		CreaCommentoPanel creaRispostaView = forumFrame.creaCommento();
		forumFrame.cambiaFinestra(creaRispostaView);

		creaRispostaView.getBtnPubblica().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String testo = creaRispostaView.getTxtTesto().getText();

				if (testo != null && !testo.trim().isEmpty()) {

					boolean successo = model.creaCommento(u, testo, LocalDateTime.now(), postPadre, commentoPadre);

					if (successo) {
						aggiornaTabellaRisposte(vistaPadre, postPadre, commentoPadre);
						forumFrame.cambiaFinestra(vistaPadre);

						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								forumFrame.mostraSuccesso("Risposta pubblicata!");
							}
						});

					} else {
						forumFrame.mostraErrore("Errore durante la pubblicazione della risposta.");
					}

				} else {
					creaRispostaView.getTxtTesto().setBorder(BorderFactory.createLineBorder(Color.RED));
				}
			}
		});

		creaRispostaView.getBtnAnnulla().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				forumFrame.cambiaFinestra(vistaPadre);
			}
		});
	}

	private void aggiornaTabellaRisposte(ApriCommentoPanel view, Post postPadre, Commento commentoPadre) {
		try {
			ArrayList<Commento> risposte = model.getCommentiDiCommenti(postPadre, commentoPadre);
			CommentoAdapter adapter = forumFrame.adaptCommenti(risposte);
			view.getTabellaRisposte().setModel(adapter);
		} catch (ForumException e) {
			e.printStackTrace();
		}
	}

	private void gestisciOrdinamento() {
		
		forumFrame.pulisciMessaggi();

		String scelta = (String) view.getComboOrdinamento().getSelectedItem();

		PostAdapter adapter = (PostAdapter) view.getTabellaPost().getModel();
		ArrayList<Post> listaDaOrdinare = adapter.getListaPost();

		if (listaDaOrdinare == null || listaDaOrdinare.isEmpty()) return;

		ForumStrategy strategy = OrdinamentoStrategyFactory.getInstance().StringaToStrategy(scelta);

		if (strategy != null) {
			
			strategy.ordinamento(listaDaOrdinare);
			view.getTabellaPost().setModel(new PostAdapter(listaDaOrdinare));
			
		} else {
			
			forumFrame.mostraErrore("Errore: Impossibile caricare gli ordinamenti");
		}
	}
}