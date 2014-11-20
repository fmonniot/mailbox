package com.github.fmonniot.mailbox.persistence;


import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;

import javax.inject.Singleton;
import javax.persistence.*;
import java.util.List;

@Singleton
public class BoxDaoImpl extends AbstractDao<Box> implements BoxDao {

    public BoxDaoImpl() {
        super("Box");
    }

    @Override
    public List<Box> getBoxesByClientId(long clientId) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT mb FROM Box AS mb WHERE mb.clientId = :id");
        selectByIdQuery.setParameter("id", clientId);

        //noinspection unchecked
        return selectByIdQuery.getResultList();
    }

    @Override
    public Box findOrCreateNewsBox() {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT mb FROM Box AS mb WHERE mb.boxType = 'newsbox'");

        Box newsbox;
        try {
            newsbox = (Box) selectByIdQuery.getSingleResult();
        } catch (NoResultException ignored) {
            newsbox = create(new Box("Common Newsbox", "newsbox"));
        }

        return newsbox;
    }

    @Override
    public void addMessageToBox(Message message, Box box) {
        EntityManager em = JpaHelpers.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        message.setBox(box);
        em.persist(message);
        box.getMessages().add(message);
        em.merge(box);
        et.commit();

        System.out.println("");
    }

    /**
     * @deprecated
     */
    @Override
    public Box findBox(long boxId) {
        return findById(boxId);
    }

    /**
     * @deprecated
     */
    @Override
    public Box createBox(final Box box) throws EntityExistsException {
        return create(box);
    }

    /**
     * @deprecated
     * @param boxId the box id that we want deleted
     */
    @Override
    public void deleteBox(final long boxId) throws EntityNotFoundException {
        delete(findById(boxId));
    }

}
