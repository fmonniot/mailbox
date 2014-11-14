package com.github.fmonniot.mailbox.api;

import com.github.fmonniot.mailbox.entity.Mailbox;
import com.github.fmonniot.mailbox.service.MailboxService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.github.fmonniot.mailbox.utils.GenericEntityUtils.generify;

@Path("/mailbox")
public class MailboxEndpoint {

    private final MailboxService mailboxService;

    @Inject
    public MailboxEndpoint(MailboxService mailboxService) {
        this.mailboxService = mailboxService;
    }

    @GET
    @Path("{boxId}")
    public Response get(@PathParam("boxId") Long boxId) {
        Mailbox mailbox = mailboxService.get(boxId);

        if (mailbox != null) {
            return Response.status(200).entity(generify(mailbox)).build();
        } else {
            return Response.status(404).build();
        }

    }

    @GET
    public Response list(@HeaderParam("X-Client-ID") Long clientId) {
        return Response.status(200)
                .entity(generify(mailboxService.list(clientId)))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Mailbox mailbox) {
        try {
            Mailbox createdMailbox = mailboxService.create(mailbox);
            if (createdMailbox == null || createdMailbox.getId() == null) {
                throw new RuntimeException();
            }

            return Response.status(200).entity(generify(createdMailbox)).build();

        } catch (RuntimeException e) {
            return Response.status(400).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Mailbox mailbox) {
        boolean deleted = mailboxService.delete(mailbox);

        if (deleted) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }
}
