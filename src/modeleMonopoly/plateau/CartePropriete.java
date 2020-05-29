package modeleMonopoly.plateau;

/**
 * Classe modelisant une carte qui peut être soit une rue, une gare ou
 * encore une compagnie.
 * 
 * @author bbaude
 * @version 1.2
 */

public class CartePropriete {

	/** Prix d'achat de la propriete. */
	private int prix;

	/** Numero du joueur detenant la propriete (0 si propriete libre). */
	private int numeroProprietaire;

	/** Loyer courant de la propriete. */
	private int loyer;

	/** Equivalent du numero de la case du plateau. */
	private int indicePlateau;
	
	/** Construire une carte propriete ou une compagnie de service public. */
	public CartePropriete (int prix, int loyer, int indicePlateau) {
		this.prix = prix;
		this.numeroProprietaire = 0;
		this.loyer = loyer;
		this.indicePlateau = indicePlateau;
	}

	/** Obtenir le loyer actuel de la propriete. */
	public int getLoyer() {
		return this.loyer;
	}

	/** Modifier le loyer actuel de la propriete. */
	public void setLoyer(int montant) {
		this.loyer = montant;
	}

	/**
	 * Obtenir le prix de la carte
	 * 
	 * @return
	 */
	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

}