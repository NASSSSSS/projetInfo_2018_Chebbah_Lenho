package Relation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Utile.MyConnection;


/**
 * Classe abstraite Relation
 * @author formation
 *
 */
public abstract class Relation {
	
	private int id_relation;
	private int id_personne1;
	private int id_personne2;
	private NatureRelation nature_relation;
	private Date date_debut;
	private Date date_fin;
	
	
	//Constructeur
	
	/**
	 * Constructeur de la classe Relation
	 * @param id_relation
	 * @param id_personne1
	 * @param id_personne2
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Relation(int id_personne1, int id_personne2) throws ClassNotFoundException, SQLException{
		this.id_personne1 = id_personne1;
		this.id_personne2 = id_personne2;
		this.create();
		this.id_relation = getIdRelation();  //prends l'identifiant que cr�ee la BDD
		
	}

	
	//Setters
	
	public void setNature_relation(NatureRelation nature_relation){
		this.nature_relation = nature_relation;
	}
	
	
	public void setDateDebut(String date_debut) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date date = simpleDateFormat.parse(date_debut);		
		Date ancienne_date = this.date_debut;  //on sauvegarde l'ancienne date
		this.date_debut = date;  //on modifie la date
		
		int ok = checkDate();  //test sur la coh�rence des dates
		
		if  (ok == -1){  //dans le cas o� il n'y a pas coh�rence
		System.out.println("Attention erreur : la date de fin est ant�rieur � la date de d�but");
		this.date_debut = ancienne_date;  //on conserve l'ancienne date
				
		}
	}
	
	public void setDateFin(String date_fin) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date date = simpleDateFormat.parse(date_fin);	
		Date ancienne_date = this.date_fin;  
		this.date_fin = date;
		
		int ok = checkDate();
		
		if  (ok == -1){
		System.out.println("Attention erreur : la date de fin est ant�rieur � la date de d�but");
		this.date_fin = ancienne_date;  //on conserve l'ancienne date	
		
		}
	}
	
	//getters
	
	/**
	 * Méthode qui renvoit l'identifiant crée par la BDD lors de la construction d'une relation
	 * En théorie on utilisera ce getter uniquement lors de la création d'une relation
	 * @return id_relation
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public int getIdRelation() throws SQLException, ClassNotFoundException {
		
		
		String sql = "SELECT * FROM relation "
				+ " WHERE relation.id_personne1 = ? "
				+ " AND relation.id_personne2 = ? ;";
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setInt(1, this.id_personne1);
		statement.setInt(2, this.id_personne2);
		System.out.println(statement.toString());
		ResultSet result = statement.executeQuery();
		
		if (result.next()) {
			return result.getInt("id");
		}
		return -1;
				
		
	}
	
	public int getId_relation(){
		return this.id_relation;
	}

	public int getId_personne1() {
		return this.id_personne1;
	}

	public int getId_personne2() {
		return this.id_personne2;
	}

	public Date getDate_debut() {
		return this.date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}
	
	//Methodes
	
	/**
	 * Méthode qui détermine si la date de debut est bien antérieur à la date de fin
	 * et renvoie un code numérique (1 si c'est le cas, -1 si ce n'est pas le cas et 0 si les infos sont insuffisantes
	 * @return int
	 */
	public int checkDate(){
		
		if(this.date_fin != null){
			if(this.date_debut != null){
				boolean check = this.date_debut.before(this.date_fin);
				
				if(check == true){
					return 1;
				}
				else{
					return -1;
				}
			}
		}
		return 0;
	}
	
	/**
	 * Méthode sauvegardant dans la BDD toutes les modifications apportées
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void save() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		String sql = "UPDATE relation SET"
				+ "  id_personne1 = ? "
				+ " , id_personne2 = ? "
				+ " , date_evenement = ? "
				+ " , date_fin = ? "
				+ " , nature_relation = ?"
				+ "WHERE id = ? ;"
								
				;
				
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);		
		statement.setInt(1, this.id_personne1);
		statement.setInt(2, this.id_personne2);
		if(this.date_debut!=null){
		statement.setString(3, simpleDateFormat.format(this.date_debut));
		}
		else{
			statement.setString(3, "");
		}
		if(this.date_fin!=null){
			statement.setString(4, simpleDateFormat.format(this.date_fin));
			}
			else{
				statement.setString(4, "");
			}
		statement.setString(5, this.relationToStr());
		statement.setInt(6, this.id_relation);
		
		System.out.println("SAUVEGARDE EFFECTUEE");
		statement.execute();
		statement.close();
		
	}
	
	
	/**
	 * Methode create() vient créer la relation dans la base de donnée
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void create() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		String sql = "INSERT INTO relation(id_personne1, id_personne2)"
				+ " VALUES (?, ?);";
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setInt(1, this.id_personne1);
		statement.setInt(2, this.id_personne2);
		
		statement.executeUpdate();
		System.out.println("RELATION CREEE");
		
		
	}

	/**
	 * Transforme nature_relation en String
	 * @return  nature de la relation au format String
	 */
	public String relationToStr(){
		
		NatureRelation nature = this.nature_relation;
		
		if(nature.equals(NatureRelation.Parente)){
			return "Parente";
		}
		if(nature.equals(NatureRelation.Fratrie)){
			return "Fratrie";
		}
		if(nature.equals(NatureRelation.Mariage)){
			return "Mariage";
		}
		if(nature.equals(NatureRelation.Parrainage)){
			return "Parrainage";
		}
		else{
			return "";
		}
	}
	

}
