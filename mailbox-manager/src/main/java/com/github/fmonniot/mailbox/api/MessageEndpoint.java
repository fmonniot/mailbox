package com.github.fmonniot.mailbox.api;

import com.github.fmonniot.mailbox.entity.Message;
import com.github.fmonniot.mailbox.service.MessageService;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
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
    public Response post(@HeaderParam("X-Client-ID") Long clientId, @HeaderParam("X-Box-ID") Long boxId, Message message) {
        try {
            Message postedMessage = messageService.post(clientId, boxId, message);
            if (postedMessage == null || postedMessage.getId() == null) {
                throw new EntityNotFoundException();
            }

            return Response.status(200).entity(strip(postedMessage)).build();

        } catch (EntityNotFoundException | NullPointerException e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = messageService.delete(id);

        if (deleted) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }

    private Message strip(Message message) {
        for (Message message1 : message.getBox().getMessages()) {
            message1.removeBox();
        }
        return message;
    }
}
