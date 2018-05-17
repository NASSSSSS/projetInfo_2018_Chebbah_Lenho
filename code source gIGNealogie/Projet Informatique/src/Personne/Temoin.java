package Personne;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Utile.MyConnection;

/**
 * Classe Temoin
 * @author formation
 *
 */
public class Temoin {
	
	private int id_temoin;
	private int id_temoignage;
	private String nom;
	private String prenom;
	
	
	//Constructeurs
	
	/**
	 * Constructeur de la classe Temoin si la personne détient un témoignage
	 * @param id_temoignage
	 * @param nom
	 * @param prenom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Temoin(int id_temoignage, String nom, String prenom) throws ClassNotFoundException, SQLException{
		this.id_temoignage = id_temoignage;
		this.nom = nom;
		this.prenom = prenom;		
		int check = this.checkInDB();   //on vérifie si l'on ne connait pas le témoin dans la BDD
		if(check != -1){
			this.create();
			this.id_temoin = check;
			this.save();
		}
		else {
			this.create();
		}
		this.save();
	}

	
	/**
	 * Constructeur de la classe Temoin si la personne ne détient pas de témoignage
	 * @param id_temoignage
	 * @param nom
	 * @param prenom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Temoin( String nom, String prenom) throws ClassNotFoundException, SQLException{
		this.nom = nom;
		this.prenom = prenom;		
		int check = this.checkInDB();   //on vérifie si l'on ne connait pas le témoin dans la BDD
		if(check != -1){
			this.create();
			this.id_temoin = check;
			this.save();
		}
		else {
			this.create();
		}
	}
	
	
	//Setters
	
	public void setId_temoignage(int id_temoignage) {
		this.id_temoignage = id_temoignage;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	
	
	//Methodes
	
	/**
	 * Méthode qui vérifie l'existence d'une personne et affiche les identifiants des personnes possédant un certain nom-prenom
	 * permet ensuite de choisir la personne qui nous intéresse
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int checkInDB() throws ClassNotFoundException, SQLException{
		
		
		String sql = "SELECT * FROM personne_civile"
				+ " WHERE nom LIKE ? "
				+ " AND prenom LIKE ? ;";
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setString(1, nom);
		statement.setString(2, prenom);
		ResultSet result = statement.executeQuery();
		
		int k=0;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();   //on vient cr�er une map contenant les identifiants des personnes ayant ce nom-prenom
		while(result.next()){
			k+=1;
			int id = result.getInt("id");
			String nom2 = result.getString("nom");
			String prenom2 = result.getString("prenom");
			String prenoms = result.getString("prenoms_secondaires");
			map.put(k, id);
			System.out.println( k + ".  " + nom2 + "  " + prenom2 + "  " + prenoms);  //affichage des diff�rentes personnes											
		}
		
		result.close();
		statement.close();
		
		
		if (k!=0){ //si une ou plusieurs personnes existent
			Scanner sc = new Scanner(System.in);
			System.out.println("Entrer le numéro de la personne correspondante, si cette personne n'est pas dans la liste entrez -1");
			int cle = sc.nextInt();  //on demande de rentrer le num�ro de la personne correspondante
			int id = map.get(cle);
			sc.close();
			return id;	
			
		}
		else{
			return -1;  //si la personne n'existe pas 
		}
		
	}
	
	/**
	 * Méthode sauvegardant dans la BDD toutes les modifications apportées
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void save() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
				
		String sql = "UPDATE temoin SET"
				+ "  id_temoin = ? "
				+ " , id_temoignage = ? "
				+ " , nom = ? "
				+ " , prenom = ? "
				+ "WHERE id_temoin = ? ;"
								
				;
				
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);		
		statement.setInt(1, this.id_temoin);
		statement.setInt(2, this.id_temoignage);		
		statement.setString(3, this.nom);	
		statement.setString(4, this.prenom);
		statement.setInt(5, this.id_temoin);
		
		System.out.println("SAUVEGARDE EFFECTUEE");
		statement.execute();
		statement.close();
		
	}
	
	
	/**
	 * Methode create() vient créer le témoin dans la base de donnée à partir de nom-prenom du temoin
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void create() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String sql = "INSERT INTO temoin(nom, prenom)"
				+ " VALUES (?, ?);";
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setString(1, this.nom);
		statement.setString(2, this.prenom);
		
		System.out.println("TEMOIN CREE");
		statement.execute();
		
		statement.close();
		
	}


}
