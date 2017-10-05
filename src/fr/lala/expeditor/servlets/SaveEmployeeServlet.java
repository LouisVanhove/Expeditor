package fr.lala.expeditor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.enums.Profile;
import fr.lala.expeditor.services.EmployeeService;

/**
 * Servlet implementation class Save
 */
public class SaveEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "ListEmployees";
    private EmployeeService serviceE = new EmployeeService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveEmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// Si l'action est Enregistrer:
		if(request.getParameter("save") != null){
			String idEmployee = request.getParameter("id_employee");
			// Si l'id est nul (donc employé inexistant en BDD), on le crée:
			if("".equals(idEmployee)){
				try {
					serviceE.insert(buildEmployee(request));
				} catch (Exception e) {
					e.printStackTrace();
				}
			// Sinon, on modifie l'employé en BDD:
			} else {
				try {
					Employee employeeToModify = buildEmployee(request);
					employeeToModify.setId(Integer.parseInt(idEmployee));
					serviceE.update(employeeToModify);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//request.getRequestDispatcher(VIEW).forward(request, response);
			response.sendRedirect(VIEW);
			
		// Si l'action est Annuler:
		} else if (request.getParameter("cancel")!= null){
			//request.getRequestDispatcher(VIEW).forward(request, response);
			response.sendRedirect(VIEW);
		}
	}

	/**
	 * Méthode de construction d'un objet Employé à partir
	 * des données du formulaire.
	 * @param request
	 * @return
	 */
	private Employee buildEmployee(HttpServletRequest request) {
		Employee employee = new Employee();
		employee.setLogin(request.getParameter("txtboxLogin").trim());
		employee.setPassword(request.getParameter("txtboxPassword").trim());
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
		Profile profile = Profile.values()[Integer.parseInt(request.getParameter("selectProfile"))-1];
		/*
		if("Manager".equals(request.getParameter("selectProfile"))){
			 profile = Profile.MANAGER;
		}
		else if ("Préparateur de Commandes".equals(request.getParameter("selectProfile"))){
			profile = Profile.SHIPPING_CLERK;
		}*/
		return profile;
	}
}
