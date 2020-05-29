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

public class VuePropriete extends JDialog {
	public JeuReel jeu;
	JLayeredPane conteneur;
	CaseRue macase;
	CarteRue macarte;
	JLabel label;
	JPanel pan;
	JTextArea text;
	JButton oui;
	JButton non;
	JButton ok;
	int longueur = 110;
	int largeur = 110;

	public VuePropriete(JFrame j, JeuReel jeuReel, String name, String couleur) {
		super(j, "Case Propriété : " + name, true);
		this.jeu = jeuReel;
		ActionListener achat = new Acheter();
		ActionListener refusAchat = new RefusAchat();
		ActionListener banquier = new Banquier();
		conteneur = new JLayeredPane();
		label = new JLabel();
		label.setSize(new Dimension(longueur, largeur));
		pan = new JPanel();
		pan.setSize(new Dimension(450, 200));
		pan.setBackground(new Color(216, 229, 208));
		oui = new JButton("oui");
		oui.addActionListener(achat);
		oui.setBounds(225, 150, 70, 30);
		non = new JButton("non");
		non.addActionListener(refusAchat);
		non.setBounds(305, 150, 70, 30);
		ok = new JButton("ok");
		ok.addActionListener(banquier);
		ok.setBounds(275, 150, 70, 30);
		// Prévoir conditionnelle dans le cas où la propriété est déjà possédée
		int numeroCase = jeu.getJoueurEnCours().getPion().getNumeroCase();
		 macase = (CaseRue) jeu.getPlateau().getCase(numeroCase);
		 macarte = (CarteRue) jeu.getCartesPropriete()[macase.getIndiceTerrain()];
		if( macase.getProprietaire()==null) {
		text = new JTextArea("Vous êtes tombé sur une case propriété.\n" + "Voulez-vous acheter " + macase.getNom() +"\npour " + macarte.getPrix() +"M ?", 200,
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
			text = new JTextArea("Vous n'êtes pas le propriétaire de cette case.\n" + "Vous devez payer ce loyer : " + macarte.getLoyer() +"M.", 200,
					150);
			oui.setVisible(false);
			non.setVisible(false);
			ok.setVisible(true);
			
		}
			
		text.setEditable(false);
		text.setBounds(120, 70, 300, 50);
		text.setBorder(null);
		text.setOpaque(false);
		text.setBackground(new Color(0, 0, 0, 0));
		ImageIcon icone = VueMonopoly.traitementImg("m_" + couleur + ".jpg", label);// new ImageIcon("chance.jpg");
		label.setIcon(icone);
		label.setBounds(0, 30, longueur, largeur);
		if(jeu.getJoueurEnCours().getArgent()<macarte.getPrix()){
			oui.setVisible(false);
			}
		conteneur.setPreferredSize(new Dimension(450, 200));
		conteneur.add(label, Integer.valueOf(2));
		conteneur.add(text, Integer.valueOf(3));
		conteneur.add(pan, Integer.valueOf(1));
		conteneur.add(oui, Integer.valueOf(4));
		conteneur.add(non, Integer.valueOf(5));
		conteneur.add(ok, Integer.valueOf(6));
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
			CasePropriete position = (CasePropriete)jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase());
			jeu.getJoueurEnCours().retirerArgent(jeu.getCartesPropriete()[position.getIndiceTerrain()].getPrix());
			((CasePropriete) jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase())).setProprietaire(jeu.getJoueurEnCours());
			jeu.getJoueurEnCours().setTerrains(position.getIndiceTerrain());
			jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase()).actionCase(jeu, jeu.getJoueurEnCours());
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
			jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase()).actionCase(jeu, jeu.getJoueurEnCours());
			dispose();
		}
		
	}
	

}
