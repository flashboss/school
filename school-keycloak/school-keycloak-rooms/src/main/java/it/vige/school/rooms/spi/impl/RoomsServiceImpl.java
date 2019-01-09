package it.vige.school.rooms.spi.impl;

import static java.util.stream.Collectors.toList;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;

import it.vige.school.rooms.CompanyRepresentation;
import it.vige.school.rooms.Room;
import it.vige.school.rooms.School;
import it.vige.school.rooms.jpa.Company;
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
	public List<CompanyRepresentation> listCompanies() {
		List<Company> companyEntities = getEntityManager().createNamedQuery("findByRealm", Company.class)
				.setParameter("realmId", getRealm().getId()).getResultList();

		List<CompanyRepresentation> result = new LinkedList<>();
		for (Company entity : companyEntities) {
			result.add(new CompanyRepresentation(entity));
		}
		return result;
	}

	@Override
	public CompanyRepresentation findCompany(String id) {
		Company entity = getEntityManager().find(Company.class, id);
		return entity == null ? null : new CompanyRepresentation(entity);
	}

	@Override
	public CompanyRepresentation addCompany(CompanyRepresentation company) {
		Company entity = new Company();
		String id = company.getId() == null ? KeycloakModelUtils.generateId() : company.getId();
		entity.setId(id);
		entity.setName(company.getName());
		entity.setRealmId(getRealm().getId());
		getEntityManager().persist(entity);

		company.setId(id);
		return company;
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
	public List<Room> findRoomsBySchool(School school) {
		List<RoomEntity> roomEntities = getEntityManager().createNamedQuery("findRoomsBySchool", RoomEntity.class)
				.setParameter("school", school).getResultList();

		return roomEntities.stream().map(t -> RoomEntityToRoom.apply(t)).collect(toList());
	}

	@Override
	public Room createPresence(Room room) {
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
