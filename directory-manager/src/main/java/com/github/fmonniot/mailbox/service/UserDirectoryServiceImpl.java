package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.NewsGroupRight;
import com.github.fmonniot.mailbox.entity.User;
import com.github.fmonniot.mailbox.persistence.UserDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.*;
import java.util.List;

@Singleton
public class UserDirectoryServiceImpl implements UserDirectoryService {

    @Inject
    private UserDao dao;

    @Override
    public User create(User user) throws EntityExistsException {
        return dao.createUser(user);
    }

    @Override
    public boolean delete(Long userId) {
        if (userId == null) {
            return false;
        }

        try {
            dao.deleteUser(userId);
        } catch (EntityNotFoundException | NoResultException e) {
            return false;
        }

        return true;
    }

    @Override
    public NewsGroupRight lookupUserRights(Long userId) {
        if (userId == null) {
            return null;
        }
        try {
            return dao.lookupUserRights(userId);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean updateUserRights(Long userId, NewsGroupRight right) {
        if (userId == null) {
            return false;
        }
        try {
            dao.setUserRights(userId, right);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public List<User> lookupAllUsers() {
        return dao.lookupAllUsers();
    }

}
