package it.vige.school.rooms.rest;

import static it.vige.school.rooms.rest.RoomsRestResource.checkRealmAdmin;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AuthenticationManager.AuthResult;

import it.vige.school.rooms.Room;
import it.vige.school.rooms.spi.RoomsService;

public class RoomResource {

	private final KeycloakSession session;
	private final AuthResult auth;

	public RoomResource(KeycloakSession session, AuthResult auth) {
		this.session = session;
		this.auth = auth;
	}

	@GET
	@Path("")
	@NoCache
	@Produces(APPLICATION_JSON)
	public List<Room> findAllRooms() {
		return session.getProvider(RoomsService.class).findAllRooms();
	}

	@GET
	@NoCache
	@Path("{school}")
	@Produces(APPLICATION_JSON)
	public List<Room> findRoomsBySchool(@PathParam("school") final String school) {
		return session.getProvider(RoomsService.class).findRoomsBySchool(school);
	}

	@POST
	@Path("")
	@NoCache
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Room createRoom(Room room) {
		checkRealmAdmin(auth);
		return session.getProvider(RoomsService.class).createRoom(room);
	}

	@DELETE
	@Path("")
	@NoCache
	@Consumes(APPLICATION_JSON)
	public Response removeRoom(Room room) {
		checkRealmAdmin(auth);
		session.getProvider(RoomsService.class).removeRoom(room);
		return Response.created(session.getContext().getUri().getAbsolutePathBuilder()
				.path(room.getClazz() + "-" + room.getSection() + "-" + room.getSchool()).build()).build();
	}

}