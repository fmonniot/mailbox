package com.github.fmonniot.mailbox.entity;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class NewsGroupRight implements Serializable {
    boolean readAccess = false;
    boolean writeAccess = false;

    public NewsGroupRight() {}

    public NewsGroupRight(boolean readAccess, boolean writeAccess) {
        setReadAccess(readAccess);
        setWriteAccess(writeAccess);
    }

    public boolean getReadAccess() {return readAccess;}
    public void setReadAccess(boolean readAccess) {this.readAccess = readAccess;}
    public boolean getWriteAccess() {return writeAccess;}
    public void setWriteAccess(boolean writeAccess) {this.writeAccess = writeAccess;}

    public String toString() {
        if (getReadAccess()) {
            if (getWriteAccess()) {
                return "rw";
            } else {
                return "ro";
            }
        } else {
            if (getWriteAccess()) {
                return "wo";
            } else {
                return "no";
            }
        }
    }
}
