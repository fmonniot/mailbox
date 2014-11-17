package com.github.fmonniot.mailbox.api;

import com.github.fmonniot.mailbox.entity.Message;
import com.github.fmonniot.mailbox.service.MessageService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/message")
public class MessageEndpoint {

    private final MessageService messageService;

    @Inject
    public MessageEndpoint(MessageService messageService) {
        this.messageService = messageService;
    }

    @GET
    public Response list(@HeaderParam("X-Client-ID") Long clientId) {
        List<Message> messages = messageService.listForClient(clientId);

        return Response.status(200)
                .entity(messages)
                .build();
    }

    @POST
    public Response post(@HeaderParam("X-Client-ID") Long clientId, Message message) {
        try {
            Message postedMessage = messageService.post(clientId, message);
            if (postedMessage == null || postedMessage.getId() == null) {
                throw new RuntimeException();
            }

            return Response.status(200).entity(postedMessage).build();

        } catch (RuntimeException e) {
            return Response.status(400).build();
        }
    }
}
