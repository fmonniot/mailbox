package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Message;

import javax.inject.Singleton;

@Singleton
public class RightsServiceImpl implements RightsService {
    @Override
    public boolean canPost(long clientId, Message message) {
        return true;
    }
}
