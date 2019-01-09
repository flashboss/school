package it.vige.school.rooms.jpa;

import java.util.Collections;
import java.util.List;

import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

/**
 * @author <a href="mailto:erik.mulder@docdatapayments.com">Erik Mulder</a>
 * 
 * Example JpaEntityProvider.
 */
public class RoomsJpaEntityProvider implements JpaEntityProvider {

    @Override
    public List<Class<?>> getEntities() {
        return Collections.<Class<?>>singletonList(Object.class);
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
