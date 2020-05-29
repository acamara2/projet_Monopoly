package modeleMonopoly.plateau;

import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import java.awt.Color;

/**
 * Plateau du jeu Monopoly, avec les 40 cases, les cartes Chance et Caisse de
 * Communaute
 * 
 * @author Benoit Baude
 * @version 1.2
 */

public class Plateau {

	/** la taille du plateau */
	static int TAILLE = 40;

	/** les cases du tableau. */
	private Case[] ensCases = new Case[Plateau.TAILLE];

	/** Les numeros des cartes chance du plateau. */
	private int[] numerosCarteChance = new int[16];

	/** Les numeros des cartes communaute du plateau. */
	private int[] numerosCarteCommunaute = new int[16];

	/**
	 * Initialiser le plateau entier : - les cases du plateau - la pioche des cartes
	 * chance - la pioche des cartes caisse de communaute
	 */
	public Plateau() {
		this.ensCases[0] = new CaseDepart(0, "Case Départ");
		this.ensCases[1] = new CaseRue(1, "Boulevard de Belleville", 0, "brun", 1, 1);
		this.ensCases[2] = new CaseCaisseDeCommunaute(2, "Caisse de Communauté");
		this.ensCases[3] = new CaseRue(3, "Rue Lecourbe", 1, "brun", 0, 0);
		this.ensCases[4] = new CaseImpots(4, "Impôts sur le revenu");
		this.ensCases[5] = new CaseGare(5, "Gare Montparnasse", 2);
		this.ensCases[6] = new CaseRue(6, "Rue de Vaugirard", 3, "turquoise", 4, 5);
		this.ensCases[7] = new CaseChance(7, "Chance");
		this.ensCases[8] = new CaseRue(8, "Rue de Courcelles", 4, "turquoise", 3, 5);
		this.ensCases[9] = new CaseRue(9, "Avenue de la Republique", 5, "turquoise", 3, 4);
		this.ensCases[10] = new CasePrison(10, "Prison");
		this.ensCases[11] = new CaseRue(11, "Boulevard de la Villette", 6, "mauve", 8, 9);
		this.ensCases[12] = new CaseCompagnieDeServicePublic(12, "Compagnie de distribution d'Electricité", 7, 28, 20);
		this.ensCases[13] = new CaseRue(13, "Avenue de Neuilly", 8, "mauve", 6, 9);
		this.ensCases[14] = new CaseRue(14, "Rue de Paradis", 9, "mauve", 6, 8);
		this.ensCases[15] = new CaseGare(15, "Gare de Lyon", 10);
		this.ensCases[16] = new CaseRue(16, "Avenue Mozart", 11, "orange", 12, 13);
		this.ensCases[17] = new CaseCaisseDeCommunaute(17, "Caisse de Communauté");
		this.ensCases[18] = new CaseRue(18, "Boulevard Saint-Michel", 12, "orange", 11, 13);
		this.ensCases[19] = new CaseRue(19, "Place Pigalle", 13, "orange", 11, 12);
		this.ensCases[20] = new CaseParcGratuit(20, "Parc Gratuit");
		this.ensCases[21] = new CaseRue(21, "Avenue Matignon", 14, "rouge", 15, 16);
		this.ensCases[22] = new CaseChance(22, "Chance");
		this.ensCases[23] = new CaseRue(23, "Boulevard Malesherbes", 15, "rouge", 14, 16);
		this.ensCases[24] = new CaseRue(24, "Avenue Henri-Martin", 16, "rouge", 14, 15);
		this.ensCases[25] = new CaseGare(25, "Gare du Nord", 17);
		this.ensCases[26] = new CaseRue(26, "Faubourg Saint-Honoré", 18, "jaune", 19, 21);
		this.ensCases[27] = new CaseRue(27, "Place de la Bourse", 19, "jaune", 18, 21);
		this.ensCases[28] = new CaseCompagnieDeServicePublic(28, "Compagnie de distributiuon des Eaux", 20, 12, 7);
		this.ensCases[29] = new CaseRue(29, "Rue Lafayette", 21, "jaune", 18, 19);
		this.ensCases[30] = new CaseAllerPrison(30, "Allez en prison");
		this.ensCases[31] = new CaseRue(31, "Avenue de Breteuil", 22, "vert", 23, 24);
		this.ensCases[32] = new CaseRue(32, "Avenue Foch", 23, "vert", 22, 24);
		this.ensCases[33] = new CaseCaisseDeCommunaute(33, "Caisse de communauté");
		this.ensCases[34] = new CaseRue(34, "Boulevard des Capucines", 24, "vert", 22, 23);
		this.ensCases[35] = new CaseGare(35, "Gare Saint-Lazare", 25);
		this.ensCases[36] = new CaseChance(36, "Chance");
		this.ensCases[37] = new CaseRue(37, "Avenue des Champs-Elysées", 26, "bleu", 27, 27);
		this.ensCases[38] = new CaseImpots(38, "Taxe de luxe");
		this.ensCases[39] = new CaseRue(39, "Rue de la Paix", 27, "bleu", 26, 26);
		
		melangerCartesPiochees(this.numerosCarteChance);
		melangerCartesPiochees(this.numerosCarteCommunaute);
	}

