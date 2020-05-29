import java.awt.EventQueue;

import modeleMonopoly.jeu.JeuReel;
import modeleSwing.VueMonopoly;


/* 
 * CETTE CLASSE PERMET DE LANCER LE JEU
 * @author CAMARA ABABACAR 
 */
public class Partie{
	////////////////////////////// PROGRAMME Principal ////////////////////////////
	public static void main(String[] args) {
		System.setProperty( "file.encoding", "UTF-8" );
		JeuReel jeu = new JeuReel(null);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new VueMonopoly(jeu);
			}
		});
	}

}
