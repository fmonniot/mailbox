package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;
import com.github.fmonniot.mailbox.persistence.BoxDao;
import com.github.fmonniot.mailbox.persistence.MessageDao;

import javax.inject.Inject;
import javax.inject.Singleton;
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

    @Override
    public List<Message> listForClient(Long clientId) {
        checkNotNull(clientId, "clientId cannot be null");

        Box mailbox = boxDao.findBox(clientId);
        checkNotNull(mailbox, "This user doesn't have a mailbox.");

        return messageDao.listInBox(mailbox);
    }

    @Override
    public Message post(Long clientId, Message message) {
        checkNotNull(clientId, "clientId cannot be null");
        checkNotNull(message, "message cannot be null");

        checkNotNull(message.getBox(), "a message must be sent to a box");
        checkNotNull(message.getBox().getId(), "the destination box must specify an id");

        // Check if user can post a message
        if (!rightsService.canPost(clientId, message)) {
            throw new IllegalStateException("Client has no rights to post this message");
        }

        // Set necessary information
        message.setSendingDate(new Date());

        // Create and return the message or throw an exception
        return messageDao.create(message);
    }
}
