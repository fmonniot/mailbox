package com.github.fmonniot.mailbox.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class NewsGroupRight implements Serializable {
    private boolean readAccess = false;
    private boolean writeAccess = false;
}
