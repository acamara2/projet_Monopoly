package modeleSwing;

import images.*;
import modeleMonopoly.plateau.*;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.lang.Object;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* CETTE CLASSE CONTIENT AUSSI BIEN LA VUE DU MONOPOLY ET
 * LES CONTROLEURS ASSOCIEES.
 * @author CAMARA ABABACAR
 * @version 2.1
 */

public class VueMonopoly {
	////////// LE MODELE //////////////////////////////////
	private JeuReel jeuPrincipal;
	private Joueur[] tabJoueur;
	@SuppressWarnings("unused")
	private int nbJoueur;
	/////// LES ELELEMNTS A RECUPERER /////////////////////
	JTextField nomJoueur;
	JTextField presentationJoueurEnCours;
	JTextField nomText = null;
	private String nomJ = null;
	private String formePion;
	private String couleurPion;
	JDialog joueurInit;
	boolean estOk;
	/////////// BOUTONS ET CONTENEURS ///////////////////
	JButton suivantBienvenue;
	JFrame vueBienvenue;
	JFrame fenetrePrincipale;
	@SuppressWarnings("rawtypes")
	JComboBox forme;
	@SuppressWarnings("rawtypes")
	JComboBox couleur;
	JDialog ordreDeJeu;
	JLayeredPane vuePrincipale;
	JPanel etatJoueur;
	JButton[] cases;
	JLayeredPane pionSurCase[];
	JButton lancerDe;
	JButton tourSuivant;
	JButton construire;
	JButton vendre;
	JButton hypothequer;
	JButton leverHypotheque;
	JButton infoSupplement;
	JButton echange;
	Map<Integer, ImageIcon> Pions;
	Map<Integer, ImageIcon> Pions2;
	JLabel[] lesNoms;
	JLabel[] lesJoueurs;
	JLabel jlabelPrincipal;
	JTextArea[] infosJoueurs;
	Box[] hBoxOrdre;
	Box vboxOrdre;
	Box boiteControle;
	Box boiteControle2;
	Box commande;
	JLayeredPane conteneurPrincipal;
	JLabel[] de;
	JLabel[] lab;
	JLabel[] lab2;
	JLabel logoJoueurEnCours;
	JTextArea infoJoueurEnCours;
	/** Case de texte affichant l'ensemble d'actions realises pendant un tour */
	JTextArea HistoriqueTour;
	/** Liste des commandes realises par un joueur pendant le tour courrant */
	ArrayList<String> CommandesTour;
	Box boiteDe;
	int t = 27;
	int tailleLogo = 150;
	private static final Map<Integer, ImageIcon> lesDes = new HashMap<Integer, ImageIcon>();
	static {
		for (int i = 1; i <= 6; i++) {
			lesDes.put(i, new ImageIcon("de" + i + ".jpg"));
		}
	}

	private boolean estCommencement;
	private boolean aLancer = false;
	private boolean monTourLancerDe = true;

	/**
	 * CONSTRUIT LA VUE MONOPOLY
	 * 
	 * @param jeuPrincipal
	 * @param b
	 */

	public VueMonopoly(JeuReel jeu) {
		estCommencement = true;
		definirFenetreDefond();
		this.jeuPrincipal = jeu;
		if (estCommencement) {
			afficherBienvenue();
			saisirNbjoueur();
			initialiserJoueurs();
			definirOrdreJeu();
			// On catualise le tableau de joueurs avec le bon ordre apres le premier
			// tirages des des
			this.jeuPrincipal.setJoueurs(tabJoueur, nbJoueur);
			presenterOrdreJeu();
			vueBienvenue.dispose();
		}
		redefinirFenetrePrincipal();
		etatJoueur = new JPanel();
		definirBandeEtatsJoueurs();
		recueillirInfosJoueurs();
		disposerInfo();
		setCases();
		afficherCommandes();
		afficherDe();
		initialiserPositionPions();
		jeuPrincipal.setNextJoueur();
		logoJoueurEnCours = new JLabel();
		logoJoueurEnCours.setBounds(200, 250, tailleLogo, tailleLogo);
		logoJoueurEnCours.setIcon(Pions2.get(jeuPrincipal.getJoueurEnCours().getNumero()));
		presentationJoueurEnCours = new JTextField("JOUEUR EN COURS : " + jeuPrincipal.getJoueurEnCours().getNom());
		presentationJoueurEnCours.setBackground(new Color(216, 229, 208));
		presentationJoueurEnCours.setEditable(false);
		presentationJoueurEnCours.setBounds(300, 210, 200, 20);
		infoJoueurEnCours = new JTextArea(jeuPrincipal.getJoueurEnCours().getPlusInfo(), 300, 150);
		infoJoueurEnCours.setEditable(false);
		infoJoueurEnCours.setBackground(new Color(216, 229, 208));
		infoJoueurEnCours.setBounds(370, 250, 300, 150);
		conteneurPrincipal.add(logoJoueurEnCours, Integer.valueOf(7));
		conteneurPrincipal.add(presentationJoueurEnCours, Integer.valueOf(7));
		conteneurPrincipal.add(infoJoueurEnCours, Integer.valueOf(7));
		this.fenetrePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetrePrincipale.pack();
		this.fenetrePrincipale.setVisible(true);


	}

