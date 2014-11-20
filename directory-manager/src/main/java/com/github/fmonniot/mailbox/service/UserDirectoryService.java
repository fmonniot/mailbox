package com.github.fmonniot.mailbox.service;

import com.github.fmonniot.mailbox.entity.NewsGroupRight;
import com.github.fmonniot.mailbox.entity.User;

import java.util.List;

public interface UserDirectoryService {
    public User create(User user);
    public boolean delete(Long userId);
    public List<User> lookupAllUsers();
    public NewsGroupRight lookupUserRights(Long userId);
    public boolean updateUserRights(Long userId, NewsGroupRight right);
}
