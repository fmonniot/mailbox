package com.github.fmonniot.mailbox.remote;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;

public interface RightsService {
    /**
     * @param clientId ID corresponding to the client
     * @param message  the message the client want to post
     *
     * @return whether or not the client can post her message
     */
    @SuppressWarnings({"SameReturnValue", "UnusedParameters"})
    boolean canPost(long clientId, Message message);

    /**
     * @param clientId ID corresponding to the client
     * @param box      the box the client want to read from
     *
     * @return whether or not the client can read her messages
     */
    boolean canRead(long clientId, Box box);
}
