package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.enums.Profile;
import fr.lala.expeditor.services.EmployeeService;
import fr.lala.expeditor.utils.HashageSalagePassword;

/**
 * Servlet implementation class Save
 */
public class SaveEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeService serviceE = new EmployeeService();
	private Map<String, String> errors = new HashMap<String, String>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveEmployeeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		// Si l'action est Enregistrer:
		if (request.getParameter("save") != null) {
			// Validation des valeurs du formulaire:
			String idEmploye = request.getParameter("id_employee");
			String enteredLogin = request.getParameter("txtboxLogin").trim();
			String enteredPassword = request.getParameter("txtboxPassword").trim();
			String enteredPasswordConfirm = request.getParameter("txtboxPasswordConfirm").trim();
			String enteredLastName = request.getParameter("txtboxLastName").trim();
			String enteredFirstName = request.getParameter("txtboxFirstName").trim();
			String comparePassword = request.getParameter("comparePassword");
			Employee employeeToSave = buildEmployee(request);
			request.setAttribute("currentEmployee", employeeToSave);
			
			errors = validateFields(enteredLogin, enteredPassword, enteredPasswordConfirm, enteredLastName, enteredFirstName);
			if ("".equals(idEmploye)) {
				validateLoginUnique(enteredLogin, (List<Employee>)session.getAttribute("listEmployees")); 
			}
			// Si le Map d'erreurs est vide (donc champs valides)
			if (errors.isEmpty()) {
				// Si l'id est vide (donc employé inexistant en BDD), on le crée:
				if ("".equals(idEmploye)) {
					//validateLoginUnique(enteredLogin, (List<Employee>)session.getAttribute("listEmployees")); 
					try {
						serviceE.insert(employeeToSave);
					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("message", "L'ajout de l'employé s'est déroulé avec succès");
					// Sinon, on modifie l'employé en BDD:
				} else {
					try {
						Employee employeeToModify = buildEmployee(request);
						if(!comparePassword.equals(employeeToModify.getPassword())){
							employeeToModify.setPassword(HashageSalagePassword.encryptPassword(employeeToModify.getPassword()));
						}
						employeeToModify.setId(Integer.parseInt(idEmploye));
						serviceE.update(employeeToModify);
					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("message", "La modification de l'employé s'est déroulée avec succès");
				}
				response.sendRedirect("ListEmployees");
			} else {
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("/WEB-INF/jsp/manager/formaddmodifyemployee.jsp").forward(request, response);;
			}			
		}
	}

	/**
	 * Méthode de construction d'un objet Employé à partir des données du formulaire.
	 * @param request
	 * @return
	 */
	private Employee buildEmployee(HttpServletRequest request) {
		Employee employee = new Employee();
		employee.setLogin(request.getParameter("txtboxLogin").trim());
		String mdp = request.getParameter("txtboxPassword");
		String confirm = request.getParameter("txtboxPasswordConfirm");
		System.out.println("mdp : "+mdp);
		System.out.println("confirm : "+confirm);
				
		if(!mdp.equals(confirm)){
			employee.setPassword("");
		}else{
			employee.setPassword(request.getParameter("txtboxPassword").trim());
		}
		
		employee.setLastName(request.getParameter("txtboxLastName").trim());
		employee.setFirstName(request.getParameter("txtboxFirstName").trim());
		employee.setProfile(buildProfile(request));
		return employee;
	}

	/**
	 * Méthode en charge de récupérer le profil de l'employé
	 * @param id_profile
	 * @return
	 */
	private Profile buildProfile(HttpServletRequest request) {
		Profile profile = Profile.values()[Integer.parseInt(request.getParameter("selectProfile")) - 1];
		return profile;
	}

	/**
	 * Méthode pour valider les champs identifiant, mot de passe, ot de passe confirm, nom et prenom.
	 * @param login
	 * @param password
	 * @param passwordConfirm
	 * @param lastName
	 * @param firstName
	 * @param enteredFirstName 
	 * @return
	 */
	private Map<String, String> validateFields(String login, String password, String passwordConfirm, String lastName, String firstName) {
		try {
			validateLogin(login);
		} catch (Exception e) {
			errors.put("login", e.getMessage());
		}
		try {
			validatePassword(password);
		} catch (Exception e) {
			errors.put("password", e.getMessage());
		}
		try{
			validatePasswordConfirm(password, passwordConfirm);
		}catch (Exception e){
			errors.put("passwordConfirm", e.getMessage());
		}
		try {
			validateLastName(lastName);
		} catch (Exception e) {
			errors.put("lastName", e.getMessage());
		}
		try {
			validateFirstName(firstName);
		} catch (Exception e) {
			errors.put("firstName", e.getMessage());
		}
		return errors;
	}

	/**
	 * Valide l'identifiant saisi dans le formulaire.
	 * @param login
	 * @throws Exception
	 */
	private void validateLogin(String login) throws Exception {
		if (login == null || login.trim().length() == 0) {
			throw new Exception("Merci de saisir un identifiant.");
		} else if(login.trim().length()<1 || login.trim().length()>30){
			throw new Exception("L'identifiant doit être compris entre 1 et 30 caractères");
		} else if(login.split(" ").length != 1){
			throw new Exception("L'identifiant ne doit pas comporter d'espace");
		}
	}

	/**
	 * Valide le mot de passe saisi dans le formulaire.
	 */
	private void validatePassword(String password) throws Exception {
		if (password != null && password.trim().length() != 0) {
			if (password.trim().length() < 6 || password.trim().length() > 100) {
				throw new Exception("Le mot de passe doit être compris entre 6 et 100 caractères.");
			}
		} else if (password == null || password.trim().length() == 0) {
			throw new Exception("Merci de saisir un mot de passe.");
		}
	}
	
	/**
	 * Valide que le 2eme mot de passe est égal au 1er
	 * @param passwordConfirm
	 * @throws Exception 
	 */
	private void validatePasswordConfirm(String password, String passwordConfirm) throws Exception {
		if(!passwordConfirm.equals(password)){
			System.out.println("erreur confirm mdp");
			throw new Exception("Le mot de passe est différent.");
		}		
	}
	
	/**
	 * Valide le prénom saisi dans le formulaire.
	 */
	private void validateFirstName(String firstName) throws Exception {
		if (firstName == null || firstName.trim().length() == 0) {
			throw new Exception("Merci de saisir un prénom.");
		} else if(firstName.trim().length()<1 || firstName.trim().length()>100){
			throw new Exception("Le prénom doit être compris entre 1 et 100 caractères");
		}
	}

	private void validateLastName(String lastName) throws Exception {
		if (lastName == null || lastName.trim().length() == 0) {
			throw new Exception("Merci de saisir un nom.");
		} else if(lastName.trim().length()<1 || lastName.trim().length()>100){
			throw new Exception("Le nom doit être compris entre 1 et 100 caractères");
		}
	}
	
	/**
	 * Valide l'unicité de l'identifiant pour la création d'un nouvel employé.
	 * @param enteredLogin
	 * @throws SQLException
	 */
	private Map<String, String> validateLoginUnique(String enteredLogin, List<Employee> listEmployees) {
			try{
				for(Employee employee : listEmployees){
					if (enteredLogin.equals(employee.getLogin())) {
						throw new Exception("Cet identifiant existe déjà");
					}
				}
			}catch (Exception e){
				errors.put("login", "Cet identifiant existe déjà");
			}
			return errors;
	}
}
