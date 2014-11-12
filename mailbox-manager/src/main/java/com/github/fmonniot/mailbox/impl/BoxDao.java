package com.github.fmonniot.mailbox.impl;

import com.github.fmonniot.mailbox.Box;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.*;
import java.util.List;

public class BoxDao<T extends Box> {

    private final EntityManager em;


    public BoxDao(EntityManager em) {
        this.em = em;
    }

    public List<T> getBoxes(long clientId) {
        Query selectByIdQuery = em.createQuery("SELECT mb FROM Mailbox AS mb WHERE mb.id = :id");
        selectByIdQuery.setParameter("id", clientId);

        //noinspection unchecked
        return selectByIdQuery.getResultList();
    }

    public T findBox(Long boxId) {
        if (boxId == null) {
            return null;
        }
        Query selectByIdQuery = em.createQuery("SELECT mb FROM Mailbox AS mb WHERE mb.id = :id");
        selectByIdQuery.setParameter("id", boxId);

        //noinspection unchecked
        return (T) selectByIdQuery.getSingleResult();
    }

    // TODO throw an exception if box already exist
    public T createBox(T box) {
        T exist = findBox(box.getId());

        if (exist == null) {
            UserTransaction transaction = null;
            try {
                transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            } catch (NamingException e) {
                e.printStackTrace();
            }
            assert transaction != null;
            try {
                transaction.begin();
                em.persist(box);
                transaction.commit();
            } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException |
                    SystemException | NotSupportedException e) {
                e.printStackTrace();
            }
            return box;
        }

        return exist;
    }

    // TODO Throw different type of exception instead of a boolean?
    public boolean deleteBox(T box) {
        T exist = findBox(box.getId());
        if (exist == null) {
            return false;
        }

        em.remove(exist);
        return true;
    }
}
