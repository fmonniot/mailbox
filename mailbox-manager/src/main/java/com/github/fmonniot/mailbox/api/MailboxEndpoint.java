package com.github.fmonniot.mailbox.api;

import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;
import com.github.fmonniot.mailbox.service.MailboxService;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/mailbox")
@Produces(MediaType.APPLICATION_JSON)
public class MailboxEndpoint {

    private final MailboxService mailboxService;

    @Inject
    public MailboxEndpoint(MailboxService mailboxService) {
        this.mailboxService = mailboxService;
    }

    @GET
    @Path("{boxId}")
    public Response get(@PathParam("boxId") Long boxId) {
        Box mailbox = mailboxService.get(boxId);

        if (mailbox != null) {
            for (Message message : mailbox.getMessages()) {
                message.removeBox();
            }
            return Response.status(200).entity(mailbox).build();
        } else {
            return Response.status(404).build();
        }

    }

    @GET
    public Response list(@HeaderParam("X-Client-ID") Long clientId) {
        List<Box> mailboxes = mailboxService.listByClientId(clientId);
        if (mailboxes != null && mailboxes.size() > 0) {
            return Response.status(200)
                    .entity(mailboxes)
                    .build();
        } else {
            return Response.status(404).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Box mailbox, @HeaderParam("X-Client-ID") Long clientId) {
        try {
            Box createdMailbox = mailboxService.create(mailbox, clientId);
            if (createdMailbox == null || createdMailbox.getId() == null) {
                return Response.status(400).build();
            }

            return Response.status(200).entity(createdMailbox).build();

        } catch (EntityExistsException e) {
            return Response.status(409).entity(e).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = mailboxService.delete(id);

        if (deleted) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }
}