	/** Obtenir une case du plateau. */
	public Case getCase(int numero) {
		assert (numero >= 0 & numero < 40);
		return this.ensCases[numero];
	}

	/** Obtenir le numero de la 1ere carte Chance de la pioche. */
	public int getNumeroCarteChance() {
		return this.numerosCarteChance[0];
	}

	/** Obtenir le numero de la 1ere carte Communaute de la pioche. */
	public int getNumeroCarteCommunaute() {
		return this.numerosCarteCommunaute[0];
	}

	/**
	 * Obtenir le numero de la derniere carte Chance de la pioche
	 * 
	 * @return l'entier compris entre 0 et 15
	 */
	public int getDernierCarteChancePioche() {
		return this.numerosCarteChance[15];
	}

	/**
	 * Obtenir le numero de la derniere carte Communaute de la pioche
	 * 
	 * @return l'entier compris entre 0 et 15
	 */
	public int getDernierCarteCommunautPioche() {
		return this.numerosCarteCommunaute[15];
	}

	/**
	 * Obtenir le tableau des indices des cartes Chance
	 * 
	 */
	public int[] getNumerosCarteChance() {
		return this.numerosCarteChance;
	}

	/**
	 * Obtenir le tableau des indices des cartes Communaute
	 * 
	 */
	public int[] getNumerosCarteCommunaute() {
		return this.numerosCarteCommunaute;
	}

	/**
	 * Remettre une carte chance dans la pioche <=> decaler tous les indices de 1.
	 */
	public void defausserCarteChance() {
		int cartePiochee = this.getNumeroCarteChance();
		for (int i = 0; i < numerosCarteChance.length - 1; i++) {
			numerosCarteChance[i] = numerosCarteChance[i + 1];
		}
		numerosCarteChance[numerosCarteChance.length - 1] = cartePiochee;
	}

	/**
	 * Remettre une carte communaute dans la pioche <=> decaler tous les indices de
	 * 1.
	 */
	public void defausserCarteCommunaute() {
		int cartePiochee = this.getNumeroCarteCommunaute();
		for (int i = 0; i < numerosCarteCommunaute.length - 1; i++) {
			numerosCarteCommunaute[i] = numerosCarteCommunaute[i + 1];
		}
		numerosCarteCommunaute[numerosCarteCommunaute.length - 1] = cartePiochee;
	}

	/**
	 * Melanger les cartes Chance ou cartes de communaute
	 * 
	 */
	public void melangerCartesPiochees(int[] pioche) {
		int[] tableau = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int compteur = 0;
		while (compteur < 16) {
			int indiceRandom = (int) (Math.random() * 16);
			if (tableau[indiceRandom] == 0) {
				pioche[compteur] = indiceRandom;
				tableau[indiceRandom] = 1;
				compteur++;
			}
		}
	}
}
