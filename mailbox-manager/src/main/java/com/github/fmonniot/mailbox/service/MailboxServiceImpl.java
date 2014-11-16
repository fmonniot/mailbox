package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.persistence.BoxDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
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

        return dao.findBox(mailboxId);
    }

    @Override
    public boolean delete(Box mailbox) {
        try {
            dao.deleteBox(mailbox);
        } catch (EntityNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public Box create(Box mailbox) {
        return dao.createBox(mailbox);
    }
}
