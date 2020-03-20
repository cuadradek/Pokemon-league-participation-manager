package cz.muni.fi.pa165.plpm.dao;


import cz.muni.fi.pa165.plpm.entity.Trainer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Trainer DAO implementation.
 *
 * @author Radoslav Cerhak
 */
@Repository
public class TrainerDaoImpl implements TrainerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createTrainer(Trainer trainer) {
        em.persist(trainer);
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        em.merge(trainer);
    }

    @Override
    public void deleteTrainer(Trainer trainer) {
        em.remove(trainer);
    }

    @Override
    public Trainer findTrainerById(Long id) {
        return em.find(Trainer.class, id);
    }

    @Override
    public List<Trainer> findTrainerByFirstName(String firstName) {
        TypedQuery<Trainer> query = em.createQuery("select t from Trainer t where lower(t.firstName) like :name", Trainer.class);
        query.setParameter("name", "%" + firstName.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Trainer> findTrainerByLastName(String lastName) {
        TypedQuery<Trainer> query = em.createQuery("select t from Trainer t where lower(t.lastName) like :name", Trainer.class);
        query.setParameter("name", "%" + lastName.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Trainer> findAllTrainers() {
        return em.createQuery("select t from Trainer t", Trainer.class).getResultList();
    }
}