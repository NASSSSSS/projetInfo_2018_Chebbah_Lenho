package Sources;

import java.sql.SQLException;

/**
 * Classe DocumentOfficieuxRelation
 * @author formation
 *
 */
public class DocumentOfficieuxRelation extends SourceRelation{
	
	//Constructeur
	
	/**
	 * 	Constructeur de la classe
	 * @param id_personne
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
		public DocumentOfficieuxRelation( int id_personne, String abs_path) throws ClassNotFoundException, SQLException{
			super(id_personne, abs_path);
			this.setNature(NatureSource.Document_officieux);
			this.save();
		}
}
