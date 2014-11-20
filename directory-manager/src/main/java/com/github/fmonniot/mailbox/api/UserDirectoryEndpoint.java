package com.github.fmonniot.mailbox.api;

import com.github.fmonniot.mailbox.entity.NewsGroupRight;
import com.github.fmonniot.mailbox.entity.User;
import com.github.fmonniot.mailbox.service.UserDirectoryService;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/directory")
@Produces(MediaType.APPLICATION_JSON)
public class UserDirectoryEndpoint {

    private final UserDirectoryService userDirectoryService;

    @Inject
    public UserDirectoryEndpoint(UserDirectoryService userDirectoryService) {
        this.userDirectoryService = userDirectoryService;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(User user) {
        try {
            User createdUser = userDirectoryService.create(user);
            System.out.println(user.getRight().toString());
            if (createdUser == null || createdUser.getId() == null) {
                return Response.status(400).build();
            }
            return Response.status(200).entity(createdUser).build();
        } catch (EntityExistsException e) {
            return Response.status(409).entity(e).build();
        }
    }

    @DELETE
    @Path("/{userId}")
    public Response delete(@PathParam("userId") Long userId) {
        boolean deleted = userDirectoryService.delete(userId);

        if (deleted) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("{userId}")
    public Response lookupUserRights(@PathParam("userId") Long userId) {
        NewsGroupRight right = userDirectoryService.lookupUserRights(userId);
        System.out.println(right.toString());

        if (right != null) {
            return Response.status(200).entity(right).build();
        } else {
            return Response.status(404).build();
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserRights(@FormParam("user") Long userId, @FormParam("read") boolean read, @FormParam("write") boolean write) {
        boolean updated = userDirectoryService.updateUserRights(userId, new NewsGroupRight(read, write));

        if (updated) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/all")
    public Response lookupAllUsers() {
        List<User> users = userDirectoryService.lookupAllUsers();
        if (users != null && users.size() > 0) {
            return Response.status(200)
                    .entity(users)
                    .build();
        } else {
            return Response.status(404).build();
        }
    }
}