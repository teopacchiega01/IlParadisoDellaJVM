package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*
 * @author: Persy
 */

public class DatabaseManager {

	private static Connection conn; 
	private static final String URL = "jdbc:mysql://localhost:3306/needforspecs";
	private static final String USER = "ipdj";
	private static final String PASSWORD = "Garpez67";
	
	
	
	


	public static Connection getConnetcion () {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. Se la connessione è null o è stata chiusa, la riapriamo
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Driver JDBC non trovato!");
			e.printStackTrace();
		}

		return conn;
		
		
	}
	
	
	
	
	
	
}
