package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/**
 * Les deux cases impot, du plateau de Monopoly.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public class CaseImpots extends Case {

	/**
	 * Constuire une case impots
	 * 
	 * @param index --> numero de la case du plateau
	 * @param nom   --> nom de la case
	 */
	public CaseImpots(int index, String nom) {
		super(index, nom);
	}

	/**
	 * Action effectuee lorsque le joueur tombe sur une case Impot.
	 * 
	 * @param jeu    -> le jeu en cours
	 * @param joueur -> le joueur qui est en train de jouer son tour de jeu
	 */
	public void actionCase(JeuReel jeu, Joueur joueur) {
		int montantImpot;
		if (this.getIndex() == 4) {
			// Le joueur se trouve sur la case "Impots sur le revenu".
			montantImpot = 200;
		} else {
			// Le joueur se trouve sur la case "Taxe de luxe".
			montantImpot = 100;
		}
		// On debite l'argent du joueur.
		joueur.retirerArgent(montantImpot);
	}

	@Override
	public String getCouleur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return super.nom;
	}

}
