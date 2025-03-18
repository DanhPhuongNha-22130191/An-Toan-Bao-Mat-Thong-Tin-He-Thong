
package dao;

import java.util.List;

public interface IDao <T,ID> {
	boolean insert(T entity);
	T getById(ID id);
	List<T> getAll();
	boolean delete(ID id);
	boolean update(T entity);
}
=======
package dao;

import java.util.List;

public interface IDao <T,ID> {
	boolean insert(T entity);
	T getById(ID id);
	List<T> getAll();
	boolean delete(ID id);
    
	boolean update(T entity);
}

