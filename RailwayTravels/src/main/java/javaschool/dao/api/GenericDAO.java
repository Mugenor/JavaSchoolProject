package javaschool.dao.api;

import java.util.List;

public interface GenericDAO<T, ID> {
    List<T> findAll();
    T findById(ID id);
    void save(T entity);
    T merge(T entity);
    void delete(T entity);
}
