package com.github.fmonniot.mailbox.config;

import com.github.fmonniot.mailbox.persistence.MailboxDao;
import com.github.fmonniot.mailbox.persistence.MailboxDaoImpl;
import com.github.fmonniot.mailbox.persistence.MessageDao;
import com.github.fmonniot.mailbox.persistence.MessageDaoImpl;
import com.github.fmonniot.mailbox.service.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // Dao
        bind(MailboxDaoImpl.class).to(MailboxDao.class);
        bind(MessageDaoImpl.class).to(MessageDao.class);

        // Services
        bind(RightsServiceImpl.class).to(RightsService.class);
        bind(MailboxServiceImpl.class).to(MailboxService.class);
        bind(MessageServiceImpl.class).to(MessageService.class);
    }
}
