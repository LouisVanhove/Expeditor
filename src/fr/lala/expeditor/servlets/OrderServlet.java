package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.Order;
import fr.lala.expeditor.services.EmployeeService;
import fr.lala.expeditor.services.OrderService;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher = null;
	private EmployeeService employeeservice = new EmployeeService();
	private OrderService orderService = new OrderService();
	private List<Order> orderList;
	private List<Employee> employeeList;
	public static final String VIEW         = "/WEB-INF/jsp/manager/suivicommande.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			
		try {
				employeeList = employeeservice.selectAllEmployeProcessOrder();
				orderList = orderService.selectAll();
				System.out.println(orderList);
				System.out.println(employeeList);
				request.setAttribute("orderList", orderList);
				request.setAttribute("employeeList", employeeList);
				dispatcher=getServletContext().getRequestDispatcher(VIEW);
				if (dispatcher!=null)
					dispatcher.forward(request, response);					
			} 	catch (Exception e) {
				e.printStackTrace();
			}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
