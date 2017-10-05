package fr.lala.expeditor.services;

import java.sql.SQLException;
import java.util.List;

import fr.lala.expeditor.dao.ArticleDao;
import fr.lala.expeditor.models.Article;

/**
 * Classe Service de Article
 * @author aurelie.lardeux2017
 *
 */
public class ArticleService {

	private ArticleDao articledao = new ArticleDao();
	private List<Article> _listeArticles;

	/**
	 * Selection de la liste de tous les Articles.
	 * 
	 * @return une liste
	 */
	public List<Article> selectAll(){
		try {
			_listeArticles = articledao.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _listeArticles;
	}
	
	/**
	 * Selection de la liste de tous les Articles en fonction du numéro de commande.
	 * 
	 * @return une liste
	 */
	public List<Article> selectAllByOrder(int idOrder){
		try {
			_listeArticles = articledao.selectAllByOrder(idOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _listeArticles;
	}
	
	/**
	 * Selection d'un article en fonction de son identifiant.
	 * 
	 * @param id
	 * @return un article
	 */
	public Article selectById(int id) {
		Article article = null;
		try {
			article = articledao.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	/**
	 * Insertion d'un nouvel article.
	 * 
	 * @param data
	 */
	public void insert(Article data) {
		try {
			for (Article article : this.selectAll()) {
				if (data.getLabel().equals(article.getLabel()) && data.getDescription().equals(article.getDescription())) {
					throw new Exception(String.format("L'article {0} existe déjà.", data));
				} 
			}
			articledao.insert(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Modification d'un article.
	 * 
	 * @param data
	 * @throws SQLException 
	 */
	public void update(Article data) {
		try {
			articledao.update(data);
		} catch (SQLException e) {
			e.printStackTrace();
		};
	}

	/**
	 * Suppression d'un article.
	 * 
	 * @param data
	 */
	public void delete(Article data) {
		try {
			articledao.delete(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
