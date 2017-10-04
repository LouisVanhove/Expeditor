package fr.lala.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import fr.lala.expeditor.models.Customer;

public class CustomerDao{
	
	private static final String TABLE_CLIENTS = "Clients";
	
	private static final String REQ_INSERT_CLIENT =
			"INSERT INTO "
			+ TABLE_CLIENTS
			+ " VALUES(?, ?, ?, ?, ?)";


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
			e.printStackTrace();
		}
		
		return id;
	}


	public void update(Customer data) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	public void delete(Customer data) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	public Customer selectById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Customer> selectAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	public Customer itemBuilder(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
