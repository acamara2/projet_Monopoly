package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/**
 * La Case Prison (ou "Rendre Visite"), du plateau de Monopoly.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public class CasePrison extends Case {

	/**
	 * Initialiser une cas Prison
	 * 
	 * @param index
	 * @param nom
	 */
	public CasePrison(int index, String nom) {
		super(index, nom);
	}

	/**
	 * Action effectuee lorsque le joueur tombe sur la case Prison.
	 * 
	 * @param jeu    -> le jeu en cours
	 * @param joueur -> le joueur qui est en train de jouer son tour de jeu
	 */
	public void actionCase(JeuReel jeu, Joueur joueur) {
		// on verifie si le joueur est en prison
		if (joueur.getEstEmprisonne() == true) {
			// si oui, alors on incremente son sejour en prison
			joueur.sejournerPrison();
		}
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