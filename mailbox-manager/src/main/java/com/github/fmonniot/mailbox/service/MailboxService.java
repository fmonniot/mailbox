package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Box;

import javax.persistence.EntityExistsException;
import java.util.List;

public interface MailboxService {

    List<Box> listByClientId(long clientId);

    Box get(Long mailboxId);

    boolean delete(Long mailboxID);

    Box create(Box mailbox, Long clientId) throws EntityExistsException;
}
