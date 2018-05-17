package Utile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe qui gère la création de l'arbre généalogique
 * l'arbre est créé dans fichier html qu'il faut ensuite ouvrir ave gephi
 * @author chebbah-lenhof
 *
 */
public class Arbre {
	
	/**
	 * Méthode qui créé un noeud de l'arbre
	 * @param nom
	 * @param prenom
	 * @param filePath
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void ecritureNoeud(String nom, String prenom, String filePath) throws ClassNotFoundException, SQLException {
	    Path logFile = Paths.get(filePath);
	    
	    
		try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) { 			
			List<String> parents;
			try {
				parents = Requete.get_parents(nom,prenom);
				for(String elem : parents) {
					writer.write("    <node id=\"" + elem + "\"/>\n" +
					"    <edge source=\"" + elem + "\" target=\"" + nom + " " + prenom + "\"/>\n");
					String[] nomprenom = elem.split(" ");
					ecritureNoeud(nomprenom[0], nomprenom[1], filePath);
			    }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  } catch (SQLException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
				
		}
		catch (IOException e) {
		e.printStackTrace();
		}
		
	}
	
	/**
	 * Méthode qui écrit l'en-tête nécessaire à la création du fichier de l'arbre généalogique
	 * @param nom
	 * @param prenom
	 * @param filePath
	 */
	public static void ecritureEnTete(String nom, String prenom, String filePath) {
	    Path logFile = Paths.get(filePath);
		if (!Files.exists(logFile)) { 
			try {
				Files.createFile(logFile);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardCharsets.UTF_8, StandardOpenOption.WRITE)) { 
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		    "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n" + 
		    "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
		    "    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n" + 
		    "     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n" +
		    "  <graph id=\"Arbre_genealogique_" + nom + "_" + prenom + "\" edgedefault=\"directed\">\n" +
		    "    <node id=\"" + nom + " " + prenom + "\"/>\n");
		}
		catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui écrit la fin du code nécessaire à la création du fichier de l'arbre généalogique
	 * @param filePath
	 */
	public static void ecritureFin(String filePath) {
		Path logFile = Paths.get(filePath);
		try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
			writer.write("  </graph>\n</graphml>");	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Méthode d'instance que l'on appelle pour créé l'arbre généalogique
	 * @param nom
	 * @param prenom
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void ecritureArbre(String nom, String prenom) throws ClassNotFoundException, SQLException {
		
		int id = Controle.check_identity(nom, prenom);
		if(id!=-1){		
		String filePath = "Arbre_genealogique_" + nom + "_" + prenom +".graphml";
		ecritureEnTete(nom, prenom, filePath);
		ecritureNoeud(nom, prenom, filePath);
		ecritureFin(filePath);
		}
		else{
			System.out.println("PERSONNE INEXISTANTE");
		}
	}

}
