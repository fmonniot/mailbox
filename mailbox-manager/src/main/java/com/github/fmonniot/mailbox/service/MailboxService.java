package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Box;

import java.util.List;

public interface MailboxService {

    List<Box> listByClientId(long clientId);

    Box get(Long mailboxId);

    boolean delete(Box mailbox);

    Box create(Box mailbox);
}
