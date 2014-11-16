package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;

import javax.inject.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Singleton
public class MessageDaoImpl implements MessageDao {
    @Override
    public List<Message> listInBox(Box box) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT msg FROM Message AS msg WHERE msg.box = :box");
        selectByIdQuery.setParameter("box", box);

        //noinspection unchecked
        return selectByIdQuery.getResultList();
    }

    private Message findMessage(long messageId) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT msg FROM Message AS msg WHERE msg.id = :id");
        selectByIdQuery.setParameter("id", messageId);

        //noinspection unchecked
        return (Message) selectByIdQuery.getSingleResult();
    }

    @Override
    public Message create(final Message message) {
        final EntityManager em = JpaHelpers.getEntityManager();
        Message exist = findMessage(message.getId());

        if (exist != null) {
            throw new EntityExistsException();
        }

        try {
            JpaHelpers.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    em.persist(message);
                }
            });
        } catch (JpaHelpers.TransactionException e) {
            e.printStackTrace();
            return null;
        }

        return message;
    }
}
