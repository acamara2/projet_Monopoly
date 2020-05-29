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

/** JDialogue qui permet d'acheter des maisons sur des propriétés construisables 
 * 
 * @author Lucas Oros
 *
 */

public class VueConstruire extends JDialog {
	JFrame frame;
	JLabel proprietes;
	JLabel maisons;
	JLabel cout;
	JLabel argent;
	JLabel presentationHotel;
	JButton valider;
	JButton retour;
	JButton hotel;
	JComboBox rues;
	JComboBox nbmaisons;
	int longueur=110;
	int largeur = 110;
	int facture;
	int nombremaisons;
	CartePropriete[] cartesJeu;
	CarteRue CarteProp;
	JeuReel jeu;
	boolean choixHotel;
	
	public VueConstruire(JFrame j, JeuReel jeuReel) {
		
		super(j,"Menu de construction de "+jeuReel.getJoueurEnCours().getNom(),true);
		
		//try {
		this.frame = j;
		this.jeu = jeuReel;
		choixHotel = false;
		cartesJeu = jeu.getCartesPropriete();
		facture = 0;
		//Initialisation des Label
		proprietes = new JLabel("Rue sur laquelle vous voulez construire : ");
		maisons = new JLabel("Nombre de maisons à construire : ");
		cout = new JLabel("Le montant à payer est de : ");
		argent = new JLabel("Vous disposez de : " + jeuReel.getJoueurEnCours().getArgent());
		presentationHotel = new JLabel("Si vous voulez transformer 4 maisons en un hôtel : ");
		
		//Initialisation des bouttons
		valider = new JButton("Valider");
		valider.addActionListener(new Valider() );
		valider.setBounds(225,150,70,30);
		retour = new JButton("Retour");
		retour.addActionListener(new Retour());
		hotel = new JButton("Hotel");
		hotel.addActionListener(new Hotel());
		
		//Construction des JComboBox
		CarteRue[] cons = getConstruisables();
		rues = new JComboBox(cons);
		Integer[] m = {Integer.valueOf(1),Integer.valueOf(2),Integer.valueOf(3),Integer.valueOf(4)};
		nbmaisons = new JComboBox(m);
		nombremaisons = nbmaisons.getSelectedIndex()+1;
		CarteProp = (CarteRue) rues.getSelectedItem();
		//Construction des Box horizontales 
		Box hRues = Box.createHorizontalBox();
		hRues.add(proprietes);
		hRues.add(rues);
		Box hMaisons = Box.createHorizontalBox();
		hMaisons.add(maisons);
		hMaisons.add(nbmaisons);
		Box hHotel = Box.createHorizontalBox();
		hHotel.add(presentationHotel);
		hHotel.add(hotel);
		Box hInfo = Box.createHorizontalBox();
		hInfo.add(cout);
		hInfo.add(Box.createGlue());
		hInfo.add(argent);
		Box hBouttons = Box.createHorizontalBox();
		hBouttons.add(retour);
		hBouttons.add(Box.createGlue());
		hBouttons.add(valider);
		
		//Construction de la box verticale
		Box vGeneral = Box.createVerticalBox();
		vGeneral.add(hRues);
		vGeneral.add(hMaisons);
		vGeneral.add(hHotel);
		vGeneral.add(hInfo);
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
  
	/**Permet de créer un tableau contenant les cartes des propriétés qui appartiennent au joueur en cours et sont construisables 
	 * 
	 */
	public CarteRue[] getConstruisables() {
		int[] propj = this.jeu.getJoueurEnCours().getTerrains();
		ArrayList<CarteRue> prop = new ArrayList<CarteRue>();
		for (int i = 0;i<propj.length;i++) {
			//Si la carte appartient au joueur
			if (propj[i]==1) {
				//Si la carte est une rue
				if (cartesJeu[i].getClass().getName().equals("CarteRue")) {
					CarteRue rue =(CarteRue) cartesJeu[i];
					//Si on peut construire des maisons sur cette rue
					if (rue.getConstructible()) {
						prop.add(rue);
					}
				}
			}
		}
		CarteRue[] ret = new CarteRue[prop.size()];
		prop.toArray(ret);
		return ret;
		
	}
	
	/**Action listener correspondant a la validation de la transaction a réaliser 
   * 
   */
	private class Valider implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		//Verifier que le joueur peut effectuer cette trnasaction et affichez un message si ce n'est pas le cas
		if (facture>jeu.getJoueurEnCours().getArgent()) {
			JOptionPane.showMessageDialog(frame, "Vous n'avez pas assez d'argent","Operation Interdite",
					JOptionPane.INFORMATION_MESSAGE);
		}
		//Si le joueur a assez d'argent pour faire la modification on la réalise sur le Jeu
		else {
			CarteRue choix = (CarteRue) rues.getSelectedItem();
			//Si quelquechose a été choisi
			if (choix != null) {
				if (choixHotel) {
					//On verifie si on peut faire un hotel sur la rue
					if (choix.getNbMaisons() == 4) {
						jeu.getJoueurEnCours().retirerArgent(facture);
						choix.putHotel();
					}
					//On ne peut pas mettre un hotel car il n'y a pas 4 maisons
					else {
						JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas construire un hôtel parce que vous n'avez pas 4 maisons sur cette rue",
								"Opération Interdite",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					if (nombremaisons+choix.getNbMaisons()>4) {
						JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas construire autant de maisons car le nombre de maisons max est 4",
								"Opération Interdite",JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						jeu.getJoueurEnCours().retirerArgent(facture);
						choix.setNbMaisons(nbmaisons.getSelectedIndex()+1);
						dispose();
					}
						
				}
				
			}
			//Si rien est choisi(par exemple aucune propriété est constructible)
			else {
				JOptionPane.showMessageDialog(frame, "Vous n'avez sélectionné aucune rue","Opération Interdite",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	
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
	
	/** Action listener qui modifie le montant a payer en fonction de la propriété selectionné 
	 * 
	 */
		
	private class ActualiserPropriete implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			CarteProp =(CarteRue) rues.getSelectedItem();
			if (choixHotel) {
				facture = CarteProp.getPrixMaison();
			}
			else {
				facture = CarteProp.getPrixMaison()*nombremaisons;
			}
		}
		
	}
	
	/** Action listener qui modifie le montant a payer en fonction du nombre de maisons a construire sur la propriété
	 * 
	 */
	private class ActualiserMaison implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//On cherche dans la rue selectionné le prix d'une maison et on multiplie par le nombre de maisons choisi si !choixhotel
			nombremaisons = nbmaisons.getSelectedIndex()+1;
			facture = CarteProp.getPrixMaison()*nombremaisons;
			
		}
		
	}
	//Pour hotel ça serait bien de faire un interrupteur (un boutton mais qui reste ou reste pas appuié)
	/**Action listener qui modifie le montant et grise la JComboBox correspondant aux maisons quand on choisit hotel 
	 * 
	 */
	private class Hotel implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			choixHotel = true;
			hotel.setEnabled(false);
			nbmaisons.setVisible(false);
			facture = CarteProp.getPrixMaison();
			//Modifier la facture		
				
			
		}
		
	}
	
	
	
}

