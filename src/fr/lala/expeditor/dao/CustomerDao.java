package fr.lala.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import fr.lala.expeditor.models.Customer;
import fr.lala.expeditor.utils.MonLogger;

/**
 * Classe permettant de gérer la couche d'accès à la base de données des clients.
 * @author lajzenberg2017
 *
 */
public class CustomerDao{
	
	private static final String TABLE_CLIENTS = "Clients";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_ADDRESS = "address";
	private static final String COLUMN_ZIP_CODE = "zip_code";
	private static final String COLUMN_CITY = "city";
	private static final String COLUMN_ARCHIVED = "archived";

	private static final String REQ_INSERT_CLIENT =
			"INSERT INTO "
			+ TABLE_CLIENTS
			+ " VALUES(?, ?, ?, ?, ?)";

	private static final String REQ_SELECT_CLIENT_BY_ID = 
			"SELECT * FROM "
			+ TABLE_CLIENTS
			+ " WHERE archived = 0"
			+ " AND id = ?";

	// monlogger retourne un objet de type logger
	Logger logger = MonLogger.getLogger(this.getClass().getName());
	
	/**
	 * Méthode gérant l'insertion d'un client.
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int insert(Customer data) throws SQLException {
		int id = 0;
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(REQ_INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, data.getName());
			preparedStatement.setString(2, data.getAddress());
			preparedStatement.setString(3, data.getZipCode());
			preparedStatement.setString(4, data.getCity());
			preparedStatement.setInt(5, 0);
			preparedStatement.executeUpdate();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			generatedKeys.next();
			id = generatedKeys.getInt(1);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}		
		return id;
	}


	public void update(Customer data) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	public void delete(Customer data) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Méthode en charge de sélectionner un client en fonction de son id.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Customer selectById(int id) throws SQLException {
		Customer result = null;
		
		try(Connection cnx = ConnectionPool.getConnection()){
			PreparedStatement stm = cnx.prepareStatement(REQ_SELECT_CLIENT_BY_ID);
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			
			if(rs.next()){
				result = itemBuilder(rs);
			}
		} catch (SQLException e) {
			logger.severe(this.getClass().getName()+"#selectId : "+e.getMessage());
			throw new SQLException("Erreur lors de la sélection d'un client dans la base de données.");
		}
		return result;
	}

	public List<Customer> selectAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Méthode en charge de construire un objet de type Client
	 * à partir des données de la BDD.
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public Customer itemBuilder(ResultSet rs) throws SQLException {
		Customer result = new Customer();
		result.setId(rs.getInt(COLUMN_ID));
		result.setName(rs.getString(COLUMN_NAME));
		result.setAddress(rs.getString(COLUMN_ADDRESS));
		result.setZipCode(rs.getString(COLUMN_ZIP_CODE));
		result.setCity(rs.getString(COLUMN_CITY));
		result.setArchived(rs.getBoolean(COLUMN_ARCHIVED));
		return result;
	}
}
