package modeleSwing;
import modeleMonopoly.plateau.*;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class VueElectricite extends JDialog {
	JLayeredPane conteneur;
	JLabel label;
	JPanel pan;
	JTextArea text;
	JButton ok;
	JButton oui;
	JButton non;
	int longueur=110;
	int largeur = 110;
	JeuReel jeu;
	CartePropriete macarte;
	CaseCompagnieDeServicePublic macase;
  public VueElectricite(JFrame j, JeuReel jeu) {
	  super(j,"Case Electricite",true);
	  this.jeu= jeu;
	  ActionListener banquier =  new Banquier();
	  ActionListener achat = new Acheter();
		ActionListener refusAchat = new RefusAchat();
	  conteneur = new JLayeredPane();
	  label = new JLabel();
	  label.setSize(new Dimension(longueur,largeur));
	  pan = new JPanel();
	  pan.setSize(new Dimension(450,200));
	  pan.setBackground(new Color(216, 229, 208));
	  ok = new JButton("ok");
	  ok.addActionListener(banquier);
	  ok.setBounds(225,150,70,30);
	  oui = new JButton("oui");
		oui.addActionListener(achat);
		oui.setBounds(225, 150, 70, 30);
		non = new JButton("non");
		non.addActionListener(refusAchat);
		non.setBounds(305, 150, 70, 30);
		int numeroCase = jeu.getJoueurEnCours().getPion().getNumeroCase();
		 macase = (CaseCompagnieDeServicePublic) jeu.getPlateau().getCase(numeroCase);
		 macarte = jeu.getCartesPropriete()[macase.getIndiceTerrain()];
		if( macase.getProprietaire()==null) {
		text = new JTextArea("Vous êtes tombé sur la Compagnie d'Electricité.\n" + "Voulez-vous acheter cette compagnie\nde service en payant " + macarte.getPrix() + "M ?", 200,
				150);
			 ok.setVisible(false);
			 oui.setVisible(true);
			 non.setVisible(true);
		}else if(macase.getProprietaire()==jeu.getJoueurEnCours()) {
			text = new JTextArea("Vous êtes le propriétaire de cette case.\n"
					+ "Félicitations !!! Appuyez sur ok pour continuer\nou réalisez une action dans la barre de gauche", 200,
					150);
			oui.setVisible(false);
			non.setVisible(false);
			ok.setVisible(true);
		}else {
			text = new JTextArea("Vous n'êtes pas le propriétaire de cette case.\n" + "Vous devez payez ce loyer : " + macase.getMontant(jeu) + "M.", 200,
					150);
			oui.setVisible(false);
			non.setVisible(false);
			ok.setVisible(true);
			
		}
	  text.setEditable(false);
	  text.setBounds(120,70,300,50);
	  text.setBorder(null);
	  text.setOpaque(false);
	  text.setBackground(new Color(0,0,0,0));
	  ImageIcon icone = VueMonopoly.traitementImg("elec.jpg", label);//new ImageIcon("chance.jpg");
	  label.setIcon(icone);
	  label.setBounds(0, 30, longueur, largeur);
	  if(jeu.getJoueurEnCours().getArgent()<macarte.getPrix()){
			oui.setVisible(false);
			}
	  conteneur.setPreferredSize(new Dimension(450, 200));
	  conteneur.add(label, Integer.valueOf(2));
	  conteneur.add(text, Integer.valueOf(3));
	  conteneur.add(pan, Integer.valueOf(1));
	  conteneur.add(ok, Integer.valueOf(4));
	  conteneur.add(oui, Integer.valueOf(4));
	  conteneur.add(non, Integer.valueOf(5));
	  this.add(conteneur);
	  this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  this.setSize(450, 200);
	  this.setLocationRelativeTo(j);
	  this.pack();
	  this.setVisible(true);
	 
  }
  
  private class Acheter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			macase = (CaseCompagnieDeServicePublic)jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase());
			
			//On retire l'argent au joueur courrant
			jeu.getJoueurEnCours().retirerArgent(macarte.getPrix());
			
			//On actualise le nombre le cartes eau/elec du joueur courrant
			jeu.getJoueurEnCours().ajouterService();
			
			//On met le joueur courrant comme proprietaire de la case
			((CaseCompagnieDeServicePublic) jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase())).setProprietaire(jeu.getJoueurEnCours());
			
			//On actualise eventuellement les loyers
			macase.actionCase(jeu, jeu.getJoueurEnCours());
		    
			//On ajoute la case au tableau des propriétés du joueur courrant
			jeu.getJoueurEnCours().setTerrains(macase.getIndiceTerrain());
			
			dispose();
		}
	}

	private class RefusAchat implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	private class Banquier implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		   macase.actionCase(jeu, jeu.getJoueurEnCours());
		   dispose();
		}
		
	}
	
		 

}
