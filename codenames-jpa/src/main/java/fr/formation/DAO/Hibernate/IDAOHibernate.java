package fr.formation.DAO.Hibernate;

import java.util.List;

public interface IDAOHibernate<T , Id> {
	
	
	public List<T> findAll();
	
	public T findById(Id id);
	
	public T save (T entity);
		
	public void delete (T entity);
	
	public void deleteById (Id id);
}
