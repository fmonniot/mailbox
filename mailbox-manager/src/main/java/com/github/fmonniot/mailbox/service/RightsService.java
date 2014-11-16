package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Message;

public interface RightsService {
    boolean canPost(long clientId, Message message);
}