	/////////////////// FIN CONSTRUCTEUR /////////////////////////////

	/////////// FONCITONS OU PROCEDURES UTILSES A LA GESTION DU JEU//////////
	/*
	 * PREND EN ENTREE/SORTIE UN TABLEAU QU'IL INVERSE
	 * 
	 * @param array
	 */
	void invertUsingFor(Object[] array) {
		for (int i = 0; i < array.length / 2; i++) {
			Object temp = array[i];
			array[i] = array[array.length - 1 - i];
			array[array.length - 1 - i] = temp;
		}
	}

	/*
	 * APRES INSCRIPTION DES JOUEURS DANS LE PLATEAU, CHACUN DES JOUEURS DOIT LANCER
	 * LE DE POUR DEFINIR L'ORDRE DANS LEQUEL LE JEU VA SE REALISER CETTE METHODE
	 * FAIT LANCER LE DE A CHACUN DES JOUEURS ET REAGENCE LE TABLEAU DANS L'ORDRE DE
	 * JEU
	 */
	private void definirOrdreJeu() {
		for (int i = 0; i < nbJoueur; i++) {
			jeuPrincipal.lancerDe();
			tabJoueur[i].setLancerDepart(jeuPrincipal.getResultatDe());
		}
		Arrays.sort(tabJoueur);
		invertUsingFor(tabJoueur);
		redefinirNumero();
		/*
		 * Maintenant que les identifiants des joueurs sont bien places on va
		 * initialiser les pions
		 */
		initialiserPions();
	}

	/**
	 * Cette methode prend l'ensemble des joueurs et associe e chaque joueur
	 * l'image correspondant au pion qu'il a choisit e travers un Map
	 * 
	 * @author Lucas Oros
	 * 
	 */
	private void initialiserPions() {
		/* On initialise le map d'abord */
		this.Pions = new HashMap<Integer, ImageIcon>();
		this.Pions2 = new HashMap<Integer, ImageIcon>();
		/*
		 * Pour chaque joueur on conserve l'image de son pion avec la valeur de sont
		 * identifiant
		 */
		ImageIcon image, image2;
		JLabel label = new JLabel();
		JLabel label2 = new JLabel();
		label2.setSize(new Dimension(tailleLogo, tailleLogo));
		label.setSize(new Dimension(t, t));
		for (int i = 0; i < this.nbJoueur; i++) {
			Joueur j = this.tabJoueur[i];
			formePion = j.getPion().getForme().toLowerCase();
			couleurPion = j.getPion().getCouleur();
			image = traitementImg(formePion + "-" + couleurPion + ".png", label);
			image2 = traitementImg(formePion + "-" + couleurPion + ".png", label2);
			Pions.put(j.getNumero(), image);
			Pions2.put(j.getNumero(), image2);
		}
	}

	/*
	 * REGARDE LA POSITION DU JOUEUR DANS LE TABLEAU PUIS LUI ATTRIBUE CE NUMERO
	 */
	private void redefinirNumero() {
		for (int i = 0; i < nbJoueur; i++) {
			jeuPrincipal.lancerDe();
			tabJoueur[i].setNumero(i + 1);
		}
	}

	private boolean aUnGagnant() {
		return false;
	}

	///////////// FONCTION UTILE POUR UN MEILLEUR AFFICHAGE /////////////
	/*
	 * FONCTION DEFINISSANT L'ARRIERE PLAN, DURANT LA PRISE D'INFORMATION PERMET
	 * L'AFFICHAGE DU PLATEAU MONOPOLY EN ARRIERE PLAN DES JDIALOG
	 */

	@SuppressWarnings("deprecation")
	public void definirFenetreDefond() {
		this.vueBienvenue = new JFrame("Monopoly");
		this.vueBienvenue.setSize(800, 700);
		this.vueBienvenue.setLocationRelativeTo(null);
		vueBienvenue.getContentPane().setLayout(new FlowLayout());
		conteneurPrincipal = new JLayeredPane();
		jlabelPrincipal = new JLabel();
		jlabelPrincipal.setSize(800, 700);
		ImageIcon icone = traitementImg("imagePlateauDeJeu.jpg", jlabelPrincipal);
		jlabelPrincipal.setIcon(icone);
		conteneurPrincipal.setPreferredSize(new Dimension(800, 700));
		conteneurPrincipal.add(jlabelPrincipal, Integer.valueOf(1));
		vueBienvenue.getContentPane().add(conteneurPrincipal);
		vueBienvenue.validate();
		this.vueBienvenue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.vueBienvenue.pack();
		this.vueBienvenue.setVisible(true);

	}

