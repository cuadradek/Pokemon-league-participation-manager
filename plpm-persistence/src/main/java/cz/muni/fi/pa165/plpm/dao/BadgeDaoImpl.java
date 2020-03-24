package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

/**
 * Implementation of {@link BadgeDao}
 *
 * @author Jakub Doczy
 */
@Repository
public class BadgeDaoImpl implements BadgeDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Badge badge) {
        em.persist(badge);
    }

    @Override
    public void remove(Badge badge) {
        em.remove(badge);
    }

    @Override
    public void update(Badge badge) {
        em.merge(badge);
    }

    @Override
    public Badge findById(Long id) {
        return em.find(Badge.class, id);
    }

    @Override
    public Collection<Badge> findByTrainer(Trainer trainer) {
        TypedQuery<Badge> query = em.createQuery("SELECT b FROM Badge b WHERE b.trainer = :trainer", Badge.class);
        query.setParameter("trainer", trainer);
        return query.getResultList();
    }

    @Override
    public Collection<Badge> findByGym(Gym gym) {
        TypedQuery<Badge> query = em.createQuery("SELECT b FROM Badge b WHERE b.gym = :gym", Badge.class);
        query.setParameter("gym", gym);
        return query.getResultList();
    }

    @Override
    public Collection<Badge> findAll() {
        return em.createQuery("SELECT b FROM Badge b", Badge.class).getResultList();
    }
}
