package modeleMonopoly.jeu;
import modeleMonopoly.joueur.Joueur;

/*
 * INTERFACE JEU 
 * @author CAMARA ABABACAR
 */
public interface Jeu {
	/**
	 * Nombre maximal de participants
	 */
    int MAX_PARTICIPANTS = 8;
    
    /**
     * Lance les deux des du jeu, le resulat du lancer est garde en attribut
     */
    public void lancerDe();
    
    /**
     * 
     * @return Le resultat du dernier lancer de de
     */
    public int getResultatDe();
    
    /**
     * 
     * @return Le joueur en cours 
     */
    public Joueur getJoueurEnCours(); // doute par rapport aleur necessite

    /**
     * Donne la main aun autre joueur.
     */
    public void setNextJoueur(); // doute par rapport a sa necessite
    
}
