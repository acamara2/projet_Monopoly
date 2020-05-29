package modeleSwing;
import modeleMonopoly.plateau.*;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/** Permet l'affichage d'un JDialog quand le joueur tombe sur une case Gare,Eau ou Electricite et actualise les parametres en coherence
 * 
 * @author Lucas Oros
 *
 */

public class VueGare extends JDialog{
	JFrame frame;
	JLayeredPane conteneur;
	JLabel label;
	JPanel pan;
	JTextArea text;
	JButton ok;
	JButton oui;
	JButton non;
	ImageIcon icone;
	JeuReel jeu;
	CasePropriete gare;
	CarteGare carte;
	Joueur jprincipal;
	int longueur=110;
	int largeur = 110;
  public VueGare(JFrame j, JeuReel jeuReel) {
	  
	  super(j,"Case Gare",true);
	  
	  this.frame = j;
	  
	  //Initialisation des elements non-graphiques
	  this.jeu = jeuReel;
	  this.jprincipal = jeu.getJoueurEnCours();
	  int numerocase = jprincipal.getPion().getNumeroCase();
	  this.gare = (CaseGare) jeu.getPlateau().getCase(numerocase);
	  int indicepropriete = gare.getIndiceTerrain();
	  this.carte = (CarteGare) jeu.getCartesPropriete()[indicepropriete];
	  //Definition des labels
	  label = new JLabel();
	  label.setSize(new Dimension(longueur,largeur));
	  this.icone = VueMonopoly.traitementImg("gare.jpg",label);
	  label.setIcon(icone);
	  label.setBounds(0, 30, longueur, largeur);
	  
	  
	  //Initialisation du conteneur
	  conteneur = new JLayeredPane();
	 
	 
	  //Definition des paneaux 
	  pan = new JPanel();
	  pan.setSize(new Dimension(450,200));
	  pan.setBackground(new Color(216, 229, 208));
	  
	  //Definition des bouttons
	  ok = new JButton("ok");
	  ok.addActionListener(new Ok());
	  ok.setBounds(275,150,70,30);
	  oui = new JButton("oui");
	  oui.addActionListener(new Achat());
	  oui.setBounds(225,150,70,30);
	  non = new JButton("non");
	  non.addActionListener(new refusAchat());
	  non.setBounds(305,150,70,30);
	  
	  //On va d'abord tester si cette propriete est libre 
	  if (gare.getProprietaire() == null) {
		  //Alors on propose au joueur d'acheter ce terrain 
		  text =  new JTextArea("Voulez-vous acheter la " + gare.getNom() +" ?\n" + "Son prix est de " 
				  + carte.getPrix() + "M.",200,150);
		  oui.setVisible(true);
		  non.setVisible(true);
		  ok.setVisible(false);
	  }
	  //Cas où le proprietaire tombe sur sa propre case 
	  else if (gare.getProprietaire() == jprincipal) {
		  text =  new JTextArea("La " + gare.getNom() + " vous appartient.",200,150);
		  oui.setVisible(false);
		  non.setVisible(false);
		  ok.setVisible(true);
	  }
	  //Cas où un joueur est tombe sur la case et doit payer un loyer
	  else {
		  text =  new JTextArea("La " + gare.getNom() + " ne vous appartient pas.\n" + "Vous devez payez ce loyer : " + carte.getLoyer() + "M.",200,150);
		  oui.setVisible(false);
		  non.setVisible(false);
		  ok.setVisible(true);
	  }
	  
	  //Caracteristiques supplementaires du texte 	
	  text.setEditable(false);
	  text.setBounds(120, 70, 300, 50);
	  text.setBorder(null);
	  text.setOpaque(false);
	  text.setBackground(new Color(0, 0, 0, 0));
	  
	  //Definition du conteneur
	  conteneur.setPreferredSize(new Dimension(450, 200));
	  conteneur.add(label, Integer.valueOf(2));
	  conteneur.add(text, Integer.valueOf(3));
	  conteneur.add(pan, Integer.valueOf(1));
	  conteneur.add(oui, Integer.valueOf(4));
	  conteneur.add(non, Integer.valueOf(5));
	  conteneur.add(ok, Integer.valueOf(6));

	  //Definition du JDialog
	  this.add(conteneur);
	  this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  this.setSize(450, 200);
	  this.setLocationRelativeTo(j);
	  this.pack();
	  this.setVisible(true);
	 
	  //On n'affichera oui que si la gare peut être achête 
	  if (jprincipal.getArgent()<carte.getPrix()) {
		  oui.setVisible(false);
	  }
  }
 /** Action Listener qui s'active quand le joueur veux acheter la case Gare dans laquelle il est tombe
  * 
  * @author Lucas Oros
  *
  */
  private class Achat implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
				//On retire au joueur la quantite d'argent corespondant au prix de la propriete
				jprincipal.retirerArgent(carte.getPrix());
				//On ajoute le joueur comme proprietaire de cette case 
				gare.setProprietaire(jprincipal);
				//On ajoute cette propriete a la liste de proprietes du joueur courrant
				jprincipal.ajouterGare();
				jprincipal.setTerrains(gare.getIndiceTerrain());
				//On actualise les loyers des gares (au cas où le joueur aurait plus d'une gare)
				gare.actionCase(jeu, jprincipal);
				dispose();
		    }

		}
  /**Action listener qui s'active quand le joueur ne veux pas acheter la case sur laquelle il est tombe 
   * 
   * @author Lucas Oros
   *
   */
  private class refusAchat implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
		
	}
	  
  }
  /** Action listener qui ferme la fenetre quand on appuie sur lui
   * 
   * @author Lucas Oros
   *
   */
  private class Ok implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gare.actionCase(jeu, jprincipal);
		dispose();
	}
	  
  }
		 
}
