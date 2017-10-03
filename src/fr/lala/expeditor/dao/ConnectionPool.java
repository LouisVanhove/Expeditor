package fr.lala.expeditor.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Classe de connexion vers la base de données.
 * @author aurelie.lardeux2017
 *
 */
public class ConnectionPool {

	/**
	 * Méthode en charge de fournir une connexion à la base de données.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() {
		
		Connection cnx =null;
		try {
			Context context = new InitialContext();
			DataSource datasource = (DataSource)context.lookup("java:comp/env/jdbc/expeditor_pool");
			cnx = datasource.getConnection();	
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnx;
	}

}
