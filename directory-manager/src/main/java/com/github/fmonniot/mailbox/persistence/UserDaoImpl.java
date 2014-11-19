package com.github.fmonniot.mailbox.persistence;

import com.github.fmonniot.mailbox.entity.NewsGroupRight;
import com.github.fmonniot.mailbox.entity.User;

import javax.inject.Singleton;
import javax.persistence.*;
import java.util.List;

@Singleton
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public UserDaoImpl() {
        super("User");
    }

    @Override
    public List<NewsGroupRight> lookupUserRights(final long userId) {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT user.right FROM User AS user");

        //noinspection unchecked
        return selectByIdQuery.getResultList();
    }

    @Override
    public void setUserRights(final long userId, final NewsGroupRight right) {
        final EntityManager em = JpaHelpers.getEntityManager();
        User user = findById(userId);

        if (user == null) {
            throw new EntityNotFoundException();
        }

        user.setRight(right);

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(user);
        em.flush();
        et.commit();
    }

    @Override
    public User createUser(final User user) throws EntityExistsException {
        return create(user);
    }

    @Override
    public void deleteUser(final long userId) throws EntityNotFoundException {
        delete(findById(userId));
    }

    @Override
    public List<User> lookupAllUsers() {
        EntityManager em = JpaHelpers.getEntityManager();
        Query selectByIdQuery = em.createQuery("SELECT user FROM User AS user");

        //noinspection unchecked
        return selectByIdQuery.getResultList();
    }

}
