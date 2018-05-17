package Personne;

import java.util.Date;

import Utile.MyConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat; 


/**
 * Classe PersonneCatholique
 * @author formation
 *
 */

public class PersonneCatholique {
		
	private final int id_personne;
	private Date date_bapteme;
	private String lieu_bapteme;
	private Date date_marriage;
	private String lieu_mariage;
	private Date enterrement;
	private String sepulture;
	
	
	//Constructeur
	
	/**
	 * Constructeur de la classe PersonneCatholique
	 * @param id
	 * @throws ClassNotFoundException 
	 */
	public PersonneCatholique(int id)throws SQLException, ClassNotFoundException {
		
		this.id_personne = id;
		this.create();
	}
	
	//Setters
	
	public void setDate_bapteme(String date_bapteme) throws ParseException {
		if(!date_bapteme.equals("")){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = simpleDateFormat.parse(date_bapteme);
		Date ancienne_date = this.date_bapteme;  
		this.date_bapteme = date;
		
		int ok = checkDate();
		if  (ok == -1){
			System.out.println("Attention erreur : veuillez entrer une date de facon � avoir le bapteme ant�rieur au marriage lui m�me ant�rieur � l'enterrement");
		this.date_bapteme = ancienne_date;  //on conserve l'ancienne date
		}
		}
	}

	public void setLieu_bapteme(String lieu_bapteme) {
		this.lieu_bapteme = lieu_bapteme;
	}

	public void setDate_marriage(String date_marriage) throws ParseException {
		if(!date_marriage.equals("")){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = simpleDateFormat.parse(date_marriage);
		Date ancienne_date = this.date_marriage;  
		this.date_marriage = date;
		
		int ok = checkDate();
		if  (ok == -1){
			System.out.println("Attention erreur : veuillez entrer une date de facon � avoir le bapteme ant�rieur au marriage lui m�me ant�rieur � l'enterrement");
		this.date_marriage = ancienne_date;  //on conserve l'ancienne date
		}
		}
	}

	public void setLieu_mariage(String lieu_mariage) {
		this.lieu_mariage = lieu_mariage;
	}

	public void setEnterrement(String enterrement) throws ParseException {
		if(!enterrement.equals("")){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = simpleDateFormat.parse(enterrement);
		Date ancienne_date = this.enterrement;  
		this.enterrement = date;
		
		int ok = checkDate();
		if  (ok == -1){
			System.out.println("Attention erreur : veuillez entrer une date de facon � avoir le bapteme ant�rieur au marriage lui m�me ant�rieur � l'enterrement");
		this.enterrement = ancienne_date;  //on conserve l'ancienne date
		}
		}
	}

	public void setSepulture(String sepulture) {
		this.sepulture = sepulture;
	}
	
	
	//Méthodes

	
	
	/**
	 * Methode create() vient créer la personne religieuse dans la BDD
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void create() throws SQLException, ClassNotFoundException {

		// TODO Auto-generated method stub
		
		
		String sql = "INSERT INTO personne_religieuse(id_personne)"
				+ " VALUES (?);";
		PreparedStatement statement2 =  MyConnection.getInstance().prepareStatement(sql);
		statement2.setInt(1, this.id_personne);		
		
		statement2.executeUpdate();
		System.out.println("PERSONNE CATHOLIQUE CREEE");
		
	}

	
	/**
	 * Méthode sauvegardant dans la BDD toutes les modifications apportées
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void save() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
		
		String sql = "UPDATE personne_religieuse SET "
				+ "  date_bapteme = ? "
				+ " , lieu_bapteme = ? "
				+ " , date_marriage = ? "
				+ " , lieu_marriage = ? "
				+ " , enterrement = ? "
				+ " , sepulture = ? "
				+ "WHERE id_personne = ? ;"
								
				;
				
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);			
		if(this.date_bapteme != null){
			statement.setString(1, simpleDateFormat2.format(this.date_bapteme));
			}
			else {
				statement.setString(1, null);
			}
		statement.setString(2, this.lieu_bapteme);
		if(this.date_marriage != null){
			statement.setString(3, simpleDateFormat2.format(this.date_marriage));
			}
			else {
				statement.setString(3, null);
			}		
		statement.setString(4, this.lieu_mariage);		
		if(this.enterrement != null){
			statement.setString(5, simpleDateFormat2.format(this.enterrement));
			}
			else {
				statement.setString(5, null);
			}
		statement.setString(6, this.sepulture);
		
		statement.setInt(7, this.id_personne);
		
		System.out.println("SAUVEGARDE REUSSIE");
		statement.execute();

		
	}
	
	/**
	 * M�thode qui v�rifie que les dates de bapteme, mariage et d'enterrement se suivent chronologiquement
	 * @return int 0 ou -1
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public int checkDate() {
		
		
		if(this.date_bapteme == null && this.date_marriage == null) {
			return 0;
		}		
		if(this.date_bapteme == null && this.enterrement == null) {
			return 0;
		}
		if(this.date_marriage == null && this.enterrement == null) {
			return 0;
		}
		if(this.date_bapteme == null) {
			if(date_marriage.before(this.enterrement)) {
				return 0;
			}
			else {
				return -1;
			}
		}
		if(this.date_marriage == null) {
			if(date_bapteme.before(this.enterrement)) {
				return 0;
			}
			else {
				return -1;
			}
		}
		if(this.enterrement == null) {
			if(date_bapteme.before(this.date_marriage)) {
				return 0;
			}
			else {
				return -1;
			}
		}
		else {
			if(this.date_bapteme.before(this.date_marriage)) {
				if(this.date_marriage.before(this.enterrement)) {
					return 0;
				}
				else {
					return -1;
				}
			}
			else {
				return -1;
			}
		}
	}
	
}
