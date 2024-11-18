package com.ssss.weather_tracker.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.cglib.core.internal.Function;

@RequiredArgsConstructor
public abstract class AbstractRepository<E> {

    private final SessionFactory sessionFactory;

    protected <T> T executeInTransaction(Function<Session, T> action){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            T result = action.apply(session);
            transaction.commit();
            return result;
        } catch (ConstraintViolationException e) {
            handleConstraintViolationException(e);
            throw  e;
        }
        catch (Exception e){
            if(transaction != null ){
                transaction.rollback();
            }
            throw e;
        }
    }

    public E save(E entity){
        return executeInTransaction(session ->{
            session.persist(entity);
            return entity;
        });
    }

    protected abstract void handleConstraintViolationException(ConstraintViolationException e);



}
