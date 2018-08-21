package javaschool.dao.impl;


import java.lang.reflect.ParameterizedType;
import java.util.List;
import javaschool.dao.api.GenericDAO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * The type Generic abstract dao.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
public abstract class GenericAbstractDAO<T, ID> implements GenericDAO<T, ID> {
    /**
     * The Entity manager.
     */
    protected EntityManager entityManager;
    /**
     * The Entity class.
     */
    protected Class<T> entityClass;
    /**
     * The Id class.
     */
    protected Class<T> idClass;

    /**
     * Instantiates a new Generic abstract dao.
     */
    public GenericAbstractDAO() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) type.getActualTypeArguments()[0];
        this.idClass = (Class<T>) type.getActualTypeArguments()[1];
    }

    /**
     * Sets entity manager.
     *
     * @param entityManager the entity manager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(entityClass);
        query.select(query.from(entityClass));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public T findById(ID id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);

    }

    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
