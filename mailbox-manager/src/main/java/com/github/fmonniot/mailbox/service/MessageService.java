package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Message;

import java.util.List;

public interface MessageService {

    /**
     * @param clientId the client who wants the messages
     *
     * @return the listForClient of message of the given client
     */
    List<Message> listForClient(Long clientId);

    /**
     * Post a message
     *
     * @param clientId Id of the client who post the message
     * @param message  the message to post
     *
     * @return the message posted (with generated attributes)
     */
    Message post(Long clientId, Message message);
}