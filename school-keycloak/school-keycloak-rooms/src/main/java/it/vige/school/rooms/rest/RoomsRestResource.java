package it.vige.school.rooms.rest;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager.AuthResult;

public class RoomsRestResource {

	private final KeycloakSession session;
	private final AuthResult auth;

	public RoomsRestResource(KeycloakSession session) {
		this.session = session;
		this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());
	}

	@Path("rooms")
	public RoomResource getRoomResource() {
		return new RoomResource(session, auth);
	}

	@Path("schools")
	public SchoolResource getSchoolResource() {
		return new SchoolResource(session, auth);
	}

	public static void checkRealmAdmin(AuthResult auth) {
		if (auth == null) {
			throw new NotAuthorizedException("Bearer");
		} else if (auth.getUser().getGroups() == null
				|| auth.getUser().getGroups().stream().filter(x -> x.getName().equals("admin")).count() == 0) {
			throw new ForbiddenException("Does not have realm admin role");
		}
	}

}
