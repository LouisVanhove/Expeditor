package fr.lala.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import fr.lala.expeditor.utils.MonLogger;

/**
 * Classe en charge de gerer la couche d'acces à la BDD pour les objets de type Employé.
 * @author aurelie.lardeux2017
 *
 */
public class EmployeeDao {
	
	private static final String COLUMN_ID = "Id";
	private static final String COLUMN_LOGIN = "login";
	private static final String COLUMN_PASSWORD = "password";
	private static final String COLUMN_NAME= "name";
	private static final String COLUMN_FIRSTNAME = "firstname";
	private static final String COLUMN_PROFILE = "profile";
	private static final String COLUMN_ARCHIVED = "archived";
	
	private static final String SELECT_EMPLOYE_BY = "SELECT e.id, e.login, e.password, e.name, e.firstname, e.profile, e.archived "
			+ " FROM EMPLOYEES e" 
			+ " WHERE e.login=? AND e.password=? AND e.archived=0" ;
	
	// monlogger retourne un objet de type logger
	Logger logger = MonLogger.getLogger(this.getClass().getName());
	
	public Employee selectById(String login, String password){
		Employee employee = null;

		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(SELECT_EMPLOYE_BY);
			cmd.setString(1, login);
			cmd.setString(2, password);

			ResultSet rs = cmd.executeQuery();

			// si le resultset nous retourne true
			if (rs.next()) {
				if (rs.getInt(COLUMN_PROFILE) == 1) {
					employee = new Employee(); // si statut est 1 alors l'objet est un Employe
				}
				if (rs.getInt(COLUMN_PROFILE) == 2) {
					employee = new Manager(); // si statut est 2 alors l'objet est un Manager
				}
				// Ajout d'un utilisateur
				employee = itemBuilder(rs);
			}

		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		return employee;
	}

	private Employee itemBuilder(ResultSet rs) {
		Employee employee = new Employee();
		employee.setId(rs.getInt(COLUMN_ID));
		employee.setLogin(rs.getString(COLUMN_LOGIN));
		employee.setPassword(rs.getString(COLUMN_PASSWORD));
		employee.setName(rs.getString(COLUMN_NAME));
		employee.setFirstName(rs.getString(COLUMN_FIRSTNAME));
		employee.setProfile(rs.getString(COLUMN_PROFILE));
		employee.setArchived(rs.getString(COLUMN_ARCHIVED));
		return employee;
	}
}
