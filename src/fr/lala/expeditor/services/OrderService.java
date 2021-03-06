package fr.lala.expeditor.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import fr.lala.expeditor.dao.ArticleDao;
import fr.lala.expeditor.dao.CustomerDao;
import fr.lala.expeditor.dao.OrderDao;
import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Order;
import fr.lala.expeditor.models.enums.State;

/**
 * Service g�rant les commandes.
 * @author lajzenberg2017
 *
 */
public class OrderService implements ICrudServices<Order> {
	
	private OrderDao orderdao = new OrderDao();
	private List<Order> _orderList;
	
	/**
	 * M�thode permettant de g�rer l'importation des commandes.
	 * @param value
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void importFromFile(String value) throws IOException, SQLException {
		List<Order> orders = OrderDao.importFromFile(value);
        for (Order order : orders) {
        		order.getCustomer().setId(new CustomerDao().insert(order.getCustomer()));
        		for(Article article : order.getListArticles()) {
        			article.setId(new ArticleDao().selectIdByLabel(article.getLabel()));
        		}
        		new OrderDao().insert(order);
        }
	}
	
	/**
	 * M�thode en charge de fournir la prochaine commande � traiter 
	 * @return Order � traiter.
	 */
	public Order getNextOrder(){
		Order result = null ; 
		result = new OrderDao().selectNextOrder();
		result.setListArticles(new ArticleService().selectAllByOrder(result.getId()));
		return result;
	}

	@Override
	public void insert(Order data) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Order data) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Order data) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Selection de la liste de toutes les commandes � traiter.
	 */
	@Override
	public List<Order> selectAll() throws Exception {
		try {
			_orderList = orderdao.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _orderList;
	}

	@Override
	public Order selectById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setShippingClerk(Order data) throws SQLException{
		new OrderDao().setShippingClerk(data, data.getEmployee());	
	}
	
	public void setProcessingDate(Order data) throws SQLException{
		new OrderDao().setProcessingDate(data);
	}

	public void updateOrderState(Order data, State state) throws SQLException {
		new OrderDao().updateOrderState(data, state);
	}
	public void resetOrder(Order data) throws SQLException {
		new OrderDao().resetOrder(data);
	}
	
}

