package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/** Les 16 cartes communautes modelisees par un switch de 16 possibilites
 * 
 * @author Omar Naim
 * @version 1.2
 */

public class CartesCommunaute {
       /* Des noms modelisants les 16 cartes communautes. */
	static final int PRISON = 1, BELLEVILLE = 2, DEPART = 3, AMENDE = 4, CARTECHANCE = 5, ERREURBANQUE = 6,
					MASOMAGIE = 7, ALBERTPAISSE = 8, ASSURANCE = 9, EXO = 10, CARNAVAL = 11, EQUIPEMENT = 12,
					LIBERERPRISON = 13, DECOUVERTES = 14, HERITAGE = 15, ANNIVERSAIRE = 16;
	/** Le texte associe à la carte communaute. */
	private String texteCarte;

	/**
	 * "Communaute" : 1 envoie en prison, 1 "Retournez à Belleville", 1 envoie
	 * sur la case de depart, 1 payer une amende, 1 tirer une carte chance. Il
	 * y a 12 autres cartes "Communaute" qui n'ont aucune influence sur la position
	 **/

	/** Appliquer l'action de la case de retour apres une teleportation via une carte chance.
	 * 
	 * @param jeu
	 * @param joueur
	 */
	public void actionCaseActuelle(JeuReel jeu, Joueur joueur) {
		// On determine le numero de la nouvelle case du joueur.
		int indiceCase = joueur.getPion().getNumeroCase();
		// On applique l'action de la case où se trouve le joueur.
		jeu.getPlateau().getCase(indiceCase).actionCase(jeu, joueur);
	}
	
	/**
	 * Activer l'une des 16 cartes commuanutes du jeu.
	 * 
	 * @param numeroCarte -> le numero de la carte communaute
	 * @param joueur      -> le joueur qui a pioche la carte
	 * @param jeu         -> le jeu
	 */

	public void piocherCarteCommunaute(int numeroCarte, Joueur joueur, JeuReel jeu) {

		switch (numeroCarte) {
		case PRISON:
			this.texteCarte = "Allez en prison.\n";
			joueur.emprisonner(); 
			// La case n°10 du plateau est la case prison.
			joueur.getPion().moveTo(10);
			break;

		case BELLEVILLE:
			this.texteCarte = "Retournez à Belleville.\n";
			joueur.getPion().moveTo(1);
			actionCaseActuelle(jeu, joueur);
			break;

		case DEPART:
			this.texteCarte = "Rendez-vous à la case départ.\n";
			joueur.getPion().moveTo(0);
			joueur.ajouterArgent(200);	
			actionCaseActuelle(jeu, joueur);
			break;

		case AMENDE:
			this.texteCarte = "Payez une amende de 200M.\n";
			joueur.retirerArgent(200);
			break;

		case CARTECHANCE:
			this.texteCarte = "Tirez une carte chance.\n";
				jeu.getPlateau().getCase(7).actionCase(jeu, joueur);
			break;

		case ERREURBANQUE:
			this.texteCarte = "Erreur de la banque en votre faveur.\nRecevez 200M.\n";
			joueur.ajouterArgent(200);
			break;

		case MASOMAGIE:
			this.texteCarte = "La maso-magie vous rapporte.\nRecevez 20M.\n";
			joueur.ajouterArgent(20);
			break;
			
		case ALBERTPAISSE:
			this.texteCarte = "Vous rendez visite à Albert Paisse.\nPayez 20M.\n";
			joueur.retirerArgent(20);
			break;

		case ASSURANCE:
			this.texteCarte = "Payez votre police d'assurance : 500M.\n";
			joueur.retirerArgent(500);
			break;

		case EXO:
			this.texteCarte = "Vous gagnez au juste EXO.\nRecevez 20M.\n";
			joueur.ajouterArgent(20);
			break;

		case CARNAVAL:
			this.texteCarte = "Vous avez gagné le deuxième prix du Carnaval.\nRecevez 100M.\n";
			joueur.ajouterArgent(150);
			break;
			

		case EQUIPEMENT:
			this.texteCarte = "Vous vendez vos équipements d'aquaponey.\nRecevez 100M.\n";
			joueur.ajouterArgent(100);
			break;

		case LIBERERPRISON:
			this.texteCarte = "Vous êtes libéré de prison. Vous pouvez utiliser\ncette carte 1 fois dans la partie.";
			joueur.setNbCartesSortiePrison( joueur.getNbCartesSortiePrison() + 1);
			break;

		case DECOUVERTES:
			this.texteCarte = "Vos découvertes vous rapporte 100M.\n";
			joueur.ajouterArgent(100);		
			break;

		case HERITAGE:
			this.texteCarte = "Votre vieil ami Enutrof est décédé...\nVous héritez 100M.\n";
			joueur.ajouterArgent(100);
			break;

		case ANNIVERSAIRE:
			texteCarte = "C'est votre anniversaire.\nChaque joueur doit vous donner 10M.\n";
			for (Joueur j : jeu.getjoueurs() ){
				j.retirerArgent(10);
				joueur.ajouterArgent(10);
			}
			break;
			
		default :
			this.texteCarte = "il semble y avoir un problème...";
		}

	}

	public String getTexteCarte() {
		return this.texteCarte;
	}

}
