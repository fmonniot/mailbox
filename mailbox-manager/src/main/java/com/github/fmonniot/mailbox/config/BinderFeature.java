package com.github.fmonniot.mailbox.config;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

public class BinderFeature implements Feature {
    @Override
    public boolean configure(final FeatureContext context) {
        context.register(new ApplicationBinder());
        return true;
    }
}
