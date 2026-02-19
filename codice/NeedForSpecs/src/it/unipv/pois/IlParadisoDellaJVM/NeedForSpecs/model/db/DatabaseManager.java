package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*
 * @author: Persy
 */

public class DatabaseManager {

	private static Connection conn; 
//	private static final String URL = "jdbc:mysql://localhost:3306/needforspecs";
//	private static final String USER = "ipdj";
	private static final String URL = "jdbc:mysql://localhost:3306/NeedForSpecsDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";;
	private static final String USER = "root";

//	private static final String PASSWORD = "Garpez67";
	private static final String PASSWORD = "persy-mac-mysql";
	
	
	
	
	


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
	
	
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				
			} catch (SQLException e) {
				System.err.println("Errore durante la chiusura della connessione!");
				e.printStackTrace();
			}
		}
	}
	
	
	public static void setAutoCommit(Connection conn, boolean autoCommit) {
		if (conn != null) {
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				System.err.println("Errore durante l'impostazione dell'auto-commit!");
				e.printStackTrace();
			}
		}
	}

	public static void commitConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				System.err.println("Errore critico durante il commit!");
				e.printStackTrace();
			}
		}
	}

	public static void rollbackConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
				System.err.println("Rollback eseguito con successo. Dati ripristinati.");
			} catch (SQLException e) {
				System.err.println("Errore critico durante il rollback!");
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
}
