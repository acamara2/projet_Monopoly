package modeleMonopoly.plateau;

/**
 * Classe modelisant les cartes des joueurs correspondants a des gares.
 * 
 * @author Omar NAIM
 * @version 1.2
 *
 */

public class CarteGare extends CartePropriete {

	/** Boolean determinant si la carte possede un proprietaire. */
	private boolean estDetenu;

	/** Valeur des loyers en fonction du nombre de gares detenues par le joueur. */
	/** De 1 a 4. */
	private int[] loyers = { 50, 100, 150, 200 };

	/**
	 * Construire une gare via une serie d'informations
	 * 
	 * @param prix
	 * @param loyer
	 * @param indicePlateau
	 */
	public CarteGare(int prix, int loyer, int indicePlateau) {
		super(prix, loyer, indicePlateau);
		this.estDetenu = false;
	}

	/** Obtenir le loyer a payer si le joueur possede i gare. */
	public int getLoyers(int i) {
		assert (i > 0 & i < 5);
		return this.loyers[i - 1];
	}

}