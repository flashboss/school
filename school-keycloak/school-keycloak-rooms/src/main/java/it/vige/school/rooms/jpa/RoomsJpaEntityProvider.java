package it.vige.school.rooms.jpa;

import java.util.Collections;
import java.util.List;

import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

public class RoomsJpaEntityProvider implements JpaEntityProvider {

    @Override
    public List<Class<?>> getEntities() {
        return Collections.<Class<?>>singletonList(RoomEntity.class);
    }

    @Override
    public String getChangelogLocation() {
    	return "META-INF/rooms-changelog.xml";
    }
    
    @Override
    public void close() {
    }

    @Override
    public String getFactoryId() {
        return RoomsJpaEntityProviderFactory.ID;
    }
}
