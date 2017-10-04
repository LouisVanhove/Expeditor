package fr.lala.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.enums.Profile;
import fr.lala.expeditor.utils.MonLogger;

/**
 * Classe en charge de gerer la couche d'acces � la BDD pour les objets de type Employ�.
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
	
	private static final String SELECT_EMPLOYE_BY_LOGIN = "SELECT e.id, e.login, e.password, e.name, e.firstname, e.profile, e.archived"
			+ " FROM EMPLOYEES e" 
			+ " WHERE e.login=? AND e.password=? AND e.archived=0" ;
	
	private static final String SELECT_ALL_EMPLOYEES = "SELECT e.id, e.login, e.password, e.name, e.firstname, e.profile, e.archived"
			+ " FROM EMPLOYEES e"
			+ " WHERE e.archived=0";
	
	private static final String INSERT_EMPLOYEE = "INSERT INTO EMPLOYEES (login, password, name, firstname, profile, archived)"
			+ " VALUES (?,?,?,?,?,0)";

	private static final String UPDATE_EMPLOYE_BY_ID = "UPDATE EMPLOYEES"
			+ " SET login=?,password=?,name=?,firstname=?,profile=?, archived=0"
			+ " WHERE id=?";
	
	private static final String ARCHIVE_EMPLOYE_BY_ID = "UPDATE EMPLOYEES"
			+ " SET login=?,password=?,name=?,firstname=?,profile=?, archived=1"
			+ " WHERE id=?";
	
	// monlogger retourne un objet de type logger
	Logger logger = MonLogger.getLogger(this.getClass().getName());
	
	/**
	 * M�thode pour ajouter un employ� dans la BDD.
	 */
	@Override
	public void insert(Employee data) throws SQLException {
		try(Connection cnx = ConnectionPool.getConnection()){
			PreparedStatement stm = cnx.prepareStatement(INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, data.getLogin());
			stm.setString(2, data.getPassword());
			stm.setString(3, data.getLastName());
			stm.setString(4, data.getFirstName());
			stm.setInt(5, (data.getProfile() == Profile.MANAGER ? 1 : 0));
			stm.executeUpdate();			
		} catch (SQLException e) {
			logger.severe(this.getClass().getName()+"#insert : "+e.getMessage());
			throw new SQLException("Erreur lors de l'insertion de l'employ� dans la base de donn�es.");
		}		
	}

	/**
	 * M�thode pour modifier un employ� dans la BDD.
	 */
	@Override
	public void update(Employee data) throws SQLException {
		try(Connection cnx = ConnectionPool.getConnection()){
			PreparedStatement stm = cnx.prepareStatement(UPDATE_EMPLOYE_BY_ID);
			stm.setString(1, data.getLogin());
			stm.setString(2, data.getPassword());
			stm.setString(3, data.getLastName());
			stm.setString(4, data.getFirstName());
			stm.setInt(5, (data.getProfile() == Profile.MANAGER ? 1 : 0));
			stm.setInt(6, data.getId());
			stm.executeUpdate();			
		} catch (SQLException e) {
			logger.severe(this.getClass().getName()+"#update : "+e.getMessage());
			throw new SQLException("Erreur lors de la modification de l'employ� dans la base de donn�es.");
		}			
	}

	/**
	 * M�thode pour archiver un employ� dans la BDD.
	 */
	@Override
	public void delete(Employee data) throws SQLException {
		try(Connection cnx = ConnectionPool.getConnection()){
			PreparedStatement stm = cnx.prepareStatement(ARCHIVE_EMPLOYE_BY_ID);
			stm.setString(1, data.getLogin());
			stm.setString(2, data.getPassword());
			stm.setString(3, data.getLastName());
			stm.setString(4, data.getFirstName());
			stm.setInt(5, (data.getProfile() == Profile.MANAGER ? 1 : 0));
			stm.setInt(6, data.getId());
			stm.executeUpdate();			
		} catch (SQLException e) {
			logger.severe(this.getClass().getName()+"#archive : "+e.getMessage());
			throw new SQLException("Erreur lors de l'archivage de l'employ� dans la base de donn�es.");
		}			
	}

	/**
	 * M�thode pour s�lectionner un employ� en fonction de son id.
	 */
	@Override
	public Employee selectById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * M�thode retournant la liste des employ�s (pr�parateurs de commande et manager) 
	 * travaillant dans l'entreprise (archived = 0).
	 * @return liste d'employ�s actifs.
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

	/**
	 * Methode de s�lection d'un employ� en base en fonction de son login et mot de passe.
	 * @param login
	 * @param password
	 * @return
	 */
	public Employee selectByLogin(String login, String password){
		Employee employee = null;

		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(SELECT_EMPLOYE_BY_LOGIN);
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
	 * M�thode en charge de r�cup�rer un employ�
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
	 * M�thode en charge de r�cup�rer le profil de l'employ�
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