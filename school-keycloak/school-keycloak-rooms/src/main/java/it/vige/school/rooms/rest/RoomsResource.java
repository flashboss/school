package it.vige.school.rooms.rest;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;

import it.vige.school.rooms.CompanyRepresentation;
import it.vige.school.rooms.spi.RoomsService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class RoomsResource {

	private final KeycloakSession session;
	
	public RoomsResource(KeycloakSession session) {
		this.session = session;
	}

    @GET
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompanyRepresentation> getCompanies() {
        return session.getProvider(RoomsService.class).listCompanies();
    }

    @POST
    @Path("")
    @NoCache
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCompany(CompanyRepresentation rep) {
        session.getProvider(RoomsService.class).addCompany(rep);
        return Response.created(session.getContext().getUri().getAbsolutePathBuilder().path(rep.getId()).build()).build();
    }

    @GET
    @NoCache
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CompanyRepresentation getCompany(@PathParam("id") final String id) {
        return session.getProvider(RoomsService.class).findCompany(id);
    }

}