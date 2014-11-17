package com.github.fmonniot.mailbox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.github.fmonniot.mailbox.api.MailboxEndpoint;
import com.github.fmonniot.mailbox.api.MessageEndpoint;
import com.google.common.collect.ImmutableSet;

import javax.ws.rs.core.Application;
import java.util.Set;

public class ApplicationConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new ImmutableSet.Builder<Class<?>>()
                .add(MailboxEndpoint.class)
                .add(MessageEndpoint.class)
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
