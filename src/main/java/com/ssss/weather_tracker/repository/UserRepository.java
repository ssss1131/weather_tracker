package com.ssss.weather_tracker.repository;

import com.ssss.weather_tracker.exception.UserAlreadyExistsException;
import com.ssss.weather_tracker.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends AbstractRepository<User> {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public boolean existsByLogin(String login) {
        return findByLogin(login).isPresent();
    }

    public Optional<User> findByLogin(String login) {
        return executeInTransaction(session ->
                session.byNaturalId(User.class)
                        .using("login", login)
                        .loadOptional());
    }

    public User findById(long id) {
        return executeInTransaction(session -> session.find(User.class, id));
    }

    @Override
    protected void handleConstraintViolationException(ConstraintViolationException e) {
        throw new UserAlreadyExistsException("User Already exists");
    }
}
