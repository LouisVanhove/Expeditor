package fr.lala.expeditor.models.enums;

/**
 * Enum�ration r�pertoriant les diff�rents �tats
 * pour une commande : 
 * - en attente, 
 * - en cours de traitement, 
 * - trait�e.
 * @author adelaune2017
 *
 */
public enum State {
	PENDING(1, "En Attente"),
	IN_PROGRESS(2, "En cours de traitement"),
	PROCESSED(3, "Trait�");
	
	private int id;
	private String label;
	
	State(int id, String label){
		this.id=id;
		this.label=label;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String toString(){
		return this.label;
	}
}
