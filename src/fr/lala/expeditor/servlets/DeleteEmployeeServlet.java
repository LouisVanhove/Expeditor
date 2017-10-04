package fr.lala.expeditor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.services.EmployeeService;

/**
 * Servlet implementation class DeleteEmployeeServlet
 */
public class DeleteEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EmployeeService serviceE = new EmployeeService();   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEmployeeServlet() {
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
		int identifiant = Integer.parseInt(request.getParameter("id_employee"));
		//request.setAttribute("id", identifiant);
		
		try {
			Employee employee = serviceE.selectById(identifiant);
			serviceE.delete(employee);
			request.setAttribute("id", employee);
			
			request.setAttribute("action", "DELETE");
			request.setAttribute("message", "La suppression s'est d�roul�e avec succ�s.");
		} catch (Exception e) {
			request.setAttribute("erreur", "La suppression a �chou�.");
			e.printStackTrace();
		}
		// Rappel de la servlet ListEmployees
		request.getRequestDispatcher("/manager/ListEmployees").forward(request, response);	
	}

}
