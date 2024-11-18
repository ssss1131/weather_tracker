package com.ssss.weather_tracker.repository;

import com.ssss.weather_tracker.exception.AlreadySavedLocationException;
import com.ssss.weather_tracker.model.Location;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationRepository extends AbstractRepository<Location> {


    public LocationRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Location> getAllByUserId(long id) {
        return executeInTransaction(session ->
                session.createQuery("FROM Location WHERE user.id = :id", Location.class)
                .setParameter("id", id)
                .getResultList());

    }

    public void deleteById(Long locationId, Long userId) {
        executeInTransaction(session ->
                session.createMutationQuery("DELETE FROM Location WHERE id = :id AND user.id = :userId")
                .setParameter("id", locationId)
                .setParameter("userId", userId)
                .executeUpdate());
    }

    @Override
    protected void handleConstraintViolationException(ConstraintViolationException e) {
        throw new AlreadySavedLocationException("This location already saved");
    }
}
