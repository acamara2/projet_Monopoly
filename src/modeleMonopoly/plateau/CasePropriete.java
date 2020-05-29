package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/**
 * La classe abstraite CasePropriete modelise une propriete du plateau qui peut
 * être achetee par un joueur.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public abstract class CasePropriete extends Case {

	/**
	 * L'indice correspondant a la carte propriete dans le jeu. exemple : indice(Rue
	 * de Courcelles) = 5 puisque c'est la 5eme propriete du jeu
	 */
	protected int indiceTerrain;

	/** Le proprietaire de la carte propriete, NULL si carte pas encore achetee. */
	protected Joueur proprietaire;
	
	/** Initialiser une case propriete. */
	public CasePropriete(int index, String nom, int indiceTerrain) {
		super(index, nom);
		this.indiceTerrain = indiceTerrain;
		this.proprietaire = null;
	}

	/** Obtenir le proprietaire de la case. */
	public Joueur getProprietaire() {
		return this.proprietaire;
	}

	/** Etablir le proprietaire de la carte de la case. */
	public void setProprietaire(Joueur joueur) {
		this.proprietaire = joueur;
	}
	
	/** Obtenir l'indice du terrain de la case. */
	public int getIndiceTerrain() {
		return this.indiceTerrain;
	}
	
}
