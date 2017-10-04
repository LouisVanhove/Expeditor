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
		if (request.getParameter("modifier")!=null || request.getParameter("ajouter")!=null) {
			try {
				afficherFormAjoutModifArticle(request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (request.getParameter("supprimer")!=null) {
			try {
				supprimerArticle(request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (request.getParameter("enregistrer")!=null) {

			try {
				// Récupération des informations saisies dans le formulaire
				String id = request.getParameter(FIELD_ID_ARTICLE);

				// Enregistrement d'une nouvel enfant
				if (id.equals("")) {
					try {
						ajouterArticle(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						modifierArticle(request, response);	
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
	private void modifierArticle(HttpServletRequest request, HttpServletResponse response) {
		Article articleAModifier = recupereParametre(request);
		articleservice.update(articleAModifier);
		view = "/manager/listarticles";
		
	}

	/**
	 * Méthode en charge d'ajouter un article.
	 * @param request
	 * @param response
	 */
	private void ajouterArticle(HttpServletRequest request, HttpServletResponse response) {
		Article nouvelarticle = recupereParametre(request);
		articleservice.insert(nouvelarticle);
		view = "/manager/listarticles";
		
	}

	/**
	 * Méthode en charge d'archiver un article et de supprimer de la liste.
	 * @param request
	 * @param response
	 */
	private void supprimerArticle(HttpServletRequest request, HttpServletResponse response) {
				// Récupération de l'id dans le formulaire
				String id = request.getParameter(FIELD_ID_ARTICLE);
				//articleservice.delete(data);
				view = "/manager/listarticles";
	}

	/**
	 * Méthode en charge d'afficher le formulaire pour l'ajout ou la modification d'un article.
	 * @param request
	 * @param response
	 */
	private void afficherFormAjoutModifArticle(HttpServletRequest request, HttpServletResponse response) {
		// Récupération de l'id de l'article
		String id = request.getParameter(FIELD_ID_ARTICLE);
		Article articleCourant = articleservice.selectById(Integer.parseInt(id));
		request.setAttribute("articleCourant", articleCourant);
		view = "/WEB-INF/jsp/manager/managearticles.jsp";
		
	}
	
	/**
	 * Méthode en charge de récuperer un article à partir de tous les champs du formulaire.
	 * @param request
	 * @return
	 */
	private Article recupereParametre(HttpServletRequest request) {
		Article article = new Article();
		article.setLabel(request.getParameter(FIELD_LABEL_ARTICLE).trim());
		article.setDescription(request.getParameter(FIELD_DESCRIPTION_ARTICLE).trim());
		article.setWeight(Integer.parseInt(request.getParameter(FIELD_WEIGHT_ARTICLE).trim()));
		return article;
	}

}
