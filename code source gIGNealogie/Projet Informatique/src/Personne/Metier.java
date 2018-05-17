package Personne;

import java.util.Date;

import Utile.MyConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat; 

/**
 * Classe Metier
 * @author Edgar Lenhof, Nassim Chebbah
 *
 */
public class Metier {
	
	private final int id_personne;
	private int id_metier;
	private String metier;
	private Date date_debut;
	private Date date_fin;

	
	//Constructeur
	
	/**
	 * Constructeur de la classe Metier
	 * @param id_personne 
	 * @param metier
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Metier (int id_personne, String metier) throws ClassNotFoundException, SQLException{
		this.id_personne = id_personne;
		this.metier = metier;
		this.create();
		
	}

	
	//Setters
	
	public void setMetier(String metier) {
		this.metier = metier;
	}

	/**
	 * Modifie la date de debut � condition que cela soit logique, sinon renvoie un message d'erreur
	 * @param date_debut au format String ("jj/mm/aaaa")
	 * @throws ParseException
	 */
	public void setDate_debut(String date_debut) throws ParseException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date date = simpleDateFormat.parse(date_debut);  //transformation string to date
		Date ancienne_date = this.date_debut;  //on conserve l'ancienne date au cas o� la nouvelle est fausse
		this.date_debut = date;  //modification de la date
		
		int ok = checkDate();  //test avec la nouvelle date
		if  (ok == -1){  //test pas concluant
		System.out.println("Attention la date de debut est apr�s la date de fin.");
		this.date_debut = ancienne_date;  //on conserve l'ancienne date
		}
	}

	/**
	 * Modifie la date de fin � condition que cela soit logique, sinon renvoie un message d'erreur
	 * @param date_fin au format String ("jj/mm/aaaa")
	 * @throws ParseException
	 */
	public void setDate_fin(String date_fin) throws ParseException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date date = simpleDateFormat.parse(date_fin);
		Date ancienne_date = this.date_fin;  //on conserve l'ancienne date au cas o� la nouvelle est fausse
		this.date_fin = date;
		
		int ok = checkDate();
		if  (ok == -1){
		System.out.println("Attention la date de fin est avant la date de d�but.");
		this.date_fin = ancienne_date;  //on conserve l'ancienne date
		}
	}
	
	
	//M�thodes
	
	/**
	 * M�thode qui d�termine si la date de d�but est bien ant�rieure �la date de fin 
	 * et renvoie un code num�rique (1 si c'est le cas, -1 si ce n'est pas le cas et 0 
	 * si les informations sont insuffisantes)
	 * @return int
	 */
	public int checkDate(){
		
		if(this.date_debut != null){
			if(this.date_fin != null){
				boolean check = this.date_debut.before(this.date_fin);
				
				if(check){
					return 1;
				}
				else{
					return -1;
				}
			}
		}
		return 0;
	}
	
	/** Methode qui vient cr�er le m�tier de la personne dans la BDD
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void create() throws SQLException, ClassNotFoundException {

		
		String sql = "INSERT INTO metier(id_personne, activite) VALUES (?,?)";
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setInt(1, this.id_personne);
		statement.setString(2, this.metier);
		statement.executeUpdate();
		System.out.println("METIER CREEE");
	}

	
	/**
	 * M�thode sauvegardant dans la BDD toutes les modifications apport�es
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void save() throws SQLException, ClassNotFoundException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		String sql = "UPDATE metier SET activite = ? , date_debut = ? , date_fin = ? WHERE id = ? ;";
				
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);		
		statement.setString(1, this.metier);
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
		statement.setInt(4, this.id_metier);
		
		statement.executeUpdate();
		System.out.println("SAUVEGARDE EFFECTUEE");
	}
}
