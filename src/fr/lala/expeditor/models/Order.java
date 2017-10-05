package fr.lala.expeditor.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.lala.expeditor.models.enums.State;

/**
 * Classe représentant l'entité Commande
 * @author adelaune2017
 *
 */
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -498660367919497394L;
	
	private int id;
	private Date orderDate;
	private Date processingDate;
	private Employee employee;
	private Customer customer;
	private State state;
	private boolean archived;
	private List<Article> listArticles = new ArrayList<>();
	
	/**
	 * Constructeur par défaut.
	 */
	public Order() {
		super();
	}

	/**
	 * Constructeur.
	 * @param id
	 * @param orderDate
	 * @param processingDate
	 * @param employee
	 * @param customer
	 * @param state
	 */
	public Order(int id, Date orderDate, Date processingDate, Employee employee, Customer customer, State state) {
		super();
		this.setId(id);
		this.setOrderDate(orderDate);
		this.setProcessingDate(processingDate);
		this.setEmployee(employee);
		this.setCustomer(customer);
		this.setState(state);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(Date processingDate) {
		this.processingDate = processingDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public List<Article> getListArticles() {
		return listArticles;
	}

	public void setListArticles(List<Article> listArticles) {
		this.listArticles = listArticles;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=").append(id).append(", orderDate=").append(orderDate).append(", processingDate=")
				.append(processingDate).append(", employee=").append(employee).append(", customer=").append(customer)
				.append(", state=").append(state).append("]");
		return builder.toString();
	}


}
