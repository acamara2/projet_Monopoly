package modeleMonopoly.joueur;

import java.util.Comparator;

import modeleMonopoly.plateau.Case;

/** Joueur modelise un joueur du jeu Monopoly.
 * @author Laurene Dardinier laurene.dardinier@gmail.com
 * @version 1.1
 */ 

public class Joueur implements Comparator<Object> , Comparable<Object> {
	private int argent;
	private String nom;
	private boolean estEnFaillite ;
	private boolean estEmprisonne ;
	private int nbToursPrison;
	
    /** Les terrains acquis par le joueur sont representes par des
     * 1 dans le tableau "terrains", les autres par des 0.
     */
	private int[] terrains = new int[28];
	private int nbServicesAcquis;
	private int nbGaresAcquises;
	private Pion pion;
	private int numero;
	private int lanceDepart;
	private int nbCartesSortiePrison;
	public static final int argentInitial = 1500;

    /** Construire un joueur a partir d'un pion, d'un numero et d'un nom.
     * A son initialisation, un joueur a une somme d'argent initiale,
     * n'est ni en faillite ni en prison, n'a fait aucun tour de prison,
     * n'a aucun service ou gare acquis, aucun terrain et aucune carte
     * de sortie de prison
     * @param pion -> le pion associe
     * @param numero -> le numero du joueur
     * @param nom -> le nom du joueur
     */
	public Joueur(Pion pion, int numero, String nom) {
		this.argent = argentInitial;
		this.nom = nom;
		this.estEnFaillite = false;
		this.estEmprisonne = false;
		this.nbToursPrison = 0;
		this.nbServicesAcquis = 0;
		this.nbGaresAcquises = 0;
		for(int i=0;i<28;i++) {
			this.terrains[i] = 0;
		}
		this.pion = pion;
		this.numero = numero;
		this.nbCartesSortiePrison = 0;
	}

    /** Obtenir l'argent que le joueur possede.
     * @return son argent
     */
	public int getArgent() {
			return this.argent;
	}

	/** Obtenir le nom du joueur.
	 * @return son nom
	 */
	public String getNom() {	
		return this.nom;
	}

    /** Savoir si le joueur est en faillite.
     * @return vrai (il l'est) ou faux 
     */
    public boolean getEstEnFaillite() {
            return this.estEnFaillite;
    }

    /** Savoir si le joueur est en prison.
     * @return vrai (il l'est) ou faux 
     */
    public boolean getEstEmprisonne() {
            return this.estEmprisonne;
    }

    /** Obtenir le nombre de tours passes par le joueur en prison.
     * @return le nombre de tours
     */
    public int getNbToursPrison() {
            return this.nbToursPrison;
    }

    /** Obtenir le tableau representant les terrains du joueur.
     * @return le tableau
     */
    public int[] getTerrains() {
            return this.terrains;
    }

    /** Obtenir le nombre de gares possedees par le joueur.
     * @return le nombre de gares
     */
    public int getNbGares() {
            return this.nbGaresAcquises;
    }

    /** Obtenir le nombre de services possedes par le joueur.
     * @return le nombre de services
     */
    public int getNbServices() {
            return this.nbServicesAcquis;
    }

    /** Obtenir le pion du joueur
     * @return son pion
     */
    public Pion getPion() {
            return this.pion;
    }

    /** Obtenir le numero du joueur
     * @return son numero
     */
    public int getNumero() {
            return this.numero;
    }
    
    /** Mettre la propriete numero i comme achetee par le joueur
     * 
     * @param i --> le numero de la propriete 
     */
    public void setTerrains(int i) {
    	this.terrains[i] =1;
    }

    /** Obtenir le lance de depart du joueur.
     * @return son lance de depart
     */
    public int getLancerDepart() {
    	return this.lanceDepart;
    }

    /** Obtenir le nombre de cartes "sortie de prison" possedees par le joueur 
     * @return le nombre de cartes
     */
    public int getNbCartesSortiePrison() {
            return this.nbCartesSortiePrison;
    }

    /** Ajouter de l'argent au joueur.
     * @param somme -> la somme a lui crediter
     */
    public void ajouterArgent(int somme) {
            this.argent += somme;
    }

    /** Retirer de l'argent au joueur.
     * @param somme -> la somme a lui debiter
     */
    public void retirerArgent(int somme) {
            this.argent -= somme;
    }

    /** Crediter le joueur si passage du pion par la case depart.
     */
    public void passageCaseDepart() {
    		if (this.pion.getPassageCaseDepart()) {
    			this.ajouterArgent(200);
    		}
    }

    /** Mettre le joueur en faillite.
     */
    public void mettreEnFaillite() {
            this.estEnFaillite = true;
    }

    /** Mettre le joueur en prison.
     */
    public void emprisonner() {
    	    this.nbToursPrison = 0;
            this.estEmprisonne = true;
    }
    
    /** Sortir le joueur de prison.
     */
    public void liberer() {
        	this.estEmprisonne = false;
    }

    /** Ajouter une carte sortie de prison au compteur du nombre des cartes sortie de prison.
     */
    public void ajouterCarteSortiePrison() {
    		this.nbCartesSortiePrison += 1;
    }

