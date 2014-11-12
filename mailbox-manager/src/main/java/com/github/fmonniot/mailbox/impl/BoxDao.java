package com.github.fmonniot.mailbox.impl;

import com.github.fmonniot.mailbox.Box;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class BoxDao<T extends Box> {

    private final Class<T> boxClass;
    private final EntityManager em;


    public BoxDao(Class<T> cls, EntityManager em) {
        this.boxClass = cls;
        this.em = em;
    }

    public List<T> getBoxes(long clientId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(boxClass);
        Root<T> from = criteriaQuery.from(boxClass);

        Predicate predicate1 = criteriaBuilder.equal(from.get("ID"), clientId);
        criteriaQuery.where(criteriaBuilder.and(predicate1));

        CriteriaQuery<T> select = criteriaQuery.select(from);

        TypedQuery<T> typedQuery = em.createQuery(select);

        return typedQuery.getResultList();
    }
}
