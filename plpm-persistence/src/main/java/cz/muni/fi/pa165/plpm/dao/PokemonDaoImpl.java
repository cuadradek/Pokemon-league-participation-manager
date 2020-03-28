package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implements PokemonDao
 * <p>
 * author: Veronika Loukotová
 */

@Repository
public class PokemonDaoImpl implements PokemonDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Pokemon pokemon) {
        em.persist(pokemon);
    }

    @Override
    public Pokemon findById(Long id) {
        return em.find(Pokemon.class, id);
    }

    @Override
    public List<Pokemon> findByName(String name) {
        TypedQuery<Pokemon> query = em.createQuery("SELECT p FROM Pokemon p WHERE p.name = :name",
                Pokemon.class);

        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Pokemon> findByNickname(String nickname) {
        TypedQuery<Pokemon> query = em.createQuery("SELECT p FROM Pokemon p WHERE p.nickname = :nickname",
                Pokemon.class);

        query.setParameter("nickname", nickname);
        return query.getResultList();
    }

    @Override
    public List<Pokemon> findPokemonsByTrainer(Trainer trainer) {
        TypedQuery<Pokemon> query;
        if (trainer == null) {
            query = em.createQuery(
                    "SELECT p FROM Pokemon p WHERE p.trainer IS NULL",
                    Pokemon.class);
        } else {
            query = em.createQuery(
                    "SELECT p FROM Pokemon p WHERE p.trainer = :trainer",
                    Pokemon.class);

            query.setParameter("trainer", trainer);
        }

        return query.getResultList();
    }

    @Override
    public void remove(Pokemon pokemon) {
        em.remove(pokemon);
    }

    @Override
    public void update(Pokemon pokemon) {
        em.merge(pokemon);
    }

    @Override
    public List<Pokemon> findAll() {
        return em.createQuery("SELECT p FROM Pokemon p", Pokemon.class).getResultList();
    }
}
