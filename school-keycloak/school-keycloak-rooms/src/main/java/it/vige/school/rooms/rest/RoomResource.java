package it.vige.school.rooms.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;

import it.vige.school.rooms.Room;
import it.vige.school.rooms.School;
import it.vige.school.rooms.spi.RoomsService;

public class RoomResource {

	private final KeycloakSession session;

	public RoomResource(KeycloakSession session) {
		this.session = session;
	}

	@GET
	@Path("")
	@NoCache
	@Produces(APPLICATION_JSON)
	public List<Room> findAllRooms() {
		return session.getProvider(RoomsService.class).findAllRooms();
	}

	@GET
	@Path("")
	@NoCache
	@Produces(APPLICATION_JSON)
	public List<School> findAllSchools() {
		return session.getProvider(RoomsService.class).findAllSchools();
	}

	@POST
	@Path("")
	@NoCache
	@Consumes(APPLICATION_JSON)
	public List<Room> findRoomsBySchool(School school) {
		return session.getProvider(RoomsService.class).findRoomsBySchool(school);
	}

	@POST
	@Path("")
	@NoCache
	@Consumes(APPLICATION_JSON)
	public Room createRoom(Room room) {
		return session.getProvider(RoomsService.class).createRoom(room);
	}

	@POST
	@Path("")
	@NoCache
	@Consumes(APPLICATION_JSON)
	public School createSchool(School school) {
		return session.getProvider(RoomsService.class).createSchool(school);
	}

	@POST
	@Path("")
	@NoCache
	@Consumes(APPLICATION_JSON)
	public Response removeRoom(Room room) {
		session.getProvider(RoomsService.class).removeRoom(room);
		return Response.created(session.getContext().getUri().getAbsolutePathBuilder()
				.path(room.getClazz() + "-" + room.getSection() + "-" + room.getSchool()).build()).build();
	}

	@POST
	@Path("")
	@NoCache
	@Consumes(APPLICATION_JSON)
	public Response removeSchool(School school) {
		session.getProvider(RoomsService.class).removeSchool(school);
		return Response.created(session.getContext().getUri().getAbsolutePathBuilder().path(school.getId()).build())
				.build();
	}

	@GET
	@NoCache
	@Path("{school}")
	@Produces(APPLICATION_JSON)
	public School findSchoolById(@PathParam("school") final String school) {
		return session.getProvider(RoomsService.class).findSchoolById(school);
	}

}