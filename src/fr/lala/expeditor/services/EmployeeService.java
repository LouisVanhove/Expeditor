package fr.lala.expeditor.services;

import fr.lala.expeditor.dao.EmployeeDao;

/**
 * Classe Service de Employé
 * @author aurelie.lardeux2017
 *
 */
public class EmployeeService {
	
	EmployeeDao employeedao = new EmployeeDao();
	
	/**
	 * Selection d'un employee en fonction de son login et mot de passe.
	 * @param login
	 * @param password
	 * @return
	 */
	public Employee selectById(String login, String password){
		
		Employee employee = null;
		try {
			employee = employeedao.selectById(login, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

}
