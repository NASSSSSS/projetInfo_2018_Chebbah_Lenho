package Relation;

import java.sql.SQLException;

public class Fratrie extends Relation{
	
	//Constructeur
	
	public Fratrie(int id_personne1, int id_personne2) throws ClassNotFoundException, SQLException{
		super( id_personne1, id_personne2);
		this.setNature_relation(NatureRelation.Fratrie);
		this.save();
	}

}
