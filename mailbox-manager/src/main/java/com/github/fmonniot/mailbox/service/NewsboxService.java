package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Box;

public interface NewsboxService {
    Box getForClient(Long clientId);
}
