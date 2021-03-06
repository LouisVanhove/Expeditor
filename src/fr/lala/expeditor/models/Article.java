package fr.lala.expeditor.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant l'entité Article.
 * @author adelaune2017
 *
 */
public class Article implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4620376540328564699L;
	
	private int id;
	private String label;
	private String description;
	private int weight;
	private int quantity;
	private boolean archived;
	//private List<Order> listOrders = new ArrayList<>();

	/**
	 * Constructeur par d�faut.
	 */
	public Article() {
		super();
	}
	
	/**
	 * Constructeur.
	 * @param id
	 * @param label
	 * @param description
	 * @param weight
	 */
	public Article(int id, String label, String description, int weight, int quantity) {
		super();
		this.setId(id);
		this.setLabel(label);
		this.setDescription(description);
		this.setWeight(weight);
		this.setQuantity(quantity);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

/*	public List<Order> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<Order> listOrders) {
		this.listOrders = listOrders;
	}*/

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Article [id=").append(id).append(", désignation=").append(label).append(", description=")
				.append(description).append(", poids=").append(weight)
				.append(", quantité=").append(quantity).append("]");
		return builder.toString();
	}
}
