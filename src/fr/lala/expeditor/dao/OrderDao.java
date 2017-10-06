package fr.lala.expeditor.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Customer;
import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.Order;
import fr.lala.expeditor.models.enums.State;
import fr.lala.expeditor.utils.MonLogger;

/**
 * Classe en charge de g�rer la couche d'acc�s � la BDD 
 * pour les objets de type Order (commandes).
 */
public class OrderDao implements ICrudDao<Order> {
	
Logger logger = MonLogger.getLogger(this.getClass().getName());
	
	private static final String TABLE_ORDERS="ORDERS";
	private static final String COLUMN_ID="id";	
	private static final String COLUMN_ID_CUSTOMER="id_client";
	private static final String COLUMN_ID_EMPLOYEE="id_employee";
	private static final String COLUMN_ORDER_DATE="order_date";
	private static final String COLUMN_TREATMENT_DATE = "treatment_date";  
	private static final String COLUMN_STATE = "state";
	private static final String COLUMN_ARCHIVED = "archived";
	private static final String TABLE_ARTICLES_ORDERS = "ARTICLES_ORDERS";
	
	private static final String SEPARATOR = ",";
	private static final String DATE_FORM = "dd/MM/yyyy HH:mm:ss";
	private static final String REQ_SET_SHIPPING_CLERK = "UPDATE ORDERS SET id_employee = ? WHERE id=?";
	private static final String REQ_GET_NEXT_ORDER = "SELECT TOP 1 * FROM ORDERS WHERE id_employee IS NULL ORDER BY order_date ASC";
	private static final String REQ_SET_PROCESSING_DATE = "UPDATE ORDERS SET treatment_date = ? WHERE id = ?";
	private static final String REQ_UPDATE_ORDER_STATE="UPDATE ORDERS SET state=? WHERE id=?";
	private static final String REQ_RESET_ORDER = "UPDATE ORDERS SET id_employee = NULL, state=1 WHERE id=?";


	private static final String REQ_INSERT_ORDER =
			"INSERT INTO "
			+ TABLE_ORDERS
			+ "("
			+ COLUMN_ID
			+ ", "
			+ COLUMN_ID_CUSTOMER
			+ ", "
			+ COLUMN_ORDER_DATE
			+ ", "
			+ COLUMN_STATE
			+ ", "
			+ COLUMN_ARCHIVED
			+ ") VALUES(?, ?, ?, ?, ?)";
	
	private static final String REQ_INSERT_ARTICLE_ORDER =
			"INSERT INTO "
			+ TABLE_ARTICLES_ORDERS
			+ " VALUES(?, ?, ?)";
	
	private static final String SELECT_ALL = "SELECT * FROM ORDERS o"
						+ " JOIN EMPLOYEES e ON e.id = o.id_employee"
						+ " WHERE state=1";




