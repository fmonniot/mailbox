package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Singleton
public class MessageDaoImpl extends AbstractDao<Message> implements MessageDao {

    public MessageDaoImpl() {
        super("Message");
    }

    @Override
    public List<Message> listInBox(Box box) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT msg FROM Message AS msg WHERE msg.box = :box");
        selectByIdQuery.setParameter("box", box);

        //noinspection unchecked
        return selectByIdQuery.getResultList();
    }

    @Override
    public void deleteMessage(Long id) {
        delete(findById(id));
    }
}
