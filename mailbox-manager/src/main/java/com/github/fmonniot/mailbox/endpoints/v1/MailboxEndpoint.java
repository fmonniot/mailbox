package com.github.fmonniot.mailbox.endpoints.v1;

import com.github.fmonniot.mailbox.Mailbox;
import com.github.fmonniot.mailbox.impl.BoxDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mailbox")
public class MailboxEndpoint {

    @PersistenceContext(unitName = "mailbox-pu")
    EntityManager em;

    @GET
    public Response list(@HeaderParam("X-Client-ID") Long clientId) {
        BoxDao<Mailbox> dao = new BoxDao<>(Mailbox.class, em);
        return Response.status(200)
                .entity(dao.getBoxes(clientId))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Mailbox mailbox) {

    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(Mailbox mailbox) {

    }



}
