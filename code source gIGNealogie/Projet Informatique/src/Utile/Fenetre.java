package Utile;

import javax.swing.JFrame;

/**
 * Classe qui permet l'affichage d'une image dans une fenetre
 * @author chebbah-lenhof
 *
 */
public class Fenetre extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Fenetre(String abs_path){
		this.init(abs_path); // on separe le constructeur du code d’initialisation des parametres graphiques (bonne pratique)
		
		}
	/**
	 * Initialise la fenetre
	 * @param abs_path chemin absolu vers l'image qu'on souhaite afficher
	 */
	 	private void init(String abs_path){
		
		this.setTitle("Source"); // titre de la fenetre
		this.setSize(400, 400); // taille de la fenetre. On utilise plutot setPreferredSize si le composant parent
		
		// utilise un LayoutManager.
		this.setLocationRelativeTo(null); // positionnement centre par rapport a l’ecran
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // comportement lors d’un clic sur la croix rouge
		this.setVisible(true); // on la rend visible
		
		AffichageSource mainPanel = new AffichageSource(abs_path);
		this.setContentPane(mainPanel);  //dimensionne la fenetre selon la taille de l'image
		}
	 	
}
