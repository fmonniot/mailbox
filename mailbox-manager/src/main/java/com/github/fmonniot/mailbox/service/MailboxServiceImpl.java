package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.persistence.BoxDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

@Singleton
public class MailboxServiceImpl implements MailboxService {

    @Inject
    private BoxDao dao;

    @Override
    public List<Box> listByClientId(long clientId) {
        return dao.getBoxesByClientId(clientId);
    }

    @Override
    public Box get(Long mailboxId) {
        if (mailboxId == null) {
            return null;
        }
        try {
            return dao.findBox(mailboxId);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean delete(Long mailboxID) {
        if (mailboxID == null) {
            return false;
        }

        try {
            dao.deleteBox(mailboxID);
        } catch (EntityNotFoundException | NoResultException e) {
            return false;
        }

        return true;
    }

    private Box create(Box mailbox) throws EntityExistsException {
        return dao.createBox(new Box(mailbox));
    }

    @Override
    public Box create(Box mailbox, Long clientId) throws EntityExistsException {
        if (clientId != null) {
            mailbox.setClientId(clientId);
        }

        return create(mailbox);
    }
}
