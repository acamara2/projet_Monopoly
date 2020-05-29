package modeleMonopoly.joueur;
import java.awt.Color;
import modeleMonopoly.plateau.*;

/** Pion modelise un pion du jeu Monopoly.
 * Un pion est modelise par sa forme, le numero de la case sur laquelle
 * il se trouve, et sa couleur.
 * @author Laurene Dardinier laurene.dardinier@gmail.com
 * @version 1.1
 */

public class Pion {
	private String forme;
	private int numeroCase;
	private Color couleur;
	private boolean passageCaseDepart;

    /** Construire un pion a partir de sa forme et sa couleur.
     * A son initialisation, un pion se trouve sur la case Depart
     * de numero 0.
     * @param forme -> sa forme
     * @param couleur -> sa couleur
     */
	public Pion(String forme, Color couleur) {
		this.forme = forme;
		this.numeroCase = 0;
		this.couleur = couleur;
		this.passageCaseDepart = false;
	}

    /** Obtenir la forme du pion.
     * @return sa forme
     */
	public String getForme() {
		return this.forme;
	}

    /** Obtenir la case sur laquelle se situe le pion.
     * @return sa case
     */
	public int getNumeroCase() {
		return this.numeroCase;
	}

    /** Obtenir la couleur du pion.
     * @return sa couleur
     */
	public String getCouleur() {
		if(this.couleur == Color.BLACK) {
			return "black";
		}
		else if(this.couleur == Color.GREEN) {
			return "green";
		}
		else if(this.couleur == Color.YELLOW) {
			return "yellow";
		}
		else if(this.couleur == Color.RED) {
			return "red";
		}
		else if(this.couleur == Color.BLUE) {
			return "bleu";
		}
		else {
			return "black";
		}
	}

    /** Savoir si le pion est passe par la case depart.
     * @return si c'est le cas
     */
	public boolean getPassageCaseDepart() {
		return this.passageCaseDepart;
	}

    /** Avancer le pion d'un certain nombre de cases.
     * @param nbCases -> le nombre de cases dont il doit avancer
     */
    public void avancer(int nbCases) {
    	this.passageCaseDepart = false;
    	int nbTotal = this.numeroCase + nbCases;
        this.numeroCase = nbTotal%40;
        if (nbTotal > 40) {
        	this.passageCaseDepart = true;
        }
    }

    /** Deplacer le pion jusqu'a une nouvelle case.
     * @param numeroCase -> le numero de la nouvelle case
     */
    public void moveTo(int numeroCase) {
        this.numeroCase = numeroCase;
    }

    /** Deplacer le pion jusqu'a une nouvelle case.
     * @param nouvelleCase -> la nouvelle case
     */
	public void moveTo(Case nouvelleCase) {
		this.numeroCase = nouvelleCase.getIndex();
	}

}
