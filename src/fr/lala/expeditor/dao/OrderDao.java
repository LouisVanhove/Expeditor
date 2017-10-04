package fr.lala.expeditor.dao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Customer;
import fr.lala.expeditor.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
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
	
	private static final char SEPARATOR = ',';
	private static final String DATE_FORM = "dd/MM/yyyy HH:mm:ss";
	
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
	
	/**
	 * Méthode en charge de récupérer une liste de commandes
	 * depuis un fichier CSV.
	 * @param File : fichier CSV
	 * @return List<Order> : liste de commandes.
	 * @throws IOException
	 */
	public static List<Order> importFromFile(File file) throws IOException {
		
		List<Order> orders = new ArrayList<>();
		FileReader fileReader = new FileReader(file);
		CSVReader csvReader = new CSVReader(fileReader, SEPARATOR);	
		String[] line = csvReader.readNext();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORM);
		
		
		while ((line = csvReader.readNext()) != null) {
			Order order = new Order();
			orders.add(order);
			order.setCustomer(new Customer());
			order.setListArticles(new ArrayList<>());
			
			try {
				order.setOrderDate(formatter.parse(line[0].trim()));
				order.setId(Integer.parseInt(line[1].trim().substring(3)));
				order.getCustomer().setName(line[2].trim());
				order.getCustomer().setAddress(line[3].trim().split(" - ")[0]);
				order.getCustomer().setZipCode(line[3].trim().split(" - ")[1].split(" ")[0]);
				order.getCustomer().setCity(line[3].trim().split(" - ")[1].substring(order.getCustomer().getZipCode().length()).trim());
				
				String[] strArticles = line[4].split("; ");
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
		try (Connection connection = ConnectionPool.getConnection()){
			
			PreparedStatement preparedStatement = connection.prepareStatement(REQ_INSERT_ORDER);
			preparedStatement.setInt(1, data.getId());
			preparedStatement.setInt(2, data.getCustomer().getId());
			preparedStatement.setDate(3, new java.sql.Date(data.getOrderDate().getTime()));
			preparedStatement.setInt(4, 1);
			preparedStatement.setInt(5, 0);
			preparedStatement.executeUpdate();
			/*
			preparedStatement = connection.prepareStatement(REQ_INSERT_ARTICLE_ORDER);
			preparedStatement.setInt(1, data.getId());
			for (Article article : data.getListArticles()) {
				preparedStatement.setInt(2, article.getId());
				preparedStatement.setInt(3, article.getQuantity());
				preparedStatement.executeUpdate();
			}*/
		} catch (Exception e) {
			e.printStackTrace();
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