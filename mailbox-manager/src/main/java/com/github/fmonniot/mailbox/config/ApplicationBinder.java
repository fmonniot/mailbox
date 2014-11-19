package com.github.fmonniot.mailbox.config;

import com.github.fmonniot.mailbox.persistence.BoxDao;
import com.github.fmonniot.mailbox.persistence.BoxDaoImpl;
import com.github.fmonniot.mailbox.persistence.MessageDao;
import com.github.fmonniot.mailbox.persistence.MessageDaoImpl;
import com.github.fmonniot.mailbox.remote.RightsService;
import com.github.fmonniot.mailbox.remote.RightsServiceImpl;
import com.github.fmonniot.mailbox.service.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // Dao
        bind(BoxDaoImpl.class).to(BoxDao.class);
        bind(MessageDaoImpl.class).to(MessageDao.class);

        // Services
        bind(RightsServiceImpl.class).to(RightsService.class);
        bind(MailboxServiceImpl.class).to(MailboxService.class);
        bind(MessageServiceImpl.class).to(MessageService.class);
        bind(NewsboxServiceImpl.class).to(NewsboxService.class);
    }
}
