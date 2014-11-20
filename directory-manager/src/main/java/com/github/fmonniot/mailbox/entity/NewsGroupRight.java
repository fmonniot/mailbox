package com.github.fmonniot.mailbox.entity;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class NewsGroupRight implements Serializable {
    boolean readAccess = false;
    boolean writeAccess = false;

    public NewsGroupRight() {}

    public NewsGroupRight(boolean readNewsGroup, boolean writeNewsGroup) {
        if (readNewsGroup)
            setReadAccess();
        if (writeNewsGroup)
            setWriteAccess();
    }

    public boolean getReadAccess() {return readAccess;}
    public void setReadAccess() {readAccess = true;}
    public boolean getWriteAccess() {return writeAccess;}
    public void setWriteAccess() {writeAccess = true;}
    public void unsetWriteAccess() { writeAccess = false;}
    public void unsetReadAccess () { readAccess = false;}

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
