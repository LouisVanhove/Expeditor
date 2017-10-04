package fr.lala.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import fr.lala.expeditor.models.Order;
import fr.lala.expeditor.utils.MonLogger;

public class OrderDao implements ICrudDao<Order>{

	Logger logger = MonLogger.getLogger(this.getClass().getName());
	
	private static final String TABLE_ORDERS="ORDERS";
	private static final String COLUMN_ID="id";	
	private static final String COLUMN_ID_CUSTOMER="id_client";
	private static final String COLUMN_ID_EMPLOYEE="id_employee";
	private static final String COLUMN_ORDER_DATE="order_date";
	private static final String COLUMN_TREATMENT_DATE = "treatment_date";  
	private static final String COLUMN_STATE = "state";
	private static final String COLUMN_ARCHIVED = "archived";
	
	private static final String REQ_GET_NEXT_ORDER =
			"SELECT TOP 1"
			+" *" 
			+" FROM "
			+ TABLE_ORDERS
			+" WHERE" 
			+ COLUMN_ID_EMPLOYEE +" IS NULL"
			+" AND "
			+ COLUMN_ARCHIVED +"=0"
			+" ORDER BY"
			+ COLUMN_ORDER_DATE+" ASC";
			
	
	
	
	
	/**
	 * Méthode en charge de fournir la prochaine à traiter par un
	 * préparateur de commande.
	 * 
	 * @return La prochaine commande à traiter.
	 */
	public Order selectNextOrder(){
		Order result = null ;
		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(REQ_GET_NEXT_ORDER);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				result = itemBuilder(rs);
			}

		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		return result;
	}
	
	@Override
	public void insert(Order data) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Order data) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Order data) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order selectById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> selectAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Fonction permettat la construction d'un objet Order à partie d'un resultset
	 * Définition incomplète.
	 */
	@Override
	public Order itemBuilder(ResultSet rs) throws SQLException {
		Order result = new Order();
		result.setId(rs.getInt(COLUMN_ID));
		result.setOrderDate(rs.getDate(COLUMN_ORDER_DATE));
		result.setArchived(rs.getBoolean(COLUMN_ARCHIVED));
	
		return result;
	}

}
