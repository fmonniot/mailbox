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
    private ArrayList<Preparation> preparations = new ArrayList<>();

    public Scenario(String title, String targetUrl) {
        this.title = title;

        Client client = ClientBuilder.newClient().register(JacksonFeature.class);
        target = client.target(targetUrl);
    }

    public static String expectActual(Object expected, Object actual) {
        return String.format("expected=%s, actual=%s", expected, actual);
    }

    public Scenario before(Preparation preparation) {
        preparations.add(preparation);
        return this;
    }

    public Scenario step(Step step) {
        steps.add(step);
        return this;
    }

    public void play() {
        for (Preparation preparation : preparations) {
            preparation.exec(target);
        }

        System.out.println("Begin Scenario: " + title);
        if (steps.size() < 1) {
            System.out.println(VIOLET + "No step defined in this scenario" + DEFAULT_COLOR);
            return;
        }

        for (Step step : steps) {
            System.out.println("\tStep " + (steps.indexOf(step) + 1) + ":\t" + step.description());
            Response response = step.action(target);
            Result result = step.verify(response);
            if (result.hasReason()) {
                System.out.println("\t" + result.getReason());
            }
        }
    }

    public static interface Preparation {
        public void exec(WebTarget target);
    }

    public static abstract class Step {

        private String description;

        public Step(String description) {
            this.description = description;
        }

        String description() {
            return description;
        }

        abstract Response action(WebTarget target);

        Result verify(Response response) {
            return new Result("");
        }
    }

    public static class Result {
        private String reasonOk;
        private String reasonNok;
        private boolean ok;
        private boolean manichean;

        public Result(String reasonOk) {
            this.manichean = false;
            this.reasonOk = reasonOk;
        }

        public Result(boolean ok, String reasonOk, String reasonNok) {
            this.manichean = true;
            this.ok = ok;
            this.reasonOk = reasonOk;
            this.reasonNok = reasonNok;
        }

        public boolean hasReason() {
            return getReason().length() > (GREEN.length() + DEFAULT_COLOR.length());
        }

        public String getReason() {
            if (!manichean) {
                return VIOLET + reasonOk + DEFAULT_COLOR;
            } else if (ok) {
                return GREEN + reasonOk + DEFAULT_COLOR;
            } else {
                return RED + reasonNok + DEFAULT_COLOR;
            }
        }
    }

    static class Bag<T> {
        private T obj;

        public T get() {
            return obj;
        }

        public void set(T obj) {
            this.obj = obj;
        }
    }
}
