package Sources;

import java.sql.SQLException;


/**
 * Classe DocumentOfficieuxPersonne
 * @author formation
 *
 */
public class DocumentOfficieuxPersonne extends SourcePersonne{
	
	//Constructeur
	
	
	/**
	 * Constructeur de la classe
	 * @param id_personne
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DocumentOfficieuxPersonne( int id_personne, String abs_path) throws ClassNotFoundException, SQLException{
		super(id_personne, abs_path);
		this.setNature(NatureSource.Document_officieux);
		this.save();
	}

}