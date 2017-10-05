package fr.lala.expeditor.models.enums;

/**
 * Enum�ration r�pertoriant les diff�rents profils
 * pour un employ�: manager ou pr�parateur de commandes.
 * @author adelaune2017
 *
 */
public enum Profile {
	MANAGER(1, "Manager"),
	SHIPPING_CLERK(2, "Pr�parateur de Commandes");
	
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
