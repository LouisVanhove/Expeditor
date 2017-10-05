package fr.lala.expeditor.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Customer;
import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.Order;
import fr.lala.expeditor.models.enums.State;
import fr.lala.expeditor.utils.MonLogger;

/**
 * Classe en charge de gérer la couche d'accès à la BDD 
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
	
	private static final String REQ_GET_NEXT_ORDER = "SELECT TOP 1 * FROM ORDERS WHERE id_employee IS NULL ORDER BY order_date ASC";
			
	
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
	 * Mï¿½thode en charge de récupérer une liste de commandes
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
	 * Méthode en charge de fournir la prochaine à traiter par un
	 * pré½parateur de commande.
	 * 
	 * @return La prochaine commande à traiter.
	 */
	public Order selectNextOrder(){
		Order result = null ;
		try (Connection cnx = ConnectionPool.getConnection()) {
			System.out.println("Entrée dans le try de selectNextOrder");
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
	
	/**z
	 * Méthode en charge d'insérer une commande dans la bdd.
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
	 * methode en charge de sélectionner la liste des commandes à traiter.
	 */
	@Override
	public List<Order> selectAll() throws SQLException {
		List<Order> orderList = new ArrayList<Order>();

		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(SELECT_ALL);

			ResultSet rs = cmd.executeQuery();

			// tant qu'il trouve quelque chose
			while (rs.next()) {
				// Ajout d'une commande à la liste
				orderList.add(itemBuilder(rs));
			}

		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		return orderList;
	}

	
	/**
	 * Fonction permettant la construction d'un objet Order à partir d'un resultset
	 * Définition incomplète.
	 */
	@Override
	public Order itemBuilder(ResultSet rs) throws SQLException {
		Order result = new Order();
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

}