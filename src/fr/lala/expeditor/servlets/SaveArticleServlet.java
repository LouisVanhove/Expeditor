package fr.lala.expeditor.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	private String view;
	private Map<String, String> erreurs = new HashMap<String, String>();
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
				String idRecupere = request.getParameter(FIELD_ID_ARTICLE);
				String labelRecupere = request.getParameter(FIELD_LABEL_ARTICLE).trim();
				String descriptionRecupere = request.getParameter(FIELD_DESCRIPTION_ARTICLE).trim();
				int poidsRecupere = Integer.parseInt(request.getParameter(FIELD_WEIGHT_ARTICLE).trim());
				
				erreurs = validerleschamps(labelRecupere, descriptionRecupere, poidsRecupere);
				
				/* Initialisation du résultat global de la validation. */
				if (erreurs.isEmpty()) {
					if (idRecupere.equals("")) {
						try {
							addArticle(poidsRecupere, labelRecupere, descriptionRecupere, response);
							view = "/manager/listarticles";
						} catch (Exception e) {
							e.printStackTrace();
						}
						request.setAttribute("message", "L'ajout s'est déroulé avec succès");
					} else {
						try {
							updateArticle(idRecupere, poidsRecupere, labelRecupere, descriptionRecupere, response);	
							view = "/manager/listarticles";
						} catch (Exception e) {
							e.printStackTrace();
						}
					request.setAttribute("message", "La modification s'est déroulée avec succès");
					}
				}else{
					/* Stockage des messages d'erreur dans l'objet request. */
					request.setAttribute("erreurs", erreurs);
					view = "/WEB-INF/jsp/manager/formaddmodifyarticle.jsp";
				}
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			request.getRequestDispatcher(view).forward(request, response);
		}
	}

	/**
	 * Méthode en charge de modifier un article.
	 * @param request
	 * @param response
	 */
	private void updateArticle(String idRecupere, int poidsRecupere, String labelRecupere, String descriptionRecupere, HttpServletResponse response) {
		Article articleToUpdate = getArticle(poidsRecupere, labelRecupere, descriptionRecupere);
		articleToUpdate.setId(Integer.parseInt(idRecupere));
		articleservice.update(articleToUpdate);
	}

	/**
	 * Méthode en charge d'ajouter un article.
	 * @param request
	 * @param response
	 */
	private void addArticle(int poidsRecupere, String labelRecupere, String descriptionRecupere, HttpServletResponse response) {
		Article newarticle = getArticle(poidsRecupere, labelRecupere, descriptionRecupere);
		articleservice.insert(newarticle);
		
	}
	
	/**
	 * Méthode en charge de valider les champs du formulaires.
	 */
	private Map<String, String> validerleschamps(String labelRecupere, String descriptionRecupere, int poidsRecupere) {
		
		/* Validation du label */
		try {
			validationLabel(labelRecupere);
		} catch (Exception e) {
			erreurs.put("label", e.getMessage());
		}
		
		/* Validation de la description */
		try {
			validationDescription(descriptionRecupere);
		} catch (Exception e) {
			erreurs.put("description", e.getMessage());
		}
		
		/* Validation du poids */
		try {
			validationPoids(poidsRecupere);
		} catch (Exception e) {
			erreurs.put("weight", e.getMessage());
		}
		
		return erreurs;
	}
	
	/**
	 * Méthode en charge de de récuperer un article à partir des champs du formulaires et.
	 * @param poidsRecupere
	 * @param labelRecupere
	 * @param descriptionRecupere
	 * @return
	 */
	private Article getArticle(int poidsRecupere, String labelRecupere, String descriptionRecupere){
		Article article = new Article();
		article.setLabel(labelRecupere);
		article.setDescription(descriptionRecupere);
		article.setWeight(poidsRecupere);
		return article;
	}

	/**
	 * Méthode en charge de valider le champs du Label.
	 * @param fieldLabelArticle
	 * @throws Exception
	 */
	private void validationLabel(String fieldLabelArticle) throws Exception {
		if (fieldLabelArticle.trim().length() == 0) {
			throw new Exception("Le label doit être renseigné.");
		}else if(fieldLabelArticle.trim().length() > 100){
			throw new Exception("Le label ne doit pas dépasser 100 caractères.");
		}
	}
	
	/**
	 * Méthode en charge de valider le champs de la description.
	 * @param fieldDescriptionArticle
	 * @throws Exception
	 */
	private void validationDescription(String fieldDescriptionArticle) throws Exception {
		if (fieldDescriptionArticle.trim().length() == 0) {
			throw new Exception("La description doit être renseignée.");
		}else if(fieldDescriptionArticle.trim().length() > 255){
			throw new Exception("La description ne doit pas dépasser 255 caractères.");
		}
	}
	
	/**
	 * Méthode en charge de valider le champs du poids.
	 * @param fieldWeightArticle
	 * @throws Exception
	 */
	private void validationPoids(int fieldWeightArticle) throws Exception {
		if (fieldWeightArticle < 0 ){
			throw new Exception("Le poids doit être positif.");
		}
	}
}
