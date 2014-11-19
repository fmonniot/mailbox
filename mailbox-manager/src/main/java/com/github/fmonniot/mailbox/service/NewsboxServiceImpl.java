package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.persistence.BoxDao;
import com.github.fmonniot.mailbox.remote.RightsService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NewsboxServiceImpl implements NewsboxService {

    @Inject
    private BoxDao dao;

    @Inject
    private RightsService rightsService;

    @Override
    public Box getForClient(Long clientId) {
        Box newsbox = dao.findOrCreateNewsBox();

        if (!rightsService.canRead(clientId, newsbox)) {
            return null;
        }

        return newsbox;
    }
}
