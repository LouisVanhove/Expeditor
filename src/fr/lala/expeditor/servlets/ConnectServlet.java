package fr.lala.expeditor.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.services.EmployeeService;

/**
 * Servlet implementation class ConnectServlet
 */
public class ConnectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher repartiteur = null;
	private String redirection =null;
	public static final String VUE          = "/WEB-INF/jsp/connection.jsp";
	public static final String CHAMP_LOGIN  = "login";
    public static final String CHAMP_PASS   = "password";
         
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			repartiteur=getServletContext().getRequestDispatcher(VUE);
	        if (repartiteur!=null)
	        repartiteur.forward(request, response);					
		} 	catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		String login = request.getParameter(CHAMP_LOGIN);
		String password = request.getParameter(CHAMP_PASS);
		
		
		//Creation et initialisation à null d'un objet user de type Personne :
		Employee user = null;
		EmployeeService employeeservice = new EmployeeService();
		
			try {
				validateLogin(login);
				validatePassword(password);
				user = employeeservice.selectById(login, password); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			request.getSession().setAttribute("User", user);
		
			if(user instanceof Manager){ 
				redirection = "/WEB-INF/manager/suivicommande.jsp";	
			}
			else if(user instanceof Employee){
				redirection = "/WEB-INF/jsp/employee/commande.jsp";	
			} else {
				request.getSession().setAttribute("message", " Ce compte n'existe pas");
				redirection = "/index.jsp";
			}
			
			repartiteur = getServletContext().getRequestDispatcher(redirection);
			repartiteur.forward(request, response);
	}


	/**
	 * Valide le login saisi
	 * @param login
	 * @throws Exception
	 */
	private void validateLogin(String login) throws Exception {
		if ( login != null && login.trim().length() != 0 ) {
	        throw new Exception( "Merci de saisir votre identifiant." );
	    }
	}
	
	/**
	 * Valide le mot de passe saisi.
	 */
	private void validatePassword(String password) throws Exception{
	    if (password != null && password.trim().length() != 0) { 
	    	if (password.trim().length() < 3) {
	            throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
	        }
	    } else {
	        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
	    }
	}

}
