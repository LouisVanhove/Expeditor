package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.dao.EmployeeDao;
import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.enums.Profile;
import fr.lala.expeditor.services.EmployeeService;
import fr.lala.expeditor.utils.HashageSalagePassword;

/**
 * Servlet implementation class Save
 */
public class SaveEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "ListEmployees";
	private EmployeeDao employeeDao = new EmployeeDao();
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
		// Si l'action est Enregistrer:
		if (request.getParameter("save") != null) {
			// Validation des valeurs du formulaire:
			String idEmploye = request.getParameter("id_employee");
			System.out.println(idEmploye);
			String enteredLogin = request.getParameter("txtboxLogin").trim();
			String enteredPassword = request.getParameter("txtboxPassword").trim();
			String enteredLastName = request.getParameter("txtboxLastName").trim();
			String enteredFirstName = request.getParameter("txtboxFirstName").trim();
			errors = validateFields(enteredLogin, enteredPassword, enteredLastName, enteredFirstName);
			// Si le Map d'erreurs est vide (donc champs valides)
			if (errors.isEmpty()) {
				// Si l'id est nul (donc employé inexistant en BDD), on le crée:
				if ("".equals(idEmploye)) {
					try {
						System.out.println("je rentre dans l'ajout");
						serviceE.insert(buildEmployee(request));
					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("message", "L'ajout de l'employé s'est déroulé avec succès");
					// Sinon, on modifie l'employé en BDD:
				} else {
					try {
						System.out.println("je rentre dans la modif");
						Employee employeeToModify = buildEmployee(request);
						employeeToModify.setId(Integer.parseInt(idEmploye));
						serviceE.update(employeeToModify);
						System.out.println(employeeToModify);
					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("message", "La modification de l'employé s'est déroulée avec succès");
				}
				response.sendRedirect(VIEW);
			} else {
				request.setAttribute("erreurs", errors);
				request.getRequestDispatcher("/WEB-INF/jsp/manager/formaddmodifyemployee.jsp").forward(request,response);
			}
			// Si l'action est Annuler:
		} else if (request.getParameter("cancel") != null) {
			// request.getRequestDispatcher(VIEW).forward(request,
			// response);
			response.sendRedirect(VIEW);
		}
	}

	/**
	 * Méthode de construction d'un objet Employé à partir des données du
	 * formulaire.
	 * 
	 * @param request
	 * @return
	 */
	private Employee buildEmployee(HttpServletRequest request) {
		Employee employee = new Employee();
		employee.setLogin(request.getParameter("txtboxLogin").trim());
		employee.setPassword(HashageSalagePassword.encryptPassword(request.getParameter("txtboxPassword").trim()));
		employee.setLastName(request.getParameter("txtboxLastName").trim());
		employee.setFirstName(request.getParameter("txtboxFirstName").trim());
		employee.setProfile(buildProfile(request));
		return employee;
	}

	/**
	 * Méthode en charge de récupérer le profil de l'employé
	 * 
	 * @param id_profile
	 * @return
	 */
	private Profile buildProfile(HttpServletRequest request) {
		Profile profile = Profile.values()[Integer.parseInt(request.getParameter("selectProfile")) - 1];
		return profile;
	}

	private Map<String, String> validateFields(String login, String password, String lastName, String firstName) {
		try {
			validateLogin(login);
		} catch (Exception e) {
			errors.put("Identifiant", e.getMessage());
		}
		try {
			validatePassword(password);
		} catch (Exception e) {
			errors.put("Password", e.getMessage());
		}
		try {
			validateLastName(lastName);
		} catch (Exception e) {
			errors.put("Nom", e.getMessage());
		}
		try {
			validateFirstName(firstName);
		} catch (Exception e) {
			errors.put("Prénom", e.getMessage());
		}
		return errors;

	}

	/**
	 * Valide l'identifiant saisi dans le formulaire.
	 * @param login
	 * @throws Exception
	 */
	private void validateLogin(String login) throws Exception {
		for (Employee employee : employeeDao.selectAll()) {
			if (login.equals(employee.getLogin())) {
				throw new Exception("Cet identifiant est déjà utilisé, merci d'en choisir un nouveau.");
			}
		} 
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
}
