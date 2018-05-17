package Sources;

import java.sql.SQLException;


/**
 * Classe ActeOfficielsRelation
 * @author formation
 *
 */
public class ActeOfficielsRelation extends SourceRelation{
	
	//Constructeur
	
	
	/**
	 * Constructeur de la classe
	 * @param id_relation
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ActeOfficielsRelation(int id_relation, String abs_path) throws ClassNotFoundException, SQLException{
		super( id_relation, abs_path);
		this.setNature(NatureSource.Acte_officiel);
		this.save();
	}

}
