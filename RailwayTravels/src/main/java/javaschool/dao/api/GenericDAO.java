package javaschool.dao.api;

import java.util.List;

/**
 * The interface Generic dao.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
public interface GenericDAO<T, ID> {
    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    T findById(ID id);

    /**
     * Save.
     *
     * @param entity the entity
     */
    void save(T entity);

    /**
     * Merge t.
     *
     * @param entity the entity
     * @return the t
     */
    T merge(T entity);

    /**
     * Delete.
     *
     * @param entity the entity
     */
    void delete(T entity);
}
