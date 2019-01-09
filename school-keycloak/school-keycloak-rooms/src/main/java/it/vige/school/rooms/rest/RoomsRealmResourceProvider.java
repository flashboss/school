package it.vige.school.rooms.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class RoomsRealmResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public RoomsRealmResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new RoomsRestResource(session);
    }

    @Override
    public void close() {
    }

}
