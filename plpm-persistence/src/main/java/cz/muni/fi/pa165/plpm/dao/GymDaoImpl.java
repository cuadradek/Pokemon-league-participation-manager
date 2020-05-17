package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of GymDao
 *
 * @author Karolína Kolouchová
 */
@Repository
public class GymDaoImpl implements GymDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Gym gym) {
        em.persist(gym);
    }

    @Override
    public void remove(Gym gym) {
        em.remove(em.merge(gym));
    }

    @Override
    public void update(Gym gym) {
        em.merge(gym);
    }

    @Override
    public Gym findById(Long id) {
        return em.find(Gym.class, id);
    }

    @Override
    public Gym findByTrainer(Trainer trainer) {
        if (trainer == null) {
            return null;
        }

        TypedQuery<Gym> query = em.createQuery("select g from Gym g where g.leader.id = :trainerId", Gym.class)
                .setParameter("trainerId", trainer.getId());
        List<Gym> found = query.getResultList();

        if (found.isEmpty()) {
            return null;
        }

        return found.get(0);
    }

    @Override
    public List<Gym> findByCity(String city) {
        TypedQuery<Gym> query = em.createQuery("select g from Gym g where g.city = :city", Gym.class)
                .setParameter("city", city);
        return query.getResultList();
    }

    @Override
    public List<Gym> findAll() {
        return em.createQuery("select g from Gym g", Gym.class).getResultList();
    }
}
