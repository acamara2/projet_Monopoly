
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

public class VueImpot extends JDialog {
	JeuReel jeu;
	JLayeredPane conteneur;
	JLabel label;
	JPanel pan;
	JTextArea text;
	JButton ok;
	int longueur = 110;
	int largeur = 110;
	CaseImpots macase;
	public VueImpot(JFrame j, JeuReel jeuReel,int n) {
		  super(j,"Case Impot",true);
		  this.jeu = jeuReel;
		  ActionListener payerImpot =  new PayerImpot();
		  conteneur = new JLayeredPane();
		  label = new JLabel();
		  label.setSize(new Dimension(longueur,largeur));
		  pan = new JPanel();
		  pan.setSize(new Dimension(450,200));
		  pan.setBackground(new Color(216, 229, 208));
		  ok = new JButton("ok");
		  ok.addActionListener(payerImpot);
		  ok.setBounds(225,150,70,30);
		  macase = (CaseImpots) jeu.getPlateau().getCase(jeu.getJoueurEnCours().getPion().getNumeroCase());
		  text =  new JTextArea("Vous êtes tombé sur " + macase.getNom() + ".\n"
				  + "Appuyez sur ok pour payer cet impôt.", 200, 150);
		  text.setEditable(false);
		  text.setBounds(120,70,300,50);
		  text.setBorder(null);
		  text.setOpaque(false);
		  text.setBackground(new Color(0,0,0,0));
		  ImageIcon icone;
		  if(n==1) {
		   icone = VueMonopoly.traitementImg("impotsurlerevenu.png", label);//new ImageIcon("chance.jpg");
		  }else {
			  icone = VueMonopoly.traitementImg("taxedeluxe.png", label)  ;
		  }
		  label.setIcon(icone);
		  label.setBounds(0, 30, longueur, largeur);
		  conteneur.setPreferredSize(new Dimension(450, 200));
		  conteneur.add(label, Integer.valueOf(2));
		  conteneur.add(text, Integer.valueOf(3));
		  conteneur.add(pan, Integer.valueOf(1));
		  conteneur.add(ok, Integer.valueOf(4));
		  this.add(conteneur);
		  this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		  this.setSize(450, 200);
		  this.setLocationRelativeTo(j);
		  this.pack();
		  this.setVisible(true);
		 
	  }

	
	
	private class PayerImpot implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			macase.actionCase(jeu, jeu.getJoueurEnCours());
			dispose();
		}

	}

}
