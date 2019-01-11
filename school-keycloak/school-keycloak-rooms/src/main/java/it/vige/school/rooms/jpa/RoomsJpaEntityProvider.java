package it.vige.school.rooms.jpa;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

public class RoomsJpaEntityProvider implements JpaEntityProvider {

	List<Class<?>> entities = new ArrayList<Class<?>>();
	{
		entities.add(RoomEntity.class);
		entities.add(SchoolEntity.class);
	}

	@Override
	public List<Class<?>> getEntities() {
		return entities;
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
