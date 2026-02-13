package hr.kmilos21.resource;

import hr.kmilos21.entity.Link;
import hr.kmilos21.entity.LinkMessage;
import hr.kmilos21.service.LinkService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class LinksResource {
    @Inject
    LinkService linkService;

    @Path("/get-link")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShortLink(LinkMessage url) {
        LinkMessage msgToSend = linkService.getShortLink(url.getMessage());

        if (msgToSend == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if (!msgToSend.isSuccess()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(msgToSend).build();
        }
        return Response.ok(msgToSend).build();
    }

    @Path("/{shortLink}")
    @GET
    public Response redirect(@PathParam("shortLink") String shortLink) {
        Link redirectionLink =  linkService.getRedirectionLink(shortLink);

        if (redirectionLink == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String originalUrl = redirectionLink.getFullLink();
        return Response.seeOther(java.net.URI.create(originalUrl)).build();
    }
}
