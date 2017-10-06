package fr.lala.expeditor.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.enums.Profile;
import fr.lala.expeditor.services.EmployeeService;

/**
 * Servlet implementation class AddModifyEmployeeServlet
 */
public class AddModifyEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EmployeeService serviceE = new EmployeeService();
	private String view = "/WEB-INF/jsp/manager/formaddmodifyemployee.jsp";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddModifyEmployeeServlet() {
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
		Employee currentEmployee = new Employee();
		HttpSession session = request.getSession(true);
		
		//Test sur l'action voulue :
		// Si c'est de l'ajoût, on ouvre un formulaire vide. Employe = null.
		// Si c'est de la modification, on ouvre un formulaire pré-rempli avec les données de l'employé sélectionné par son id.
		if(request.getParameter("add") != null){
			currentEmployee = null;
		}else if (request.getParameter("modify") != null){
			//Appel au service Employee pour récupérer l'employé id = id
			int id = Integer.parseInt(request.getParameter("id_employee"));
			try {
				currentEmployee = serviceE.selectById(id);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		session.setAttribute("profiles", Profile.values());
		request.setAttribute("currentEmployee", currentEmployee);			
		request.getRequestDispatcher(view).forward(request, response);
	}
}