	/*
	 * AFFICHE UN MESSAGE DE BIENVENUE GRACE A UN JDIALOG DEUX OPTIONS SONT
	 * POSSIBLES : SUIVANT OU LECTURE DU MANUEL
	 */
	@SuppressWarnings("static-access")
	public void afficherBienvenue() {
		this.estCommencement = false;
		String message = "Bienvenue dans le jeu Monopoly.\nPour accéder au manuel utilisateur, appuyez sur 'Man'. Pour commencer le jeu, appuyez sur 'Suivant'.";
		JOptionPane bienvenue = new JOptionPane();
		String option[] = { "Man", "Suivant" };
		int retour;
		do {
			retour = bienvenue.showOptionDialog(vueBienvenue, message, "Bienvenue !!!", 1, 1,
					new ImageIcon(
							new ImageIcon("hello.jpeg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),
					option, option[1]);
			if (retour == 0) {
				// TO DO afficher un manuel

			} else if (retour == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			}
		} while (retour != 1);
	}

	/*
	 * AFFICHE LE NOMBRE DE JOUEUR DE JOUEUR VOULANT ETRE PRIS A PARTIR DE
	 * JOptionPANE, LE NOMBRE VARIANT DE 2 A 4
	 */
	public void saisirNbjoueur() {
		boolean estCorrectSaisieNbJoueur = false;
		while (!estCorrectSaisieNbJoueur) {
			Object selected = null;
			try {
				Integer valeurPossibles[] = { 2, 3, 4 };
				selected = JOptionPane.showInputDialog(null, "Choisissez un nombre.", "Nb de joueurs",
						JOptionPane.INFORMATION_MESSAGE, null, valeurPossibles, valeurPossibles[0]);
				nbJoueur = (Integer) selected;
				estCorrectSaisieNbJoueur = true;
			} catch (Exception e) {
				if (selected == null) {
					System.exit(9);
				}
				estCorrectSaisieNbJoueur = false;
			}

		}
	}

	/*
	 * PERMET D'INITIALISER LES JOUEURS EN LEUR DEMANDANT LEUR NOM, LA FORME DU PION
	 * ET SA COULEUR A PARTIR D'UN JOptionPane MUNI DE JComboBox.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialiserJoueurs() {
		tabJoueur = new Joueur[nbJoueur];
		Pion p = null;
		forme = new JComboBox(new String[] { "Chapeau", "Voiture", "Bateau", "Chat" });
		couleur = new JComboBox(new String[] { "vert", "jaune", "rouge", "bleu", "noir" });
		for (int i = 0; i < nbJoueur; i++) {
			estOk = false;

			while (!estOk) {
				try {
					///// PARAMETRAGE DU JDialog///////////////////
					joueurInit = new JDialog(vueBienvenue, "Initialisation Joueur " + (i + 1), true);
					joueurInit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					joueurInit.setSize(300, 200);
					joueurInit.setLocationRelativeTo(vueBienvenue);
					//// LES LISTENERS ///////////////////////////
					ActionListener passerSuivant = new PasserSuivant();
					/////////////// ZONES DE SAISIE GRAPHIQUE //////
					JLabel formeLabel = new JLabel("Forme du pion : ");
					Box hBox0 = Box.createHorizontalBox();
					hBox0.add(formeLabel);
					hBox0.add(forme);
					/// nom et prenom
					JLabel nomLabel = new JLabel("Nom : ");
					nomText = new JTextField(10);
					nomText.setMaximumSize(nomText.getPreferredSize());
					Box hBox1 = Box.createHorizontalBox();
					hBox1.add(nomLabel);
					hBox1.add(Box.createHorizontalStrut(5));
					hBox1.add(nomText);

					JLabel couleurLabel = new JLabel("Couleur de votre pion : ");
					Box hBox2 = Box.createHorizontalBox();
					hBox2.add(couleurLabel);
					hBox2.add(couleur);
					Box hBox3 = Box.createHorizontalBox();
					JButton ok = new JButton("Suivant");
					ok.addActionListener(passerSuivant);
					JButton annuler = new JButton("Quitter");
					hBox3.add(annuler);
					hBox3.add(Box.createGlue());
					hBox3.add(ok);
					Box vBox = Box.createVerticalBox();
					vBox.add(hBox1);
					vBox.add(hBox2);
					vBox.add(hBox0);
					vBox.add(Box.createGlue());
					vBox.add(hBox3);
					joueurInit.add(vBox, BorderLayout.CENTER);
					joueurInit.setVisible(true);
				} catch (Exception e) {
					System.exit(1);
				}
			}
			/////// INITIALISATION DES JOUEURS //////////
			switch (couleurPion) {
			case "vert":
				p = new Pion(formePion, Color.GREEN);
				break;
			case "jaune":
				p = new Pion(formePion, Color.YELLOW);
				break;
			case "rouge":
				p = new Pion(formePion, Color.RED);
				break;
			case "bleu":
				p = new Pion(formePion, Color.BLUE);
			default:
				p = new Pion(formePion, Color.black);
			}
			tabJoueur[i] = new Joueur(p, i, nomJ);
		}
	}

	/*
	 * PRESENTATION DE L'ORDRE DE JEU A PARTIR D'UN JDialog
	 */
	public void presenterOrdreJeu() {
		estOk = false;
		while (!estOk) {
			try {
				//// LISTENER//////////////////////////////////
				ActionListener passerSuivant = new Suivant();
				ActionListener quitter = new Quitter();
				//// ZONE GRAPHIQUE ///////////////////////////
				ordreDeJeu = new JDialog(vueBienvenue, "Ordre du jeu :", true);
				ordreDeJeu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ordreDeJeu.setSize(300, 200);
				ordreDeJeu.setLocationRelativeTo(vueBienvenue);
				lesNoms = new JLabel[nbJoueur];
				lesJoueurs = new JLabel[nbJoueur];
				hBoxOrdre = new Box[nbJoueur];
				vboxOrdre = Box.createVerticalBox();
				infosJoueurs = new JTextArea[nbJoueur];
				for (int i = 0; i < nbJoueur; i++) {
					lesNoms[i] = new JLabel("Joueur numéro " + (i + 1) + " : ");
					lesJoueurs[i] = new JLabel(tabJoueur[i].getNom());
					hBoxOrdre[i] = Box.createHorizontalBox();
					hBoxOrdre[i].add(lesNoms[i]);
					hBoxOrdre[i].add(Box.createHorizontalStrut(7));
					hBoxOrdre[i].add(lesJoueurs[i]);
					vboxOrdre.add(hBoxOrdre[i]);
				}
				Box hBox3 = Box.createHorizontalBox();
				JButton next = new JButton("Suivant");
				next.addActionListener(passerSuivant);
				JButton annuler = new JButton("Quitter");
				annuler.addActionListener(quitter);
				hBox3.add(annuler);
				hBox3.add(Box.createGlue());
				hBox3.add(next);
				vboxOrdre.add(hBox3);
				ordreDeJeu.add(vboxOrdre, BorderLayout.CENTER);
				ordreDeJeu.setVisible(true);
			} catch (Exception e) {
				System.exit(1);
			}
		}
	}

	/*
	 * DEFINIR UN BOUTON AU NIVEAU DE CHAQUE CASE EVENTUELLEMENT LORSQUE L'ON CLIQUE
	 * SUR CES DERNIERS DONNENT LES INFORMATIONS DE LA CASES (LES LISTENERS DONNANT
	 * CES INFORMATIONS NE SONT PAS ENCORES IMPLANTES)
	 */
	@SuppressWarnings("deprecation")
	public void setCases() {
		cases = new JButton[40];
		pionSurCase = new JLayeredPane[40];
		for (int i = 0; i < 40; i++) {
			cases[i] = new JButton();
			cases[i].setBorderPainted(true);
			cases[i].setContentAreaFilled(false);
			cases[i].setFocusPainted(false);
			cases[i].setLayout(new FlowLayout());
			pionSurCase[i] = new JLayeredPane();
			pionSurCase[i].setLayout(new FlowLayout());
			if (i > 0 && i < 10) {
				pionSurCase[i].setBounds(697 - (i) * 66, 607, 66, 93);
				cases[i].setBounds(697 - (i) * 66, 607, 66, 93);
			} else if (10 < i && i < 20) {
				pionSurCase[i].setBounds(0, 607 - (i - 10) * 58, 103, 58);
				cases[i].setBounds(0, 607 - (i - 10) * 58, 103, 58);
			} else if (20 < i && i < 30) {
				pionSurCase[i].setBounds((i - 20) * 66 + 40, 0, 65, 93);
				cases[i].setBounds((i - 20) * 66 + 40, 0, 65, 93);
			} else {
				pionSurCase[i].setBounds(697, 58 * (i - 30) + 35, 103, 58);
				cases[i].setBounds(697, 58 * (i - 30) + 35, 103, 58);
			}
			// conteneurPrincipal.add(cases[i], Integer.valueOf(4 + i));
			conteneurPrincipal.add(pionSurCase[i], Integer.valueOf(5 + i));
		}
		cases[20].setBounds(0, 0, 103, 90);
		cases[30].setBounds(697, 0, 103, 90);
		cases[0].setBounds(697, 607, 103, 90);
		cases[10].setBounds(0, 607, 103, 90);
		pionSurCase[20].setBounds(0, 0, 103, 90);
		pionSurCase[30].setBounds(697, 0, 103, 90);
		pionSurCase[0].setBounds(697, 607, 103, 90);
		pionSurCase[10].setBounds(0, 607, 103, 90);

	}

	/**
	 * Methode qui ajoute les images des pions des joueurs e la case de depart
	 * 
	 */
	public void initialiserPositionPions() {
		lab = new JLabel[nbJoueur];
		lab2 = new JLabel[nbJoueur];
		for (int i = 0; i < nbJoueur; i++) {
			lab[i] = new JLabel();
			lab[i].setSize(t, t);
			lab[i].setIcon(Pions.get(tabJoueur[i].getNumero()));
			pionSurCase[0].add(lab[i], Integer.valueOf(82));
		}

	}

	/**
	 * Methode qui met e jour la position de l'image representant le pion du joueur
	 * principal en fonction de la position du joueur selon le modele
	 */
	public void majPion() {

	}

	/*
	 * APRES AVOIR RECUEILLI LES INFORMATIONS LA FENETRE DE FOND EST REDEFINIS POUR
	 * FAIRE APPARAITRES LES COMMANDES ET LES ETATS DES JOUEURS.
	 */
	public void redefinirFenetrePrincipal() {
		this.fenetrePrincipale = new JFrame("Monopoly");
		this.fenetrePrincipale.setSize(800, 700);
		this.fenetrePrincipale.setLocationRelativeTo(null);
		fenetrePrincipale.getContentPane().setLayout(new FlowLayout());
		fenetrePrincipale.getContentPane().add(conteneurPrincipal);
		fenetrePrincipale.validate();
	}

	/*
	 * DEFINIR LE CONTENEUR DE PLUS GRANDE PROFONDEUR(GRAPHIQUE ) DE LA PARTIE
	 * DONNANT LES INFORMATIONS DES JOUEURS.
	 */
	@SuppressWarnings("deprecation")
	public void definirBandeEtatsJoueurs() {
		ImageIcon icon = traitementImg("plateau.jpg", jlabelPrincipal);
		jlabelPrincipal.setIcon(icon);
		etatJoueur.setLocation(20, 20);
		etatJoueur.setBounds(800, 00, 400, 700);
		etatJoueur.setBackground(new Color(216, 229, 208));
		conteneurPrincipal.setPreferredSize(new Dimension(1200, 700));
		conteneurPrincipal.add(etatJoueur, Integer.valueOf(2));
	}

	/*
	 * RECUEILLE LES INFOS DU JOUEURS DANS LE MODELE
	 */
	public void recueillirInfosJoueurs() {
		for (int i = 0; i < nbJoueur; i++) {
			infosJoueurs[i] = new JTextArea(tabJoueur[i].getInfo(), 300, 100);
			infosJoueurs[i].setEditable(false);
			infosJoueurs[i].setBackground(new Color(10, 186, 181));
			hBoxOrdre[i] = Box.createVerticalBox();
			hBoxOrdre[i].add(lesJoueurs[i]);
			hBoxOrdre[i].add(Box.createVerticalStrut(7));
			hBoxOrdre[i].add(infosJoueurs[i]);

		}
	}

	/*
	 * DISPOSE LES INFOS DANS INFOS_JOUEURS
	 */
	@SuppressWarnings("deprecation")
	private void disposerInfo() {

		switch (nbJoueur) {
		case 2:

			hBoxOrdre[0].setBounds(820, 50, 350, 120);
			conteneurPrincipal.add(hBoxOrdre[0], Integer.valueOf(51));
			hBoxOrdre[1].setBounds(820, 350, 350, 120);
			conteneurPrincipal.add(hBoxOrdre[1], Integer.valueOf(51));
			break;
		case 3:
			hBoxOrdre[0].setBounds(850, 20, 300, 120);
			conteneurPrincipal.add(hBoxOrdre[0], Integer.valueOf(50));
			hBoxOrdre[1].setBounds(850, 250, 300, 120);
			conteneurPrincipal.add(hBoxOrdre[1], Integer.valueOf(51));
			hBoxOrdre[2].setBounds(850, 450, 300, 120);
			conteneurPrincipal.add(hBoxOrdre[2], Integer.valueOf(51));
			break;
		case 4:
			hBoxOrdre[0].setBounds(850, 0, 300, 120);
			conteneurPrincipal.add(hBoxOrdre[0], Integer.valueOf(50));
			hBoxOrdre[1].setBounds(850, 150, 300, 120);
			conteneurPrincipal.add(hBoxOrdre[1], Integer.valueOf(51));
			hBoxOrdre[2].setBounds(850, 300, 300, 120);
			conteneurPrincipal.add(hBoxOrdre[2], Integer.valueOf(51));
			hBoxOrdre[3].setBounds(850, 450, 300, 120);
			conteneurPrincipal.add(hBoxOrdre[3], Integer.valueOf(51));
		}
	}

	/*
	 * DEFINIS L'ENSEMBLES DES COMMANDES QUE LE JOUEUR EN COURS A ACCES:
	 * CONSTRUCTION, VENTE, ECHANGE, HYPOTH- EQUE,ETC... AVEC DES BOUTONS TOUS MUNIS
	 * DE LISTENERS (CERTAINS NE SONT PAS ENCORE IMPLANTES)
	 */
	@SuppressWarnings("deprecation")
	public void afficherCommandes() {
		ActionListener lancement = new LancerDe();
		ActionListener next = new TourSuivant();
		construire = new JButton("CONSTRUIRE");
		construire.addActionListener(new Construire());
		vendre = new JButton("VENDRE");
		vendre.addActionListener(new Vendre());
		hypothequer = new JButton("HYPOTHEQUER");
		hypothequer.addActionListener(new Hypothequer());
		leverHypotheque = new JButton("Lever hypothèque");
		leverHypotheque.addActionListener(new LeverHypotheque());
		echange = new JButton("ECHANGE");
		infoSupplement = new JButton("Plus d'infos");
		boiteControle = Box.createHorizontalBox();
		boiteControle.add(construire);
		boiteControle.add(Box.createHorizontalStrut(2));
		boiteControle.add(vendre);
		boiteControle.add(Box.createHorizontalStrut(2));
		boiteControle.add(infoSupplement);
		boiteControle2 = Box.createHorizontalBox();
		boiteControle2.add(hypothequer);
		boiteControle2.add(Box.createHorizontalStrut(2));
		boiteControle2.add(leverHypotheque);
		boiteControle2.add(Box.createHorizontalStrut(2));
		boiteControle2.add(echange);
		commande = Box.createVerticalBox();
		commande.add(boiteControle);
		commande.add(Box.createVerticalStrut(1));
		commande.add(boiteControle2);
		commande.setBounds(810, 600, 350, 80);
		lancerDe = new JButton("Lancer les dés");
		lancerDe.setBounds(590, 570, 100, 30);
		lancerDe.addActionListener(lancement);
		tourSuivant = new JButton("Tour suivant");
		tourSuivant.setBounds(483, 570, 100, 30);
		tourSuivant.addActionListener(next);
		conteneurPrincipal.add(commande, Integer.valueOf(52));
		conteneurPrincipal.add(lancerDe, Integer.valueOf(53));
		conteneurPrincipal.add(tourSuivant, Integer.valueOf(53));
	}

	/*
	 * AFFICHE DEUX DES SUR LE PLATEAU DE JEU, APRES AVOIR APPUYER SUR LE BOUTON
	 * LANCER DE, LES DES AFFICHENT LE RESULTAT DU LANCER
	 */
	@SuppressWarnings("deprecation")
	public void afficherDe() {
		de = new JLabel[2];
		boiteDe = Box.createHorizontalBox();
		for (int i = 0; i < 2; i++) {
			de[i] = new JLabel();
			de[i].setSize(200, 200);
			de[i].setIcon(lesDes.get(i + 1));

		}
		boiteDe.add(de[0]);
		boiteDe.add(Box.createHorizontalStrut(10));
		boiteDe.add(de[1]);
		boiteDe.setBounds(330, 400, 200, 200);
		conteneurPrincipal.add(boiteDe, Integer.valueOf(56));
	}

	/*
	 * CREE UNE ICONE A PARTIR D'UNE IMAGE DE FACON QUEL SOIT BIEN ADAPTE AUX JLabel
	 * L'UTILISANT
	 */
	public static ImageIcon traitementImg(String nomImage, JLabel label) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(nomImage));

			// ImageIO.read(new File(""+nomImage));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);

		return new ImageIcon(dimg);
	}

