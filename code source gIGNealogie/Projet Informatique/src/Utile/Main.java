package Utile;

import Utile.Requete;

import java.sql.SQLException;
import java.text.ParseException;

import Utile.Arbre;

public class Main {
		
	public static void interraction() throws ClassNotFoundException, SQLException, ParseException{
		
		System.out.println("QUE SOUHAITEZ VOUS FAIRE ? (entrer le numero correspondant)" + "\n" + "\n"
		
		+ "1. CREER UNE PERSONNE CIVILE" + "\n"
		+ "2. MODIFIER UNE PERSONNE CIVILE" + "\n"
		+ "3. CREER UNE PERSONNE RELIGIEUSE" + "\n"
		+ "4. MODIFIER UNE PERSONNE RELIGIEUSE" + "\n"
		+ "5. RENSEIGNER LA PROFESSION D'UNE PERSONNE" + "\n"
		+ "6. AFFICHER LES INFOS RELATIVES A UNE PERSONNE" + "\n" + "\n"
		
		+ "7. DEFINIR UN MARIAGE" + "\n"
		+ "8. DEFINIR UNE PARENTE" + "\n"
		+ "9. DEFINIR UNE FRATRIE" + "\n"
		+ "10. DEFINIR UN PARRAINAGE" + "\n"
		+ "11. MODIFIER UNE RELATION" + "\n"
		+ "12. AFFICHER LES INFOS RELATIVES A UNE RELATION" + "\n" + "\n"
		
		+ "13. CREER UNE SOURCE RELATIVE A UNE PERSONNE" + "\n"
		+ "14. MODIFIER UNE SOURCE RELATIVE A UNE PERSONNE" + "\n"
		+ "15. CREER UNE SOURCE RELATIVE A UNE RELATION" + "\n"
		+ "16. MODIFIER UNE SOURCE RELATIVE A UNE RELATION" + "\n" 
		+ "17. AFFICHER UNE SOURCE RELATIVE A UNE PERSONNE"+ "\n"
		+ "18. AFFICHER UNE SOURCE RELATIVE A UNE RELATION"+ "\n" + "\n"
		
		+ "19. CREER L'ARBRE GENEALOGIQUE D'UNE PERSONNE" + "\n");
		
		int reponse = Scan.sc.nextInt();
		Scan.sc.nextLine();
		
		if(reponse==1){
			Requete.creer_personne();
		}
		if(reponse==2){
			Requete.modifier_attribut_personne();
		}
		if(reponse==3){
			Requete.creer_catho();
		}
		if(reponse==4){
			Requete.modifier_attribut_catho();
		}
		if(reponse==5){
			Requete.renseigner_metier();
		}
		if(reponse==6){
			Requete.get_info_personne();
		}
		if(reponse==7){
			Requete.creer_mariage();
		}
		if(reponse==8){
			Requete.creer_parente();
		}
		if(reponse==9){
			Requete.creer_fratrie();
		}
		if(reponse==10){
			Requete.creer_parrainage();
		}
		if(reponse==11){
			Requete.modifier_attribut_relation();
		}
		if(reponse==12){
			Requete.get_info_relation();
		}
		if(reponse==13){
			Requete.creer_source_personne();
		}
		if(reponse==14){
			Requete.modifier_attribut_sourcePersonne();
		}
		if(reponse==15){
			Requete.creer_source_relation();
		}
		if(reponse==16){
			Requete.modifier_attribut_sourceRelation();
		}
		if(reponse==17){
			Requete.afficher_source_personne();
		}
		if(reponse==18){
			Requete.afficher_source_relation();
		}
		if(reponse==19){
			
			System.out.println("NOM :");
			String nom = Scan.sc.nextLine();
			System.out.println("PRENOM :");
			String prenom = Scan.sc.nextLine();
			Arbre.ecritureArbre(nom, prenom);
		}
		
		System.out.println("FINI :)");
		
		System.out.println("VOULEZ VOUS CONTINUER ? (Y or N)");
		String reponse2 = Scan.sc.nextLine();
		if(reponse2.equals("Y")){
			interraction();
		}
		else{
			System.out.println("AU REVOIR!!");
		}
	}

	public static void main(String[] args) throws SQLException, ParseException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		interraction();	
		

	}
	

}
