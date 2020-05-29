package modeleMonopoly.plateau;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import java.awt.Color;

/**
 * La classe CaseRue modelise la case d'une gare, ou le loyer a payer depend du
 * nombre de gares achetees.*
 * 
 * @author Omar NAIM
 * @version 1.2
 */

public class CaseGare extends CasePropriete {

	/** La couleur de la rue. */
	private Color couleur;

	/** L'indice des quatres gares du jeu. */
	private int[] caseFamille = { 5, 15, 25, 35 };
	/** L'indice des gares dans le tableau des cases proprietes. */
	private int[] indicesCartesGare = {2,10,17,25};
	/** Initialiser une case gare */
	public CaseGare(int index, String nom, int indiceTerrain) {
		super(index, nom, indiceTerrain);
		
	}
	
	/**
	 * Procedure lancee apres que le joueur a pris sa decision d'acheter ou non la
	 * propriete sur laquelle il est tombe.
	 * 
	 * @param jeu    -> le jeu en cours
	 * @param joueur -> le joueur qui joue son tour de jeu
	 */
	public void actionCase(JeuReel jeu, Joueur joueur) {
		// La case possede un proprietaire qui est different de joueur -> On lui retire
		// de l'argent.
		if (this.proprietaire != null && this.proprietaire != joueur) {
			CartePropriete carte = jeu.getCartesPropriete()[super.getIndiceTerrain()];
			joueur.retirerArgent(carte.getLoyer());
			Joueur proprio = ((CasePropriete) jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase())).getProprietaire();
			proprio.ajouterArgent(carte.getLoyer());
		}
		// Le joueur est le proprietaire de la gare, on verifie met a jour le loyer de
		// chacune de ses gares.
		else if (this.proprietaire == joueur) {
			if (joueur.getNbGares() > 1) {
				// On met a jour le loyer de tous les gares qu'il possede
				for (int i = 0; i < indicesCartesGare.length; i++) {
					//Si le proprietaire de la ième carte de la famille est le joueur courrant alors
					if (((CasePropriete) jeu.getPlateau().getCase(caseFamille[i]))
							.getProprietaire() == joueur) {
						int leloyer = ((CarteGare) jeu.getCartesPropriete()[indicesCartesGare[i]]).getLoyers(joueur.getNbGares());
						jeu.getCartesPropriete()[indicesCartesGare[i]].setLoyer(leloyer);
					}
				}

			} else {
				// Rien a faire si le joueur a decide de ne pas acheter la propriete.
			}
		}
	}

	@Override
	public String getCouleur() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