	public void maj() {
		presentationJoueurEnCours.setText("JOUEUR EN COURS : " + jeuPrincipal.getJoueurEnCours().getNom());
		logoJoueurEnCours.setIcon(Pions2.get(jeuPrincipal.getJoueurEnCours().getNumero()));
		infoJoueurEnCours.setText(jeuPrincipal.getJoueurEnCours().getPlusInfo());
		for (int i = 0; i < nbJoueur; i++) {
			infosJoueurs[i].setText(jeuPrincipal.getjoueurs()[i].getInfo());
		}

	}
	/** Affiche la fenêtre finale avec le nom du joueur qui a gagne le jeu
	 * 
	 */
	public void afficherFin() {
		JDialog finDialog = new JDialog(fenetrePrincipale,"La partie est finie.",true);
		JButton fin = new JButton("fin");
		fin.setBounds(275, 150, 70, 30);
		fin.addActionListener(new FinPartie());
		JTextArea msg = new JTextArea("Le joueur " + jeuPrincipal.getJoueurEnCours().getNom() + " a gagné cette partie !" ,300,200);
		JLayeredPane conteneur = new JLayeredPane();
		JPanel pan = new JPanel();
		pan.setSize(new Dimension(350, 230));
		pan.setBackground(new Color(216, 229, 208));
		msg.setEditable(false);
		msg.setBounds(120, 70, 300, 50);
		msg.setBorder(null);
		msg.setOpaque(false);
		msg.setBackground(new Color(0, 0, 0, 0));
		conteneur.setPreferredSize(new Dimension(450, 200));
		conteneur.add(msg, Integer.valueOf(2));
		conteneur.add(pan, Integer.valueOf(1));
		conteneur.add(fin, Integer.valueOf(4));
		finDialog.add(conteneur);
		finDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		finDialog.setSize(350,230);
		finDialog.setLocationRelativeTo(fenetrePrincipale);
		finDialog.pack();
		finDialog.setVisible(true);
	}

