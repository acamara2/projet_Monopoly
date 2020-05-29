package modeleMonopoly.plateau;


import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

import modeleMonopoly.joueur.Joueur;

public class ActionCase extends JDialog {
	Joueur j;
    public ActionCase( Joueur joueurEnCours) {
    	
    	j = joueurEnCours;
    	switch(1) {
    	case 1:
    		this.setBackground(Color.BLACK);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setSize(300, 200);
			this.setVisible(true);
			
    		break;
    	case 2:
    		break;
    	case 3:
    	    break;
    	default:
    	     break;
    	}
    }
}
