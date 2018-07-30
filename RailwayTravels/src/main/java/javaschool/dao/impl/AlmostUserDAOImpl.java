package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.AlmostUserDAO;
import javaschool.entity.AlmostUser;
import javaschool.entity.AlmostUser_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

@Repository
public class AlmostUserDAOImpl extends GenericAbstractDAO<AlmostUser, String> implements AlmostUserDAO {
    @Override
    public List<AlmostUser> findByUsernameOrEmail(String username, String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AlmostUser> query = builder.createQuery(AlmostUser.class);
        Root<AlmostUser> almostUserRoot = query.from(AlmostUser.class);
        Predicate usernameOrEmailEq = builder.or(builder.equal(almostUserRoot.get(AlmostUser_.username), username),
                builder.equal(almostUserRoot.get(AlmostUser_.email), email));
        return entityManager.createQuery(query.select(almostUserRoot).where(usernameOrEmailEq)).getResultList();
    }

    @Override
    public int deleteByHash(String hash) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<AlmostUser> delete = builder.createCriteriaDelete(AlmostUser.class);
        Root<AlmostUser> almostUserRoot = delete.from(AlmostUser.class);
        Predicate hashEq = builder.equal(almostUserRoot.get(AlmostUser_.hash), hash);
        delete.where(hashEq);
        return entityManager.createQuery(delete).executeUpdate();
    }

    @Override
    public int deleteRegisteredBefore(LocalDateTime dateTime) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<AlmostUser> delete = builder.createCriteriaDelete(AlmostUser.class);
        Root<AlmostUser> almostUserRoot = delete.from(AlmostUser.class);
        Predicate hashEq = builder.lessThan(almostUserRoot.get(AlmostUser_.registered), dateTime);
        delete.where(hashEq);
        return entityManager.createQuery(delete).executeUpdate();
    }

    @Override
    public AlmostUser findByUsername(String username) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<AlmostUser> query = builder.createQuery(AlmostUser.class);
            Root<AlmostUser> almostUserRoot = query.from(AlmostUser.class);
            Predicate usernameEq = builder.equal(almostUserRoot.get(AlmostUser_.username), username);
            return entityManager.createQuery(query.select(almostUserRoot).where(usernameEq)).getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public AlmostUser findByEmail(String email) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<AlmostUser> query = builder.createQuery(AlmostUser.class);
            Root<AlmostUser> almostUserRoot = query.from(AlmostUser.class);
            Predicate emailEq = builder.equal(almostUserRoot.get(AlmostUser_.email), email);
            return entityManager.createQuery(query.select(almostUserRoot).where(emailEq)).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public AlmostUser findByNameAndSurnameAndBirthday(String name, String surname, LocalDate birthday) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<AlmostUser> query = builder.createQuery(AlmostUser.class);
            Root<AlmostUser> almostUserRoot = query.from(AlmostUser.class);
            Predicate nameAndSurnameAndBirthdayEq = builder.and(builder.equal(almostUserRoot.get(AlmostUser_.name), name),
                    builder.equal(almostUserRoot.get(AlmostUser_.surname), surname),
                    builder.equal(almostUserRoot.get(AlmostUser_.birthday), birthday));
            return entityManager.createQuery(query.select(almostUserRoot).where(nameAndSurnameAndBirthdayEq)).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
