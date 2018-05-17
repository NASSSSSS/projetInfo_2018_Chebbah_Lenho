package Utile;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe qui contient l'ensemble des m�thodes de controle n�cessaires au fonctionnement des m�thodes de la classe Requete
 * @author lenhof-chebbah
 *
 */
public class Controle {


	/**
	 * Méthode qui vérifie l'existence d'une personne et affiche les identifiants des personnes possédant un certain nom-prenom
	 * permet ensuite de choisir la personne qui nous intéresse
	 * @param nom
	 * @param prenom
	 * @return id_personne ou -1 si la personne n'existe pas
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */	
	public static int check_identity(String nom, String prenom) throws ClassNotFoundException, SQLException{
		
		String sql = "SELECT * FROM personne_civile"
				+ " WHERE nom LIKE ? "
				+ " AND prenom LIKE ? ;";
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setString(1, nom);
		statement.setString(2, prenom);
		ResultSet result = statement.executeQuery();
		
		int k=0;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		while(result.next()){
			k+=1;
			int id = result.getInt("id");
			String nom2 = result.getString("nom");
			String prenom2 = result.getString("prenom");
			String prenoms = result.getString("prenoms_secondaires");
			map.put(k, id);
			System.out.println( k + ".  " + nom2 + "  " + prenom2 + "  " + prenoms);											
		}
		
		
		
		if (k!=0){			
			System.out.println("Entrer le numéro de la personne correspondante, si cette personne n'est pas dans la liste entrez -1");
			int cle = Scan.sc.nextInt();
			Scan.sc.nextLine();
			if(cle!=-1) {
				int id = map.get(cle);			
				return id;
				}
				else {
					return -1;
				}
			
		}
		else{
			return -1;  //si la personne n'existe pas 
		}
	}
	
