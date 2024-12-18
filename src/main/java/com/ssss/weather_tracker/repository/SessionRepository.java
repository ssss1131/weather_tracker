package com.ssss.weather_tracker.repository;

import com.ssss.weather_tracker.model.Session;
import com.ssss.weather_tracker.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.query.MutationQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Repository
public class SessionRepository extends AbstractRepository<Session> {

    public SessionRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Session findById(UUID id) {
        return executeInTransaction(session -> {
            RootGraph<Session> graph = session.createEntityGraph(Session.class);
            graph.addAttributeNode("user");
            Map<String, Object> properties = Map.of(
                    GraphSemantic.LOAD.getJakartaHintName(), graph
            );
            return session.find(Session.class, id, properties);
        });
    }


    public void updateTime(UUID sessionId, LocalDateTime localDateTime) {
        executeInTransaction(session -> {
            MutationQuery query = session.createMutationQuery("UPDATE Session SET expiresAt = :time WHERE id = :uuid");
            query.setParameter("time", localDateTime);
            query.setParameter("uuid", sessionId);
            query.executeUpdate();
            return null;
        });
    }

    public Session findByUser(User user) {
        return executeInTransaction(session ->
                session.createQuery("FROM Session s WHERE s.user = :user", Session.class)
                        .setParameter("user", user)
                        .getSingleResult());
    }

    @Override
    protected void handleConstraintViolationException(ConstraintViolationException e) {
        throw e;
    }
}
