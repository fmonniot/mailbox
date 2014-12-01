package com.github.fmonniot.mailbox.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "User")
@Table(name = "mb_user")
public class User implements EntityIdentifiable, Serializable {
    private String userName;

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private NewsGroupRight permission;
}
