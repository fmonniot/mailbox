package com.github.fmonniot.mailbox.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
    private NewsGroupRight permission;

    public User() {}

    public User(String userName, NewsGroupRight permission) {
        setUserName(userName);
        setPermission(permission);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getId() {
        return this.id;
    }

    public NewsGroupRight getPermission() {
        return permission;
    }

    public void setPermission(NewsGroupRight permission) {
        this.permission = permission;
    }

    public String toString() {
        return "User " + getUserName() + " with " + getPermission().toString();
    }
}
