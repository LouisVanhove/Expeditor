package fr.lala.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.enums.Profile;
import fr.lala.expeditor.utils.MonLogger;

/**
 * Classe en charge de gerer la couche d'acces à la BDD pour les objets de type Employé.
 * @author aurelie.lardeux2017
 *
 */
public class EmployeeDao implements ICrudDao<Employee>{
	
	private static final String COLUMN_ID = "Id";
	private static final String COLUMN_LOGIN = "login";
	private static final String COLUMN_PASSWORD = "password";
	private static final String COLUMN_NAME= "name";
	private static final String COLUMN_FIRSTNAME = "firstname";
	private static final String COLUMN_PROFILE = "profile";
	private static final String COLUMN_ARCHIVED = "archived";
	
	private static final String SELECT_EMPLOYE_BY = "SELECT e.id, e.login, e.password, e.name, e.firstname, e.profile, e.archived"
			+ " FROM EMPLOYEES e" 
			+ " WHERE e.login=? AND e.password=? AND e.archived=0" ;
	
	private static final String SELECT_ALL_EMPLOYEES = "SELECT e.id, e.login, e.password, e.name, e.firstname, e.profile, e.archived"
			+ " FROM EMPLOYEES e"
			+ " WHERE e.archived=0";

	// monlogger retourne un objet de type logger
	Logger logger = MonLogger.getLogger(this.getClass().getName());
	
	@Override
	public void insert(Employee data) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Employee data) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Employee data) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee selectById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Méthode retournant la liste des employés (préparateurs de commande et manager) 
	 * travaillant dans l'entreprise (archived = 0).
	 * @return liste d'employés actifs.
	 */
	@Override
	public List<Employee> selectAll() throws SQLException {
		List<Employee> result = new ArrayList<>();
		
		try(Connection cnx = ConnectionPool.getConnection()){
			PreparedStatement cmd = cnx.prepareStatement(SELECT_ALL_EMPLOYEES);
			
			ResultSet rs = cmd.executeQuery();
			while (rs.next()){
				result.add(itemBuilder(rs));
			}
		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		return result;
	}

	public Employee selectByLogin(String login, String password){
		Employee employee = null;

		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(SELECT_EMPLOYE_BY);
			cmd.setString(1, login);
			cmd.setString(2, password);

			ResultSet rs = cmd.executeQuery();

			// si le resultset nous retourne true
			if (rs.next()) {
				// Ajout d'un utilisateur
				employee = itemBuilder(rs);
			}

		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		return employee;
	}

	/**
	 * Méthode en charge de récupérer un employé
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Employee itemBuilder(ResultSet rs) throws SQLException {
		Employee employee = new Employee();
		employee.setId(rs.getInt(COLUMN_ID));
		employee.setLogin(rs.getString(COLUMN_LOGIN));
		employee.setPassword(rs.getString(COLUMN_PASSWORD));
		employee.setLastName(rs.getString(COLUMN_NAME));
		employee.setFirstName(rs.getString(COLUMN_FIRSTNAME));
		employee.setProfile(profileBuilder(rs.getInt(COLUMN_PROFILE)));
		employee.setArchived(rs.getBoolean(COLUMN_ARCHIVED));
		return employee;
	}

	/**
	 * Méthode en charge de récupérer le profil de l'employé
	 * @param id_profile
	 * @return
	 */
	private Profile profileBuilder(int id_profile) {
		Profile profile = null;
		if(1==id_profile){
			 profile = Profile.MANAGER;
		}
		else if (2==id_profile){
			profile = Profile.SHIPPING_CLERK;
		}
		return profile;
	}
}
