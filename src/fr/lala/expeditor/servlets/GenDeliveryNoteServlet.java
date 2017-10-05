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
		Order currentOrder = null ;
		
		if(request.getSession().getAttribute("currentOrder") instanceof Order){
			currentOrder = (Order)request.getSession().getAttribute("currentOrder");
		};
		
		String realPath = request.getSession().getServletContext().getRealPath("/")+"pdf/" ;
		String nomFichier = null ;
		request.setAttribute("order", currentOrder);
		try {
			nomFichier = PDFUtils.createDeliveryNote(currentOrder,realPath );
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		System.out.println(nomFichier);
		if(nomFichier != null){
			String path = "/pdf/"+nomFichier;
			request.setAttribute("path", request.getContextPath()+"/pdf/"+nomFichier);
			request.getRequestDispatcher(path).forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
