package Utile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * classe qui charge une image à afficher
 * @author chebbah-lenhof
 *
 */
public class AffichageSource extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Image image; // attribut de type Image
	
	public AffichageSource(String abs_path){
		// on cree une icone de type image
		ImageIcon ii = new ImageIcon(abs_path); // chemin vers l’image
		this.image = ii.getImage(); // on recupere l’Image de l’icone
	}
	
	/**
	 * recupere et dessine l'image
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Appel de la methode paintComponent de la classe mere
		Graphics2D g2d = (Graphics2D) g; // on cast en Graphics2D, objet permettant des manipulations plus evoluees
		g2d.drawImage(this.image, 0, 0, null); // on affiche l’image sans redimentionnement, en haut a gauche du panel
	}
	

}
