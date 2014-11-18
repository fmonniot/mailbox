package com.github.fmonniot.mailbox;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class Scenario {
    public static final String GREEN = "\033[32m";
    public static final String RED = "\033[31m";
    public static final String DEFAULT_COLOR = "\033[0m";
    private static final String VIOLET = "\033[35m";

    private final String title;
    private final List<Step> steps = new ArrayList<>();
    private final WebTarget target;

    public Scenario(String title, String targetUrl) {
        this.title = title;

        Client client = ClientBuilder.newClient().register(JacksonFeature.class);
        target = client.target(targetUrl);
    }

    public static String expectActual(Object expected, Object actual) {
        return String.format("expected=%s, actual=%s", expected, actual);
    }

    public Scenario step(Step step) {
        steps.add(step);
        return this;
    }

    public void play() {
        System.out.println("Begin Scenario: " + title);
        if (steps.size() < 1) {
            System.out.println(VIOLET + "No step defined in this scenario" + DEFAULT_COLOR);
            return;
        }

        for (Step step : steps) {
            System.out.println("\tStep " + (steps.indexOf(step) + 1) + ":\t" + step.description());
            Response response = step.action(target);
            Result result = step.verify(response);
            System.out.println("\t" + (result.isOk() ? GREEN : RED) + result.getReason() + DEFAULT_COLOR);
        }
    }

    public static abstract class Step {
        abstract String description();

        abstract Response action(WebTarget target);

        abstract Result verify(Response response);
    }

    public static class Result {
        private final String reasonNok;
        private boolean ok;
        private String reasonOk;

        public Result(boolean ok, String reasonOk, String reasonNok) {
            this.ok = ok;
            this.reasonOk = reasonOk;
            this.reasonNok = reasonNok;
        }

        public boolean isOk() {
            return ok;
        }

        public String getReason() {
            return ok ? reasonOk : reasonNok;
        }
    }
}
