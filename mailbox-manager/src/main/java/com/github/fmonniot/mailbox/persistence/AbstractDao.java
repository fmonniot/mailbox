package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.EntityIdentifiable;

import javax.persistence.*;

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

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(entity);
        em.flush();
        et.commit();

        return entity;
    }

    public T findById(long id) {
        final EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT mb FROM " + entityClassName + " AS mb WHERE mb.id = :id");
        selectByIdQuery.setParameter("id", id);

        //noinspection unchecked
        return (T) selectByIdQuery.getSingleResult();
    }

    public void delete(T box) throws EntityNotFoundException {
        final EntityManager em = JpaHelpers.getEntityManager();
        T exist = findById(box.getId());

        if (exist == null) {
            throw new EntityNotFoundException();
        }

        em.remove(exist);
    }
}
