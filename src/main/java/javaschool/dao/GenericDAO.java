package javaschool.dao;

import java.util.List;

public interface GenericDAO<T, ID> {
    List<T> findAll();
    T findById(ID id);
    void save(T entity);
    void delete(T entity);
}
