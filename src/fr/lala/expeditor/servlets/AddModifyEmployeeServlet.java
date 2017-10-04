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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Employee employee = null;
		//Test sur l'action voulue :
		// Si c'est de l'ajoût, on ouvre un formulaire vide
		if(request.getParameter("ajouter") != null){
			request.setAttribute("action", "AJOUT");
			
		// Si c'est de la modification, on ouvre un formulaire pré-rempli avec les données de l'employé sélectionné
		}else if (request.getParameter("modifier") != null){
			request.setAttribute("action", "MODIFICATION");
			//Appel au service Employee pour récupérer l'employé id = id
			int id = Integer.parseInt(request.getParameter("id_employee"));
			request.setAttribute("id", id);
			try {
				employee = serviceE.selectById(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		//Mettre l'employé dans la request
		request.setAttribute("employee", employee);
		//forwarder
		request.getRequestDispatcher("/WEB-INF/jsp/manager/formaddmodifyemployee.jsp").forward(request, response);
	}

}
