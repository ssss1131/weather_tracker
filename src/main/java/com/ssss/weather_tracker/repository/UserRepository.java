package com.ssss.weather_tracker.repository;

import com.ssss.weather_tracker.model.User;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends AbstractRepository<User> {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public boolean existsByLogin(String login) {
        return find(login).isPresent();
    }

    public Optional<User> find(String login){
        return executeInTransaction(session ->
                session.byNaturalId(User.class)
                        .using("login", login)
                        .loadOptional());
    }
}
