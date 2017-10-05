package fr.lala.expeditor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.services.ArticleService;

/**
 * Servlet implementation class DeleteArticleServlet
 */
public class DeleteArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleService articleservice = new ArticleService();
	private String view = "/manager/listarticles";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteArticleServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("delete")!=null) {
			try {
				String id = request.getParameter("id_article");
				Article articleToDelete = articleservice.selectById(Integer.parseInt(id));
				System.out.println(articleToDelete);
				articleservice.delete(articleToDelete);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//request.getRequestDispatcher(view).forward(request, response);
		response.sendRedirect(request.getContextPath() + view);
	}

}
