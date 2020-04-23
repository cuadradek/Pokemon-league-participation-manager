package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;

import java.util.Collection;
import java.util.List;

/**
 * Gym Badge DAO
 *
 * @author Jakub Doczy
 */
public interface BadgeDao {

    /**
     * Adds new badge to the database.
     *
     * @param badge - Gym badge that we want to add to DB.
     */
    void create(Badge badge);

    /**
     * Removes badge from the database.
     *
     * @param badge - Gym badge that we want to remove from DB.
     */
    void remove(Badge badge);

    /**
     * Updates badge in database.
     *
     * @param badge - Gym badge that we want to update.
     */
    void update(Badge badge);

    /**
     * Finds gym badge with specified id.
     *
     * @param id -  Primary key of the badge we want to obtain.
     * @return gym badge with specified id.
     */
    Badge findById(Long id);

    /**
     * Finds all badges that belong to specified trainer.
     *
     * @param trainer Pokemon trainer whose badges we want to obtain.
     * @return collection of badges that belong to the specified trainer.
     */
    List<Badge> findByTrainer(Trainer trainer);

    /**
     * Finds all badges that were awarded for defeating gym leader from
     * specified gym.
     * @param gym Gym to which belong all badges we want to obtain.
     * @return collection of badges that belong to the specified gym.
     * */
    List<Badge> findByGym(Gym gym);

    /**
     * @return collection of all badges from database.
     */
    List<Badge> findAll();

}
