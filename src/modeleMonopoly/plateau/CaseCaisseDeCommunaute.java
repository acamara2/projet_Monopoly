package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/**
 * La Case Caisse De communaute, du plateau de Monopoly.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public class CaseCaisseDeCommunaute extends Case {

	/** La pioche de cartes communaute. */
	private CartesCommunaute pioche;

	/**
	 * Constuire une case communaute
	 * 
	 * @param index --> numero de la case du plateau
	 * @param nom   --> nom de la case
	 */
	public CaseCaisseDeCommunaute(int index, String nom) {
		super(index, nom);
		this.pioche = new CartesCommunaute();
	}

	/**
	 * Action effectuee lorsque le joueur tombe sur une case caisse de communaute.
	 * 
	 * @param jeu    -> le jeu en cours
	 * @param joueur -> le joueur qui est en train de jouer son tour de jeu
	 */
	public void actionCase(JeuReel jeu, Joueur joueur) {
		// On "pioche" une carte communaute
		int numero = jeu.getPlateau().getNumeroCarteCommunaute();
		this.pioche.piocherCarteCommunaute(numero, joueur, jeu);
		jeu.getPlateau().defausserCarteCommunaute();
		
	}

	/** Obtenir la pioche. */
	public CartesCommunaute getPioche() {
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
	public CartesCommunaute getCarteCommunaute() {
		return this.pioche;
	}

}
