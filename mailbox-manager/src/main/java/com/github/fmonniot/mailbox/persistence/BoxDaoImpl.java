package com.github.fmonniot.mailbox.persistence;


import com.github.fmonniot.mailbox.entity.Box;

import javax.inject.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.List;

@Singleton
public class BoxDaoImpl extends AbstractDao<Box> implements BoxDao {

    public BoxDaoImpl() {
        super("Mailbox");
    }

    @Override
    public List<Box> getBoxesByClientId(long clientId) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT mb FROM Box AS mb WHERE mb.clientId = :id");
        selectByIdQuery.setParameter("id", clientId);

        //noinspection unchecked
        return selectByIdQuery.getResultList();
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
     */
    @Override
    public void deleteBox(Box box) throws EntityNotFoundException {
        delete(box);
    }

}
