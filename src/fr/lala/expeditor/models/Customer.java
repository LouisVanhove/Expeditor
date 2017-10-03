package fr.lala.expeditor.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant l'entité Client
 * @author adelaune2017
 *
 */
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4597833943589029830L;

	private int id;
	private String name;
	private String zipCode;
	private String city;
	private boolean archived;
	private List<Order> listOrders = new ArrayList<>();
	
	/**
	 * Constructeur par défaut.
	 */
	public Customer() {
		super();
	}

	/**
	 * Constructeur.
	 * @param id
	 * @param name
	 * @param zipCode
	 * @param city
	 */
	public Customer(int id, String name, String zipCode, String city) {
		super();
		this.setId(id);
		this.setName(name);
		this.setZipCode(zipCode);
		this.setCity(city);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public List<Order> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<Order> listOrders) {
		this.listOrders = listOrders;
	}	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [id=").append(id).append(", name=").append(name).append(", zipCode=").append(zipCode)
				.append(", city=").append(city).append("]");
		return builder.toString();
	}
}
