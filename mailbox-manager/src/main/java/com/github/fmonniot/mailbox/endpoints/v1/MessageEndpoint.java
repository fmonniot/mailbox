package com.github.fmonniot.mailbox.endpoints.v1;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/message")
public class MessageEndpoint {

    @Context
    UriInfo uriInfo;

    @GET
    public Response index(@HeaderParam("X-Client-ID") Long clientId) {
        return Response.status(200)
                .entity("addUser is called, clientId : " + clientId)
                .build();
    }

    @POST
    public String create() {
        return "";
    }
}
