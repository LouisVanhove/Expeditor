package fr.lala.expeditor.services;

import java.util.List;

import fr.lala.expeditor.dao.OrderDao;
import fr.lala.expeditor.models.Order;

public class OrderService implements ICrudServices<Order> {

	/**
	 * Méthode en charge de fournir la prochaine commande à traiter 
	 * @return Order à traiter.
	 */
	public Order getNextOrder(){
		Order result = null ; 
		result = new OrderDao().selectNextOrder();
		
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

	@Override
	public List<Order> selectAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order selectById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}