package modeleMonopoly.jeu;
import modeleMonopoly.plateau.*;
import modeleMonopoly.joueur.Joueur;
import java.util.*;

/**
 * CETTE CLASSE REPRESENTE LE JEU REEL
 * @author CAMARA ABABACAR , OROS LUCAS
 */
public class JeuReel implements Jeu {
	/**
	 * resultatLancerDe
	 */
	private int resultatLancerDe;
	/**
	 * nombre de joueurs
	 */
    private int nbJoueur;
	private Integer[] resultatDeSepare = new Integer[2];

	/** Le 1er de du jeu. */
	private De de1;

	/** Le 2eme de du jeu. */
	private De de2;

	/** Le plateau de jeu. */
	private Plateau plateau;

	/** Les cartes Chance du jeu reunies. */
	private CartesChance cartesChance;

	/** Les cartes Caisse de communaute du jeu reunies. */
	private CartesCommunaute cartesCommunaute;

	/** Les cartes propriete du jeu. */
	private CartePropriete[] cartesPropriete = new CartePropriete[28];

	/**
	 * represente les joueurs et leurs indices de cases respectives.
	 */
	private Map<Joueur, Integer> caseActives;

	/**
	 * represente les joueurs qui sont encore en train de jouer 
	 */
	private Joueur[] Joueurs;
	
	/**
	 * represente la liste des joueurs qui ont deja perdu le jeu dans l'ordre finalisation
	 */
	private Joueur[] Ranking;
	private int dernierRanking;
	
	/**
	 *  Joueur qui est en train de jouer la partie 
	 */
	private Joueur joueurPrincipal = null;
	
	/** 
	 * Indice du joueur principal dans la liste des joueurs actifs 
	 */
	private int indicePrincipal;
	/** 
	 * Indique si le joueur a demande de finir son tour via le boutton 
	 */
	
	private boolean tourTermine;
	
	
	/**
	 * Indique si la partie est termine
	 */
	private boolean jeuTermine;
	
	/**
	 * Construit un jeu a partir d'un plateau et d'un set de joueurs initiaux.
	 * @param p
	 * @param JoueursInitiaux
	 */
	public JeuReel(Joueur[] JoueursInitiaux) {
		this.plateau = new Plateau();
		this.InitialiserCartes();
		/*On transforme le tableau de joueurs en un ArrayList de joueurs*/
		this.Joueurs = JoueursInitiaux;
		
		
		/*Le tour du premier joueur n'est pas termine pour l'instant ainsi que le jeu*/
		this.tourTermine = false;
		this.jeuTermine = false;
		
		///////////////////////////////////////////////////////////////////////// Atribut possiblement innecesaire 
		/*Les positions de tous les joueurs sont initialement mises a0*/
		this.caseActives = new HashMap<Joueur, Integer>();
		
		//////////////////////////////////////////////////////////////////////////
		}

	
	/** Initialiser les 28 cartes propriete du jeu. */
	public void InitialiserCartes() {
		CartePropriete BoulevardDeBelleville = new CarteRue(60, 2, 1, new int[] {2, 10, 30, 90, 160 , 250}, 50);
		this.cartesPropriete[0] = BoulevardDeBelleville;
		CartePropriete RueLecourbe = new CarteRue(60, 4, 3, new int[] {4, 20, 60, 180, 320, 450}, 50);
		this.cartesPropriete[1] = RueLecourbe;
		CartePropriete GareMontparnasse = new CarteGare(200, 25, 5);
		this.cartesPropriete[2] = GareMontparnasse;
		CartePropriete RueDeVaugirard = new CarteRue(100, 6, 6, new int[] {6, 30, 90, 270, 400, 550}, 50);
		this.cartesPropriete[3] = RueDeVaugirard;
		CartePropriete RueDeCourcelles = new CarteRue(100, 6, 8, new int[] {6, 30, 90, 270, 400, 550}, 50);
		this.cartesPropriete[4] = RueDeCourcelles;
		CartePropriete AvenueDeLaRepublique = new CarteRue(120, 8, 9, new int[] {8, 40, 100, 300, 450, 600}, 50);
		this.cartesPropriete[5] = AvenueDeLaRepublique;
		CartePropriete BoulevardDeLaVillette = new CarteRue(140, 10, 11, new int[] {10, 50, 150, 450, 620, 750}, 100);
		this.cartesPropriete[6] = BoulevardDeLaVillette;
		CartePropriete CompagnieElectricite = new CartePropriete(150, 4, 12);
		this.cartesPropriete[7] = CompagnieElectricite;
		CartePropriete AvenueDeNeuilly = new CarteRue(140, 10, 13, new int[] {10, 50, 150, 450, 620, 750}, 100);
		this.cartesPropriete[8] = AvenueDeNeuilly;
		CartePropriete RueDeParadis = new CarteRue(160, 12, 14, new int[] {12, 60, 180, 500, 700, 900}, 100);
		this.cartesPropriete[9] = RueDeParadis;
		CartePropriete GareDeLyon = new CarteGare(200, 25, 15);
		this.cartesPropriete[10] = GareDeLyon;
		CartePropriete AvenueMozart = new CarteRue(180, 14, 16, new int[] {14, 70, 200, 550, 750, 950}, 100);
		this.cartesPropriete[11] = AvenueMozart;
		CartePropriete BoulevardSaintMichel = new CarteRue(180, 14, 18, new int[] {14, 70, 200, 550, 750, 950}, 100);
		this.cartesPropriete[12] = BoulevardSaintMichel;
		CartePropriete PlacePigalle = new CarteRue(200, 16, 19, new int[] {16, 80, 220, 600, 800, 1000}, 100);
		this.cartesPropriete[13] = PlacePigalle;
		CartePropriete AvenueMatignon = new CarteRue(220, 18, 21, new int[] {18, 90, 250, 700, 875, 1050}, 150);
		this.cartesPropriete[14] = AvenueMatignon;
		CartePropriete BoulevardMalesherbes = new CarteRue(220, 18, 23, new int[] {18, 90, 250, 700, 875, 1050}, 150);
		this.cartesPropriete[15] = BoulevardMalesherbes;
		CartePropriete AvenueHenriMartin = new CarteRue(240, 20, 24, new int[] {20, 100, 300, 750, 925, 1100}, 150);
		this.cartesPropriete[16] = AvenueHenriMartin;
		CartePropriete GareDuNord = new CarteGare(200, 25, 25);
		this.cartesPropriete[17] = GareDuNord;
		CartePropriete FaubourgSaintHonore = new CarteRue(260, 22, 26, new int[] {22, 110, 330, 800, 975, 1150}, 150);
		this.cartesPropriete[18] = FaubourgSaintHonore;
		CartePropriete PlaceDeLaBourse = new CarteRue(260, 22, 27, new int[] {22, 110, 330, 800, 975, 1150}, 150);
		this.cartesPropriete[19] = PlaceDeLaBourse;
		CartePropriete CompagnieDesEaux = new CartePropriete(150, 4, 28);
		this.cartesPropriete[20] = CompagnieDesEaux;
		CartePropriete RueLafayette = new CarteRue(280, 24, 29, new int[] {24, 120, 360, 850, 1025, 1200}, 150);
		this.cartesPropriete[21] = RueLafayette;		
		CartePropriete AvenueDeBreteuil = new CarteRue(300, 26, 31, new int[] {26, 130, 390, 900, 1100, 1275}, 200);
		this.cartesPropriete[22] = AvenueDeBreteuil;		
		CartePropriete AvenueFoch = new CarteRue(300, 26, 32, new int[] {26, 130, 390, 900, 1100, 1275}, 200);
		this.cartesPropriete[23] = AvenueFoch;	
		CartePropriete BoulevardDesCapucines = new CarteRue(300, 28, 34, new int[] {28, 150, 450, 1000, 1200, 1400}, 200);
		this.cartesPropriete[24] = BoulevardDesCapucines;
		CartePropriete GareSaintLazare = new CarteGare(200, 25, 35);
		this.cartesPropriete[25] = GareSaintLazare;
		CartePropriete BoulevardDesChampsElysees = new CarteRue(350, 35, 36, new int[] {35, 175, 500, 1100, 1300, 1500}, 200);
		this.cartesPropriete[26] = BoulevardDesChampsElysees;
		CartePropriete RueDeLaPaix = new CarteRue(400, 50, 38, new int[] {50, 200, 600, 1400, 1700, 2000}, 200);
		this.cartesPropriete[27] = RueDeLaPaix;
	}


	
	@Override
	public void lancerDe() {
		// TODO Auto-generated method stub
		De de1 = new De();
		De de2 = new De();
		// Lance de des
		de1.lancerDe();
		de2.lancerDe();
		this.resultatDeSepare[0] = de1.getResultat();
		this.resultatDeSepare[1] = de2.getResultat();
		// le resultat du dernier lancer de de est mise ajour.
		this.resultatLancerDe = de1.getResultat() + de2.getResultat();
	}

