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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("delete") != null) {
			int identifiant = Integer.parseInt(request.getParameter("id_employee"));
			try {
				Employee employeeToArchive = serviceE.selectById(identifiant);
				serviceE.delete(employeeToArchive);
				System.out.println(employeeToArchive);
				request.setAttribute("message", "La suppression s'est déroulée avec succès.");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				request.setAttribute("erreur", "La suppression a échoué.");
				e.printStackTrace();
			}
		}
		// Rappel de la servlet ListEmployees
		response.sendRedirect(request.getContextPath() + "/manager/ListEmployees");
		//request.getRequestDispatcher("/manager/ListEmployees").forward(request, response);
	}
}
