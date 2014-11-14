package com.github.fmonniot.mailbox.config;

import com.github.fmonniot.mailbox.persistence.MailboxDao;
import com.github.fmonniot.mailbox.persistence.MailboxDaoImpl;
import com.github.fmonniot.mailbox.service.MailboxService;
import com.github.fmonniot.mailbox.service.MailboxServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // Dao
        bind(MailboxDaoImpl.class).to(MailboxDao.class);

        // Services
        bind(MailboxServiceImpl.class).to(MailboxService.class);
    }
}
