package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import java.awt.Color;

/**
 * La case propriete compagnie des eaux.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public class CaseCompagnieDeServicePublic extends CasePropriete {

	/** La case du plateau qui correspond a l'autre service public. */
	private int autreCompagnie;
	
	/** La Carte propriete qui correspond à l'autre service public. */
	private int carteAutreCompagnie;

	/**
	 * Initialiser une case de compagnie de service public.
	 * 
	 * @param index
	 * @param nom
	 * @param indiceTerrain
	 * @param caseAutreCompagnie
	 */
	public CaseCompagnieDeServicePublic(int index, String nom, int indiceTerrain, int caseAutreCompagnie, int carteAutreCompagnie) {
		super(index, nom, indiceTerrain);
		this.autreCompagnie = caseAutreCompagnie;
		this.carteAutreCompagnie = carteAutreCompagnie;
	}

	/**
	 * Action effectuee lorsque le joueur tombe sur une case de compagnie de service
	 * public.
	 * 
	 * @param jeu    -> le jeu en cours
	 * @param joueur -> le joueur qui est en train de jouer son tour de jeu
	 */
	public void actionCase(JeuReel jeu, Joueur joueur) {
		// Si le joueur n'est pas le proprietaire
		Joueur proprietaire = super.getProprietaire();
		if ( proprietaire != joueur) {
			Integer[] deDouble = jeu.getResultatDeSepare();
			int montant = (deDouble[0] + deDouble[1]) * jeu.getCartesPropriete()[super.indiceTerrain].getLoyer();
			// on lui retire 4 ou 10 fois le resultat des 2 des
			joueur.retirerArgent(montant);
			Joueur proprio = ((CasePropriete) jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase())).getProprietaire();
			proprio.ajouterArgent(montant);
		}
		// Si le joueur est le proprietaire de cette compagnie
		else if (super.getProprietaire().equals(joueur)) {
			// On verifie si le proprietaire possede les deux compagnies de service public
			if (((CasePropriete) jeu.getPlateau().getCase(autreCompagnie)).getProprietaire() == joueur) {
				// si oui, alors on augmente le loyer des 2 compagnies
				jeu.getCartesPropriete()[super.indiceTerrain].setLoyer(10);
				jeu.getCartesPropriete()[carteAutreCompagnie].setLoyer(10);
			}
		}
	}

	@Override
	public String getCouleur() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getMontant(JeuReel jeu) {
		Integer[] deDouble = jeu.getResultatDeSepare();
		int montant = (deDouble[0] + deDouble[1]) * jeu.getCartesPropriete()[super.indiceTerrain].getLoyer();
		return montant;
	}
	
	
}
