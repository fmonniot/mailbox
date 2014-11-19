package com.github.fmonniot.mailbox.persistence;


import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface BoxDao {
    Box findBox(long boxId);

    Box createBox(final Box box) throws EntityExistsException;

    void deleteBox(long boxId) throws EntityNotFoundException;

    List<Box> getBoxesByClientId(long clientId);

    /**
     * @return the box with `boxType="newsbox"`
     */
    Box findOrCreateNewsBox();

    void addMessageToBox(Message message, Box box);
}
