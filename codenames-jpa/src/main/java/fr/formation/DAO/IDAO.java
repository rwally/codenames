package fr.formation.DAO;

import java.sql.SQLException;
import java.util.List;



public interface IDAO<T, Id>  {
	
	public List<T> findAll() throws SQLException;
	public T findById(Id id) throws SQLException;
	public T save(T entity) throws SQLException;
	public void delete (T entity) throws SQLException;
	public void deleteById(Id id) throws SQLException;
	

}
