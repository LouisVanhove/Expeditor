package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.services.EmployeeService;

/**
 * Servlet implementation class ListEmployeesServlet
 */
public class ListEmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EmployeeService serviceE = new EmployeeService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListEmployeesServlet() {
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

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		List<Employee> listEmployees = new ArrayList<>();
		
		try{
			// Récupération de la liste des employées
			listEmployees = serviceE.selectAll();
			session.setAttribute("employees", listEmployees);
		} catch (Exception e){
			request.setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/jsp/manager/employees.jsp").forward(request, response);
	}	
}
