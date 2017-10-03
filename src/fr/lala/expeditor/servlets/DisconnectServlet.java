package fr.lala.expeditor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DisconnectServlet
 */
public class DisconnectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String redirection =null; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisconnectServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try { 
			/* Recuperation et destruction de la session en cours */
	        HttpSession session = request.getSession();
	        session.invalidate();
	        redirection =  "/connexion";		
		} catch(Exception e){	
		      e.printStackTrace();
		}
		/* Affichage de la page de connexion */
	   this.getServletContext().getRequestDispatcher(redirection).forward( request, response );	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
