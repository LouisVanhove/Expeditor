package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.lala.expeditor.dao.CustomerDao;
import fr.lala.expeditor.models.Order;
import fr.lala.expeditor.services.OrderService;

/**
 * Servlet implementation class SwitchToNextOrderServlet
 */
public class SwitchToNextOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		
		//Purge de la commande précédente : 
		if(session.getAttribute("currentOrder") != null){
			session.setAttribute("currentOrder", null);
		}
		
		try {
			System.out.println(new CustomerDao().selectById(2481));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Order currentOrder=new OrderService().getNextOrder();
		session.setAttribute("currentOrder", currentOrder);
		request.getRequestDispatcher("/WEB-INF/jsp/employee/commande.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
