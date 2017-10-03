package fr.lala.expeditor.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface de CRUD pour la DAO.
 * @author adelaune2017
 *
 * @param <T>
 */
public interface ICrudDao <T>{	
	public void insert(T data) throws SQLException;
	public void update(T data) throws SQLException;
	public void delete(T data) throws SQLException;
	public T selectById(int id) throws SQLException;
	public List<T> selectAll() throws SQLException;
	public T itemBuilder(ResultSet rs) throws SQLException;
}
