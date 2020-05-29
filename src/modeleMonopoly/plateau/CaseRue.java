package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import java.awt.Color;

/**
 * La classe CaseRue modélise une propriété particulière du plateau : la rue, où
 * l'on peut placer des maisons.
 * 
 * @author Benoit Baude
 * @version 1.2
 */

public class CaseRue extends CasePropriete {

	/** La couleur de la rue. */
	private String couleur;

	/** Les deux CARTES PROPRIETE (du jeu) de la meme couleur que la rue. */
	private int[] caseFamille = new int[2];

	/**
	 * Initialiser une case avec sa couleur et l'information de ses cartes de la
	 * meme famille.
	 * 
	 * @param index				--> l'indice du plateau de la case
	 * @param nom				--> le nom de la case
	 * @param indiceTerrain 	--> le numero de la carte achetable
	 * @param couleur			--> sa couleur
	 * @param famille1, 2		--> Les 2 entiers correspondant aux cases 
	 * 								du plateau de la meme famille que cette rue
	 */
	public CaseRue(int index, String nom, int indiceTerrain, String couleur, int famille1, int famille2) {
		super(index, nom, indiceTerrain);
		this.couleur = couleur;
		this.caseFamille[0] = famille1;
		this.caseFamille[1] = famille2;
	}

	/**
	 * Procédure lancée après que le joueur a pris sa décision d'acheter ou non la
	 * propriété sur laquelle il est tombé.
	 * 
	 * @param jeu    -> le jeu en cours
	 * @param joueur -> le joueur qui joue son tour de jeu
	 */
	public void actionCase(JeuReel jeu, Joueur joueur) {
		// La case possède un propriétaire qui n'est pas le joueur.
		if (this.proprietaire != null && !this.proprietaire.equals(joueur)) {
			CartePropriete carte = jeu.getCartesPropriete()[super.getIndiceTerrain()];
			joueur.retirerArgent(carte.getLoyer());
			Joueur proprio = ((CasePropriete) jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase())).getProprietaire();
			proprio.ajouterArgent(carte.getLoyer());
		}
		// Le joueur est le propriétaire de la rue.
		else if (this.proprietaire == joueur) {
			// On vérifie si le propriétaire possède toutes les cartes de la couleur de
			// cette rue.
			if (joueur.getTerrains()[caseFamille[0]] == 1 & joueur.getTerrains()[caseFamille[1]] == 1 & !((CarteRue) jeu.getCartesPropriete()[this.indiceTerrain]).getConstructible()) {
				// Il faut alors doubler le loyer de chaque carte de la famille.
				jeu.getCartesPropriete()[this.indiceTerrain].setLoyer(2 * jeu.getCartesPropriete()[this.indiceTerrain].getLoyer());
				jeu.getCartesPropriete()[caseFamille[0]].setLoyer(2 * jeu.getCartesPropriete()[caseFamille[0]].getLoyer());
				// Pour les cases du plateau par groupe de 2 et non de 3
				if (caseFamille[0] != caseFamille[1]) {
				jeu.getCartesPropriete()[caseFamille[1]].setLoyer(2 * jeu.getCartesPropriete()[caseFamille[1]].getLoyer());
				}
				// Et rendre chaque carte de la famille constructible.
				((CarteRue) jeu.getCartesPropriete()[this.indiceTerrain]).setConstructible();
				((CarteRue) jeu.getCartesPropriete()[caseFamille[0]]).setConstructible();
				((CarteRue) jeu.getCartesPropriete()[caseFamille[1]]).setConstructible();
			}
			// Rien à faire si la famille n'est pas réunie par le joueur.
		} else {
			// Rien à faire si le joueur a décidé de ne pas acheter la propriété.
		}
	}

	@Override
	public String getCouleur() {
		// TODO Auto-generated method stub
		return this.couleur;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return super.nom;
	}

}
