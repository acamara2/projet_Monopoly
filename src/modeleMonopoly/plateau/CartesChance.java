package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;

/**
 * Les 16 cartes Chance modelisées par un switch de 16 possibilités
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public class CartesChance {

	/** les noms correspondants aux 16 cartes chance. */
	static final int PRISON = 1, HENRIMARTIN = 2, VILLETTE = 3, RUEDELAPAIX = 4, GAREDELYON = 5, DEPART = 6,
			RECULER = 7, AMENDEIVRESSE = 8, MOTSCROISES = 9, EXCESVITESSE = 10, SCOLARITE = 11, REPARATION1 = 12,
			PRET = 13, BANQUE = 14, REPARATION2 = 15, LIBERE = 16;

	/** Le texte associé à la carte chance. */
	private String texteCarte;

	/**
	 * Appliquer l'action de la case de retour après une téléportation via une carte
	 * chance.
	 * 
	 * @param jeu
	 * @param joueur
	 */
	public void actionCaseActuelle(JeuReel jeu, Joueur joueur) {
		// On détermine le numéro de la nouvelle case du joueur.
		int indiceCase = joueur.getPion().getNumeroCase();
		// On applique l'action de la case où se trouve le joueur.
		jeu.getPlateau().getCase(indiceCase).actionCase(jeu, joueur);
	}

	/**
	 * Activer l'une des 16 cartes chance du jeu.
	 * 
	 * @param numeroCarte -> le numéro de la carte chance
	 * @param joueur      -> le joueur qui a pioché la carte
	 * @param jeu         -> le jeu
	 */
	public void piocherCarteChance(int numeroCarte, Joueur joueur, JeuReel jeu) {
		switch (numeroCarte + 1 ) {
		case PRISON:
			this.texteCarte = "Allez en prison.\nNe passez pas par la case Départ.";
			joueur.emprisonner(); // La case n°10 du plateau est la case prison.
			joueur.getPion().moveTo(10);
			break;

		case HENRIMARTIN:
			this.texteCarte = "Rendez-vous avenue Henri-Martin.";
			joueur.getPion().moveTo(21);
			// On verifie si le joueur passera par la case de depart.
			if (joueur.getPion().getNumeroCase() > 21) {
				joueur.ajouterArgent(200);
			}
			actionCaseActuelle(jeu, joueur);
			break;

		case VILLETTE:
			this.texteCarte = "Rendez-vous boulevard de la Villette.";
			joueur.getPion().moveTo(11);
			// On verifie si le joueur passera par la case de depart.
			if (joueur.getPion().getNumeroCase() > 11) {
				joueur.ajouterArgent(200);
			}
			actionCaseActuelle(jeu, joueur);
			break;

		case RUEDELAPAIX:
			this.texteCarte = "Rendez-vous rue de la Paix.";
			joueur.getPion().moveTo(39);
			actionCaseActuelle(jeu, joueur);
			break;

		case GAREDELYON:
			this.texteCarte = "Rendez-vous gare de Lyon.";
			joueur.getPion().moveTo(15);
			// On verifie si le joueur passera par la case de depart.
			if (joueur.getPion().getNumeroCase() > 15) {
				joueur.ajouterArgent(200);
			}
			actionCaseActuelle(jeu, joueur);
			break;

		case DEPART:
			this.texteCarte = "Avancez jusqu'à la case depart.";
			joueur.getPion().moveTo(0);
			actionCaseActuelle(jeu, joueur);
			break;

		case RECULER:
			this.texteCarte = "Reculez de 3 cases.";
			joueur.getPion().avancer(-3);
			actionCaseActuelle(jeu, joueur);
			break;

		case AMENDEIVRESSE:
			this.texteCarte = "Amende pour ivresse, payez 20M.";
			joueur.retirerArgent(20);
			break;

		case MOTSCROISES:
			this.texteCarte = "Vous avez gagné le concours de mots-croisés !\nRecevez 100M.";
			joueur.ajouterArgent(100);
			break;

		case EXCESVITESSE:
			this.texteCarte = "Amende pour excès de vitesse, payez 15M.";
			joueur.retirerArgent(15);
			break;

		case SCOLARITE:
			this.texteCarte = "Vous devez payer des frais de scolarité\ns'élevant à 150M.";
			joueur.retirerArgent(150);
			break;

		case REPARATION1:
			this.texteCarte = "Réparations de votre patrimoine :\nPayez 25M/maison et 100M/hôtel.";
			int montantReparation1 = 0;
			for (int numTerrain = 0; numTerrain < joueur.getTerrains().length; numTerrain++) {
				// Si le joueur possede le terrain
				if (joueur.getTerrains()[numTerrain] == 1) {
					// et qu'on peut y placer des maisons
					if (jeu.getCartesPropriete()[numTerrain] instanceof CarteRue) {
						CarteRue carteAvecMaison = ((CarteRue) jeu.getCartesPropriete()[numTerrain]);
						// Le cout augmente de 25M par maison et 100M par hôtel
						montantReparation1 += carteAvecMaison.getNbMaisons() * 25;
						montantReparation1 += carteAvecMaison.getNbMaisons() * 100;
					}
				}
			}
			joueur.retirerArgent(montantReparation1);
			break;

		case 13:
			this.texteCarte = "Votre livret d'épargne vous rapporte 150M !";
			joueur.ajouterArgent(150);
			break;

		case 14:
			this.texteCarte = "La banque vous verse 50M.";
			joueur.ajouterArgent(50);
			break;

		case 15:
			this.texteCarte = "Grosses réparations de votre patrimoine :\nPayez 40M/maison et 115M/hôtel.";
			int montantReparation2 = 0;
			for (int numTerrain = 0; numTerrain < joueur.getTerrains().length; numTerrain++) {
				// Si le joueur possede le terrain
				if (joueur.getTerrains()[numTerrain] == 1) {
					// et qu'on peut y placer des maisons
					if (jeu.getCartesPropriete()[numTerrain] instanceof CarteRue) {
						CarteRue carteAvecMaison = ((CarteRue) jeu.getCartesPropriete()[numTerrain]);
						// Le cout augmente de 40M par maison et 115M par hôtel
						montantReparation2 += carteAvecMaison.getNbMaisons() * 40;
						montantReparation2 += carteAvecMaison.getNbMaisons() * 115;
					}
				}
			}
			joueur.retirerArgent(montantReparation2);
			break;

		case 16:
			texteCarte = "Vous êtes libéré de prison. Vous pouvez utiliser\ncette carte 1 fois dans la partie.";
			joueur.setNbCartesSortiePrison(joueur.getNbCartesSortiePrison() + 1);
			break;
		default :
			this.texteCarte = "il semble y avoir un problème...";
		}
	}

	public String getTexteCarte() {
		return this.texteCarte;
	}

}