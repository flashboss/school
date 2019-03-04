package it.vige.school.rooms.rest;

import static it.vige.school.rooms.rest.RoomsRestResource.checkRealmAdmin;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.noContent;
import static org.keycloak.models.utils.KeycloakModelUtils.generateId;
import static org.keycloak.services.ErrorResponse.exists;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AuthenticationManager.AuthResult;

import it.vige.school.rooms.School;
import it.vige.school.rooms.spi.RoomsService;

public class SchoolResource {

	private final KeycloakSession session;
	private final AuthResult auth;

	public SchoolResource(KeycloakSession session, AuthResult auth) {
		this.session = session;
		this.auth = auth;
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
	@Produces(APPLICATION_JSON)
	public Response createSchool(final School school) {
		try {
			checkRealmAdmin(auth);
			if (school.getId() == null)
				school.setId(generateId());
			School createdSchool = session.getProvider(RoomsService.class).createSchool(school);

			if (session.getTransactionManager().isActive()) {
				session.getTransactionManager().commit();
			}
			return created(session.getContext().getUri().getAbsolutePathBuilder().path(createdSchool.getId()).build())
					.build();
		} catch (Exception ex) {
			if (session.getTransactionManager().isActive()) {
				session.getTransactionManager().setRollbackOnly();
			}
			return exists("Could not create user");
		}
	}

	/**
	 * Update the school
	 *
	 * @param school
	 * @return
	 */
	@PUT
	@Path("{school.id}")
	@NoCache
	@Consumes(APPLICATION_JSON)
	public Response updateSchool(final School school) {

		try {
			checkRealmAdmin(auth);

			session.getProvider(RoomsService.class).updateSchool(school);

			if (session.getTransactionManager().isActive()) {
				session.getTransactionManager().commit();
			}
			return noContent().build();
		} catch (Exception me) { // JPA
			return exists("Could not update school!");
		}
	}

	@DELETE
	@Path("{school}")
	@NoCache
	public Response removeSchool(@PathParam("school") final String schoolId) {
		checkRealmAdmin(auth);
		School school = new School();
		school.setId(schoolId);
		session.getProvider(RoomsService.class).removeSchool(school);
		return created(session.getContext().getUri().getAbsolutePathBuilder().path(school.getId()).build()).build();
	}

	@GET
	@NoCache
	@Path("{school}")
	@Produces(APPLICATION_JSON)
	public School findSchoolById(@PathParam("school") final String school) {
		return session.getProvider(RoomsService.class).findSchoolById(school);
	}

}