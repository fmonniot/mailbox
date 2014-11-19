package com.github.fmonniot.mailbox.remote;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;

import javax.inject.Singleton;

/**
 * Mock service while directory is not implemented
 */
@Singleton
public class RightsServiceImpl implements RightsService {
    @Override
    public boolean canPost(long clientId, Message message) {
        return true;
    }

    @Override
    public boolean canRead(long clientId, Box box) {
        return true;
    }
}
