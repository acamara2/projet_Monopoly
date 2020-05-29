package modeleMonopoly.plateau;

/**
 * Classe modélisant les cartes des joueurs qui correspondent à des rues.
 * 
 * @author Benoit Baude
 * @version 1.2
 *
 */

public class CarteRue extends CartePropriete {

	/** Boolean déterminant si la carte a été achetée. */
	private boolean estDetenu;

	/** Valeur des loyers en fonction du nombre de maisons présente sur la rue. */
	/** De 0 à 4 maisons puis pour un hotel. */
	private int[] loyers = new int[6];

	/** Le nombre de maisons placées sur la rue. (de 0 à 4) */
	private int nbMaisons;

	/** La présence ou non d'un hotel sur la rue. (0 ou 1) */
	private boolean presenceHotel;

	/**
	 * Le prix pour placer une maison sur la rue. (50 100 150 ou 200) suivant la
	 * position sur le plateau.
	 */
	private int prixMaison;

	/** La possibilité de constuire des maisons. */
	private boolean constructible;

	/**
	 * Construire une rue via une série d'informations
	 * 
	 * @param prix
	 * @param loyer
	 * @param indicePlateau
	 */
	public CarteRue(int prix, int loyer, int indicePlateau, int[] loyers, int prixMaison) {
		super(prix, loyer, indicePlateau);
		this.estDetenu = false;
		this.loyers[0] = loyers[0];
		this.loyers[1] = loyers[1];
		this.loyers[2] = loyers[2];
		this.loyers[3] = loyers[3];
		this.loyers[4] = loyers[4];
		this.loyers[5] = loyers[5];
		this.nbMaisons = 0;
		this.prixMaison = prixMaison;
		this.presenceHotel = false;
		this.constructible = false;
	}

	/**
	 * Obtenir le nombre de maisons de la rue.
	 * 
	 */
	public int getNbMaisons() {
		return this.nbMaisons;
	}

	/**
	 * Changer le nombre de maisons de la rue
	 *
	 * @param maisons, le nombre de maisons à placer
	 */
	public void setNbMaisons(int maisons) {
		assert (maisons >= 0 & maisons < 5);
		// On place les maisons sur la rue
		this.nbMaisons = maisons;
		// On réactualise le loyer
		this.setLoyer(loyers[maisons]);
	}

	/**
	 * Mettre un hotel sur la rue
	 * 
	 */
	public void putHotel() {
		this.setNbMaisons(0);
		this.presenceHotel = true;
	}

	/** Rendre la propriété constructible. */
	public void setConstructible() {
		this.constructible = true;
	}

	/**
	 * Retourne la valeur de constructible
	 * 
	 */
	public boolean getConstructible() {
		return constructible;
	}

	/**
	 * Retourne le prix d'une maison dans cette rue
	 * 
	 */
	public int getPrixMaison() {
		return this.prixMaison;
	}

	/**
	 * Retourne le tableau des loyers de la rue
	 */
	public int[] getLoyers() {
		return this.loyers;
	}
}
