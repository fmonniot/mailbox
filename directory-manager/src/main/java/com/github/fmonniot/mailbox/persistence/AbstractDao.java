package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.EntityIdentifiable;

import javax.persistence.*;

class AbstractDao<T extends EntityIdentifiable> {

    private final String entityName;

    AbstractDao(String entityName) {
        this.entityName = entityName;
    }

    public T create(final T entity) throws EntityExistsException {
        final EntityManager em = JpaHelpers.getEntityManager();
        T exist = entity.getId() != null ? findById(entity.getId()) : null;

        if (exist != null) {
            throw new EntityExistsException();
        }

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        et.commit();

        return entity;
    }

    T findById(long id) {
        final EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT mb FROM " + entityName + " AS mb WHERE mb.id = :id");
        selectByIdQuery.setParameter("id", id);

        //noinspection unchecked
        return (T) selectByIdQuery.getSingleResult();
    }

    void delete(T box) throws EntityNotFoundException {
        final EntityManager em = JpaHelpers.getEntityManager();

        if (findById(box.getId()) == null) {
            throw new EntityNotFoundException();
        }

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.remove(em.merge(box));
        em.flush();
        et.commit();
    }
}
