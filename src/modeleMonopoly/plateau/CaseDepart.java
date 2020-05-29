package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/** La Case Depart, du plateau de Monopoly.
 * 
 * La case est modelisee seulement par son action lorsqu'un joueur finit son
 * tour dessus.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public class CaseDepart extends Case {

	/** Initialiser une case depart.
	 * 
	 * @param index
	 * @param nom
	 */
	public CaseDepart(int index, String nom) {
		super(index, nom);
	}
	/**
	 * Action effectuee lorsque le joueur tombe sur la case Depart.
	 * @param jeu -> le jeu en cours
	 * @param joueur -> le joueur qui est en train de jouer son tour de jeu
	 */
	public void actionCase(JeuReel jeu, Joueur joueur) {
		joueur.ajouterArgent(200);
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