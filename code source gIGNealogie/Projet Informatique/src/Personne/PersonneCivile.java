package Personne;


import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import Utile.MyConnection;


/**
 * Classe PersonneCivile
 * @author Edgar Lenhof, Nassim Chebbah
 *
 */
public class PersonneCivile {
	
	private final int id_personne;
	private String nom;  //au format NOM
	private String nom_jeune_fille; //au format NOM
	private String prenom;  // au format Prenom/Prenom-Compose
	private String prenoms_secondaire; // sous la forme "Jules Edouard Moustic"
	private Sexe sexe;
	private Date date_naissance;  // au format jj/mm/aaaa
	private Date date_mort;    // au format jj/mm/aaaa
	private String adresse;
	private String lieu_naissance;
	private String lieu_deces;
	
	
	//Constructeur
	
	
	/**
	 * Constructeur de la classe PersonneCivile
	 * @param prenom
	 * @param nom
	 * @throws ClassNotFoundException 
	 */
	
	public PersonneCivile(String prenom, String nom) throws SQLException, ClassNotFoundException{
		this.prenom = prenom;
		this.nom = nom;		
		this.create();
		this.id_personne = this.getIdPersonne();
		
	}
	
	
	
	//Setters
	
	public void setNom(String nom){
		this.nom = nom;
	}
		
	public void setNomJF(String nom){
		this.nom_jeune_fille = nom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public void setPrenomsSecondaires(String prenoms){
		this.prenoms_secondaire = prenoms;  
	}
		
	public void setSexe(Sexe sexe){
		this.sexe = sexe;
	}

	/**
	 * Modifie la date de naissance à condition que cela soit logique, sinon renvoit un message d'erreur
	 * @param date_naissance au format String ("jj/mm/aaaa")
	 * @throws ParseException
	 */
	public void setDateNaisance (String date_naissance) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date date = simpleDateFormat.parse(date_naissance);
		Date ancienne_date = this.date_naissance;    //on garde l'ancienne date en m�moire
		this.date_naissance = date;  //modification de la date
		
		int ok = checkDate();  //on v�rifie la coh�rence de la nouvelle date
		if  (ok == -1){  //si la nouvelle date n'est pas coh�rente
		System.out.println("Attention cette personne décède avant de naitre");
		this.date_naissance = ancienne_date;  //on conserve l'ancienne date
		}
		
	}
	
	
	public void setLieuNaissance (String lieu_naissance){
		this.lieu_naissance = lieu_naissance;
	}
		
	/**
	 * Modifie la date de mort à condition que cela soit logique, sinon renvoit un message d'erreur
	 * @param date_mort au format String ("jj/mm/aaaa")
	 * @throws ParseException
	 */
	public void setDateMort (String date_mort) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date date = simpleDateFormat.parse(date_mort);
		Date ancienne_date = this.date_mort;  
		this.date_mort = date;
		
		int ok = checkDate();
		
		if  (ok == -1){
		System.out.println("Attention erreur : cette personne décède avant de naitre");
		this.date_mort = ancienne_date;  //on conserve l'ancienne date
		}
		
	}
	
	public void setLieuMort (String lieu_mort){
		this.lieu_deces = lieu_mort;
	}
	
	public void setAdresse (String adresse){
		this.adresse = adresse;
	}
	
	
	
	//getters
	
	/**
	 * M�thode qui renvoie l'identifiant cr�e par la BDD lors de la construction d'une personne.
	 * En th�orie on utilisera ce getter uniquement lors de la cr�ation d'une personne.
	 * @return id_personne
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public int getIdPersonne() throws SQLException, ClassNotFoundException {
		
		
		String sql = "SELECT * FROM personne_civile "
				+ " WHERE personne_civile.nom = ? "
				+ " AND personne_civile.prenom = ?;";
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setString(1, this.nom);
		statement.setString(2, this.prenom);
		ResultSet result = statement.executeQuery();
		
		if (result.next()) {
			int a = result.getInt("id");
			
			return a;
			
		}
		
		return -1;		
		
		
	}

	/**
	 * Méthode qui renvoit plus facilement le numéro d'identification de la personne après que celle ci soit crée
	 * @return int id_personne
	 */
	public int getId(){
		return this.id_personne;
	}
	
	
	
	//Méthodes
	
	/**
	 * Méthode renvoyant le sexe d'une personne sous forme de string 
	 * Sert � ensuite enregistrer le sexe dans la BDD
	 * @return string M, F ou I
	 */
	public String sexeToStr(){
		
		if(this.sexe == Sexe.Feminin){
			return "F";
		}
		if (this.sexe == Sexe.Masculin){
			return "M";			
		}
		
		else {
			return "I";
		}
	}
	
	
	/**
	 * Méthode qui détermine si la date de naissance est bien antérieur à la date de mort 
	 * et renvoie un code numérique (1 si c'est le cas, -1 si ce n'est pas le cas )
	 * @return int
	 */
	public int checkDate(){
		
		if(this.date_mort != null){
			if(this.date_naissance != null){
				boolean check = this.date_naissance.before(this.date_mort);
				
				if(check == true){
					return 0;
				}
				else{
					return -1;
				}
			}
		}
		return 0;
	}
	
	/**
	 * Methode create() vient cr�er la personne civile dans la BDD
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void create() throws SQLException, ClassNotFoundException {

		
		String sql = "INSERT INTO personne_civile(nom, prenom) VALUES (?, ?);";
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setString(1, this.nom);
		statement.setString(2, this.prenom);
		statement.executeUpdate();
		System.out.println("PERSONNE CIVILE CREEE");
		
		
	}
	
	
	/**
	 * Méthode sauvegardant dans la BDD toutes les modifications apportées
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public void save() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		String sql = "UPDATE personne_civile SET"
				+ "  nom = ? "			
				+ " , prenom = ? "
				+ " , nom_jeune_fille = ? "
				+ " , sexe = ? "
				+ " , adresse = ? "
				+ " , date_naissance = ? "
				+ " , lieu_naissance = ? "
				+ " , date_mort = ? "
				+ " , lieu_deces = ? "
				+ " , prenoms_secondaires = ?"
				+ " WHERE id = ? ;"				
				;
				
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);		
		statement.setString(1, this.nom);
		
		statement.setString(2, this.prenom);
		statement.setString(3, this.nom_jeune_fille);
		
		String sexe = this.sexeToStr();
		statement.setString(4, sexe);
		
		statement.setString(5, this.adresse);
		
		if(this.date_naissance != null){
		statement.setString(6, simpleDateFormat.format(this.date_naissance)); //on passe la date au format string
		}
		else {
			statement.setString(6, null);
		}
		statement.setString(7, this.lieu_naissance);
		
		if(this.date_mort != null){
		statement.setString(8, simpleDateFormat.format(this.date_mort)); //on passe la date au format string
		}
		else {
			statement.setString(8, null);
		}
		statement.setString(9, this.lieu_deces);
		statement.setString(10, this.prenoms_secondaire);
		statement.setInt(11, this.id_personne);
		
		
		statement.execute();
		System.out.println("SAUVEGARDE REUSSIE");
		
		statement.close();
		
	}

}
