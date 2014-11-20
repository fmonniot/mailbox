package com.github.fmonniot.mailbox.persistence;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.github.fmonniot.mailbox.entity.NewsGroupRight;
import com.github.fmonniot.mailbox.entity.User;

import java.util.List;

public interface UserDao {
    NewsGroupRight lookupUserRights(long userId);

    void setUserRights(long userId, NewsGroupRight right);

    User createUser(final User user) throws EntityExistsException;

    void deleteUser(long userId) throws EntityNotFoundException;

    List<User> lookupAllUsers();
}
