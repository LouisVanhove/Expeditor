package fr.lala.expeditor.models;

import java.io.Serializable;

import fr.lala.expeditor.models.enums.Profile;

/**
 * Classe repr�sentant l'entit� Employ�.
 * @author adelaune2017
 *
 */
public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3715019386031945031L;

	private int id;
	private String login;
	private String password;
	private String lastName;
	private String firstName;
	private Profile profile;
	private boolean archived;
	
	/**
	 * Constructeur par d�faut.
	 */
	public Employee() {
		super();
	}

	/**
	 * Constructeur.
	 * @param id
	 * @param login
	 * @param password
	 * @param lastName
	 * @param firstName
	 */
	public Employee(int id, String login, String password, String lastName, String firstName, Profile profile) {
		super();
		this.setId(id);
		this.setLogin(login);
		this.setPassword(password);
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setProfile(profile);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Employee [id=").append(id).append(", login=").append(login).append(", password=")
				.append(password).append(", lastName=").append(lastName).append(", firstName=").append(firstName)
				.append(", profile=").append(profile).append("]");
		return builder.toString();
	}	
}