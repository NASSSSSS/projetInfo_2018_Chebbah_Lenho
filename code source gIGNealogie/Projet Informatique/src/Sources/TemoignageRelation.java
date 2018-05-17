package Sources;

import java.sql.SQLException;


/**
 * Classe TemoignageRelation
 * @author formation
 *
 */
public class TemoignageRelation extends SourceRelation{
	
	//Constructeur
	
	
	/**
	 * Constructeur de la classe
	 * @param id_relation
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
			public TemoignageRelation(int id_relation, String abs_path) throws ClassNotFoundException, SQLException{
				super(id_relation, abs_path);
				this.setNature(NatureSource.Temoignage);
				this.save();
			}

}
