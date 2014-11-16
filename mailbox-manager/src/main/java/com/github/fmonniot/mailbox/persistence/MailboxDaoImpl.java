package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.Mailbox;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.List;

@RequestScoped
public class MailboxDaoImpl implements MailboxDao {

    @Override
    public List<Mailbox> getBoxesByClientId(long clientId) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT mb FROM Mailbox AS mb WHERE mb.clientId = :id");
        selectByIdQuery.setParameter("id", clientId);

        //noinspection unchecked
        return selectByIdQuery.getResultList();
    }

    @Override
    public Mailbox findBox(long boxId) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT mb FROM Mailbox AS mb WHERE mb.id = :id");
        selectByIdQuery.setParameter("id", boxId);

        //noinspection unchecked
        return (Mailbox) selectByIdQuery.getSingleResult();
    }

    @Override
    public Mailbox createBox(final Mailbox box) throws EntityExistsException {
        final EntityManager em = JpaHelpers.getEntityManager();
        Mailbox exist = findBox(box.getId());

        if (exist != null) {
            throw new EntityExistsException();
        }

        try {
            JpaHelpers.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    em.persist(box);
                }
            });
        } catch (JpaHelpers.TransactionException e) {
            e.printStackTrace();
            return null;
        }

        return box;
    }

    @Override
    public void deleteBox(Mailbox box) throws EntityNotFoundException {
        EntityManager em = JpaHelpers.getEntityManager();
        Mailbox exist = findBox(box.getId());

        if (exist == null) {
            throw new EntityNotFoundException();
        }

        em.remove(exist);
    }

}
