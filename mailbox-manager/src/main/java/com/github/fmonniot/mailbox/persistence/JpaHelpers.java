package com.github.fmonniot.mailbox.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

class JpaHelpers {
    private static final String DEFAULT_PU = "mailbox-pu";

    static EntityManager getEntityManager() {
        return getEntityManager(DEFAULT_PU);
    }

    static EntityManager getEntityManager(String persistentUnit) {
        return Persistence.createEntityManagerFactory(persistentUnit).createEntityManager();
    }
}
