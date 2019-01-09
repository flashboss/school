package it.vige.school.rooms.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;

public class RoomsRestResource {

	private final KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;
	
	public RoomsRestResource(KeycloakSession session) {
		this.session = session;
        this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());
	}
	
    @Path("companies")
    public RoomsResource getCompanyResource() {
        return new RoomsResource(session);
    }

    // Same like "companies" endpoint, but REST endpoint is authenticated with Bearer token and user must be in realm role "admin"
    // Just for illustration purposes
    @Path("companies-auth")
    public RoomsResource getCompanyResourceAuthenticated() {
        checkRealmAdmin();
        return new RoomsResource(session);
    }

    private void checkRealmAdmin() {
        if (auth == null) {
            throw new NotAuthorizedException("Bearer");
        } else if (auth.getToken().getRealmAccess() == null || !auth.getToken().getRealmAccess().isUserInRole("admin")) {
            throw new ForbiddenException("Does not have realm admin role");
        }
    }

}
