package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Employee;
import fr.lala.expeditor.models.enums.Profile;
import fr.lala.expeditor.services.EmployeeService;

/**
 * Servlet implementation class ConnectServlet
 */
public class ConnectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher = null;
	private String redirection =null;
	public static final String VIEW         = "/WEB-INF/jsp/connection.jsp";
	public static final String FIELD_LOGIN  = "login";
    public static final String FIELD_PASS   = "password";
         
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
		if(request.getAttribute("message")==null){
				dispatcher=getServletContext().getRequestDispatcher(VIEW);
				if (dispatcher!=null)
					dispatcher.forward(request, response);		
		} else {
			
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		String login = request.getParameter(FIELD_LOGIN);
		String password = request.getParameter(FIELD_PASS);
		
		Map<String, String> erreurs = new HashMap<String, String>();
		
		//Creation et initialisation à null d'un objet user de type Employee :
		Employee user = null;
		EmployeeService employeeservice = new EmployeeService();
		
			try {
				validateLogin(login);
			} catch (Exception e) {
				erreurs.put(FIELD_LOGIN, e.getMessage());
			}
			
			try{
				validatePassword(password);
			} catch (Exception e) {
				erreurs.put(FIELD_PASS, e.getMessage());
			}
			
			/* Initialisation du résultat global de la validation. */
			if (erreurs.isEmpty()) {
			user = employeeservice.selectByLogin(login, password); 
			}
	
			if(user!=null){
					request.getSession().setAttribute("User", user);
				
					if(user.getProfile()==Profile.MANAGER){ 
						redirection = "/WEB-INF/jsp/manager/suivicommande.jsp";	
					}
					else if(user.getProfile()==Profile.SHIPPING_CLERK){
						redirection = "/employee/Commande";	
					} 
			}else {
					request.getSession().setAttribute("message", " Ce compte n'existe pas");
					redirection = "/WEB-INF/jsp/connection.jsp";	
			}
			
			/* Stockage des messages d'erreur dans l'objet request. */
			request.setAttribute("erreurs", erreurs);
			request.getRequestDispatcher(redirection).forward(request, response);
	}


	/**
	 * Valide le login saisi
	 * @param login
	 * @throws Exception
	 */
	private void validateLogin(String login) throws Exception {
		if (login.trim().length() == 0 ) {
	        throw new Exception( "Merci de saisir votre identifiant." );
	    }
	}
	
	/**
	 * Valide le mot de passe saisi.
	 */
	private void validatePassword(String password) throws Exception{
	    if (password != null && password.trim().length() != 0) { 
	    	if (password.trim().length() < 6) {
	            throw new Exception("Les mots de passe doivent contenir au moins 6 caractères.");
	        }
	    } else {
	        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
	    }
	}

}
