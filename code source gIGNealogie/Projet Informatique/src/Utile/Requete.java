package Utile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Personne.Metier;
import Personne.PersonneCatholique;
import Personne.PersonneCivile;
import Personne.Sexe;
import Personne.Temoin;
import Relation.Fratrie;
import Relation.Mariage;
import Relation.Parente;
import Relation.Parrainage;
import Sources.ActeOfficielsPersonne;
import Sources.ActeOfficielsRelation;
import Sources.DocumentOfficieuxPersonne;
import Sources.DocumentOfficieuxRelation;
import Sources.TemoignagePersonne;
import Sources.TemoignageRelation;

/**
 * Classe Requete contient des requetes pré-écrites pour l'utilisateur
 * @author formation
 *
 */
public class Requete {
	

	
	//Méthodes de création  -- Personne
	
	
	/**
	 * Methode qui vient créer une personne civile et catholique (si elle l'est)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public static void creer_personne() throws ClassNotFoundException, SQLException, ParseException{
				
		
		// Partie construction PersonneCivile
		System.out.println("CREATION PERSONNE CIVILE");
		System.out.println("NOM (tout en majuscule) : ");
		String nom = Scan.sc.nextLine();
		System.out.println("PRENOM (majuscule sur la première lettre): ");
		String prenom = Scan.sc.nextLine();
		
		int id = Controle.check_identity(nom , prenom);  //on v�rifie que la personne n'existe pas d�j�
		
		if(id==-1){   //si elle n'existe pas encore
		PersonneCivile personne = new PersonneCivile(prenom, nom);  //construction de la personne civile
		
		//on demande les param�tres secondaires
		System.out.println("PRENOMS SECONDAIRES (tapez directement 'entrer' s'il n'y en a pas) : ");
		String prenoms = Scan.sc.nextLine();
		personne.setPrenomsSecondaires(prenoms);
		
		System.out.println("NOM DE JEUNE FILLE (tapez directement 'entrer' s'il n'y en a pas) : ");
		String njf = Scan.sc.nextLine();
		personne.setNomJF(njf);
		
		System.out.println("SEXE (M F I) : ");
		String sexe = Scan.sc.nextLine();
		Sexe sexe_bis = Sexe.Indefini;
		if(sexe.equals("M")){
			sexe_bis = Sexe.Masculin;
		}
		if(sexe.equals("F")){
			sexe_bis = Sexe.Feminin;
		}	
		personne.setSexe(sexe_bis);
		
		System.out.println("DATE DE NAISSANCE (jj/mm/aaaa) : ");
		String naissance = Scan.sc.nextLine();
		if(!naissance.equals("")){
		personne.setDateNaisance(naissance);
		}
		
		System.out.println("LIEU DE NAISSANCE : ");
		String lieu_naissance = Scan.sc.nextLine();
		personne.setLieuNaissance(lieu_naissance);
		
		System.out.println("DATE DE MORT (jj/mm/aaaa, tapez directement 'entrer' s'il n'y en a pas) : ");		
		String mort = Scan.sc.nextLine();
		if(!mort.equals("")){
		personne.setDateMort(mort);
		}
		
		System.out.println("LIEU DE DECES (tapez directement 'entrer' s'il n'y en a pas): ");
		String lieu_mort = Scan.sc.nextLine();
		personne.setLieuMort(lieu_mort);
				
		personne.save(); //sauvegarde
		
				
		
		// Partie construction PersonneCatholique
		System.out.println("CETTE PERSONNE EST-ELLE CATHOLIQUE?  (enter Y or N) ");
		String reponse = Scan.sc.nextLine();
		
		if (reponse.equals("Y")){  //si la personne est catholique on vient directement la cr�ee
			System.out.println("CREATION PERSONNE CATHOLIQUE");
			int id_personne = personne.getId();
			PersonneCatholique catho = new PersonneCatholique(id_personne);  //construction de la personne catholique
			
			// on demande les informations secondaires
			System.out.println("DATE DU BAPTEME (format jj/mm/aaaa ou tapez directement 'entrer' s'il n'y en a pas) : ");
			String date_bapteme = Scan.sc.nextLine();
			if(!date_bapteme.equals("") ){
				catho.setDate_bapteme(date_bapteme);
			}
			
			System.out.println("LIEU DE BAPTEME (tapez directement 'entrer' s'il n'y en a pas): ");
			String lieu_bapteme = Scan.sc.nextLine();
			catho.setLieu_bapteme(lieu_bapteme);
			
			System.out.println("DATE DU MARIAGE (format jj/mm/aaaa ou tapez directement 'entrer' s'il n'y en a pas) : ");
			String date_mariage = Scan.sc.nextLine();
			if(!date_mariage.equals("") ){
				catho.setDate_marriage(date_mariage);
			}
			
			System.out.println("LIEU DE MARIAGE (tapez directement 'entrer' s'il n'y en a pas): ");
			String lieu_mariage = Scan.sc.nextLine();
			catho.setLieu_mariage(lieu_mariage);
			
			System.out.println("DATE D'ENTERREMENT (format jj/mm/aaaa ou tapez directement 'entrer' s'il n'y en a pas) : ");
			String date_enterrement = Scan.sc.nextLine();
			if(!date_enterrement.equals("") ){
				catho.setEnterrement(date_enterrement);
			}
			
			System.out.println("SEPULTURE (tapez directement 'entrer' s'il n'y en a pas): ");
			String sepulture = Scan.sc.nextLine();
			catho.setSepulture(sepulture);
			
				catho.save();  //on sauvegarde
			
			

		}
		
		else{
			System.out.println("CETTE PERSONNE N'EST PAS CATHOLIQUE ");
		}
		}
		else{
			System.out.println("CETTE PERSONNE EXISTE DEJA");
		}
		
		
		
				
	}

	
	/**
	 * Méthode qui permet de créer une personne religieuse
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public static void creer_catho() throws ClassNotFoundException, SQLException, ParseException{
		
		System.out.println("CREATION PERSONNE CATHOLIQUE");
		
		System.out.println("NOM : ");
		String nom = Scan.sc.nextLine();
		System.out.println("PRENOM : ");
		String prenom = Scan.sc.nextLine();
		
		int id = Controle.check_identity(nom , prenom);  //on v�rifie que la personne existe bien dans la BDD Civile
		if(id != -1){  //si elle existe
			
		int id_catho = Controle.get_id_catho(id); //on v�rifie que la personne catholique n'existe pas d�j� dans la BDD
		
		if(id_catho == -1){  //si elle n'existe pas encore
		
		PersonneCatholique catho = new PersonneCatholique(id);  //construction de la personne catholique
		
		//on demande les infos secondaires
		System.out.println("DATE DU BAPTEME (format jj/mm/aaaa ou tapez directement 'entrer' s'il n'y en a pas) : ");
		String date_bapteme = Scan.sc.nextLine();
		int ok = Controle.check_catho(id, date_bapteme, "date_naissance");
		if(ok!=-1){
			catho.setDate_bapteme(date_bapteme);
		}

		System.out.println("LIEU DE BAPTEME (tapez directement 'entrer' s'il n'y en a pas): ");
		String lieu_bapteme = Scan.sc.nextLine();
		catho.setLieu_bapteme(lieu_bapteme);
		
		System.out.println("DATE DU MARIAGE (format jj/mm/aaaa ou tapez directement 'entrer' s'il n'y en a pas) : ");
		String date_mariage = Scan.sc.nextLine();
		if(!date_mariage.equals("") ){
			catho.setDate_marriage(date_mariage);
		}
		
		System.out.println("LIEU DE MARIAGE (tapez directement 'entrer' s'il n'y en a pas): ");
		String lieu_mariage = Scan.sc.nextLine();
		catho.setLieu_mariage(lieu_mariage);
		
		System.out.println("DATE D'ENTERREMENT (format jj/mm/aaaa ou tapez directement 'entrer' s'il n'y en a pas) : ");
		String date_enterrement = Scan.sc.nextLine();
		int ok2 = Controle.check_catho(id, date_enterrement, "date_mort");
		if(ok2!=-1 ){
			catho.setEnterrement(date_enterrement);
		}
		
		System.out.println("SEPULTURE (tapez directement 'entrer' s'il n'y en a pas): ");
		String sepulture = Scan.sc.nextLine();
		catho.setSepulture(sepulture);
		
			catho.save();  //on sauvegarde
		
		}
		
		else{
			System.out.println("CETTE PERSONNE RELIGIEUSE EXISTE DEJA");
		}
		}
		else{
			System.out.println("CETTE PERSONNE CIVILE N'EXISTE PAS, VEUILLEZ D'ABORD LA CREER");
		}
		
		
	}

	
	/**
	 * M�thode qui cr�er un t�moin avec ou sans t�moignage
	 * @param id_temoignage  on rentre -1 si le temoin ne d�tient pas de temoignage enregistr�
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void creer_temoin(int id_temoignage) throws ClassNotFoundException, SQLException {
		
		System.out.println("CREATION TEMOIN");
		
		
		System.out.println("NOM DU TEMOIN: ");
		String nom = Scan.sc.nextLine();
		System.out.println("PRENOM DU TEMOIN: ");
		String prenom = Scan.sc.nextLine();
		
		if(id_temoignage!=-1) {
			new Temoin(id_temoignage,  nom,  prenom);
			System.out.println("TEMOIN CREE ");
		}
		
		else {
			new Temoin( nom,  prenom);
			System.out.println("TEMOIN CREE ");
		}
		
	}
		
		public static void renseigner_metier() throws ClassNotFoundException, SQLException, ParseException{
			
			System.out.println("DE QUI RENSEIGNE-T-ON LA PROFESSION ? ");

			System.out.println("NOM : ");
			String nom = Scan.sc.nextLine();
			System.out.println("PRENOM : ");
			String prenom = Scan.sc.nextLine();
			
			int id = Controle.check_identity(nom , prenom);  //on v�rifie que la personne n'existe pas d�j�
			
			if(id!=-1){   //si elle n'existe pas encore
				
			System.out.println("PROFESSION : ");
			String profession = Scan.sc.nextLine();
			Metier metier = new Metier(id, profession);  //construction de la profession
			System.out.println("METIER CREE : ");
			
			//on demande les param�tres secondaires
			System.out.println("DATE ENTREE EN SERVICE (jj/mm/aaaa) : ");
			String debut = Scan.sc.nextLine();
			if(!debut.equals("")){
			metier.setDate_debut(debut);
			metier.save();
			}
			
			System.out.println("DATE FIN DE SERVICE (jj/mm/aaaa) : ");
			String fin = Scan.sc.nextLine();
			if(!fin.equals("")){
			metier.setDate_fin(fin);
			metier.save();
			}
		}
	
			else{
				System.out.println("CETTE PERSONNE CIVILE N'EXISTE PAS, VEUILLEZ D'ABORD LA CREER");
			}
		}
	
	// Relation
	
	/**
	 * Methode qui permet de c�er un mariage apr�s v�rification de la coh�rence
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void creer_mariage() throws ClassNotFoundException, SQLException, ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("CREATION MARIAGE");		

		System.out.println("NOM CONJOINT 1 : ");
		String nom1 = Scan.sc.nextLine();
		System.out.println("PRENOM CONJOINT 1 : ");
		String prenom1 = Scan.sc.nextLine();
		
		
		int id1 = Controle.check_identity(nom1 , prenom1);  //on v�rifie l'existence de la premi�re personne
		if(id1 != -1){   //si elle existe
			
			System.out.println("NOM CONJOINT 2: ");
			String nom2 = Scan.sc.nextLine();
			System.out.println("PRENOM CONJOINT 2: ");
			String prenom2 = Scan.sc.nextLine();
						
			int id2 = Controle.check_identity(nom2 , prenom2);  //on v�rifie l'existence de la seconde personne
			if(id2 != -1){   //si elle existe
				
				int id_relation = Controle.check_relation(id1, id2);  //on vérifie que la relation n'est pas déjà enregistréé
				if(id_relation==-1){
					
					//on demande les dates
				System.out.println("DATE MARIAGE: ");
				 String date_mariagestr = Scan.sc.nextLine();
				 java.util.Date date_mariage = null;
				 if(!date_mariagestr.equals("")){
					 date_mariage = simpleDateFormat.parse(date_mariagestr);
				 }
					
				 System.out.println("DATE DIVORCE/VEUVAGE: ");
				 String date_divorcestr = Scan.sc.nextLine();
				 
				 int ok = 0;
				 if(!date_mariagestr.equals("")) { //si la date de mariage a �t� renseign�e
				 ok = Controle.check_mariage(id1,id2,date_mariage); //renvoit -1 si les dates ne sont pas coh�rentes
				 }
				 
				 if(ok ==0){
				 Mariage mariage = new Mariage(id1,id2);  // on cr�e le mariage
				 				 
				 if(!date_mariagestr.equals("") ){
						mariage.setDateDebut(date_mariagestr);
					}
					
				 if(!date_divorcestr.equals("") ){
						mariage.setDateFin(date_divorcestr);
					}
				 mariage.save();  //on sauvegarde
				
				System.out.println("MARIAGE ENREGISTRE");
				 }
			
				 // si le mariage n'est pas coh�rent un message d'erreur s'affiche dans la m�thode check_mariage
				}
				else{
					System.out.println("RELATION DEJA EXISTANTE");
				}
			}
			else{
				System.out.println("PERSONNE INEXISTANTE");
			}
			
		}
		else{
			System.out.println("PERSONNE INEXISTANTE");
		}
		
	}
	
	/**
	 *  Methode qui permet de c�er une parent� apr�s v�rification de la coh�rence
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void creer_parente() throws ClassNotFoundException, SQLException, ParseException{
		
		System.out.println("PARENTE");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		System.out.println("NOM PARENT : ");
		String nom1 = Scan.sc.nextLine();
		System.out.println("PRENOM PARENT : ");
		String prenom1 = Scan.sc.nextLine();
		
		int id1 = Controle.check_identity(nom1 , prenom1);  //on v�rifie l'existence de la premi�re personne
		if(id1 != -1){  //si elle existe
			
			System.out.println("NOM ENFANT: ");
			String nom2 = Scan.sc.nextLine();
			System.out.println("PRENOM ENFANT: ");
			String prenom2 = Scan.sc.nextLine();
			
			int id2 = Controle.check_identity(nom2 , prenom2);  //on v�rifie l'existence de la seconde personne
			if(id2 != -1){   //si elle existe
				
				System.out.println("ENFANT ADOPTE? (Y or N) ");  //on demande s'il s'agit d'une adoption
				String reponse = Scan.sc.nextLine();
				
				java.util.Date date_debut = null;
				String date_debutstr =null;
				
				int adoption = -1;   //cet integer d�finit si un enfant est adopt�, il est n�cessaire pour le controle de coh�rence
				
				if(reponse.equals("Y")) {
				System.out.println("DATE D'ADOPTION: "); //si l'enfant est adopté on demande la date d'adoption
				 date_debutstr = Scan.sc.nextLine();
				 if(!date_debutstr.equals("")){
					 date_debut = simpleDateFormat.parse(date_debutstr);
				 }
				 adoption = 0;
				}
				
				else{
					date_debutstr = Controle.get_date_naissance(id2);
					if(!date_debutstr.equals("")){
						 date_debut = simpleDateFormat.parse(date_debutstr);
					 }
				}
				
				
				int ok = Controle.check_parente(id1,id2,date_debut,adoption);   //on v�rifie la coh�rence de la parent�
				if(ok==0){   //si elle l'est
					
				Parente parent = new Parente(id1,id2);			//cr�ation de la parent�
				if(!date_debutstr.equals("")){
					parent.setDateDebut(date_debutstr);
					parent.save();
					}
				System.out.println("PARENTE ENREGISTRE");
				}
				
				// si la parent� n'est pas coh�rent un message d'erreur s'affiche dans la m�thode check_parente
			}
			else{
				System.out.println("PERSONNE INEXISTANTE");
			}
			
		}
		else{
			System.out.println("PERSONNE INEXISTANTE");
		}
		
	}
	
	/**
	 *  Methode qui permet de c�er un parrainage apr�s v�rification de la coh�rence
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void creer_parrainage() throws ClassNotFoundException, SQLException, ParseException{
		
		System.out.println("PARRAINAGE");

		System.out.println("NOM PARRAIN/MARRAINE : ");
		String nom1 = Scan.sc.nextLine();
		System.out.println("PRENOM PARENT/MARRAINE : ");
		String prenom1 = Scan.sc.nextLine();
		
		int id1 = Controle.check_identity(nom1 , prenom1);  //on v�rifie que la personne existe
		if(id1 != -1){
			
			System.out.println("NOM FILLEUL: ");
			String nom2 = Scan.sc.nextLine();
			System.out.println("PRENOM FILLEUL: ");
			String prenom2 = Scan.sc.nextLine();
			
			int id2 = Controle.check_identity(nom2 , prenom2);   //on verifie que la personne existe
			if(id2 != -1){
				
				System.out.println("DATE DEBUT: ");
				 String date_debut = Scan.sc.nextLine();
					
				 System.out.println("DATE FIN: ");  //si jamais le parrain ne veut plus assurer sa tache
				 String date_fin = Scan.sc.nextLine();
				
				int ok = Controle.check_parrainage(id1,id2,date_debut);  //v�rification de la coh�rence
				if(ok==0){
				Parrainage parrain = new Parrainage(id1,id2);  //cr�ation de la relation
				
				if(!date_debut.equals("") ){
					parrain.setDateDebut(date_debut);
				}
				
			 if(!date_fin.equals("") ){
					parrain.setDateFin(date_fin);
				}
			 parrain.save();  //on sauvegarde
				System.out.println("PARRAINAGE ENREGISTRE");
				}
			}
			else{
				System.out.println("PERSONNE INEXISTANTE");
			}
			
		}
		else{
			System.out.println("PERSONNE INEXISTANTE");
		}
		
	}
	
	
	/**
	 *  Methode qui permet de c�er une fratrie apr�s v�rification de la coh�rence
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void creer_fratrie() throws ClassNotFoundException, SQLException{
		
		System.out.println("FRATRIE");
		
		System.out.println("NOM FRERE/SOEUR : ");
		String nom1 = Scan.sc.nextLine();
		System.out.println("PRENOM FRERE/SOEUR : ");
		String prenom1 = Scan.sc.nextLine();
		
		int id1 = Controle.check_identity(nom1 , prenom1);  //v�rifie l'existence de la personne
		if(id1 != -1){
			
			System.out.println("NOM FRERE/SOEUR: ");
			String nom2 = Scan.sc.nextLine();
			System.out.println("PRENOM FRER/SOEUR: ");
			String prenom2 = Scan.sc.nextLine();
			
			int id2 = Controle.check_identity(nom2 , prenom2);   //v�rifie l'existence de la personne
			if(id2 != -1){
				

				new Fratrie(id1,id2);    //cr�ation de la fratrie
				System.out.println("FRATRIE ENREGISTREE");
			
			}
			else{
				System.out.println("PERSONNE INEXISTANTE");
			}
			
		}
		else{
			System.out.println("PERSONNE INEXISTANTE");
		}
		
	}
	
	
	//Source
	/**
	 * M�thode qui vient cr�er les informations relatives � un document source d'une personne
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void creer_source_personne() throws ClassNotFoundException, SQLException {
		
		System.out.println("SOURCE PERSONNE");
		
		System.out.println("A QUI CETTE SOURCE FAIT-ELLE REFERENCE? ");
		System.out.println("NOM  : ");
		String nom1 = Scan.sc.nextLine();
		System.out.println("PRENOM  : ");
		String prenom1 = Scan.sc.nextLine();
		
		int id1 = Controle.check_identity(nom1 , prenom1);  //v�rifie l'existence de la personne
		if(id1 != -1){
			
			int check = Controle.check_sourcePersonne(id1);   //on v�rifie que la source n'existe pas d�j�
			if (check == -1) {
			
			System.out.println("NOM DU FICHIER  : ");
			String abs_path = "sources/" + Scan.sc.nextLine();
			
			int ok = Controle.check_file(abs_path);
			if(ok!=-1){
			
			System.out.println("NATURE (Acte officiel, Document officieux, Temoignage)  : ");
			String nature = Scan.sc.nextLine();
			System.out.println("EVENEMENT RENSEIGNE (Naissance, Deces, Bapteme, Mariage, Divorce, Filiation)  : ");
			String event = Scan.sc.nextLine();
			
			if(nature.equals("Acte officiel")) {
				ActeOfficielsPersonne source = new ActeOfficielsPersonne(id1, abs_path);
				source.modifier_event(event);
				source.save();
			}
			
			if(nature.equals("Document officieux")) {
				DocumentOfficieuxPersonne source = new DocumentOfficieuxPersonne(id1, abs_path);
				source.modifier_event(event);
				source.save();
			}
			
			if(nature.equals("Temoignage")) {
				TemoignagePersonne source = new TemoignagePersonne(id1, abs_path);
				source.modifier_event(event);
				source.save();
				System.out.println("CONNAIT-ON LE TEMOIN (Y or N)  : ");
				String reponse = Scan.sc.nextLine();
				if(reponse.equals("Y")) {
					creer_temoin(id1);
				}
			
			
			else {
				System.out.println("NATURE NON RECONNUE"
						+ "SOURCE NON ENREGISTREE ");
			}
			
			}
			
			}
			else{
				System.out.println("LE DOCUMENT SOURCE N'EXISTE PAS");
			}
			}
			else {
				System.out.println("LA SOURCE EXISTE DEJA");
			}
		}
		else{
			System.out.println("PERSONNE INEXISTANTE");
		}
		
	}
	
	
	/**
	 * M�thode qui vient cr�er les informations relatives � un document source d'une relation
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void creer_source_relation() throws ClassNotFoundException, SQLException {
		
		System.out.println("SOURCE RELATION");

		System.out.println("A QUI CETTE SOURCE FAIT-ELLE REFERENCE? ");
		System.out.println("NOM 1 : ");
		String nom1 = Scan.sc.nextLine();
		System.out.println("PRENOM 1 : ");
		String prenom1 = Scan.sc.nextLine();
		
		int id1 = Controle.check_identity(nom1 , prenom1);  //v�rifie l'existence de la personne
		if(id1 != -1){
			
			System.out.println("NOM 2 : ");
			String nom2 = Scan.sc.nextLine();
			System.out.println("PRENOM 2 : ");
			String prenom2 = Scan.sc.nextLine();
			
			int id2 = Controle.check_identity(nom2 , prenom2);  //v�rifie l'existence de la personne
			if(id2 != -1){
				
			int idr = Controle.check_relation(id1,id2);   //on v�rifie que la relation existe 
			if (idr != -1) {		// si la relation existe
				
				int check = Controle.check_sourceRelation(idr); 
				if (check == -1) {   //on v�rifie que la source n'existe pas d�j�
			
			System.out.println("NOM DU FICHIER  : ");    //on remplit les informations n�cessaires
			String abs_path = "sources/" + Scan.sc.nextLine();
			
			int ok = Controle.check_file(abs_path);
			if(ok!=-1){
			
			System.out.println("NATURE (Acte officiel, Document officieux, Temoignage)  : ");
			String nature = Scan.sc.nextLine();
			System.out.println("EVENEMENT RENSEIGNE (Naissance, Deces, Bapteme, Mariage, Divorce, Filiation)  : ");
			String event = Scan.sc.nextLine();
			
			if(nature.equals("Acte officiel")) {
				ActeOfficielsRelation source = new ActeOfficielsRelation(id1, abs_path);
				source.modifier_event(event);
				source.save();
			}
			
			if(nature.equals("Document officieux")) {
				DocumentOfficieuxRelation source = new DocumentOfficieuxRelation(id1, abs_path);
				source.modifier_event(event);
				source.save();
			}
			
			if(nature.equals("Temoignage")) {
				TemoignageRelation source = new TemoignageRelation(id1, abs_path);
				source.modifier_event(event);
				source.save();
				System.out.println("CONNAIT-ON LE TEMOIN (Y or N)  : ");
				String reponse = Scan.sc.nextLine();
				if(reponse.equals("Y")) {
					creer_temoin(id1);
				}
			}
			
			else {
				System.out.println("NATURE NON RECONNUE "
						+ "SOURCE NON ENREGISTREE ");
			}
			
			}
			else{
				System.out.println("LE DOCUMENT SOURCE N'EXISTE PAS");
			}
			
			}
				
			
			else {
				System.out.println("CETTE SOURCE EXISTE DEJA");
			}
			}
			else {
				System.out.println("CETTE RELATION N'EXISTE PAS");
			
				}
			
			}
			
			else {
				System.out.println("PERSONNE INEXISTANTE");
			
			}
		}
		else{
			System.out.println("PERSONNE INEXISTANTE");
		}
		
		
	}
	
	
	//Méthodes de modification
	
	
	/**
	 * Methode qui permet de modifier un attribut d'une personne dans la BDD
	 * ATTENTION l'attribut appellé à être modifier doit avoir le même nom que dans la BDD
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void modifier_attribut_personne() throws ClassNotFoundException, SQLException{
		
		System.out.println("MODIFICATION PERSONNE CIVILE");

		System.out.println("NOM : ");
		String nom = Scan.sc.nextLine();
		System.out.println("PRENOM : ");
		String prenom = Scan.sc.nextLine();
		
		
		int id = Controle.check_identity(nom , prenom);
		
		if (id != -1){
			
			System.out.println("liste des attributs modifiables : " + "\n");  
			System.out.println("nom  prenom  prenoms_ secondaires  nom_jeune_fille" +"\n"
					+ "sexe  adresse" + "\n"
					+ "date_naissance  lieu_naissance  date_mort  lieu_deces");
			
			System.out.println("ATTRIBUT A MODIFIER : ");
			String attribut = Scan.sc.nextLine();
			System.out.println("NOUVEL ATTRIBUT : ");
			String new_attribut = Scan.sc.nextLine();
						
			
			String sql2 = "UPDATE personne_civile "
					+ " SET " + attribut +  " = ? "     
					+ " WHERE id = ? ;";
					
			PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql2);			
			statement2.setString(1, new_attribut);
			statement2.setInt(2, id);
			
			
			statement2.execute();
			System.out.println("MODIFICATION EFFECTUEE");
			statement2.close();
			
		}
		else{
			System.out.println("CETTE PERSONNE N'EST PAS REPERTORIEE");
		}
		
		
	}

	
	/**
	 * Methode qui vient modifier un attribut de la table personne_religieuse
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void modifier_attribut_catho() throws ClassNotFoundException, SQLException{
		
		System.out.println("MODIFICATION PERSONNE RELIGIEUSE");
		
		System.out.println("NOM : ");
		String nom = Scan.sc.nextLine();
		System.out.println("PRENOM : ");
		String prenom = Scan.sc.nextLine();
		
		
		int id = Controle.check_identity(nom , prenom);
		int id_catho = Controle.get_id_catho(id);
		
		if (id_catho != -1){
			
			System.out.println("liste des attributs modifiables : " + "\n");  
			System.out.println("date_bapteme  lieu_bapteme" + "\n"
			+ "date_marriage  lieu_marriage" + "\n"
			+ "enterrement  sepulture");
			
			System.out.println("ATTRIBUT A MODIFIER : ");
			String attribut = Scan.sc.nextLine();
			System.out.println("NOUVEL ATTRIBUT : ");
			String new_attribut = Scan.sc.nextLine();
			
			
			String sql2 = "UPDATE personne_religieuse "
					+ " SET " + attribut + " = ? "
					+ " WHERE id = ? ;";
					
			PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql2);
			statement2.setString(1, attribut);
			statement2.setString(2, new_attribut);
			statement2.setInt(3, id_catho);
			
			
			statement2.executeQuery();
			System.out.println("MODIFICATION EFFECTUEE");
		}
		else{
			System.out.println("CETTE PERSONNE N'EST PAS REPERTORIEE COMME RELIGIEUSE");
		}		
		
		 
	}
	
	public static void modifier_attribut_relation() throws ClassNotFoundException, SQLException {
		
		System.out.println("MODIFICATION RELATION");

		System.out.println("NOM PERSONNE 1 : ");
		String nom1 = Scan.sc.nextLine();
		System.out.println("PRENOM PERSONNE 1: ");
		String prenom1 = Scan.sc.nextLine();
		
		int id1 = Controle.check_identity(nom1 , prenom1);
		
		if (id1 != -1){
			
			System.out.println("NOM PERSONNE 2 : ");
			String nom2 = Scan.sc.nextLine();
			System.out.println("PRENOM PERSONNE 2: ");
			String prenom2 = Scan.sc.nextLine();
			
			int id2 = Controle.check_identity(nom2 , prenom2);
			
			if (id2 != -1){
				
				int idr = Controle.check_relation(id1,id2);   //on v�rifie que la relation n'existe pas d�j�
				if (idr != -1) {		// si la relation existe
					
					System.out.println("liste des attributs modifiables : " + "\n");  
					System.out.println("date_evenement   date_fin ");
					
					System.out.println("ATTRIBUT A MODIFIER : ");
					String attribut = Scan.sc.nextLine();
					System.out.println("NOUVEL ATTRIBUT : ");
					String new_attribut = Scan.sc.nextLine();
					
					
					String sql2 = "UPDATE relation "
							+ " SET " + attribut +  " = ? "     
							+ " WHERE id = ? ;";
							
					PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql2);			
					statement2.setString(1, new_attribut);
					statement2.setInt(2, idr);
					
					
					statement2.execute();
					System.out.println("MODIFICATION EFFECTUEE");
					statement2.close();
				}
				else {
					System.out.println("CETTE RELATION N'EXISTE PAS");
				}
			}
			else {
				System.out.println("CETTE PERSONNE N'EXISTE PAS");
			}
		}
		
		else {
			System.out.println("CETTE PERSONNE N'EXISTE PAS");
		}
		
		
	}
	
	/**
	 * Permet de modifier un attribut d'une sourcePersonne
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void modifier_attribut_sourcePersonne() throws ClassNotFoundException, SQLException {
		
System.out.println("MODIFICATION SOURCE PERSONNE");
		
		System.out.println("NOM : ");
		String nom = Scan.sc.nextLine();
		System.out.println("PRENOM : ");
		String prenom = Scan.sc.nextLine();
		
		
		int id = Controle.check_identity(nom , prenom);
		
		if (id != -1){
			
			int id_source = Controle.check_sourcePersonne(id);
			if(id_source != -1) {
			
			System.out.println("liste des attributs modifiables : " + "\n");  
			System.out.println("nature_source (Acte officiels, Document officieux, Temoignage) " +"\n"
					+ "type_evenement (Filiation, Naissance, Deces ...)" + "\n"
					+ "path_file (chemin vers le r�pertoire o� est enregistr� le document)");
			
			System.out.println("ATTRIBUT A MODIFIER : ");
			String attribut = Scan.sc.nextLine();
			System.out.println("NOUVEL ATTRIBUT : ");
			String new_attribut = Scan.sc.nextLine();
			
			String sql2 = "UPDATE source_personne "
					+ " SET " + attribut +  " = ? "     
					+ " WHERE id = ? ;";
					
			PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql2);			
			statement2.setString(1, new_attribut);
			statement2.setInt(2, id_source);
			
			
			statement2.execute();
			System.out.println("MODIFICATION EFFECTUEE");
			statement2.close();
			}
			
			else {
				System.out.println("CETTE SOURCE N'EST PAS REPERTORIEE");
			}
		}
		else{
			System.out.println("CETTE PERSONNE N'EST PAS REPERTORIEE");
		}
		
		
	}

	/**
	 * Permet de modifier un attribut d'une sourceRelation
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void modifier_attribut_sourceRelation() throws SQLException, ClassNotFoundException {
		
System.out.println("MODIFICATION SOURCE RELATION");

		System.out.println("NOM PERSONNE 1: ");
		String nom = Scan.sc.nextLine();
		System.out.println("PRENOM PERSONNE 1: ");
		String prenom = Scan.sc.nextLine();
		
		
		int id1 = Controle.check_identity(nom , prenom);
		
		if (id1 != -1){
			
			System.out.println("NOM PERSONNE 2: ");
			String nom2 = Scan.sc.nextLine();
			System.out.println("PRENOM PERSONNE 2: ");
			String prenom2 = Scan.sc.nextLine();
			
			
			int id2 = Controle.check_identity(nom2 , prenom2);
			
			if (id2 != -1){
			
				int idr = Controle.check_relation(id1,id2);
				if(idr !=-1) {									
				
			int id_source = Controle.check_sourceRelation(idr);
			if(id_source != -1) {
			
			System.out.println("liste des attributs modifiables : " + "\n");  
			System.out.println("nature_source (Acte officiels, Document officieux, Temoignage) " +"\n"
					+ "type_evenement (Filiation, Naissance, Deces ...)" + "\n"
					+ "path_file (chemin vers le r�pertoire o� est enregistr� le document)");
			
			System.out.println("ATTRIBUT A MODIFIER : ");
			String attribut = Scan.sc.nextLine();
			System.out.println("NOUVEL ATTRIBUT : ");
			String new_attribut = Scan.sc.nextLine();
			
			String sql2 = "UPDATE source_personne "
					+ " SET " + attribut +  " = ? "     
					+ " WHERE id = ? ;";
					
			PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql2);			
			statement2.setString(1, new_attribut);
			statement2.setInt(2, id_source);
			
			
			statement2.execute();
			System.out.println("MODIFICATION EFFECTUEE");
			statement2.close();
			}
			
			else {
				System.out.println("CETTE SOURCE N'EST PAS REPERTORIEE");
			}
		}
		else{
			System.out.println("CETTE RELATIION N'EST PAS REPERTORIEE");
		}
			}
			else {
				System.out.println("CETTE PERSONNE N'EST PAS REPERTORIEE");
			}
		}
		else {
			System.out.println("CETTE PERSONNE N'EST PAS REPERTORIEE");
		}
		
		
	}
	
	
	// M�thodes de requ�tes

	/**
	 * M�thode qui permet d'obtenire les informations civiles et religieuses d'une personne
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void get_info_personne() throws ClassNotFoundException, SQLException{
		
		System.out.println("DE QUI SOUHAITEZ VOUS OBTENIR DES INFOS");
		System.out.println("NOM : ");
		String nom = Scan.sc.nextLine();
		System.out.println("PRENOM : ");
		String prenom = Scan.sc.nextLine();
		
		int id = Controle.check_identity(nom , prenom);  //on v�rifie que la personne existe 
		
		if(id!=-1){   //si elle existe 
			
			String sql = "SELECT * FROM personne_civile"
					+ " WHERE id = ? ;";
			PreparedStatement statement = MyConnection.getInstance().prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			result.next();
						
			String prenom_sec = result.getString("prenoms_secondaires");
			String sexe = result.getString("sexe");
			String nom_jeune_fille = result.getString("nom_jeune_fille");
			String adresse = result.getString("adresse");
			String date_naissance = result.getString("date_naissance");
			String lieu_naissance = result.getString("lieu_naissance");
			String date_mort = result.getString("date_mort");
			String lieu_mort = result.getString("lieu_deces");
			
			System.out.println(prenom + " " + prenom_sec + " " + nom +  "(nee " + nom_jeune_fille + " )"+"\n"
					+ "ne(e) le " + date_naissance + " a " + lieu_naissance + "\n"
					+ "de sexe " + sexe + ", adresse : " + adresse + "\n"
					+ "decede(e) le " + date_mort + " a " + lieu_mort );
			
			
			int id_catho = Controle.get_id_catho(id);  //on regarde si cette personne est religieuse
			if(id_catho != -1) {
				
				System.out.println("CETTE PERSONNE EST CATHOLIQUE");
				String sql2 = "SELECT * FROM personne_religieuse"
						+ " WHERE id = ? ;";
				PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql2);
				statement2.setInt(1, id_catho);
				ResultSet result2 = statement2.executeQuery();
				result2.next();
				
				String date_bapteme = result2.getString("date_bapteme");
				String lieu_bapteme = result2.getString("lieu_bapteme");
				String date_mariage = result2.getString("date_marriage");
				String lieu_mariage = result2.getString("lieu_marriage");
				String enterrement = result2.getString("enterrement");
				String sepulture = result2.getString("sepulture");
				
				System.out.println("baptise(e) le " + date_bapteme + " a " + lieu_bapteme + "\n"
						+ "marie(e) le " + date_mariage + " a " + lieu_mariage + "\n"
						+ "enterre(e) le " + enterrement + " a " + sepulture);
			
			}
			
			String sql3 = "SELECT * FROM metier"
					+ " WHERE id_personne == ? ";
			PreparedStatement statement3 = MyConnection.getInstance().prepareStatement(sql3);
			statement3.setInt(1, id);
			ResultSet result3 = statement3.executeQuery();
			
			while(result3.next()) {
				String metier = result3.getString("activite");
				String date_debut = result3.getString("date_debut");
				String date_fin = result3.getString("date_fin");
				System.out.println("Cette personne a ete " + metier + " de " + date_debut + " � " + date_fin);
			}
			
		}
		else {
			System.out.println("PERSONNE NON ENREGISTREE");
		}
		
		
	}
	
	/**
	 * M�thode qui renvoit l'ensemble des relations d'une personne
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void get_info_relation() throws ClassNotFoundException, SQLException {
		
		System.out.println("DE QUI SOUHAITEZ VOUS OBTENIR DES INFOS");
		System.out.println("NOM : ");
		String nom = Scan.sc.nextLine();
		System.out.println("PRENOM : ");
		String prenom = Scan.sc.nextLine();
		
		int id = Controle.check_identity(nom , prenom);  //on v�rifie que la personne existe 
		
		if(id!=-1){   //si elle existe 
			
			String sql1 = "SELECT * FROM relation"
					+ " WHERE id_personne1 = ? ;";
			PreparedStatement statement1 = MyConnection.getInstance().prepareStatement(sql1);
			statement1.setInt(1, id);
			ResultSet result1 = statement1.executeQuery();
			
			System.out.println("L'ensemble des relation de " + prenom + " " + nom);
			int k=0;
			while(result1.next()) {
				k+=1;
				String date = result1.getString("date_evenement");
				String date_fin = result1.getString("date_fin");
				int id_personne2 = result1.getInt("id_personne2");
				String nature = result1.getString("nature_relation");
				
				
				String sqlbis = "SELECT * FROM personne_civile"
						+ " WHERE id = ? ;";
				PreparedStatement statementbis = MyConnection.getInstance().prepareStatement(sqlbis);
				statementbis.setInt(1, id_personne2);
				ResultSet resultbis = statementbis.executeQuery();
				
				String nom2 = resultbis.getString("nom");
				String prenom2 = resultbis.getString("prenom");
				
				System.out.println(nature + " avec " + prenom2 + " " + nom2 + " du " + date + " au " + date_fin);
				
			}
			
			String sql2 = "SELECT * FROM relation"
					+ " WHERE id_personne2 = ? ;";
			PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql2);
			statement2.setInt(1, id);
			ResultSet result2 = statement2.executeQuery();
			
			while(result2.next()) {
				k+=1;
				String date = result2.getString("date_evenement");
				String date_fin = result2.getString("date_fin");
				int id_personne2 = result2.getInt("id_personne2");
				String nature = result2.getString("nature_relation");
				
				
				String sqlbis = "SELECT * FROM personne_civile"
						+ " WHERE id == ? ";
				PreparedStatement statementbis = MyConnection.getInstance().prepareStatement(sqlbis);
				statementbis.setInt(1, id_personne2);
				ResultSet resultbis = statementbis.executeQuery();
				
				String nom2 = resultbis.getString("nom");
				String prenom2 = resultbis.getString("prenom");
				
				System.out.println(nature + " avec " + prenom2 + " " + nom2 + " du " + date + " au " + date_fin);
				
			}
			if(k==0) {
				System.out.println("CETTE PERSONNE N'A PAS DE RELATION ENREGISTREE");
			}
		}
		else {
			System.out.println("PERSONNE NON ENREGISTREE");
		}
		
		
	}
	
	/**
	 * Méthode qui affiche dans une fenetre le document source correspondant à une personne
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void afficher_source_personne() throws ClassNotFoundException, SQLException{
		
		System.out.println("A QUI CETTE SOURCE FAIT-ELLE REFERENCE? ");
		System.out.println("NOM  : ");
		String nom1 = Scan.sc.nextLine();
		System.out.println("PRENOM  : ");
		String prenom1 = Scan.sc.nextLine();
		
		int id1 = Controle.check_identity(nom1 , prenom1);  //v�rifie l'existence de la personne
		if(id1 != -1){
			
			int check = Controle.check_sourcePersonne(id1);   //on v�rifie que la source n'existe pas d�j�
			if (check != -1) {
			
			String sql = " SELECT * FROM source_personne "
					+ " WHERE id_personne = ? ";
			
			PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql);
			statement2.setInt(1, id1);
			ResultSet result2 = statement2.executeQuery();
			String abs_path = result2.getString("path_file");
			
			int ok = Controle.check_file(abs_path);
			if(ok!=-1){
				
				new Fenetre(abs_path);
			}
			
			else{
				System.out.println("LE DOCUMENT SOURCE N'EXISTE PAS");
			}
			}
			else {
				System.out.println("LA SOURCE N'EXISTE PAS");
			}
		}
		else{
			System.out.println("PERSONNE INEXISTANTE");
		}
	}
	
	public static void afficher_source_relation() throws ClassNotFoundException, SQLException{
		
		System.out.println("SOURCE RELATION");

		System.out.println("A QUI CETTE SOURCE FAIT-ELLE REFERENCE? ");
		System.out.println("NOM 1 : ");
		String nom1 = Scan.sc.nextLine();
		System.out.println("PRENOM 1 : ");
		String prenom1 = Scan.sc.nextLine();
		
		int id1 = Controle.check_identity(nom1 , prenom1);  //v�rifie l'existence de la personne
		if(id1 != -1){
			
			System.out.println("NOM 2 : ");
			String nom2 = Scan.sc.nextLine();
			System.out.println("PRENOM 2 : ");
			String prenom2 = Scan.sc.nextLine();
			
			int id2 = Controle.check_identity(nom2 , prenom2);  //v�rifie l'existence de la personne
			if(id2 != -1){
				
			int idr = Controle.check_relation(id1,id2);   //on v�rifie que la relation existe 
			if (idr != -1) {		// si la relation existe
				
				int check = Controle.check_sourcePersonne(id1);   //on v�rifie que la source n'existe pas d�j�
				if (check != -1) {
				
				String sql = " SELECT * FROM source_relation "
						+ " WHERE id_relation = ? ";
				
				PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql);
				statement2.setInt(1, idr);
				ResultSet result2 = statement2.executeQuery();
				String abs_path = result2.getString("path_file");
				
				int ok = Controle.check_file(abs_path);
				if(ok!=-1){
					
					new Fenetre(abs_path);
				}
				
				else{
					System.out.println("LE DOCUMENT SOURCE N'EXISTE PAS");
				}
			
			}
				
			
			else {
				System.out.println("CETTE SOURCE EXISTE DEJA");
			}
			}
			else {
				System.out.println("CETTE RELATION N'EXISTE PAS");
			
				}
			
			}
			
			else {
				System.out.println("PERSONNE INEXISTANTE");
			
			}
		}
		else{
			System.out.println("PERSONNE INEXISTANTE");
		}
	}
	
	/**
	 * Renvoit la liste des parents biologiques ou adoptifs (si il n'y a pas de parents biologiques) d'une personne
	 * ou une liste vide si la personne n'a aucun parents
	 * @param nom
	 * @param prenom
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static List<String> get_parents(String nom, String prenom) throws ClassNotFoundException, SQLException{
		
		List<String> L = new ArrayList<String>();
		
		int id = Controle.check_identity_bis(nom, prenom);
		if(id!=-1){
			
			String sql1 = "SELECT * FROM relation"
					+ " WHERE id_personne2 = ?"
					+ " AND nature_relation LIKE ?";
			
			PreparedStatement statement1 = MyConnection.getInstance().prepareStatement(sql1);
			statement1.setInt(1, id);
			statement1.setString(2, "Parente");
			ResultSet result1 = statement1.executeQuery();
			
			while(result1.next()){				
				int id2 = result1.getInt("id_personne1");
				
				String sql2 = "SELECT * FROM personne_civile"
						+ " WHERE id = ?"
						;
				PreparedStatement statement2 = MyConnection.getInstance().prepareStatement(sql2);
				statement2.setInt(1, id2);
				ResultSet result2 = statement2.executeQuery();
				while(result2.next()){
					String nom_parent = result2.getString("nom");
					String prenom_parent = result2.getString("prenom");
					L.add(nom_parent + " " + prenom_parent);
				}
			}
		}
		
		return L;
	}
	

	//Méthode de supression
	
	/**
	 * Méthode qui supprime toutes les valeurs de la BDD
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void supprimer_bdd() throws ClassNotFoundException, SQLException{
		
		Statement state = MyConnection.getInstance().createStatement();
		state.executeUpdate("DELETE FROM personne_civile;");
		state.executeUpdate("DELETE FROM personne_religieuse;");
		state.executeUpdate("DELETE FROM metier;");
		state.executeUpdate("DELETE FROM relation;");
		state.executeUpdate("DELETE FROM source_personne;");
		state.executeUpdate("DELETE FROM source_relation;");
		state.executeUpdate("DELETE FROM temoin;");		
				
	}
	
	

}

	