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
		return new RoomResource(session);
	}

	@Path("schools")
	public SchoolResource getSchoolResource() {
		return new SchoolResource(session);
	}

	// Same like "rooms" endpoint, but REST endpoint is authenticated with
	// Bearer token and user must be in realm role "admin"
	// Just for illustration purposes
	@Path("rooms-auth")
	public RoomResource getRoomyResourceAuthenticated() {
		checkRealmAdmin();
		return new RoomResource(session);
	}

	// Same like "rooms" endpoint, but REST endpoint is authenticated with
	// Bearer token and user must be in realm role "admin"
	// Just for illustration purposes
	@Path("schools-auth")
	public SchoolResource getSchoolResourceAuthenticated() {
		checkRealmAdmin();
		return new SchoolResource(session);
	}

	private void checkRealmAdmin() {
		if (auth == null) {
			throw new NotAuthorizedException("Bearer");
		} else if (auth.getToken().getRealmAccess() == null
				|| !auth.getToken().getRealmAccess().isUserInRole("admin")) {
			throw new ForbiddenException("Does not have realm admin role");
		}
	}

}