	public static int check_identity_bis(String nom, String prenom) throws SQLException, ClassNotFoundException{
		
	
		String sql = "SELECT * FROM personne_civile"
				+ " WHERE nom LIKE ? "
				+ " AND prenom LIKE ? ;";
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setString(1, nom);
		statement.setString(2, prenom);
		ResultSet result = statement.executeQuery();
				
		if(result.next()){
			int id = result.getInt("id");
			return id;
		}
		else{
			return -1;
		}
	}
	
	
	/**
	 * Renvois l'identifiant catholique correspondant à un identifiant civil s'il existe, sinon renvoit -1
	 * @param id_personne
	 * @return id_catho 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static int get_id_catho(int id) throws SQLException, ClassNotFoundException{
		
		String sql = "SELECT * FROM personne_religieuse"
				+ " WHERE id_personne = ? ;";
				
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setInt(1, id);		
		ResultSet result = statement.executeQuery();
		
		if(result.next()){
			int id_catho = result.getInt("id");
			return id_catho;
		}
		else{
			System.out.println("CETTE PERSONNE RELIGIEUSE N'EXISTE PAS");
			return -1;
		}
	}
	
	public static int check_catho(int id_personne, String date_religieuse, String parametre) throws ParseException, ClassNotFoundException, SQLException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		String sql = "SELECT * FROM personne_civile"  
				+ " WHERE id=? ";
		
		PreparedStatement state1 = MyConnection.getInstance().prepareStatement(sql);
		state1.setInt(1, id_personne);
		ResultSet result1 = state1.executeQuery();
		result1.next();
			
			String dateStr = result1.getString(parametre);
			if(dateStr==null){
				return 0;
			}
			else{
			Date date_civile = simpleDateFormat.parse(dateStr);	//renvoit la date de naissance ou de deces		
			
		if(!date_religieuse.equals("") && parametre.equals("date_naissance")){
			Date date2 = simpleDateFormat.parse(date_religieuse);
			if(date2.before(date_civile)){
				System.out.println("ERREUR : LE BAPTEME A LIEU AVANT LA NAISSANCE");
				return -1;
			}
			else{
				return 0;
			}
		}

		if(!date_religieuse.equals("") && parametre.equals("date_mort")){
			Date date2 = simpleDateFormat.parse(date_religieuse);
			if(date2.before(date_civile)){
				System.out.println("ERREUR : L'ENTERREMENT A LIEU AVANT LA MORT");
				return -1;
			}
			else{
				return 0;
			}
		}
		else{
			return -1;  //si la date est inconnue
		}
			}
			
	}
		
	
	/**
	 * Methode qui vérifie la cohérence entre les dates d'un mariage
	 * Si les deux personnes sont bien vivantes le jour du mariage, si elles ont plus de 15 ans...
	 * @param id1
	 * @param id2
	 * @param date_evenement
	 * @return int -1 ou 0
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static int check_mariage(int id1, int id2, Date date_evenement) throws ClassNotFoundException, SQLException, ParseException{
		
			
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		
		
		String sql = "SELECT * FROM personne_civile"
				+ " WHERE id = ? ;";
		
		PreparedStatement state1 = MyConnection.getInstance().prepareStatement(sql);
		state1.setInt(1, id1);
		ResultSet result1 = state1.executeQuery();
		result1.next();
			
			Date naissance1 = null;
			String naissance1str = result1.getString("date_naissance");
			if(naissance1str!=null){
				naissance1 = simpleDateFormat.parse(naissance1str);
			}
			
			Date mort1 = null;
			String mort1str = result1.getString("date_mort");
			if(mort1str!=null){
				mort1 = simpleDateFormat.parse(mort1str);
			}
			
		
		PreparedStatement state2 = MyConnection.getInstance().prepareStatement(sql);
		state2.setInt(1, id2);
		ResultSet result2 = state2.executeQuery();
		result2.next();
			
			Date naissance2 = null;
			String naissance2str = result2.getString("date_naissance");
			if(naissance2str!=null){
				naissance2 = simpleDateFormat.parse(naissance2str);
			}
			
			Date mort2 = null;
			String mort2str = result2.getString("date_mort");
			if(mort2str!=null){
				 mort2 = simpleDateFormat.parse(mort2str);
			}
			
		
		// Tests sur les impossibilitées
		if(mort1!=null && mort1.before(date_evenement)){
			System.out.println("PERSONNE 1 MORTE AVANT LE MARIAGE" + "\n" 
					+ "MARIAGE NON ENREGISTRE");
			return -1;
		}
		if(mort2!=null && mort2.before(date_evenement)){
			System.out.println("PERSONNE 2 MORTE AVANT LE MARIAGE" + "\n" 
					+ "MARIAGE NON ENREGISTRE");
			return -1; 
		}
		if(naissance1!=null && naissance1.after(date_evenement)){
			System.out.println("PERSONNE 1 NEE APRES LE MARIAGE" + "\n" 
					+ "MARIAGE NON ENREGISTRE");
			return -1;
		}
		if(naissance2!=null && naissance2.after(date_evenement)){
			System.out.println("PERSONNE 2 NEE APRES LE MARIAGE" + "\n" 
					+ "MARIAGE NON ENREGISTRE");
			return -1;
		}
		
		//Tests sur les improbabilitées
		final Date date18 = simpleDateFormat.parse("01/01/1988");
		final long dix_huit_ans = date18.getTime();  //18 ans en millisecondes
		
		if(naissance1!= null && EcartAge.ecart(naissance1,date_evenement)< dix_huit_ans){
			System.out.println("PERSONNE 1 S'EST-ELLE VRAIMENT MARIEE AVANT 18 ANS? (Y or N)");			
			String reponse = Scan.sc.nextLine();
			if(reponse.equals("Y")){
				return 0;
			}
			else{
				System.out.println("MARIAGE NON ENREGISTRE");
				return -1;
			}
		}
			
		
		if(naissance2!=null &&  EcartAge.ecart(naissance2,date_evenement)< dix_huit_ans){
			System.out.println("PERSONNE 2 S'EST-ELLE VRAIMENT MARIEE AVANT 18 ANS? (Y or N)");
			String reponse = Scan.sc.nextLine();
			if(reponse.equals("Y")){
				return 0;
			}
			else{
				System.out.println("MARIAGE NON ENREGISTRE");
				return -1;
			}
		}
	
		
		// Tout va pour le mieux dans le meilleur des monde
		
		return 0;
		
	}
	
	/**
	 * M�thode qui v�rifie la coh�rence d'une parent�: si l'age du parent par rapport � l'enfant tient la route, 
	 * si le parent n'est pas mort avant la naissance de l'enfant
	 * @param id1
	 * @param id2
	 * @return int 0 ou -1
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static int check_parente(int id1, int id2, Date date_debut, int adoption) throws ClassNotFoundException, SQLException, ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		
		//on r�cup�re les infos sur les personnes
		String sql = "SELECT * FROM personne_civile"
				+ " WHERE id = ? ;";
		
		PreparedStatement state1 = MyConnection.getInstance().prepareStatement(sql);
		state1.setInt(1, id1);
		ResultSet result1 = state1.executeQuery();
		result1.next();
		
		Date naissance1 = null;
		String naissance1str = result1.getString("date_naissance");
		if(naissance1str!=null){
			naissance1 = simpleDateFormat.parse(naissance1str);
		}
		
		Date mort1 = null;
		String mort1str = result1.getString("date_mort");
		if(mort1str!=null){
			mort1 = simpleDateFormat.parse(mort1str);
		}
			
		String sexe1 = result1.getString("sexe");
		
			
		PreparedStatement state2 = MyConnection.getInstance().prepareStatement(sql);
		state2.setInt(1, id2);
		ResultSet result2 = state2.executeQuery();
		result2.next();
		
		Date naissance2 = null;
		String naissance2str = result2.getString("date_naissance");
		if(naissance2str!=null){
			naissance2 = simpleDateFormat.parse(naissance2str);
		}			
		
			//Tests sur les impossibilitées


		if(naissance2!=null && mort1!=null){			
			if(mort1.before(naissance2)){  //on part du principe qu'une personne décédée ne peut avoir d'enfant même un homme decedant dans les 9 mois de la conception
				System.out.println("PARENT DECEDE AVANT LA NAISSANCE DE L'ENFANT" + "\n"
						+ "PARENTE NON ENREGISTREE");
				return -1;
			}
			
		}
			
		if(mort1!=null){
			if(mort1.before(date_debut) && adoption==0){  //on v�rifie que l'adoption a lieu avant le d�c�s du parent
				System.out.println("PARENT DECEDE AVANT L'ADOPTION DE L'ENFANT" + "\n"
						+ "PARENTE NON ENREGISTREE");
				return -1;
			}
			
		}
		
			
		final Date date18 = simpleDateFormat.parse("01/01/1988");
		final long dix_huit_ans = date18.getTime();  //18 ans en millisecondes
		
		if(naissance1!=null){
			if(adoption==0 && EcartAge.ecart(naissance1,date_debut)< dix_huit_ans){     //on v�rifie que le parent adoptif est majeur
				System.out.println("PARENT PAS MAJEUR LORS DE L'ADOPTION DE L'ENFANT" + "\n"
						+ "PARENTE NON ENREGISTREE");
				return -1;
			}
			
		
		
		
		if(naissance2!=null){
			
			if(naissance1.after(naissance2) && adoption==-1){
				System.out.println("PARENT PLUS JEUNE QUE L'ENFANT" + "\n"
						+ "PARENTE NON ENREGISTREE");
				return -1;
			}
		
			
			//Tests sur les improbabilitées
			
			final Date date15 = simpleDateFormat.parse("01/01/1985");
			final Date date70 = simpleDateFormat.parse("01/01/2040");
			final Date date50 = simpleDateFormat.parse("01/01/2020");
			
			final long quinze_ans = date15.getTime();  //15 années en millisecondes
			final long soixante_dix_ans = date70.getTime();
			final long cinquante_ans = date50.getTime();
			
			System.out.println(naissance1.getTime());
			System.out.println(naissance2.getTime());
			System.out.println(soixante_dix_ans);
			System.out.println(EcartAge.ecart(naissance1,naissance2));
			
			if(adoption!=0){
				if(EcartAge.ecart(naissance1,naissance2) <quinze_ans){
					System.out.println("LE PARENT A-T-IL VRAIMENT EU UN ENFANT AVANT 15 ANS? (Y or N)");
					String reponse = Scan.sc.nextLine();
					if(reponse.equals("Y")){
						return 0;
					}
					else{
						System.out.println("PARENTE NON ENREGISTREE");
						return -1;
					}
				}
					
				
			
			if(sexe1.equals("F") && EcartAge.ecart(naissance1,naissance2)> cinquante_ans){
				System.out.println("LE PARENT A-T-IL VRAIMENT EU UN ENFANT APRES 50 ANS? (Y or N)");
				String reponse = Scan.sc.nextLine();
				if(reponse.equals("Y")){
					return 0;
				}
				else{
					System.out.println("PARENTE NON ENREGISTREE");
					return -1;
				}
							
			}
			
			if(sexe1.equals("M") && EcartAge.ecart(naissance1,naissance2)> soixante_dix_ans){
				System.out.println("LE PARENT A-T-IL VRAIMENT EU UN ENFANT APRES 70 ANS? (Y or N)");
				String reponse = Scan.sc.nextLine();
				if(reponse.equals("Y")){
					return 0;
				}
				else{
					System.out.println("PARENTE NON ENREGISTREE");
					return -1;
				}
			}
			else{
				return 0;
			}
			}
			else{
				return 0;
			}
			
		}
		else{
			return 0;
		}
		
		}					
			
		else{
			return 0;
		}
			
			
			
		}
				
			
				
	
	
	/**
	 * M�thode qui v�rifie la coh�rence du parrainage: parrain d�c�d� avant la date de d�but, parrain plus jeune que fillot...
	 * @param id1
	 * @param id2
	 * @return int 0 ou -1
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public static int check_parrainage(int id1, int id2, String date_debut) throws ClassNotFoundException, SQLException, ParseException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		//on r�cup�re les infos sur les personnes
				String sql = "SELECT * FROM personne_civile"
						+ " WHERE id = ? ;";
				
				PreparedStatement state1 = MyConnection.getInstance().prepareStatement(sql);
				state1.setInt(1, id1);
				ResultSet result1 = state1.executeQuery();
				result1.next();
				
				Date naissance1 = null;
				String naissance1str = result1.getString("date_naissance");
				if(naissance1str!=null){
					naissance1 = simpleDateFormat.parse(naissance1str);
				}
				
				Date mort1 = null;
				String mort1str = result1.getString("date_mort");
				if(mort1str!=null){
					mort1 = simpleDateFormat.parse(mort1str);
				}
	
				
				PreparedStatement state2 = MyConnection.getInstance().prepareStatement(sql);
				state2.setInt(1, id2);
				ResultSet result2 = state2.executeQuery();
				result2.next();

				Date naissance2 = null;
				String naissance2str = result1.getString("date_naissance");
				if(naissance2str!=null){
					naissance2 = simpleDateFormat.parse(naissance2str);
				}
					
		
		if(!date_debut.equals("")) {
			Date date = simpleDateFormat.parse(date_debut);
			
			if(naissance1!=null && date.before(naissance1)) {
				System.out.println("LE PARRAIN N'EST PAS ENCORE NE A CE MOMENT" + "\n"
						+ "PARRAINAGE NON ENREGISTREE");
				return -1;
			}
			
			if(mort1!=null && date.after(mort1)) {
				System.out.println("LE PARRAIN N'EST PLUS EN VIE A CE MOMENT" + "\n"
						+ "PARRAINAGE NON ENREGISTREE");
				return -1;
			}
			
			
			final Date date18 = simpleDateFormat.parse("01/01/1988");
			final long dix_huit_ans = date18.getTime();  //18 ans en millisecondes
			
			if(naissance1!=null && EcartAge.ecart(naissance1,date)< dix_huit_ans){
				System.out.println("LE PARRAIN N'EST PAS MAJEUR A CE MOMENT" + "\n"
						+ "PARRAINAGE NON ENREGISTREE");
				return -1;   //le parrain en tant que responsable l�gale doit �tre majeur
			}
			
			return 0;
		}
		
		if(naissance1!=null && naissance2!=null){
		if(naissance1.after(naissance2)) {
			System.out.println("LE PARRAIN EST PLUS JEUNE QUE LE FILLOT" + "\n"
					+ "PARRAINAGE NON ENREGISTREE");
			return -1;  //on part du principe que le parrain est n�cessairement plus vieux s'il doit devenir le responsable l�gale du fillot
		}
		
		else {
			return 0;
		}
		}
		else{
			return 0;
		}
	}
	
	
	/**
	 * 	 * Méthode qui vérifie l'existence d'une relation et affiche les identifiants des relations possédant un certain couple d'identifiant
	 * permet ensuite de choisir la personne qui nous intéresse
	 * @param id1
	 * @param id2
	 * @return int 0 ou -1
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static int check_relation(int id1, int id2) throws SQLException, ClassNotFoundException{
		
		String sql = "SELECT * FROM relation"
				+ " WHERE id_personne1 = ? "
				+ " AND id_personne2 = ? ;";
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setInt(1, id1);
		statement.setInt(2, id2);
		ResultSet result = statement.executeQuery();
		
		PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql);  //on v�rifie le cas o� les  personnes sont dans l'ordre oppos�
		statement2.setInt(1, id2);
		statement2.setInt(2, id1);
		ResultSet result2 = statement.executeQuery();
		
		int k=0;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();  //on cr�er une map dans laquelle on enregistre tous les id_relation
		while(result.next()){   //on vient afficher tout les r�sultats 
			k+=1;
			int id = result.getInt("id");
			String nature = result.getString("nature_relation");
			String debut = result.getString("date_evenement");
			String fin = result.getString("date_fin");
			map.put(k, id);
			System.out.println( k + ".  " + nature + "  " + debut + "  " + fin);											
		}
		
		
		int j=k;		
		while(result2.next()){  //on vient afficher tout les r�sultats 
			j+=1;
			int id = result2.getInt("id");
			String nature = result2.getString("nature_relation");
			String debut = result2.getString("date_evenement");
			String fin = result2.getString("date_fin");
			map.put(j, id);
			System.out.println( j + ".  " + nature + "  " + debut + "  " + fin);											
		}
		
		
		if (k!=0 || j!=0){
			System.out.println("Entrer le numéro de la relation correspondante, si cette relation n'est pas dans la liste entrez -1");
			int cle = Scan.sc.nextInt();
			Scan.sc.nextLine();
			
			if(cle!=-1) {
			int id = map.get(cle);			
			return id;
			}
			else {
				return -1;
			}
			
		}
		else{
			return -1;  //si la relation n'existe pas 
		}
	}
	
	
	/**
	 * M�thode qui v�rifie l'existence d'une sourcePersonne
	 * @param id_personne
	 * @return int 0 ou -1
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static int check_sourcePersonne(int id_personne) throws SQLException, ClassNotFoundException {
		
		String sql = "SELECT * FROM source_personne"
				+ " WHERE id_personne = ? ;";
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setInt(1, id_personne);
		ResultSet result = statement.executeQuery();
		
		int k=0;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();  //on cr�er une map dans laquelle on enregistre tous les id_source
		while(result.next()){   //on vient afficher tout les r�sultats 
			k+=1;
			int id = result.getInt("id");
			String nature = result.getString("nature_source");
			String event = result.getString("type_evenement");
			map.put(k, id);
			System.out.println( k + ".  " + nature + "  " + event + "  " );											
		}
		
		
		if (k!=0 ){
			System.out.println("Entrer le numéro de la source correspondante, si cette source n'est pas dans la liste entrez -1");
			int cle = Scan.sc.nextInt();
			Scan.sc.nextLine();
			if(cle!=-1) {
				int id = map.get(cle);			
				return id;
				}
				else {
					return -1;
				}
			
		}
		else{
			return -1;  //si la relation n'existe pas 
		}
	}
	
	/**
	 * M�thode qui v�rifie l'existence d'une sourceRelation 
	 * @param id_relation
	 * @return int 0 ou -1
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static int check_sourceRelation(int id_relation) throws SQLException, ClassNotFoundException {
		
		String sql = "SELECT * FROM source_relation"
				+ " WHERE id_relation = ? ;";
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setInt(1, id_relation);
		ResultSet result = statement.executeQuery();
		
		int k=0;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();  //on cr�er une map dans laquelle on enregistre tous les id_relation
		while(result.next()){   //on vient afficher tout les r�sultats 
			k+=1;
			int id = result.getInt("id");
			String nature = result.getString("nature_source");
			String event = result.getString("type_evenement");
			map.put(k, id);
			System.out.println( k + ".  " + nature + "  " + event + "  " );											
		}
		
		
		if (k!=0 ){
			System.out.println("Entrer le numéro de la source correspondante, si cette source n'est pas dans la liste entrez -1");
			int cle = Scan.sc.nextInt();
			Scan.sc.nextLine();
			if(cle!=-1) {
				int id = map.get(cle);			
				return id;
				}
				else {
					return -1;
				}	
			
		}
		else{
			return -1;  //si la source n'existe pas 
		}
	}
	
	
	/**
	 * Méthode qui renvoit la date de naissance d'une personne de la BDD
	 * @param id
	 * @return date de naissance au format String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String get_date_naissance(int id) throws ClassNotFoundException, SQLException{
		
		String sql = "SELECT * FROM source_relation"
				+ " WHERE id_relation = ? ;";
		
		PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet result_test = statement.executeQuery();
		
		while(result_test.next()){
		String date = result_test.getString("date_naissance");
		if(date==null){
			return "";
		}
		else{
			return date;
		}
		}
		return "";
	}
	
	/**
	 * Verifie qu'un fichier existe bien
	 * @param path
	 * @return int 0 ou -1
	 */
	public static int check_file(String path){
		
		File file = new File(path);	
		if(file.exists()){
			return 0;
		}
		else{
			return -1;
		}
	}

}


