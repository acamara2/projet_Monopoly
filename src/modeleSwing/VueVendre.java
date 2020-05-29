package modeleSwing;
import modeleMonopoly.plateau.*;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class VueVendre extends JDialog {
	JFrame frame;
	JLabel proprietes;
	JLabel prixrue;
	JButton valider;
	JButton retour;
	JComboBox rues;
	CartePropriete[] cartesJeu;
	JeuReel jeu;
	int prix;
	CarteRue[] prop;
	
public VueVendre(JFrame j, JeuReel jeuReel) {
		
		super(j,"Menu de construction de "+jeuReel.getJoueurEnCours().getNom(),true);
		
		//try {
		this.frame = j;
		this.jeu = jeuReel;
		cartesJeu = jeu.getCartesPropriete();
		prix = 0;
		//Initialisation des Label
		proprietes = new JLabel("Rue que vous voulez vendre : ");
		prixrue = new JLabel("Le prix de la rue sélectionnée est " + prix + "M.");
		
		//Initialisation des bouttons
		valider = new JButton("Valider");
		valider.addActionListener(new Valider() );
		valider.setBounds(225,150,70,30);
		retour = new JButton("Retour");
		retour.addActionListener(new Retour());
		
		
		//Construction des JComboBox
		CarteRue[] prop = getProprietes();
		rues = new JComboBox(prop);
		
		
		//Construction des Box horizontales 
		Box hRues = Box.createHorizontalBox();
		hRues.add(proprietes);
		hRues.add(rues);
		Box hIndicateurs = Box.createHorizontalBox();
		hIndicateurs.add(prixrue);
		Box hBouttons = Box.createHorizontalBox();
		hBouttons.add(retour);
		hBouttons.add(Box.createGlue());
		hBouttons.add(valider);
		
		//Construction de la box verticale
		Box vGeneral = Box.createVerticalBox();
		vGeneral.add(hRues);
		vGeneral.add(Box.createGlue());
		vGeneral.add(hIndicateurs);
		vGeneral.add(Box.createGlue());
		vGeneral.add(hBouttons);
		
		//Implantation du JDialogue
		this.add(vGeneral,BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(450, 200);
		this.setLocationRelativeTo(j);
		this.pack();
		this.setVisible(true);//}
		//catch(Exception exc) {
		//	System.out.println("Problem" + exc.getLocalizedMessage());
			
		//}
	 
  }

/**Permet de creer un tableau contenant les cartes des proprietes qui appartiennent au joueur en cours et sont construisables 
 * 
 */
public CarteRue[] getProprietes() {
	int[] propj = this.jeu.getJoueurEnCours().getTerrains();
	ArrayList<CarteRue> prop = new ArrayList<CarteRue>();
	for (int i = 0;i<propj.length;i++) {
		//Si la carte appartient au joueur
		if (propj[i]==1) {
			//Si la carte est une rue
			if (cartesJeu[i].getClass().getName().equals("CarteRue")) {
				CarteRue rue =(CarteRue) cartesJeu[i];
				prop.add(rue);
				}
		}
	}
	CarteRue[] ret = new CarteRue[prop.size()];
	prop.toArray(ret);
	return ret;
	
}

/**Action listener correspondant a la validation de la transaction a realiser 
* 
*/
private class Valider implements ActionListener{

@Override
public void actionPerformed(ActionEvent e) {
	
	}

}
	


/** Action listener correspondant au retour dans le menu de commandes
 */
private class Retour implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
		
	}
	
}

/** Action listener qui modifie le montant a payer en fonction de la propriete selectionne 
 * 
 */
	
private class ActualiserPropriete implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
}
}