	@Override
	public int getResultatDe() {
		int temp = this.resultatLancerDe;
		return temp;
	}

	public Integer[] getResultatDeSepare() {
		Integer[] temp = this.resultatDeSepare;
		return temp;
	}

	@Override
	public Joueur getJoueurEnCours() {

		return this.joueurPrincipal;
	}

	
	
	/**
	 * Est utilise quand le jeu se termine
	 */
	
	public void setjeuTermine() {
		this.jeuTermine = true;
	}
	
	/** Redefinit les joueurs apartir d'un tableau de joueurs 
	 * 
	 */

	public void setJoueurs(Joueur[] tab, int nbJoueurs) {
		this.Joueurs = tab;
		this.nbJoueur = nbJoueurs;
	}
	
	
	///////////// SOUS METHODES //////////////////////

	public CartePropriete[] getCartesPropriete() {
		return this.cartesPropriete;
	}

	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public Joueur[] getjoueurs() {
		return this.Joueurs;
	}

	



	@Override
	public void setNextJoueur() {
		// TODO Auto-generated method stub
		if(joueurPrincipal == null) {
    		joueurPrincipal = Joueurs[0];
    	}
		else {
    		//On verifie si le joueur est en faillite a la fin de son tour et actualise dans le cas pertinent
    		if (joueurPrincipal.getArgent()<=0) {
    			joueurPrincipal.mettreEnFaillite();
    			//On verifie maintenant si la partie est encore en cours 
    			int enjeu = 0;
    			for (Joueur j:Joueurs) {
    				if (!(j.getEstEnFaillite())) {
    					enjeu ++;
    				}
    				//S'il reste une seule personne dans la partie qui joue elle est alors finie
    				
    				}
    			if (enjeu == 1) {
					this.setjeuTermine();
    			}
    		}
    		//Le joueur principal n'a pas perdu donc il existe au moins un autre joueur avec un argent non-negatif ou nul 
    		int i = 1;
    		joueurPrincipal = Joueurs[joueurPrincipal.getNumero()%nbJoueur];
    		while ((joueurPrincipal.getEstEnFaillite())&&(i<nbJoueur)) {
    				i++;
    				joueurPrincipal = Joueurs[joueurPrincipal.getNumero()%nbJoueur];
    		}
    		
    		
    		}

	}
	


	public boolean getJeuTermine() {
		return this.jeuTermine;
	}

	
}
