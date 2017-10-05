package fr.lala.expeditor.models.enums;

/**
 * Enumération répertoriant les différents profils
 * pour un employé: manager ou préparateur de commandes.
 * @author adelaune2017
 *
 */
public enum Profile {
	MANAGER(1, "Manager"),
	SHIPPING_CLERK(2, "Préparateur de Commandes");
	
	private int id;
	private String label="";
	
	Profile(int id, String label){
		this.id = id;
		this.label = label;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String toString(){
		return this.label;
	}
}
