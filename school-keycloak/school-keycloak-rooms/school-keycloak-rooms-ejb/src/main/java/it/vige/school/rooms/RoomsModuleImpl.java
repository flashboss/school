package it.vige.school.rooms;

import static java.util.stream.Collectors.toList;
import static org.jboss.logging.Logger.getLogger;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import it.vige.school.rooms.dto.Room;
import it.vige.school.rooms.dto.School;
import it.vige.school.rooms.model.RoomEntity;
import it.vige.school.rooms.model.SchoolEntity;

@Stateless
public class RoomsModuleImpl implements RoomsModule, Converters {

	private static Logger log = getLogger(RoomsModuleImpl.class);

	@PersistenceContext(unitName = "rooms")
	private EntityManager em;

	@Override
	public List<Room> findAllRooms() throws ModuleException {
		try {
			TypedQuery<RoomEntity> query = em.createNamedQuery("findAllRooms", RoomEntity.class);
			List<RoomEntity> roomList = query.getResultList();
			log.debug("room found: " + roomList);
			return roomList.stream().map(t -> RoomEntityToRoom.apply(t)).collect(toList());
		} catch (Exception e) {
			String message = "Cannot find room";
			throw new ModuleException(message, e);
		}
	}

	@Override
	public List<School> findAllSchools() throws ModuleException {
		try {
			TypedQuery<SchoolEntity> query = em.createNamedQuery("findAllSchools", SchoolEntity.class);
			List<SchoolEntity> schoolList = query.getResultList();
			log.debug("school found: " + schoolList);
			return schoolList.stream().map(t -> SchoolEntityToSchool.apply(t)).collect(toList());
		} catch (Exception e) {
			String message = "Cannot find room";
			throw new ModuleException(message, e);
		}
	}

	@Override
	public School findSchoolById(String id) throws ModuleException {
		return null;
	}

	@Override
	public List<Room> findRoomsBySchool(School school) throws ModuleException {
		if (school != null) {
			try {
				TypedQuery<RoomEntity> query = em.createNamedQuery("findRoomsBySchool", RoomEntity.class);
				query.setParameter("school", school.getId());
				List<RoomEntity> roomList = query.getResultList();
				log.debug("room found: " + roomList);
				return roomList.stream().map(t -> RoomEntityToRoom.apply(t)).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find room";
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("school cannot be null");
		}
	}

	@Override
	public Room createRoom(Room room) throws ModuleException {
		if (room != null) {
			RoomEntity roomEntity = RoomToRoomEntity.apply(room);
			School school = room.getSchool();
			roomEntity.getId().setClazz(room.getClazz());
			roomEntity.getId().setSection(room.getSection());
			roomEntity.getId().setSchool(school.getId());
			em.persist(roomEntity);
			log.debug("room created: " + roomEntity);
			return RoomEntityToRoom.apply(roomEntity);
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public School createSchool(School school) throws ModuleException {
		if (school != null) {
			SchoolEntity schoolEntity = SchoolToSchoolEntity.apply(school);
			schoolEntity.setId(school.getId());
			schoolEntity.setDescription(school.getDescription());
			em.persist(schoolEntity);
			log.debug("school created: " + schoolEntity);
			return SchoolEntityToSchool.apply(schoolEntity);
		} else {
			throw new IllegalArgumentException("school cannot be null");
		}
	}

	@Override
	public void removeRoom(Room room) throws ModuleException {
		RoomEntity roomEntity = findRoomEntityByClazzSectionAndSchool(room);
		em.remove(roomEntity);
		log.debug("room removed: " + roomEntity);
	}

	@Override
	public void removeSchool(School school) throws ModuleException {
		SchoolEntity schoolEntity = em.find(SchoolEntity.class, school.getId());
		em.remove(schoolEntity);
		log.debug("school removed: " + schoolEntity);
	}

	private RoomEntity findRoomEntityByClazzSectionAndSchool(Room room) throws ModuleException {
		TypedQuery<RoomEntity> query = em.createNamedQuery("findRoomByClazzSectionAndSchool", RoomEntity.class);
		query.setParameter("clazz", room.getClazz());
		query.setParameter("section", room.getSection());
		query.setParameter("school", room.getSchool());
		return query.getSingleResult();
	}

}
