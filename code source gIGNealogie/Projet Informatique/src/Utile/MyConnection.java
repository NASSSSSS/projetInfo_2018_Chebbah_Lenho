package Utile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Classe MyConnection
 * @author Edgar Lenhof, Nassim Chebbah
 *
 */
public class MyConnection {
	
	private final String url = "jdbc:sqlite:Genealogie";
	private static Connection connection;
	
	
	/** Constructeur privée 
	 * @throws ClassNotFoundException
	 */
	private MyConnection() throws ClassNotFoundException {
        try {           
            // create a connection to the database
            connection = DriverManager.getConnection(url);            
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
    }
	        
	
	/**
	 * @return connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getInstance() throws SQLException, ClassNotFoundException {
		if (connection == null) {
			new MyConnection();
		}
		return connection;
	}
}
