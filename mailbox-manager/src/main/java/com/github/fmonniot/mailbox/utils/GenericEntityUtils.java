package com.github.fmonniot.mailbox.utils;

import javax.ws.rs.core.GenericEntity;

public class GenericEntityUtils {

    public static <T> GenericEntity<T> generify(T object) {
        return new GenericEntity<T>(object) {
        };
    }
}