	////////// LISTENER ASSOCIEES AUX BOUTONS /////////////////////////////
	/*
	 * A LA SUITE DU CHOIX D'UN ITEM REDEFINI LA FORME ET LA COULEUR DU PION DU
	 * JOUEUR CONSIDERE
	 */
	private class DonnerItem implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			formePion = (String) forme.getSelectedItem();
			couleurPion = (String) couleur.getSelectedItem();
		}

	}

	/*
	 * PERMET DE PASSER A LA PRISE DES DONNES D'UN AUTRE JOUEURS
	 */
	private class PasserSuivant implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			nomJ = nomText.getText();
			if (!(nomText.getText() != null && nomText.getText().length() > 0)) {
				estOk = false;
			} else {
				formePion = (String) forme.getSelectedItem();
				couleurPion = (String) couleur.getSelectedItem();
				if (formePion == null) {
					formePion = (String) forme.getItemAt(0);
					forme.removeItemAt(0);
				} else {
					forme.removeItem(forme.getSelectedItem());
				}

				if (couleurPion == null) {
					couleurPion = (String) couleur.getItemAt(0);
					couleur.removeItemAt(0);
				} else {
					couleur.removeItem(couleur.getSelectedItem());
				}

				estOk = true;
			}
			joueurInit.dispose();
		}

	}

	/*
	 * PERMET DE QUITTER LA PARTIE
	 */
	private class Quitter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(2);
		}
	}

	/*
	 * PERMET DE COMMENCER LE JEU EN TANT QUE TEL (SONNE LA FIN DE LA PRISE DE
	 * DONNE)
	 */
	private class Suivant implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			estOk = true;
			ordreDeJeu.dispose();
		}
	}

	/*
	 * LANCE LE DE DU MODELE PUIS AFFICHE LE RESULTAT SUR LES DES DU PLATEAU
	 */
	private class LancerDe implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (monTourLancerDe) {
				
				monTourLancerDe = false;
				aLancer = true;
				jeuPrincipal.lancerDe();
				for (int i = 0; i < 2; i++) {
					de[i].setIcon(lesDes.get(jeuPrincipal.getResultatDeSepare()[i]));
				}
				if(!jeuPrincipal.getJoueurEnCours().getEstEmprisonne()) {
				pionSurCase[jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()]
						.remove(lab[jeuPrincipal.getJoueurEnCours().getNumero() - 1]);
				
				jeuPrincipal.getJoueurEnCours().getPion().avancer(jeuPrincipal.getResultatDe());//
				
				
				pionSurCase[jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()]
						.add(lab[jeuPrincipal.getJoueurEnCours().getNumero() - 1], Integer.valueOf(150));
				}
		
				int numberCase = jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase();
				boolean changeDeLocation = false;
				jeuPrincipal.getJoueurEnCours().passageCaseDepart();
				maj();
				fenetrePrincipale.repaint();
				do {
					changeDeLocation = false;
					if (!jeuPrincipal.getJoueurEnCours().getEstEmprisonne()) {
						switch (jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()) { // jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()
						case 0: // CASE DEPART //ok
							new VueCaseDepart(fenetrePrincipale, jeuPrincipal);
							break;

						case 2: // CASE COMMUNAUTE // ok
						case 17:
						case 33:
							new VueCommunaute(fenetrePrincipale, jeuPrincipal);
							if (numberCase != jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()) {
								changeDeLocation = true;
								pionSurCase[numberCase].remove(lab[jeuPrincipal.getJoueurEnCours().getNumero() - 1]);
								pionSurCase[jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()]
										.add(lab[jeuPrincipal.getJoueurEnCours().getNumero() - 1], Integer.valueOf(150));

							}
							maj();
							fenetrePrincipale.repaint();
							break;
						case 5: // Case Gare
						case 15:
						case 25:
						case 35:
							new VueGare(fenetrePrincipale, jeuPrincipal);
							break;
						case 7: // CASE CHANCE // ok
						case 22:
						case 36:
							new VueChance(fenetrePrincipale, jeuPrincipal);
							if (numberCase != jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()) {
								changeDeLocation = true;
								pionSurCase[numberCase].remove(lab[jeuPrincipal.getJoueurEnCours().getNumero() - 1]);
								pionSurCase[jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()]
										.add(lab[jeuPrincipal.getJoueurEnCours().getNumero() - 1], Integer.valueOf(150));

							}
							maj();
							fenetrePrincipale.repaint();
							break;
						case 10: // CASE VISITE PRISON  // ok
							new VueVisitePrison(fenetrePrincipale, jeuPrincipal);
							break;
						case 12: // ELEC
							new VueElectricite(fenetrePrincipale, jeuPrincipal);
							break;
						case 28: // EAU
							new VueEau(fenetrePrincipale, jeuPrincipal);
							break;
						case 20:
							new VueParcGratuit(fenetrePrincipale, jeuPrincipal);
							break;
						case 30:
							new VueAllerPrison(fenetrePrincipale, jeuPrincipal);
							if (numberCase != jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()) {
								changeDeLocation = true;
								pionSurCase[numberCase].remove(lab[jeuPrincipal.getJoueurEnCours().getNumero() - 1]);
								pionSurCase[jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase()]
										.add(lab[jeuPrincipal.getJoueurEnCours().getNumero() - 1], Integer.valueOf(150));

							}
							maj();
							break;
						case 4: // CASE IMPOT ok
							new VueImpot(fenetrePrincipale, jeuPrincipal, 1);
							break;
						case 38: // CASE IMPOT ok
							new VueImpot(fenetrePrincipale, jeuPrincipal, 2);
							break;

						default: // CASE propriete ok
							int numeroCase = jeuPrincipal.getJoueurEnCours().getPion().getNumeroCase();
							CaseRue caseRue = (CaseRue) jeuPrincipal.getPlateau().getCase(numeroCase);
							new VuePropriete(fenetrePrincipale, jeuPrincipal, caseRue.getNom(), caseRue.getCouleur());
							break;
						}
					} else {
						if (jeuPrincipal.getResultatDeSepare()[0] == jeuPrincipal.getResultatDeSepare()[1]) {
							jeuPrincipal.getJoueurEnCours().liberer();
							new VueVisitePrison(fenetrePrincipale, jeuPrincipal);
						} else {
							jeuPrincipal.getJoueurEnCours().sejournerPrison();
							new VueVisitePrison(fenetrePrincipale, jeuPrincipal);
						}

					}
				} while (changeDeLocation);
				maj();
				fenetrePrincipale.repaint();
			}

		}

	}

	/*
	 * DONNER LA MAIN A UN AUTRE JOUEUR
	 */
	private class TourSuivant implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (aLancer) {
				aLancer = false;
				monTourLancerDe = true;
				jeuPrincipal.setNextJoueur();
				boolean fini = jeuPrincipal.getJeuTermine();
				if (fini) {
					afficherFin();
				}
				else {
					maj();
					fenetrePrincipale.repaint();
				}
				
			}
		}
	}

	/**
	 * Action listener du boutton qui sert e Hypothequer des proprietes
	 * 
	 * @author Lucas Oros
	 */
	private class Hypothequer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * Action listener du boutton qui sert e lever l'hypotheque d'une maison
	 * 
	 * @author Lucas Oros
	 */
	private class LeverHypotheque implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}

	}

	/**
	 * Action listener du boutton qui sert e vendre une propriete
	 * 
	 */
	private class Vendre implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * Action listener du boutton qui sert e construire des maisons ou des hotels
	 * sur une propriete
	 * 
	 */
	private class Construire implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// try {
			new VueConstruire(fenetrePrincipale, jeuPrincipal);// }
			// catch (Exception exc) {
			System.out.println("Exception déclenchée par le constructeur.");

			// }
			// Actualisation de l'historique de commandes

		}

	}

	/**
	 * Action listener qui permet à un joueur d'échanger une propriété avec un
	 * autre joueur
	 * 
	 */
	private class Echanger implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}	
	/** Action listener qui s'active lors de la fin de la partie pour fermer la fenêtre principale
	 * 
	 */
	private class FinPartie implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			fenetrePrincipale.dispose();
			
		}
		
	}

}