	/**
	 * M�thode en charge de r�cup�rer une liste de commandes
	 * depuis un fichier CSV.
	 * @param File : fichier CSV
	 * @return List<Order> : liste de commandes.
	 * @throws IOException
	 */
	public static List<Order> importFromFile(String value) throws IOException {
		
		List<Order> orders = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORM);
		String[] lines = value.split("\n");
		boolean firstLine = true;
		for(String line : lines) {
			if(firstLine) {
				firstLine = false;
				continue;
			}
			String[] cells = line.split(SEPARATOR);
			Order order = new Order();
			orders.add(order);
			order.setCustomer(new Customer());
			order.setListArticles(new ArrayList<>());
				
			try {
				order.setOrderDate(formatter.parse(cells[0].trim()));
				order.setId(Integer.parseInt(cells[1].trim().substring(3)));
				order.getCustomer().setName(cells[2].trim());
				order.getCustomer().setAddress(cells[3].trim().split(" - ")[0]);
				order.getCustomer().setZipCode(cells[3].trim().split(" - ")[1].split(" ")[0]);
				order.getCustomer().setCity(cells[3].trim().split(" - ")[1].substring(order.getCustomer().getZipCode().length()).trim());
				
				String[] strArticles = cells[4].split("; ");
				for (String art_qt : strArticles) {
					Article article = new Article();
					article.setLabel(art_qt.split("\\(")[0]);
					article.setQuantity(Integer.parseInt(art_qt.split("\\(")[1].split("\\)")[0]));
					order.getListArticles().add(article);
				}
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		return orders;
	}

	
	/**
	 * M�thode en charge de fournir la prochaine � traiter par un
	 * pr�parateur de commande.
	 * 
	 * @return La prochaine commande � traiter.
	 */
	public Order selectNextOrder(){
		Order result = null ;
		try (Connection cnx = ConnectionPool.getConnection()) {
			System.out.println("Entr�e dans le try de selectNextOrder");
			PreparedStatement cmd = cnx.prepareStatement(REQ_GET_NEXT_ORDER);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				result = itemBuilder(rs);
			}

		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		System.out.println("Next order : " +result);
		return result;
	}
	
	/**
	 * Méthode en charge d'insérer une commande dans la base de données.
	 *
	 * @param data Commande à insérer dans la base de données.
	 */
	@Override
	public void insert(Order data) throws SQLException {
		try (Connection connection = ConnectionPool.getConnection()){
			
			PreparedStatement preparedStatement = connection.prepareStatement(REQ_INSERT_ORDER);
			preparedStatement.setInt(1, data.getId());
			preparedStatement.setInt(2, data.getCustomer().getId());
			preparedStatement.setDate(3, new java.sql.Date(data.getOrderDate().getTime()));
			preparedStatement.setInt(4, 1);
			preparedStatement.setInt(5, 0);
			preparedStatement.executeUpdate();
			preparedStatement = connection.prepareStatement(REQ_INSERT_ARTICLE_ORDER);
			preparedStatement.setInt(1, data.getId());
			for (Article article : data.getListArticles()) {
				preparedStatement.setInt(2, article.getId());
				preparedStatement.setInt(3, article.getQuantity());
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
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

	/**
	 * Méthode en charge de sélectionner la liste des commandes à traiter.
	 *
	 * @return Liste de toutes les commandes à traiter.
	 */
	@Override
	public List<Order> selectAll() throws SQLException {
		List<Order> orderList = new ArrayList<Order>();

		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = cmd.executeQuery();
			while (rs.next()) {
				orderList.add(itemBuilder(rs));
			}
		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		return orderList;
	}

	
	/**
	 * Fonction permettant la construction d'un objet Order à partir d'un resultset
	 *
	 * @param rs Curseur positionné sur la ligne à traiter.
	 * @return Commande construite depuis la position courante du curseur.
	 */
	@Override
	public Order itemBuilder(ResultSet rs) throws SQLException {
		Order result = new Order();
		System.out.println(rs.getInt(COLUMN_ID_CUSTOMER));
		Customer c = new CustomerDao().selectById(rs.getInt(COLUMN_ID_CUSTOMER));
		Employee e = new EmployeeDao().selectById(rs.getInt(COLUMN_ID_EMPLOYEE));
		result.setCustomer(c);
		result.setId(rs.getInt(COLUMN_ID));
		result.setOrderDate(rs.getDate(COLUMN_ORDER_DATE));
		result.setProcessingDate(rs.getDate(COLUMN_TREATMENT_DATE));
		result.setEmployee(e);
		result.setState(State.values()[rs.getInt(COLUMN_STATE)-1]);
		result.setArchived(rs.getBoolean(COLUMN_ARCHIVED));
	
		return result;
	}

	/**
	 * Méthode en charge de modifier une commande afin de lui affecter
	 * un préparateur.
	 *
	 * @param data Commande à modifer.
	 * @param clerk Préparateur de commandes à affecter.
	 * @throws SQLException
	 */
	public void setShippingClerk(Order data, Employee clerk) throws SQLException {
		try (Connection connection = ConnectionPool.getConnection()){
			System.out.println("OrderDao#setShippingClerk#try");
			System.out.println("Id employ� : "+clerk.getId());
			System.out.println("Id commande : "+data.getId());
			PreparedStatement preparedStatement = connection.prepareStatement(REQ_SET_SHIPPING_CLERK);
			preparedStatement.setInt(1, clerk.getId());
			preparedStatement.setInt(2, data.getId());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		
	}

	/**
	 * Méthode en charge de mettre à jour la date de traitement d'une commande.
	 *
	 * @param data Commande à modifier.
	 * @throws SQLException
	 */
	public void setProcessingDate(Order data) throws SQLException {
		System.out.println(data);
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(REQ_SET_PROCESSING_DATE);
			preparedStatement.setDate(1, new java.sql.Date(new Date().getTime()));
			preparedStatement.setInt(2, data.getId());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			throw e;
			//throw new SQLException(e.getMessage());
		}
		
	}

	/**
	 * Méthode permettant de mettre à jour l'état d'avancement d'une commande. Les différents
	 * états possibles sont contenus dans la table STATES de la base de données associée.
	 *
	 * @param data Commande dont l"état doit être modifié
	 * @param state Etat à affecter à la commande.
	 * @throws SQLException
	 */
	public void updateOrderState(Order data, State state) throws SQLException {
		System.out.println("id state : "+state.getId());
		System.out.println("id order : "+data.getId());
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(REQ_UPDATE_ORDER_STATE);
			preparedStatement.setInt(2, data.getId());
			preparedStatement.setInt(1, state.getId());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	/**
	 * Méthode en charge de remettre à zero une commande attribuée à un préparateur
	 * lors de la deconnexion de celui ci. Sans cette modification, la commande attribuée
	 * mais non traitée ne serait jamais proposée de nouveau à un préparateur.
	 *
	 * @param data Commande à renvoyer dans la pile à traiter.
	 * @throws SQLException
	 */
	public void resetOrder(Order data) throws SQLException {
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(REQ_RESET_ORDER);
			preparedStatement.setInt(1, data.getId());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}




}