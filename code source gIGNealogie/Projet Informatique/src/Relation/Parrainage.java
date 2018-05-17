package Relation;

import java.sql.SQLException;

public class Parrainage extends Relation{

	//Constructeur
	
		public Parrainage(int id_personne1, int id_personne2) throws ClassNotFoundException, SQLException{
			super(id_personne1, id_personne2);
			this.setNature_relation(NatureRelation.Parrainage);
			this.save();
		}

}
