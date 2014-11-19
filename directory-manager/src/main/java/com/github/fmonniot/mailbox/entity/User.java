package com.github.fmonniot.mailbox.entity;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.CascadeType.ALL;

@Entity(name = "User")
@Table(name = "mb_user")
public class User implements EntityIdentifiable, Serializable {
    private String userName;

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private NewsGroupRight right;

    public User() {}

    public User(String userName, NewsGroupRight right) {
        setUserName(userName);
        setRight(right);
    }

    public User(String userName, boolean read, boolean write) {
        setUserName(userName);
        setRight(new NewsGroupRight(read, write));
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public NewsGroupRight getRight() {
        return right;
    }

    public void setRight(NewsGroupRight right) {
        this.right = right;
    }
}
