/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 François Monniot & Alexis Mousset
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.fmonniot.mailbox;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static com.github.fmonniot.mailbox.Scenario.expectActual;

public class App {
    public static void main(String[] args) {
        String baseUrl;
        if (args.length > 0) {
            baseUrl = args[0];
        } else {
            baseUrl = "http://localhost:8080/mailbox-manager/api/v1/";
        }
        System.out.println(baseUrl);
        scenarioAdmin(baseUrl).play();
        scenarioUser(baseUrl).play();
    }

    private static Scenario scenarioAdmin(String baseUrl) {
        final List<Box> boxBag = new ArrayList<>(1);

        return new Scenario("An admin wants to manage mailboxes.", baseUrl)
                .step(new Scenario.Step("Create a new mailbox with name `mb`") {
                    Box box = new Box("mb", "mailbox");

                    @Override
                    public Response action(WebTarget target) {
                        return target.path("mailbox")
                                .request()
                                .header("X-Client-ID", 1)
                                .accept(MediaType.APPLICATION_JSON)
                                .post(Entity.entity(box, MediaType.APPLICATION_JSON));
                    }

                    @Override
                    public Scenario.Result verify(Response response) {
                        Box received = response.readEntity(Box.class);
                        boxBag.add(received);
                        boolean result = box.getBoxType().equals(received.getBoxType()) &&
                                box.getName().equals(received.getName());

                        return new Scenario.Result(result,
                                "Entity received is valid",
                                expectActual(box, received));
                    }
                })
                .step(new Scenario.Step("List all mailbox of owner") {
                    @Override
                    public Response action(WebTarget target) {
                        return target.path("mailbox")
                                .request()
                                .header("X-Client-ID", 1)
                                .accept(MediaType.APPLICATION_JSON)
                                .get();
                    }

                    @Override
                    public Scenario.Result verify(Response response) {
                        List<Box> boxes = response.readEntity(new GenericType<List<Box>>() {
                        });
                        boolean result = false;
                        for (Box box : boxes) {
                            if (box.getId().equals(boxBag.get(0).getId())) {
                                result = true;
                                break;
                            }
                        }

                        return new Scenario.Result(result,
                                "Found " + boxes.size() + " boxes for this user",
                                "Box previously created not found");
                    }
                })
                .step(new Scenario.Step("Remove the previously created mailbox") {
                    @Override
                    Response action(WebTarget target) {
                        return target.path("mailbox/" + boxBag.get(0).getId())
                                .request()
                                .accept(MediaType.APPLICATION_JSON)
                                .delete();
                    }

                    @Override
                    Scenario.Result verify(Response response) {
                        boolean result = response.getStatus() == 200;
                        return new Scenario.Result(result,
                                "Entity marked as deleted",
                                "Entity not marked as deleted [" + expectActual(200, response.getStatus()) + "]");
                    }
                });
    }

    public static Scenario scenarioUser(String baseUrl) {
        final BoxBag mailbox1 = new BoxBag();
        final BoxBag mailbox2 = new BoxBag();

        return new Scenario("A User wants to consult her mails.", baseUrl)
                .before(createMailboxAndStoreIdIn(mailbox1, 1))
                .before(createMailboxAndStoreIdIn(mailbox2, 2))
                .step(new Scenario.Step("List all messages of user 1") {
                    @Override
                    Response action(WebTarget target) {
                        return target.path("message")
                                .request()
                                .header("X-Client-ID", mailbox1.getClientId())
                                .accept(MediaType.APPLICATION_JSON)
                                .get();
                    }

                    @Override
                    Scenario.Result verify(Response response) {
                        List<Message> messages = response.readEntity(new GenericType<List<Message>>() {
                        });

                        return new Scenario.Result("Found " + messages.size() + " messages for this user");
                    }
                })
                .step(new Scenario.Step("Post a message to a mailbox") {
                    @Override
                    Response action(WebTarget target) {
                        return target.path("message")
                                .request()
                                .header("X-Client-ID", mailbox1.getClientId())
                                .accept(MediaType.APPLICATION_JSON)
                                .post(Entity.entity(
                                        new Message("senderName", "receiverName", "subject", "body", mailbox2.get()),
                                        MediaType.APPLICATION_JSON
                                ));
                    }

                    @Override
                    Scenario.Result verify(Response response) {
                        boolean result = response.getStatus() == 200;
                        String message;

                        if (result) {
                            message = response.readEntity(Message.class).toString();
                        } else {
                            message = response.readEntity(String.class);
                        }


                        return new Scenario.Result(result,
                                "Posted message " + message,
                                "Error while posting message [" + expectActual(200, response.getStatus()) + "]" +
                                        "[content: " + message + "]");
                    }
                })
                .step(new Scenario.Step("List all messages of user 2") {
                    @Override
                    Response action(WebTarget target) {
                        return target.path("message")
                                .request()
                                .header("X-Client-ID", mailbox2.getClientId())
                                .accept(MediaType.APPLICATION_JSON)
                                .get();
                    }

                    @Override
                    Scenario.Result verify(Response response) {
                        List<Message> messages = response.readEntity(new GenericType<List<Message>>() {
                        });

                        boolean result = messages.size() == 1;


                        return new Scenario.Result(result,
                                "Found " + messages.size() + " messages for this user",
                                "Found " + messages.size() + " messages for this user [" + expectActual(1, messages.size()) + "]");
                    }
                });
    }

    private static Scenario.Preparation createMailboxAndStoreIdIn(final BoxBag mailboxId, final long clientId) {
        return new Scenario.Preparation() {
            @Override
            public void exec(WebTarget target) {
                Box b = target.path("mailbox")
                        .request()
                        .header("X-Client-ID", clientId)
                        .accept(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(new Box("mb", "mailbox"), MediaType.APPLICATION_JSON))
                        .readEntity(Box.class);
                mailboxId.set(b);
            }
        };
    }

    private static class BoxBag {
        private Box box;

        public Box get() {
            return box;
        }

        public void set(Box id) {
            this.box = id;
        }

        public Long getClientId() {
            return box.getClientId();
        }
    }
}
