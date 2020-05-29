package modeleMonopoly.jeu;


import java.util.Random;
/*
 * CETTE CLASSE REPRESENTE LE DE 
 * @author CAMARA ABABACAR
 */
public class De {

	private int min;
	private int max;
    private int resultat;
	De(){ 
		System.setProperty( "file.encoding", "UTF-8" );
		this.max = 6;
		this.min = 1;
		this.resultat = min + (int)(Math.random() * ((max - min) + 1));
	}
	public void lancerDe() {
		this.resultat = min + (int)(Math.random() * ((max - min) + 1));
	}
    public int getResultat() {
    	int temp = this.resultat;
    	return temp;
    }
    public void setMin(int m) {
    	this.min = m;
    }
    public void setMax(int m) {
    	this.max =m;
    }
}
