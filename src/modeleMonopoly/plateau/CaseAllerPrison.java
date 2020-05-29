package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/**
 * La Case Aller en prison, du plateau de Monopoly.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public class CaseAllerPrison extends Case {

	/** Construire une case aller en prison
	 * 
	 * @param index
	 * @param nom
	 */
	public CaseAllerPrison(int index, String nom) {
		super(index, nom);
	}
	
	/**
	 * Action effectuee lorsque le joueur tombe sur la case "Aller en prison".
	 * 
	 * @param jeu    -> le jeu en cours
	 * @param joueur -> le joueur qui est en train de jouer son tour de jeu
	 * @return 
	 */
	public void actionCase(JeuReel jeu, Joueur joueur) {
		joueur.emprisonner();
		// La case n°10 du plateau est la case prison.
		joueur.getPion().moveTo(10);
	
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