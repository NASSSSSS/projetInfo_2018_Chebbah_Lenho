package Sources;

import java.sql.SQLException;


/**
 * Classe TemoignagePersonne
 * @author formation
 *
 */
public class TemoignagePersonne extends SourcePersonne{
	
	
	//Constructeur
	
	
	/**
	 * Constructeur de la classe
	 * @param id_personne
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
		public TemoignagePersonne(int id_personne, String abs_path) throws ClassNotFoundException, SQLException{
			super(id_personne, abs_path);
			this.setNature(NatureSource.Temoignage);
			this.save();
		}



}
