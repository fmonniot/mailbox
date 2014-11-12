package com.github.fmonniot.mailbox.endpoints.v1;

import com.github.fmonniot.mailbox.Mailbox;
import com.github.fmonniot.mailbox.impl.BoxDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/mailbox")
public class MailboxEndpoint {

    @PersistenceContext(unitName = "mailbox-pu")
    EntityManager em;

    @GET
    public Response list(@HeaderParam("X-Client-ID") Long clientId) {
        BoxDao<Mailbox> dao = new BoxDao<>(em);
        List<Mailbox> boxes = dao.getBoxes(clientId);

        return Response.status(200)
                .entity(new GenericEntity<List<Mailbox>>(boxes) {
                })
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Mailbox mailbox) {
        BoxDao<Mailbox> dao = new BoxDao<>(em);
        Mailbox createdMailbox = dao.createBox(new Mailbox("whatever"));

        if (createdMailbox.getId() != null) {
            return Response.status(200).entity(new GenericEntity<Mailbox>(createdMailbox) {
            }).build();
        } else {
            return Response.status(400).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Mailbox mailbox) {
        BoxDao<Mailbox> dao = new BoxDao<>(em);
        boolean deleted = dao.deleteBox(mailbox);

        if (deleted) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }

    }



}
