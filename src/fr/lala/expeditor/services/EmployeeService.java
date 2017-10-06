package fr.lala.expeditor.services;

import java.util.List;

import fr.lala.expeditor.dao.EmployeeDao;
import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.utils.HashageSalagePassword;

/**
 * Classe Service de Employ�
 * @author aurelie.lardeux2017
 *
 */
public class EmployeeService implements ICrudServices<Employee>{
	
	EmployeeDao employeedao = new EmployeeDao();
	
	/**
	 * Selection d'un employee en fonction de son login et mot de passe.
	 * @param login
	 * @param password
	 * @return
	 */
	public Employee selectByLogin(String login, String password){
		
		Employee employee = null;
		try {
			String passwordCrypte = HashageSalagePassword.encryptPassword(password);
			employee = employeedao.selectByLogin(login, passwordCrypte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;		
	}

	/**
	 * M�thode pour ajouter un employ� dans la BDD.
	 */
	@Override
	public void insert(Employee data) throws Exception {
		employeedao.insert(data);		
	}

	/**
	 * M�thode pour modifier un employ� dans la BDD.
	 */
	@Override
	public void update(Employee data) throws Exception {
		employeedao.update(data);
	}

	/**
	 * M�thode pour archiver un employ� dans la BDD.
	 */
	@Override
	public void delete(Employee data) throws Exception {
		employeedao.delete(data);
	}

	/**
	 * M�thode pour afficher l'ensemble des employ�s non archiv�s.
	 */
	@Override
	public List<Employee> selectAll() throws Exception {
		List<Employee> result = employeedao.selectAll();
		return result;
	}
	
	/**
	 * M�thode pour afficher l'ensemble des employ�s non archiv�s et archiv�s.
	 */
	public List<Employee> selectTotalEmployees() throws Exception {
		List<Employee> result = employeedao.selectTotalEmployees();
		return result;
	}
	
	/**
	 * M�thode pour afficher l'ensemble des employ�s non archiv�s.
	 * avec leur nombre de commandes trait�es � la date du jour.
	 */
	public List<Employee> selectAllEmployeProcessOrder() throws Exception {
		List<Employee> result = employeedao.selectAllEmployeProcessOrder();
		System.out.println("service : " + result);
		return result;
	}

	/**
	 * M�thode pour s�lectionner un employ� donn�.
	 */
	@Override
	public Employee selectById(int id) throws Exception {
		Employee result = employeedao.selectById(id);
		return result;
	}

	/**
	 * Valide l'identifiant saisi dans le formulaire.
	 * @param login
	 * @throws Exception
	 */
	private boolean validateLogin(String login) throws Exception {
		boolean isValid = false;
		
		for (Employee employee : employeedao.selectAll()) {
			if (login.equals(employee.getLogin())) {
				throw new Exception(String.format("Cet identifiant est d�j� utilis�, merci d'en choisir un nouveau."));
			} 
		}
		if (login.trim().length() == 0 ) {
		    throw new Exception( "Merci de saisir un identifiant." );
	    } else {
	    	isValid = true;
	    }
		return isValid;
	}
	
	/**
	 * Valide le mot de passe saisi dans le formulaire.
	 */
	private boolean validatePassword(String password) throws Exception{
		boolean isValid = false;
	    if (password != null && password.trim().length() != 0) { 
	    	if (password.trim().length() < 6 || password.trim().length() > 100) {
	            throw new Exception("Le mot de passe doit �tre compris entre 6 et 100 caract�res.");
	        }
	    } else if(password == null || password.trim().length() == 0){
	        throw new Exception("Merci de saisir un mot de passe.");
	    } else {
	    	isValid = true;
	    }
		return isValid;
	}
	

	private boolean validateFirstName(String firstName) throws Exception {
		boolean isValid = false;		

		if (firstName.trim().length() == 0 ) {
		    throw new Exception( "Merci de saisir un pr�nom." );
	    } else {
	    	isValid = true;
	    }
		return isValid;
	}

	private boolean validateLastName(String lastName) throws Exception {
		boolean isValid = false;

		if (lastName.trim().length() == 0 ) {
		    throw new Exception( "Merci de saisir un nom." );
	    } else {
	    	isValid = true;
	    }
		return isValid;
	}
}
