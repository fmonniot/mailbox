package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;

import java.util.List;

public interface MessageDao {

    List<Message> listInBox(Box box);

    Message create(Message message);

    void deleteMessage(Long id);
}
