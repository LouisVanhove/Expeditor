package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.services.EmployeeService;

/**
 * Servlet implementation class ManageEmployeesServlet
 */
public class ManageEmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String view;
    private EmployeeService serviceE = new EmployeeService();   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageEmployeesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		listEmployees(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;

		// Si l'action est "Modifier" ou "Ajouter", on ouvre le formulaire d'ajout/modif d'un employ�.
		if (request.getParameter("modifier")!= null || request.getParameter("ajouter")!= null) {
			try {
				openFormAddModifEmployee(request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		// Si l'action est "Supprimer", on archive l'employ�.
		} else if (request.getParameter("supprimer")!= null) {
			try {
				deleteEmployee(request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		// Si l'action est "Enregistrer", on sauve en BDD les ajouts/modif.
		} else if (request.getParameter("enregistrer")!= null) {
			try {
				// R�cup�ration des informations saisies dans le formulaire
				String id = request.getParameter("id_employee");

				// Si id inexistant, enregistrement d'un nouvel employ�:
				if (id.equals("")) {
					try {
						addEmployee(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				// Sinon, modification de l'employ� courant:
				} else {
					try {
						modifyEmployee(request, response);	
					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("message", "La modification s'est d�roul�e avec succ�s");
				}
			} catch (NumberFormatException nfe) {
				request.setAttribute("error", nfe.getMessage());
				view = "/WEB-INF/erreur.jsp";
			}
		}
		dispatcher = getServletContext().getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}


	/**
	 * M�thode pour s�lectionner l'ensemble des employ�s actifs (non archiv�s).
	 * @param request
	 * @param response
	 */
	private void listEmployees(HttpServletRequest request, HttpServletResponse response) {
		List<Employee> listEmployees = new ArrayList<>();
		
		try{
			listEmployees = serviceE.selectAll();
			request.setAttribute("listEmployees", listEmployees);
			request.getRequestDispatcher("/WEB-INF/jsp/manager/employees.jsp").forward(request, response);
		} catch (Exception e){
			request.setAttribute("error", e.getMessage());
		}
	}
	
	private void openFormAddModifEmployee(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * M�thode en charge de modifier l'employ� s�lectionn�.
	 * @param request
	 * @param response
	 */
	private void modifyEmployee(HttpServletRequest request, HttpServletResponse response) {
		
	}

	private void addEmployee(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * M�thode en charge d'archiver l'employ� s�lectionn�.
	 * @param request
	 * @param response
	 */
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) {
		try {
			int identifiant = Integer.parseInt(request.getParameter("id"));
			Employee employee = new EmployeeService().selectById(identifiant);
			this.serviceE.delete(employee);
			request.setAttribute("message", "La suppression s'est d�roul�e avec succ�s.");
		} catch (Exception e) {
			request.setAttribute("erreur", "La suppression a �chou�.");
			e.printStackTrace();
		}

		// Rappel de la m�thode affichant la liste pour actualisation.
		this.listEmployees(request, response);		
	}


}
