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
import fr.lala.expeditor.models.enums.State;
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
		HttpSession session  = request.getSession(true);
		Order currentOrder = (Order) session.getAttribute("currentOrder") ;
		
		
		
		if( currentOrder != null){
			//Mise à jour de la commande qui vient d'être cloturée
			try {
				orderService.setProcessingDate(currentOrder);
				orderService.updateOrderState(currentOrder, State.PROCESSED);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//Purge de la commande.
			session.setAttribute("currentOrder", null);
		}
		
		
		
		//Récupération de la commande suivante.
		try {
			currentOrder=orderService.getNextOrder();
			Employee emp = (Employee)session.getAttribute("User");
			currentOrder.setEmployee(emp);
			orderService.setShippingClerk(currentOrder);
			orderService.updateOrderState(currentOrder, State.IN_PROGRESS);
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
		HttpSession session  = request.getSession(true);
		
		doGet(request, response);
	}

}
