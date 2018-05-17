package Sources;

import java.sql.SQLException;


/**
 * Classe ActeOfficielsPersonne
 * @author formation
 *
 */
public class ActeOfficielsPersonne extends SourcePersonne{
	
	//Constructeur
	
	
	/**
	 * Constructeur de la classe
	 * @param id_source
	 * @param id_personne
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ActeOfficielsPersonne(int id_personne, String abs_path) throws ClassNotFoundException, SQLException{
		super(id_personne, abs_path);
		this.setNature(NatureSource.Acte_officiel);
		this.save();
	}

}
