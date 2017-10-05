package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Customer;
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
		// TODO Auto-generated method stub
		HttpSession session  = request.getSession(true);
		
		//Purge de la commande précédente : 
		if(session.getAttribute("currentOrder") != null){
			session.setAttribute("currentOrder", null);
		}
		
		//Order currentOrder=new OrderService().getNextOrder();
		
		//DEBUT BOUCHON
		Customer customer = new Customer();
		customer.setName("Aurélia Delauné");
		customer.setAddress("Palais de l'Elysée");
		customer.setZipCode("85000");
		customer.setCity("Baton Rouge");
		
		List<Article> articles = new ArrayList<>();
		for(int i=0; i <10; i++){
			Article a = new Article();
			a.setId(12+i);
			a.setLabel("Article n°"+i);
			a.setWeight(i*i);
			a.setQuantity(i+2);
			articles.add(a);
		}
		
		Order currentOrder = new Order();
		currentOrder.setId(8770);
		currentOrder.setOrderDate(new Date());
		currentOrder.setListArticles(articles);
		currentOrder.setCustomer(customer);
		//Fin BOUCHON
		
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
