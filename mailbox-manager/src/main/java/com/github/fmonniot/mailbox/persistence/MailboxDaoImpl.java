package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.Mailbox;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.List;

@RequestScoped
public class MailboxDaoImpl extends AbstractDao<Mailbox> implements MailboxDao {

    public MailboxDaoImpl() {
        super("Mailbox");
    }

    @Override
    public List<Mailbox> getBoxesByClientId(long clientId) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT mb FROM Mailbox AS mb WHERE mb.clientId = :id");
        selectByIdQuery.setParameter("id", clientId);

        //noinspection unchecked
        return selectByIdQuery.getResultList();
    }

    /**
     * @deprecated
     */
    @Override
    public Mailbox findBox(long boxId) {
        return findById(boxId);
    }

    /**
     * @deprecated
     */
    @Override
    public Mailbox createBox(final Mailbox box) throws EntityExistsException {
        return create(box);
    }

    /**
     * @deprecated
     */
    @Override
    public void deleteBox(Mailbox box) throws EntityNotFoundException {
        delete(box);
    }

}
