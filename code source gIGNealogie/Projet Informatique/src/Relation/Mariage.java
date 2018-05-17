package Relation;

import java.sql.SQLException; 

public class Mariage extends Relation{
	
	
	
	//Constructeur
	
	public Mariage(int id_personne1, int id_personne2) throws ClassNotFoundException, SQLException{
		super(id_personne1, id_personne2);
		this.setNature_relation(NatureRelation.Mariage);
		this.save();
	}


}
