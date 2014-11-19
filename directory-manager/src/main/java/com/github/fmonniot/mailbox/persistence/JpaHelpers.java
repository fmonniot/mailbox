package com.github.fmonniot.mailbox.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

class JpaHelpers {
    private static final String DEFAULT_PU = "directory-pu";

    static EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory(JpaHelpers.DEFAULT_PU).createEntityManager();
    }
}
