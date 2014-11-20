package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;
import com.github.fmonniot.mailbox.persistence.BoxDao;
import com.github.fmonniot.mailbox.persistence.MessageDao;
import com.github.fmonniot.mailbox.remote.RightsService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jersey.repackaged.com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class MessageServiceImpl implements MessageService {

    @Inject
    private MessageDao messageDao;

    @Inject
    private BoxDao boxDao;

    @Inject
    private RightsService rightsService;

    @Inject
    private NewsboxService newsboxService;

    @Override
    public List<Message> listForClient(Long clientId) {
        checkNotNull(clientId, "clientId cannot be null");

        List<Box> boxes = boxDao.getBoxesByClientId(clientId);

        List<Message> messages = new ArrayList<>();
        for (Box box : boxes) {
            messages.addAll(messageDao.listInBox(box));
        }

        return messages;
    }

    @Override
    public Message post(Long clientId, Long boxId, Message message) {
        checkNotNull(clientId, "clientId cannot be null");
        checkNotNull(message, "message cannot be null");

        checkNotNull(boxId, "a message must be sent to a box");

        // Check if user can post a message
        if (!rightsService.canPost(clientId, message)) {
            throw new IllegalStateException("Client has no rights to post this message");
        }

        // Set necessary information
        message.setSendingDate(new Date());

        Box b = boxDao.findBox(boxId);
        boxDao.addMessageToBox(message, b);

        // Create and return the message or throw an exception
        return message;
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            return false;
        }

        try {
            messageDao.deleteMessage(id);
        } catch (EntityNotFoundException | NoResultException e) {
            return false;
        }

        return true;
    }
}
