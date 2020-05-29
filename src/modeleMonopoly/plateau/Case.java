package modeleMonopoly.plateau;

import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import java.awt.Color;

/**
 * La classe abstraite Case, modelisant n'importe quelle case du plateau de
 * Monopoly.
 * 
 * @author Benoit Baude, Omar Naim
 * @version 1.2
 */

public abstract class Case {

	/** Le numero de la case du plateau. */
	private int index;

	/** Le nom de la case. */
	protected String nom;
	
	/** la couleur */

	/**
	 * Initialiser une case du plateau de jeu.
	 * 
	 * @param index
	 * @param nom
	 */
	public Case(int index, String nom) {
		this.index = index;
		this.nom = nom;
	}

	/** Obtenir le numero de la case du plateau. */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Methode abstraite modelisant l'action effectuee lorsqu'un joueur finit son
	 * tour sur la case.
	 */
	public abstract void actionCase(JeuReel jeu, Joueur joueur);
	
	public abstract String getCouleur();
	
	/**
	 * Obtenir le nom de la case
	 * 
	 * @return --> nom : la chaine de caractere du nom de la case
	 */
	public String getNom() {
		return this.nom;
	}

	

}