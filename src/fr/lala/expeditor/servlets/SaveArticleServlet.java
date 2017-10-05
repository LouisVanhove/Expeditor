package fr.lala.expeditor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.services.ArticleService;

/**
 * Servlet implementation class SaveArticleServlet
 */
public class SaveArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleService articleservice = new ArticleService();
	private String view = "/manager/listarticles";
	public static final String FIELD_ID_ARTICLE = "id_article";
	public static final String FIELD_LABEL_ARTICLE = "txtboxLabel";
	public static final String FIELD_DESCRIPTION_ARTICLE = "txtboxDescription";
	public static final String FIELD_WEIGHT_ARTICLE = "txtboxWeight";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveArticleServlet() {
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
		
		if (request.getParameter("save")!=null) {

			try {
				// Récupération des informations saisies dans le formulaire
				String id = request.getParameter(FIELD_ID_ARTICLE);

				// Enregistrement d'une nouvel article
				if (id.equals("")) {
					try {
						addArticle(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("message", "L'ajout s'est déroulé avec succès");
				} else {
					try {
						updateArticle(request, response);	
					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("message", "La modification s'est déroulée avec succès");
				}
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			request.getRequestDispatcher(view).forward(request, response);
		
		} else if (request.getParameter("cancel")!=null){
			request.getRequestDispatcher("/WEB-INF/jsp/manager/listearticles.jsp").forward(request, response);
		}
		
	}

	
	/**
	 * Méthode en charge de modifier un article.
	 * @param request
	 * @param response
	 */
	private void updateArticle(HttpServletRequest request, HttpServletResponse response) {
		Article articleToUpdate = getArticle(request);
		articleToUpdate.setId(Integer.parseInt(request.getParameter(FIELD_ID_ARTICLE)));
		articleservice.update(articleToUpdate);
	}

	/**
	 * Méthode en charge d'ajouter un article.
	 * @param request
	 * @param response
	 */
	private void addArticle(HttpServletRequest request, HttpServletResponse response) {
		Article newarticle = getArticle(request);
		articleservice.insert(newarticle);
		
	}
	
	/**
	 * Méthode en charge de récuperer un article à partir de tous les champs du formulaire.
	 * @param request
	 * @return
	 */
	private Article getArticle(HttpServletRequest request) {
		Article article = new Article();
		article.setLabel(request.getParameter(FIELD_LABEL_ARTICLE).trim());
		article.setDescription(request.getParameter(FIELD_DESCRIPTION_ARTICLE).trim());
		article.setWeight(Integer.parseInt(request.getParameter(FIELD_WEIGHT_ARTICLE).trim()));
		return article;
	}
}
