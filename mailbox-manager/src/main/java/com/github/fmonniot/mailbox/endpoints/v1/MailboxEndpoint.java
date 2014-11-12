package com.github.fmonniot.mailbox.endpoints.v1;

import com.github.fmonniot.mailbox.Mailbox;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/mailbox")
public class MailboxEndpoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Mailbox mailbox) {

    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(Mailbox mailbox) {

    }



}
