package com.github.fmonniot.mailbox.remote;

import com.github.fmonniot.mailbox.entity.Message;

public interface RightsService {
    /**
     * Mock service while directory is not implemented
     *
     * @param clientId ID corresponding to the client
     * @param message  the message the client want to post
     *
     * @return whether or not the client can post her message
     */
    @SuppressWarnings({"SameReturnValue", "UnusedParameters"})
    boolean canPost(long clientId, Message message);
}
