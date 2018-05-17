package Sources;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Utile.MyConnection;

/**
 * Classe abstraite SourcePersonne
 * @author formation
 *
 */
public abstract class SourcePersonne extends Source{
	
	private int id_personne;
	
	//Constructeur
	
	/**
	 * Constructeur de la Classe SourcePersonne
	 * @param id_personne
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public SourcePersonne( int id_personne, String abs_path) throws ClassNotFoundException, SQLException{
		super(abs_path);
		this.id_personne = id_personne;
		this.create();
		this.setId_source(this.getIdSourcePersonne());
	}
	
	//getters
	
	/**
	 * Méthode qui renvoit l'identifiant crée par la BDD lors de la construction d'une source
	 * En théorie on utilisera ce getter uniquement lors de la création d'une relation
	 * @return id_source
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public int getIdSourcePersonne() throws SQLException, ClassNotFoundException {
		
		
		String sql = "SELECT * FROM source_personne "
				+ " WHERE path_file LIKE ? "
				+ "AND id_personne = ?; "
				;
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setString(1, this.getAbsFile());
		statement.setInt(2, this.id_personne);
		
		System.out.println(statement.toString());
		ResultSet result = statement.executeQuery();
		if (result.next()) {
			return result.getInt("id");
		}
		return -1;
				
		
	}
	
	
	//Méthodes
	
	/**
	 * Méthode sauvegardant dans la BDD toutes les modifications apportées
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void save() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String sql = "UPDATE source_personne SET"
				+ "  id_personne = ? "
				+ " , path_file = ? "
				+ " , type_evenement = ? "
				+ " , nature_source = ? "
				+ "WHERE id = ? ;"
								
				;
		
		
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);		
		statement.setInt(1, this.id_personne);
		statement.setString(2, this.getAbsFile());
		if(this.getEvenement()!=null){
		statement.setString(3, this.eventStr());
		}
		statement.setString(4, this.natureStr());
		statement.setInt(5, this.getIdSource());
		
		System.out.println("SAUVEGARDE EFFECTUEE");
		statement.execute();
		statement.close();
		
	}
	
	
	/**
	 * Methode create() vient créer la source dans la base de donnée
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void create() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String sql = "INSERT INTO source_personne(id_personne, path_file)"
				+ " VALUES (?, ?);";
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setInt(1, this.id_personne);
		statement.setString(2, this.getAbsFile());
		
		statement.executeUpdate();
		System.out.println("SOURCE CREEE");
		
		
	}
	
	/**
	 * Evenement sous forme de chaine de caractères
	 * @return string
	 */
	public String eventStr(){
		
		Event event = this.getEvenement();
		if(event.equals(Event.Bapteme)){
			return "Bapteme";
		}
		if(event.equals(Event.Deces)){
			return "Deces";
		}
		if(event.equals(Event.Divorce)){
			return "Divorce";
		}
		if(event.equals(Event.Filiation)){
			return "Filiation";
		}
		if(event.equals(Event.Naissance)){
			return "Naissance";
		}
		if(event.equals(Event.Marriage)){
			return "Marriage";
		}
		else{
			return "";
		}
		
	}
	
	/**
	 * Nature sous forme de chaine de caractères
	 * @return string
	 */
	public String natureStr(){
		
		NatureSource nature = this.getNature();
		if(nature.equals(NatureSource.Acte_officiel)){
			return "Acte officiel";
		}
		if(nature.equals(NatureSource.Document_officieux)){
			return "Document officieux";
		}
		if(nature.equals(NatureSource.Temoignage)){
			return "Temoignage";
		}
		else{
			return "";
		}
	}

}
	