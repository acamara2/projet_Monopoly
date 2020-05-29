package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/**
 * La Case ParcGratuit, du plateau de Monopoly.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public class CaseParcGratuit extends Case {

	/**
	 * Construire une case parc gratuit
	 * 
	 * @param index
	 * @param nom
	 */
	public CaseParcGratuit(int index, String nom) {
		super(index, nom);
	}

	public void actionCase(JeuReel jeu, Joueur joueur) {
		// rien à faire quand on tombe sur le parc gratuit
		// le joueur finit son tour tranquillement
	}

	@Override
	public String getCouleur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

}