    /** Definir le nombre de cartes sortie de prison possedees par le joueur.
     * @param nombre -> le nombre de cartes
     */
    public void setNbCartesSortiePrison(int nombre) {
    		this.nbCartesSortiePrison = nombre;
    }

    /** Mettre a jour le nombre de tours passes en prison.
     * Il augmente de 1 puis repasse a 0 s'il vaut 3.
     */
    public void sejournerPrison() {
            this.nbToursPrison = (this.nbToursPrison + 1)%3;
            if (this.nbToursPrison == 0) {
            	this.liberer();
            }
    }

    /**
     * @author : CAMARA ABABACAR
     * Modifier le numero du joueur
     * @param numero -> le nouveau numero du joueur
     */
    public void setNumero(int numero) {
    		this.numero = numero;
    }
    public void setLancerDepart(int valeur) {
    	this.lanceDepart = valeur;
    }

    /** Ajouter un service au compteur du nombre de services.
     */
    public void ajouterService() {
		this.nbServicesAcquis += 1;
    }

    /** Ajouter une gare au compteur du nombre de gares.
     */
    public void ajouterGare() {
    		this.nbGaresAcquises += 1;
    }

    /** Ajouter un nouveau terrain a ceux possedes par le joueur.
     * La case du tableau "terrains" associee a l'index du nouveau
     * terrain passe de 0 a 1.
     * @param terrain -> le terrain dont il prend possession
     */
    public void ajouterTerrain(Case terrain) {
            this.terrains[terrain.getIndex()-1] = 1;
    }

    /** Retirer un terrain de ceux possedes par le joueur.
     * La case du tableau "terrains" associee a l'index du
     * terrain a retirer passe de 1 a 0.
     * @param terrain -> le terrain dont il perd la possession
     */
    public void retirerTerrain(Case terrain) {
            this.terrains[terrain.getIndex()] = 0;
    }

	/**
	 * @author CAMARA ABABACAR
	 * Lister l'index des terrains que le joueur possede pour le partager tel quel.
	 * @return une chaine de caracteres contenant cette liste
	 */
	public String getInfoTerrains() {
		String Newligne = System.getProperty("line.separator");
		String s = "";
		int nbTerrains = 0;
		for (int i=0;i<28;i++) {
			if(terrains[i] == 1) {
				if (nbTerrains == 0) {
					s += (i+1);
				} else {
					s += ", " + (i+1);
				}
				nbTerrains += 1;
			}
		}
		if (nbTerrains == 1) {
			return "J'ai le terrain suivant : " + s + "." + Newligne;
		} else if (nbTerrains > 1) {
			return "J'ai les terrains suivants : " + s + "." + Newligne;
		} else {
			return "Je n'ai aucun terrain actuellement." + Newligne;
		}
	}

	/**
	 * @author CAMARA ABABACAR
	 * Stocker les informations de l'argent et des terrains possedes par le joueur
	 * pour les partager telles quelles.
	 * @return une chaine de caracteres contenant ces informations
	 */
	public String getInfo() {
		String Newligne = System.getProperty("line.separator");
		return "J'ai comme fond : " + argent + "M." + Newligne + getInfoTerrains();	
	}

	/**
	 * Stocker toutes les informations du joueur pour les partager telles quelles.
	 * @return une chaine de caracteres contenant ces informations
	 */
	public String getPlusInfo() {
		String Newligne = System.getProperty("line.separator");
		String Infos = getInfo() + "Je possède donc " + this.nbServicesAcquis + " service(s) et ";
		Infos += this.nbGaresAcquises + " gare(s)." + Newligne;
		if (this.estEmprisonne) {
			Infos += "Je suis en prison depuis " + this.nbToursPrison + " tour(s)";
		} else {
			Infos += "Je ne suis pas en prison";
		}
		if (this.nbCartesSortiePrison >= 1) {
			Infos += " et je possède " + this.nbCartesSortiePrison + "\ncarte(s) 'Libéré de prison'.";
		} else {
			Infos += ".";
		}
		return Infos;
	}

	/** @author CAMARA ABABACAR
	 * Comparer le lance de depart de deux joueurs.
	 * @param j1 -> le premier joueur
	 * @param j2 -> le deuxieme joueur
	 * @return la difference entre les deux lances (si >0, j1 a obtenu un plus grand lance que j2)
	 */
	public int compare(Joueur j1, Joueur j2) {
		return  j1.getLancerDepart() - j2.getLancerDepart();
	}
	
     /** @author CAMARA ABABACAR
     * Reecrire la methode "compare(Object, Object)" pour comparer les objets avec la fonction "compare" des joueurs.
     * @param o1 -> le premier objet
     * @param o2 -> le deuxieme objet
     * @return le resultat de la methode "compare" des joueurs.
     */
	@Override
	public int compare(Object o1, Object o2) {
		return compare((Joueur) o1,(Joueur) o2);
	}

    /**
     * @author CAMARA ABABACAR
     * Reecrire la methode "compare(Object)" pour comparer les lances de depart de notre joueur et un autre objet.
     * @param autreObjet -> l'objet auquel on compare
     * @return la difference entre les 2 lances (si >0, notre joueur a obtenu le plus grand lance)
     */
	@Override
	public int compareTo(Object autreObjet) {
		Joueur autreJoueur = (Joueur) autreObjet;
		return this.lanceDepart - autreJoueur.getLancerDepart();
	}
}
