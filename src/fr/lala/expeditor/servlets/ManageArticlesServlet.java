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
 * Servlet implementation class ManageArticlesServlet
 */
public class ManageArticlesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleService articleservice = new ArticleService();
	private String view;
	public static final String FIELD_ID_ARTICLE = "id_article";
	public static final String FIELD_LABEL_ARTICLE = "txtboxLabel";
	public static final String FIELD_DESCRIPTION_ARTICLE = "txtboxDescription";
	public static final String FIELD_WEIGHT_ARTICLE = "txtboxWeight";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageArticlesServlet() {
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		RequestDispatcher dispatcher = null;

		// L'action est "Modifier" : on positionne un article à modifier comme articleCourant
		if (request.getParameter("modify")!=null) {
			try {
				// Récupération de l'id de l'article
				String id = request.getParameter("id");
				Article articleCurrent = articleservice.selectById(Integer.parseInt(id));
				request.setAttribute("articleCurrent", articleCurrent);
				view = "/WEB-INF/jsp/manager/formaddmodifyarticle.jsp";
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		} else if (request.getParameter("add")!=null) {
			try {
				Article articleCurrent = new Article();;
				request.setAttribute("articleCurrent", articleCurrent);
				view = "/WEB-INF/jsp/manager/formaddmodifyarticle.jsp";
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (request.getParameter("delete")!=null) {
			try {
				deleteArticle(request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (request.getParameter("save")!=null) {

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
				view = "/WEB-INF/erreur.jsp";
			}
		}

		dispatcher = getServletContext().getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}

	/**
	 * Méthode en charge de modifier un article.
	 * @param request
	 * @param response
	 */
	private void updateArticle(HttpServletRequest request, HttpServletResponse response) {
		Article articleToUpdate = getArticle(request);
		articleservice.update(articleToUpdate);
		view = "/manager/listarticles";
		
	}

	/**
	 * Méthode en charge d'ajouter un article.
	 * @param request
	 * @param response
	 */
	private void addArticle(HttpServletRequest request, HttpServletResponse response) {
		Article newarticle = getArticle(request);
		articleservice.insert(newarticle);
		view = "/manager/listarticles";
		
	}

	/**
	 * Méthode en charge d'archiver un article et de supprimer de la liste.
	 * @param request
	 * @param response
	 */
	private void deleteArticle(HttpServletRequest request, HttpServletResponse response) {
		Article articleToDelete = new Article();
		articleToDelete.setId(Integer.parseInt(request.getParameter("id")));
		articleToDelete.setLabel(request.getParameter("label"));
		articleToDelete.setDescription(request.getParameter("description"));
		articleToDelete.setWeight(Integer.parseInt(request.getParameter("weight")));
		System.out.println(articleToDelete);
		articleservice.delete(articleToDelete);
		view = "/manager/listarticles";
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
