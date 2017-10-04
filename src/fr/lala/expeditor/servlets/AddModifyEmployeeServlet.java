package fr.lala.expeditor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddModifyEmployeeServlet
 */
public class AddModifyEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddModifyEmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Test sur l'action voulue :
		if(request.getParameter("ajouter") != null){
			//Cas de l'ajout, pas de remplissage formulaire
			request.setAttribute("test", "AJOUT");
		}else if (request.getParameter("modifier") != null){
			// Cas modification 
			request.setAttribute("test", "MODIFICATION");
		}
		
		
		
		//Appel au service Employee pour récupérer l'employé id = id
		
		//Mettre l'employé dans la request
		
		//forwarder
		
		
		request.getRequestDispatcher("/WEB-INF/jsp/manager/formaddmodifyemployee.jsp").forward(request, response);
	}

}
