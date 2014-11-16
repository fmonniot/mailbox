package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Mailbox;
import com.github.fmonniot.mailbox.persistence.MailboxDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Singleton
public class MailboxServiceImpl implements MailboxService {

    @Inject
    private MailboxDao dao;

    @Override
    public List<Mailbox> listByClientId(long clientId) {
        return dao.getBoxes(clientId);
    }

    @Override
    public Mailbox get(Long mailboxId) {
        if (mailboxId == null) {
            return null;
        }

        return dao.findBox(mailboxId);
    }

    @Override
    public boolean delete(Mailbox mailbox) {
        try {
            dao.deleteBox(mailbox);
        } catch (EntityNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public Mailbox create(Mailbox mailbox) {
        return dao.createBox(new Mailbox("whatever"));
    }
}
