package fr.lala.expeditor.services;

import java.util.List;

import fr.lala.expeditor.dao.EmployeeDao;
import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.utils.HashageSalagePassword;

/**
 * Classe Service de Employé
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
	 * Méthode pour ajouter un employé dans la BDD.
	 */
	@Override
	public void insert(Employee data) throws Exception {
		for (Employee employee : employeedao.selectAll()) {
			if (data.getLogin().equals(employee.getLogin()) && 
				data.getLastName().equals(employee.getLastName()) &&
				data.getFirstName().equals(employee.getFirstName())) {
				throw new Exception(String.format("L'employé {0} existe déjà.", data));
			} 
		}
		employeedao.insert(data);		
	}

	/**
	 * Méthode pour modifier un employé dans la BDD.
	 */
	@Override
	public void update(Employee data) throws Exception {
		employeedao.update(data);
	}

	/**
	 * Méthode pour archiver un employé dans la BDD.
	 */
	@Override
	public void delete(Employee data) throws Exception {
		employeedao.delete(data);
	}

	/**
	 * Méthode pour afficher l'ensemble des employés non archivés.
	 */
	@Override
	public List<Employee> selectAll() throws Exception {
		List<Employee> result = employeedao.selectAll();
		return result;
	}

	/**
	 * Méthode pour sélectionner un employé donné.
	 */
	@Override
	public Employee selectById(int id) throws Exception {
		Employee result = employeedao.selectById(id);
		return result;
	}

}
