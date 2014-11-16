package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Mailbox;

import java.util.List;

public interface MailboxService {

    List<Mailbox> listByClientId(long clientId);

    Mailbox get(Long mailboxId);

    boolean delete(Mailbox mailbox);

    Mailbox create(Mailbox mailbox);
}
