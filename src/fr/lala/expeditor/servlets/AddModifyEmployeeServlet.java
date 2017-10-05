package fr.lala.expeditor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Employee;
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
		//Test sur l'action voulue :
		// Si c'est de l'ajoût, on ouvre un formulaire vide
		if(request.getParameter("add") != null){
			request.setAttribute("currentEmployee", null);			
		// Si c'est de la modification, on ouvre un formulaire pré-rempli avec les données de l'employé sélectionné
		}else if (request.getParameter("modify") != null){
			//Appel au service Employee pour récupérer l'employé id = id
			int id = Integer.parseInt(request.getParameter("id_employee"));
			try {
				Employee currentEmployee = serviceE.selectById(id);
				request.setAttribute("currentEmployee", currentEmployee);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		request.getRequestDispatcher(view).forward(request, response);
	}

}
