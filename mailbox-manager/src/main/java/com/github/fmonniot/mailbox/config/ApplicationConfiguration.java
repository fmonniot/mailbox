package com.github.fmonniot.mailbox.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfiguration extends ResourceConfig {

    public ApplicationConfiguration() {
        register(JacksonFeature.class);
        register(new ApplicationBinder());

        packages(true, "com.github.fmonniot.mailbox.api");
    }
}
