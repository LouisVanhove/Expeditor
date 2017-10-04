package fr.lala.expeditor.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.services.ArticleService;

/**
 * Servlet implementation class AddModifyArticleServlet
 */
public class AddModifyArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleService articleservice = new ArticleService();
	private String view = "/WEB-INF/jsp/manager/formaddmodifyarticle.jsp";
	public static final String FIELD_ID_ARTICLE = "id_article";
	public static final String FIELD_LABEL_ARTICLE = "txtboxLabel";
	public static final String FIELD_DESCRIPTION_ARTICLE = "txtboxDescription";
	public static final String FIELD_WEIGHT_ARTICLE = "txtboxWeight";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddModifyArticleServlet() {
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
		
				// L'action est "Modifier" : on positionne un article à modifier comme articleCourant
				if (request.getParameter("modify")!=null) {
					try {
						// Récupération de l'id de l'article
						String id = request.getParameter("id_article");
						Article articleCurrent = articleservice.selectById(Integer.parseInt(id));
						request.setAttribute("articleCurrent", articleCurrent);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				} else if (request.getParameter("add")!=null) {
					try {
						request.setAttribute("articleCurrent", null);
						
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				request.getRequestDispatcher(view).forward(request, response);
				
			}
			

}
