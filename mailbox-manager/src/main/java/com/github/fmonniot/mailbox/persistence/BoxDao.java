package com.github.fmonniot.mailbox.persistence;


import com.github.fmonniot.mailbox.entity.Box;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface BoxDao {
    Box findBox(long boxId);

    Box createBox(final Box box) throws EntityExistsException;

    void deleteBox(Box box) throws EntityNotFoundException;

    List<Box> getBoxesByClientId(long clientId);
}
