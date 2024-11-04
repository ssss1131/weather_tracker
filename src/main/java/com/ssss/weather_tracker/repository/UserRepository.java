package com.ssss.weather_tracker.repository;

import com.ssss.weather_tracker.model.User;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public boolean existsByLogin(String login) {
        return executeInTransaction(session ->
                session.byNaturalId(User.class)
                        .using("login", login)
                        .load() != null
        );
    }

    public User save(User user) {
        return executeInTransaction(session -> {
            session.persist(user);
            return user;
        });
    }


}
