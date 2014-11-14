package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.Mailbox;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface MailboxDao {
    List<Mailbox> getBoxes(long clientId);

    Mailbox findBox(long boxId);

    Mailbox createBox(final Mailbox box) throws EntityExistsException;

    void deleteBox(Mailbox box) throws EntityNotFoundException;
}
