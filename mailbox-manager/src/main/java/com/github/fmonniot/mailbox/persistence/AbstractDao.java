package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.EntityIdentifiable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

class AbstractDao<T extends EntityIdentifiable> {

    private final String entityClassName;

    public AbstractDao(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public T create(final T entity) throws EntityExistsException {
        final EntityManager em = JpaHelpers.getEntityManager();
        T exist = entity.getId() != null ? findById(entity.getId()) : null;

        if (exist != null) {
            throw new EntityExistsException();
        }

        try {
            JpaHelpers.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    em.persist(entity);
                }
            });
        } catch (JpaHelpers.TransactionException e) {
            e.printStackTrace();
            return null;
        }

        return entity;
    }

    public T findById(long id) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT mb FROM " + entityClassName + " AS mb WHERE mb.id = :id");
        selectByIdQuery.setParameter("id", id);

        //noinspection unchecked
        return (T) selectByIdQuery.getSingleResult();
    }

    public void delete(T box) throws EntityNotFoundException {
        EntityManager em = JpaHelpers.getEntityManager();
        T exist = findById(box.getId());

        if (exist == null) {
            throw new EntityNotFoundException();
        }

        em.remove(exist);
    }
}
