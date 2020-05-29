package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/**
 * La Case Chance, du plateau de Monopoly.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public class CaseChance extends Case {

	/** La pioche de cartes chance. */
	private CartesChance pioche;

	/**
	 * Constuire une case chance
	 * 
	 * @param index --> numero de la case du plateau
	 * @param nom   --> nom de la case
	 */
	public CaseChance(int index, String nom) {
		super(index, nom);
		this.pioche = new CartesChance();
	}

	/**
	 * Action effectuee lorsque le joueur tombe sur une case Chance.
	 * 
	 * @param jeu    -> le jeu en cours
	 * @param joueur -> le joueur qui est en train de jouer son tour de jeu
	 */
	public void actionCase(JeuReel jeu, Joueur joueur) {
		// On "pioche" une carte chance
		int numero = jeu.getPlateau().getNumeroCarteChance();
		this.pioche.piocherCarteChance(numero, joueur, jeu);
		jeu.getPlateau().defausserCarteChance();
	}

	/** Obtenir la pioche. */
	public CartesChance getPioche() {
		return this.pioche;
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
    
	public CartesChance getCarteChance() {
		return this.pioche;
	}
}
