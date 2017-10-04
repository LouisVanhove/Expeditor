package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Customer;
import fr.lala.expeditor.models.Order;
import fr.lala.expeditor.utils.PDFUtils;

/**
 * Servlet implementation class GenDeliveryNoteServlet
 */
public class GenDeliveryNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenDeliveryNoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Customer customer = new Customer();
		customer.setName("Aurélia Delauné");
		customer.setAddress("Palais de l'Elysée");
		customer.setZipCode("85000");
		customer.setCity("Baton Rouge");
		List<Article> articles = new ArrayList<>();
		for(int i=0; i <10; i++){
			Article a = new Article();
			a.setId(12);
			a.setLabel("Article n°"+i);
			a.setWeight(i*i);
			
			articles.add(a);
		}
		
		Order order = new Order();
		order.setId(8770);
		order.setOrderDate(new Date());
		order.setListArticles(articles);
		order.setCustomer(customer);
		String path = null ;
		String realPath = request.getSession().getServletContext().getRealPath("/")+"pdf/" ;
		System.out.println(realPath);
		String nomFichier = null ;
		request.setAttribute("order", order);
		try {
			nomFichier = PDFUtils.createDeliveryNote(order,realPath );
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(nomFichier != null){
			
			//path = "file://"+path;
			System.out.println("Nom du fichier : " + path);
			request.setAttribute("path", request.getContextPath()+"/pdf/"+nomFichier);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/bon_livraison.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
