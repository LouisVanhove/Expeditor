package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.lala.expeditor.dao.CustomerDao;
import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.Order;
import fr.lala.expeditor.services.OrderService;

/**
 * Servlet implementation class SwitchToNextOrderServlet
 */
public class SwitchToNextOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = new OrderService();   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SwitchToNextOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("NextOrderServlet#doGet");
		HttpSession session  = request.getSession(true);
		Order currentOrder = null ;
		//Purge de la commande précédente : 
		if(session.getAttribute("currentOrder") != null){
			session.setAttribute("currentOrder", null);
		}
		
		
		try {
			currentOrder=orderService.getNextOrder();
			Employee emp = (Employee)session.getAttribute("User");
			System.out.println(emp);
			currentOrder.setEmployee(emp);
			System.out.println("Positionnement de l'employé en charge du colis");
			System.out.println("Id commande : "+currentOrder.getId());
			System.out.println("Id employé : "+currentOrder.getEmployee().getId());
			orderService.setShippingClerk(currentOrder);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("currentOrder", currentOrder);
		request.getRequestDispatcher("/WEB-INF/jsp/employee/commande.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("NextOrderServlet#doPost");
		HttpSession session  = request.getSession(true);
		try {
			System.out.println("Positionnement de la date de traitement.");
			orderService.setProcessingDate((Order)session.getAttribute("currentOrder"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
