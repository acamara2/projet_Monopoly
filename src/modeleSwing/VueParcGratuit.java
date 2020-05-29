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



public class VueParcGratuit extends JDialog {
	JLayeredPane conteneur;
	JLabel label;
	JPanel pan;
	JTextArea text;
	JButton ok;
	int longueur=110;
	int largeur = 110;
  public VueParcGratuit(JFrame j, JeuReel jeuReel) {
	  super(j,"Case Parc Gratuit",true);
	  ActionListener pioche =  new Piocher();
	  conteneur = new JLayeredPane();
	  label = new JLabel();
	  label.setSize(new Dimension(longueur,largeur));
	  pan = new JPanel();
	  pan.setSize(new Dimension(450,200));
	  pan.setBackground(new Color(216, 229, 208));
	  ok = new JButton("ok");
	  ok.addActionListener(pioche);
	  ok.setBounds(225,150,70,30);
	  text =  new JTextArea("Vous êtes tombé sur le Parc Gratuit.\n"
			  + "Appuyez pour passer.", 200, 150);
	  text.setEditable(false);
	  text.setBounds(120,70,300,50);
	  text.setBorder(null);
	  text.setOpaque(false);
	  text.setBackground(new Color(0,0,0,0));
	  ImageIcon icone = VueMonopoly.traitementImg("parc.jpg", label);//new ImageIcon("chance.jpg");
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
  private class Piocher implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		
		         dispose();
			}

		}
}
