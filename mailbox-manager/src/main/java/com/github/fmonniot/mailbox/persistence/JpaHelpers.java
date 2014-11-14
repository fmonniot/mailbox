package com.github.fmonniot.mailbox.persistence;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.*;

class JpaHelpers {
    private static final String DEFAULT_PU = "mailbox-pu";

    static EntityManager getEntityManager() {
        return getEntityManager(DEFAULT_PU);
    }

    static EntityManager getEntityManager(String persistentUnit) {
        return Persistence.createEntityManagerFactory(persistentUnit).createEntityManager();
    }

    static UserTransaction getTransaction() {
        try {
            return (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        } catch (NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void runInTransaction(Runnable runnable) throws TransactionException {
        try {
            UserTransaction transaction = JpaHelpers.getTransaction();
            transaction.begin();
            runnable.run();
            transaction.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException | NotSupportedException e) {
            e.printStackTrace();
            throw new TransactionException();
        }
    }

    static class TransactionException extends Exception {

    }
}
