package it.vige.school.rooms.spi.impl;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import it.vige.school.rooms.Room;
import it.vige.school.rooms.School;
import it.vige.school.rooms.jpa.RoomEntity;
import it.vige.school.rooms.jpa.SchoolEntity;
import it.vige.school.rooms.spi.RoomsService;

public class RoomsServiceImpl implements RoomsService, Converters {

	private final KeycloakSession session;

	public RoomsServiceImpl(KeycloakSession session) {
		this.session = session;
		if (getRealm() == null) {
			throw new IllegalStateException("The service cannot accept a session without a realm in its context.");
		}
	}

	private EntityManager getEntityManager() {
		return session.getProvider(JpaConnectionProvider.class).getEntityManager();
	}

	protected RealmModel getRealm() {
		return session.getContext().getRealm();
	}

	@Override
	public List<Room> findAllRooms() {
		List<RoomEntity> roomEntities = getEntityManager().createNamedQuery("findAllRooms", RoomEntity.class)
				.getResultList();
		return roomEntities.stream().map(t -> RoomEntityToRoom.apply(t)).collect(toList());
	}

	@Override
	public List<School> findAllSchools() {
		List<SchoolEntity> schoolEntities = getEntityManager().createNamedQuery("findAllSchools", SchoolEntity.class)
				.getResultList();
		return schoolEntities.stream().map(t -> SchoolEntityToSchool.apply(t)).collect(toList());
	}

	@Override
	public School findSchoolById(String school) {
		SchoolEntity entity = getEntityManager().find(SchoolEntity.class, school);
		return SchoolEntityToSchool.apply(entity);
	}

	@Override
	public List<Room> findRoomsBySchool(String school) {
		List<RoomEntity> roomEntities = getEntityManager().createNamedQuery("findRoomsBySchool", RoomEntity.class)
				.setParameter("school", school).getResultList();

		return roomEntities.stream().map(t -> RoomEntityToRoom.apply(t)).collect(toList());
	}

	@Override
	public Room createRoom(Room room) {
		RoomEntity entity = RoomToRoomEntity.apply(room);
		getEntityManager().persist(entity);

		return RoomEntityToRoom.apply(entity);
	}

	@Override
	public School createSchool(School school) {
		SchoolEntity entity = SchoolToSchoolEntity.apply(school);
		getEntityManager().persist(entity);

		return SchoolEntityToSchool.apply(entity);
	}

	@Override
	public School updateSchool(School school) {
		SchoolEntity entity = getEntityManager().find(SchoolEntity.class, school.getId());
		entity.setDescription(school.getDescription());
		Map<String, List<String>> rooms = school.getRooms();
		entity.getRooms().clear();
		for (String section : rooms.keySet()) {
			List<String> classes = rooms.get(section);
			for (String clazz : classes) {
				Room room = new Room();
				room.setSection(section.charAt(0));
				room.setClazz(parseInt(clazz));
				room.setSchool(school);
				entity.getRooms().add(RoomToRoomEntity.apply(room));
			}
		}

		return SchoolEntityToSchool.apply(entity);
	}

	@Override
	public void removeRoom(Room room) {
		EntityManager em = getEntityManager();
		RoomEntity entity = em.createNamedQuery("findRoomByClazzSectionAndSchool", RoomEntity.class)
				.setParameter("school", room.getSchool().getId()).setParameter("clazz", room.getClazz())
				.setParameter("section", room.getSection()).getSingleResult();
		em.remove(entity);
	}

	@Override
	public void removeSchool(School school) {
		EntityManager em = getEntityManager();
		SchoolEntity entity = em.find(SchoolEntity.class, school.getId());
		em.remove(entity);
	}

	public void close() {
		// Nothing to do.
	}

}
