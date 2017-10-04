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
			employee = employeedao.selectByLogin(login, password);
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
	 * M�thode pour s�lectionner un employ� donn�.
	 */
	@Override
	public Employee selectById(int id) throws Exception {
		Employee result = employeedao.selectById(id);
		return result;
	}

}
