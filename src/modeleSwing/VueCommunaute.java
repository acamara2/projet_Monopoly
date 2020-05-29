package modeleSwing;
import modeleMonopoly.plateau.*;
import modeleMonopoly.jeu.*;
import modeleMonopoly.joueur.*;
/** @Author Omar NAIM */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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

public class VueCommunaute extends JDialog {
	JLayeredPane conteneur;
	JLabel label;
	JPanel pan;
	JTextArea text;
	JButton ok;
	int longueur=110;
	int largeur = 110;
	JeuReel jeuReel;
	JFrame j;
	boolean first = true;
	
  public VueCommunaute(JFrame j, JeuReel jeuReel) {
	  super(j,"Case Communaute",true);
	  this.j = j;
	  this.jeuReel = jeuReel;
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
	  text =  new JTextArea("Vous êtes tombé sur une case Caisse de\nCommunauté. Veuillez piocher une carte\nCaisse de Communauté.", 200, 150);
	  text.setEditable(false);
	  text.setBounds(120,70,300,50);
	  text.setBorder(null);
	  text.setOpaque(false);
	  text.setBackground(new Color(0,0,0,0));
	  ImageIcon icone = VueMonopoly.traitementImg("communaute.jpg", label);//new ImageIcon("chance.jpg");
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
			//Obtenir l'action de la case communauté
			if(first) {
			Joueur joueur = jeuReel.getJoueurEnCours();
			CaseCaisseDeCommunaute caseCommunaute = (CaseCaisseDeCommunaute) jeuReel.getPlateau().getCase(joueur.getPion().getNumeroCase());//
			caseCommunaute.actionCase(jeuReel, joueur);
			String textcarte = caseCommunaute.getCarteCommunaute().getTexteCarte();
			text.setText("Vous avez tiré 1 carte Caisse de Communauté :\n"+
			textcarte);
			first =false;
			repaint();
			}else {
			dispose();
			}
			
			}

		}
		 
  

}
