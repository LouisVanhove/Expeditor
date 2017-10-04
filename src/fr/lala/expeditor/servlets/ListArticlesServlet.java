package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.services.ArticleService;

/**
 * Servlet implementation class ListArticlesServlet
 */
public class ListArticlesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher = null;
	private List<Article> listarticles=null;
	public static final String VIEW = "/WEB-INF/jsp/manager/listearticles.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListArticlesServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleService articleService = new ArticleService();
		
		try {
		   listarticles = articleService.selectAll();
		   request.setAttribute("listarticles", listarticles);
		   dispatcher=getServletContext().getRequestDispatcher(VIEW);
	        if (dispatcher!=null)
	        	dispatcher.forward(request, response);					
		} 	catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
