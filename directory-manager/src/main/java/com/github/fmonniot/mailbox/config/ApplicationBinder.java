package com.github.fmonniot.mailbox.config;

import com.github.fmonniot.mailbox.persistence.UserDao;
import com.github.fmonniot.mailbox.persistence.UserDaoImpl;
import com.github.fmonniot.mailbox.service.UserDirectoryService;
import com.github.fmonniot.mailbox.service.UserDirectoryServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // Dao
        bind(UserDaoImpl.class).to(UserDao.class);

        // Services
        bind(UserDirectoryServiceImpl.class).to(UserDirectoryService.class);
    }
}
