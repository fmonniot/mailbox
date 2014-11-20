package com.github.fmonniot.mailbox.api;


import com.github.fmonniot.mailbox.entity.Box;
import com.github.fmonniot.mailbox.entity.Message;
import com.github.fmonniot.mailbox.service.NewsboxService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/newsbox")
@Produces(MediaType.APPLICATION_JSON)
public class NewsboxEndpoint {

    private final NewsboxService newsboxService;

    @Inject
    public NewsboxEndpoint(NewsboxService newsboxService) {
        this.newsboxService = newsboxService;
    }

    @GET
    public Response get(@HeaderParam("X-Client-ID") Long clientId) {
        Box newsbox = null;
        try {
            newsbox = newsboxService.getForClient(clientId);
            for (Message message : newsbox.getMessages()) {
                message.removeBox();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        if (newsbox != null) {
            return Response.status(200).entity(newsbox).build();
        } else {
            return Response.status(403).build();
        }
    }

}
