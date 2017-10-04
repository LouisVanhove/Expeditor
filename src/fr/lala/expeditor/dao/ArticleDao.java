package fr.lala.expeditor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.utils.MonLogger;

/**
 * Classe en charge de gerer la couche d'acces � la BDD pour les objets de type Article.
 * @author aurelie.lardeux2017
 *
 */
public class ArticleDao implements ICrudDao<Article>{
	
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_LABEL = "label";
	private static final String COLUMN_DESCRIPTION = "description";
	private static final String COLUMN_WEIGHT= "weight";
	private static final String COLUMN_ARCHIVED = "archived";
	
	private static final String SELECT_ARTICLES = "SELECT a.id, a.label, a.description, a.weight, a.archived "
			+ " FROM ARTICLES a WHERE a.archived=0" ;
	private static final String SELECT_ARTICLE_BY_ID = "SELECT a.id, a.label, a.description, a.weight, a.archived FROM ARTICLES a"
			+ " WHERE a.Id=?";
	private static final String INSERT_ARTICLE= "INSERT INTO ARTICLES (Label, Description, Weight, Archived) VALUES(?,?,?,0);";
	private static final String UPDATE_ARTICLE = "UPDATE ARTICLES SET Label=?, Description=?, Weight=? WHERE Id = ?";
	private static final String ARCHIVE_ARTICLE = "UPDATE ARTICLES SET Archived=1 WHERE Id = ?";
	
	// monlogger retourne un objet de type logger
	Logger logger = MonLogger.getLogger(this.getClass().getName());

	/**
	 * M�thode d'insertion d'un article en base.
	 */
	@Override
	public void insert(Article data) throws SQLException {
		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(INSERT_ARTICLE);
			cmd.setString(1, data.getLabel());
			cmd.setString(2, data.getDescription());
			cmd.setInt(3, data.getWeight());
			cmd.execute();

		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		
	}

	/**
	 * M�thode de modification d'un article en base.
	 */
	@Override
	public void update(Article data) throws SQLException {
		
		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(UPDATE_ARTICLE);
			cmd.setString(1, data.getLabel());
			cmd.setString(2, data.getDescription());
			cmd.setInt(3, data.getWeight());
			cmd.setInt(4, data.getId());

			cmd.executeUpdate();

		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		
	}

	/**
	 * M�thode de suppression d'un article en base.
	 */
	@Override
	public void delete(Article data) throws SQLException {
		
		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(ARCHIVE_ARTICLE);
			cmd.setInt(1, data.getId());

			cmd.executeUpdate();

		} catch (SQLException e) {
			logger.severe(this.getClass().getName()+"#archive : "+e.getMessage());
			throw new SQLException("Erreur lors de l'archivage de l'article dans la base de donn�es.");
		}
	}

	/**
	 * M�thode de s�lection d'un article en base en fonction de son id.
	 */
	@Override
	public Article selectById(int id) throws SQLException {
		Article article= null;
		
		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(SELECT_ARTICLE_BY_ID);
			cmd.setInt(1, id);

			ResultSet rs = cmd.executeQuery();

			// si le resultset nous retourne true
			if (rs.next()) {
				// Ajout d'un article
				article = itemBuilder(rs);
			}

		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}
		return article;
	}

	/**
	 * M�thode de s�lection de tous les articles en base.
	 */
	@Override
	public List<Article> selectAll() throws SQLException {
		List<Article> listearticles = new ArrayList<Article>();

		try (Connection cnx = ConnectionPool.getConnection()) {
			PreparedStatement cmd = cnx.prepareStatement(SELECT_ARTICLES);

			ResultSet rs = cmd.executeQuery();

			// tant qu'il trouve quelque chose
			while (rs.next()) {
				// Ajout d'un article � la liste
				listearticles.add(itemBuilder(rs));
			}

		} catch (SQLException e) {
			logger.severe("Erreur : " + e.getMessage());
		}

		return listearticles;
	}

	
	/**
	 * M�thode en charge de r�cup�rer un article.
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Article itemBuilder(ResultSet rs) throws SQLException {
		Article article = new Article();
		article.setId(rs.getInt(COLUMN_ID));
		article.setLabel(rs.getString(COLUMN_LABEL));
		article.setDescription(rs.getString(COLUMN_DESCRIPTION));
		article.setWeight(rs.getInt(COLUMN_WEIGHT));
		article.setArchived(rs.getBoolean(COLUMN_ARCHIVED));
		return article;
	}


}