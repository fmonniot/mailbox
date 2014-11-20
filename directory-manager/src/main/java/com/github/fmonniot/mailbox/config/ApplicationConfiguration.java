package com.github.fmonniot.mailbox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.github.fmonniot.mailbox.api.UserDirectoryEndpoint;
import com.google.common.collect.ImmutableSet;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * see http://stackoverflow.com/a/23332716 and http://www.trajano.net/2014/10/predictiability-and-versioning-jax-rs-rest-api/
 * to understand the configuration of this application.
 */
public class ApplicationConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new ImmutableSet.Builder<Class<?>>()
                .add(UserDirectoryEndpoint.class)
                .add(BinderFeature.class)
                .build();
    }

    @Override
    public Set<Object> getSingletons() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JaxbAnnotationModule());
        return ImmutableSet
                .builder()
                .add(new JacksonJaxbJsonProvider(mapper,
                        JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS))
                .build();
    }
}
