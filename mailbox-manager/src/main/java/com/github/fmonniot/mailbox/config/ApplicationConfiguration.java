package com.github.fmonniot.mailbox.config;

import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfiguration extends ResourceConfig {

    public ApplicationConfiguration() {
        register(new ApplicationBinder());

        packages(true, "com.github.fmonniot.mailbox.api");
    }
}
