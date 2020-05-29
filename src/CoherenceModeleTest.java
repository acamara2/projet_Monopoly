import java.awt.Color;
import org.junit.*;
import static org.junit.Assert.*;

import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import modeleMonopoly.plateau.*;


public class CoherenceModeleTest {

	Joueur j1, j2;
	Joueur[] joueurs;
	JeuReel jeu;
	int[] cartesChance = new int[16];

	@Before
	public void setUp() {
		// Initialisation des joueurs
		j1 = new Joueur(new Pion("Chapeau", Color.GREEN), 1, "JoueurUNO");
		j2 = new Joueur(new Pion("Bateau", Color.RED), 2, "JoueurDOS");
		joueurs = new Joueur[] { j1, j2 };

		// Initialisation du jeu
		jeu = new JeuReel(joueurs);
		jeu.setJoueurs(joueurs, 2);

		// Tableau des cartes chance
		for (int i = 0; i < cartesChance.length; i++) {
			cartesChance[i] = jeu.getPlateau().getNumerosCarteChance()[i];
		}
	}

	@Test
	public void testerDefausserCartes() {
		jeu.setNextJoueur();
		// On déplace le joueur vers une case Chance.
		jeu.getJoueurEnCours().getPion().moveTo(7);
		// On tire une carte puis on la place à la fin de la pioche.
		jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase()).actionCase(jeu,
				jeu.getJoueurEnCours());
		int[] cartesChanceApresDefausse = jeu.getPlateau().getNumerosCarteChance();

		for (int j = 0; j < cartesChance.length; j++) {
			assertEquals(cartesChanceApresDefausse[j], cartesChance[(j + 1) % 16]);
		}
	}

	@Test
	public void testerChangementDeJoueurEnCours() {
		jeu.setNextJoueur();
		assert ("JoueurUNO" == jeu.getJoueurEnCours().getNom());
		jeu.setNextJoueur();
		assert ("JoueurDOS" == jeu.getJoueurEnCours().getNom());
	}

	@Test
	public void testerPaiementLoyer() {
		// On déplace le 1ER JOUEUR vers la place Pigalle
		jeu.setNextJoueur();
		jeu.getJoueurEnCours().getPion().moveTo(19);

		int PrixPlacePigalle = jeu.getCartesPropriete()[((CasePropriete) jeu.getPlateau().getCase(19)).getIndiceTerrain()].getPrix();
		CasePropriete casePlacePigalle = (CaseRue) jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase());
		CarteRue cartePlacePigalle = (CarteRue) jeu.getCartesPropriete()[casePlacePigalle.getIndiceTerrain()];

		// Le joueur j1 achete la propriete
		jeu.getJoueurEnCours().retirerArgent(cartePlacePigalle.getPrix());
		jeu.getJoueurEnCours().setTerrains(casePlacePigalle.getIndiceTerrain());
		casePlacePigalle.setProprietaire(jeu.getJoueurEnCours());
		casePlacePigalle.actionCase(jeu, jeu.getJoueurEnCours());

		// On vérifie que :
		// - j1 a été débité du prix de la rue
		// - l'argent de j2 reste inchangé
		// - la case Place Pigalle possède j1 en propriétaire
		// - j1 a son Terrain[indicePlacePigalle] égal à 1
		assert (j1.getArgent() == 1500 - PrixPlacePigalle);
		assert (j2.getArgent() == 1500);
		assert (casePlacePigalle.getProprietaire().equals(j1));
		assert (j1.getTerrains()[casePlacePigalle.getIndiceTerrain()] == 1);
	}

	@Test
	public void testerActualisationLoyerLoyer() {
		// On déplace le 1ER JOUEUR vers la rue de la Paix.
		jeu.setNextJoueur();
		jeu.getJoueurEnCours().getPion().moveTo(39);

		CasePropriete caseRueDeLaPaix = (CaseRue) jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase());
		CarteRue carteRueDeLaPaix = (CarteRue) jeu.getCartesPropriete()[caseRueDeLaPaix.getIndiceTerrain()];

		// Le joueur j1 achete la propriete
		jeu.getJoueurEnCours().retirerArgent(carteRueDeLaPaix.getPrix());
		jeu.getJoueurEnCours().setTerrains(caseRueDeLaPaix.getIndiceTerrain());
		caseRueDeLaPaix.setProprietaire(jeu.getJoueurEnCours());
		caseRueDeLaPaix.actionCase(jeu, jeu.getJoueurEnCours());

		// On déplace le 2EME JOUEUR vers la rue de la Paix.
		jeu.setNextJoueur();
		jeu.getJoueurEnCours().getPion().moveTo(39);

		int argentJ1 = j1.getArgent();
		int argentJ2 = j2.getArgent();
		int loyerRueDeLaPaix = carteRueDeLaPaix.getLoyer();

		// Le joueur j2 paie le loyer
		caseRueDeLaPaix.actionCase(jeu, j2);

		// On vérifie qu'après avoir actionné la case, le montant du loyer a bien
		// transité du joueur au propriétaire
		assert (j1.getArgent() == argentJ1 + loyerRueDeLaPaix);
		assert (j2.getArgent() == argentJ2 - loyerRueDeLaPaix);

		// On déplace le 1ER JOUEUR vers l'avenue des Champs-Elysées
		jeu.setNextJoueur();
		jeu.getJoueurEnCours().getPion().moveTo(37);

		CasePropriete caseAvenueDesChampsElysees = (CaseRue) jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase());
		CarteRue carteAvenueDesChampsElysees = (CarteRue) jeu.getCartesPropriete()[caseAvenueDesChampsElysees.getIndiceTerrain()];

		// Le joueur j1 achete la propriete
		j1.retirerArgent(carteAvenueDesChampsElysees.getPrix());
		j1.setTerrains(caseAvenueDesChampsElysees.getIndiceTerrain());
		caseAvenueDesChampsElysees.setProprietaire(j1);
		caseAvenueDesChampsElysees.actionCase(jeu, j1);

		// On vérifie que :
		// - Le loyer des champs Elysées est DOUBLE par rapport à celui initial
		// - Les Champs Elysées devient CONSTRUCTIBLE
		// - De même pour la Rue de la PAIX
		assert (carteAvenueDesChampsElysees.getLoyer() == (2 * carteAvenueDesChampsElysees.getLoyers()[0]));
		assert (carteAvenueDesChampsElysees.getConstructible());

		assert (carteRueDeLaPaix.getLoyer() == (2 * carteRueDeLaPaix.getLoyers()[0]));
		assert (carteRueDeLaPaix.getConstructible());

	}

}