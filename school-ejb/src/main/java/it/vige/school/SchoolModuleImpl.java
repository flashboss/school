package it.vige.school;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.stream.Collectors.toList;
import static org.jboss.logging.Logger.getLogger;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import it.vige.school.dto.Presence;
import it.vige.school.dto.Pupil;
import it.vige.school.model.PresenceEntity;
import it.vige.school.model.PupilEntity;

@Stateless
public class SchoolModuleImpl implements SchoolModule, Converters {

	private static Logger log = getLogger(SchoolModuleImpl.class);

	@PersistenceContext(unitName = "school")
	private EntityManager em;

	@Override
	public List<Pupil> findAllPupils() throws ModuleException {
		try {
			TypedQuery<PupilEntity> query = em.createNamedQuery("findAllPupils", PupilEntity.class);
			List<PupilEntity> pupilList = query.getResultList();
			log.debug("pupil found: " + pupilList);
			return pupilList.stream().map(t -> PupilEntityToPupil.apply(t)).collect(toList());
		} catch (Exception e) {
			String message = "Cannot find pupil";
			throw new ModuleException(message, e);
		}
	}

	@Override
	public List<Presence> findAllPresences() throws ModuleException {
		try {
			TypedQuery<PresenceEntity> query = em.createNamedQuery("findAllPresences", PresenceEntity.class);
			List<PresenceEntity> presenceList = query.getResultList();
			log.debug("pupil found: " + presenceList);
			return presenceList.stream().map(t -> PresenceEntityToPresence.apply(t)).collect(toList());
		} catch (Exception e) {
			String message = "Cannot find presence";
			throw new ModuleException(message, e);
		}
	}

	@Override
	public List<Pupil> findPupilsByRoom(String room) throws ModuleException {
		if (room != null) {
			try {
				TypedQuery<PupilEntity> query = em.createNamedQuery("findPupilsByRoom", PupilEntity.class);
				query.setParameter("room", room);
				List<PupilEntity> pupilList = query.getResultList();
				log.debug("pupil found: " + pupilList);
				return pupilList.stream().map(t -> PupilEntityToPupil.apply(t)).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find pupil by room " + room;
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public List<Pupil> findPupilsBySchool(String school) throws ModuleException {
		if (school != null) {
			try {
				TypedQuery<PupilEntity> query = em.createNamedQuery("findPupilsBySchool", PupilEntity.class);
				query.setParameter("school", school);
				List<PupilEntity> pupilList = query.getResultList();
				log.debug("pupil found: " + pupilList);
				return pupilList.stream().map(t -> PupilEntityToPupil.apply(t)).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find pupil by room " + school;
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public List<Pupil> findPupilsBySchoolAndRoom(String school, String room) throws ModuleException {
		if (school != null) {
			try {
				TypedQuery<PupilEntity> query = em.createNamedQuery("findPupilsBySchoolAndRoom", PupilEntity.class);
				query.setParameter("school", school);
				query.setParameter("room", room);
				List<PupilEntity> pupilList = query.getResultList();
				log.debug("pupil found: " + pupilList);
				return pupilList.stream().map(t -> PupilEntityToPupil.apply(t)).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find pupil by room " + school;
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public List<Presence> findPresencesByPupil(Pupil pupil) throws ModuleException {
		if (pupil != null) {
			try {
				TypedQuery<PresenceEntity> query = em.createNamedQuery("findPresencesByPupil", PresenceEntity.class);
				query.setParameter("pupil", em.find(PupilEntity.class, pupil.getId()));
				List<PresenceEntity> presenceList = query.getResultList();
				log.debug("pupil found: " + presenceList);
				return presenceList.stream().map(t -> PresenceEntityToPresence.apply(t)).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find presence";
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public List<Presence> findPresencesByDay(Calendar day) throws ModuleException {
		if (day != null) {
			try {
				TypedQuery<PresenceEntity> query = null;
				query = em.createNamedQuery("findPresencesByDay", PresenceEntity.class);
				query.setParameter("day", day);
				List<PresenceEntity> presenceList = query.getResultList();
				log.debug("pupil found: " + presenceList);
				return presenceList.stream().map(t -> PresenceEntityToPresence.apply(t)).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find presence";
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public List<Presence> findPresencesByMonth(Calendar month) throws ModuleException {
		if (month != null) {
			try {
				TypedQuery<PresenceEntity> query = null;
				query = em.createNamedQuery("findPresencesByMonth", PresenceEntity.class);
				query.setParameter("month", month.get(MONTH) + 1);
				query.setParameter("year", month.get(YEAR));
				List<PresenceEntity> presenceList = query.getResultList();
				log.debug("pupil found: " + presenceList);
				return presenceList.stream().map(t -> PresenceEntityToPresence.apply(t)).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find presence";
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public List<Presence> findPresencesByYear(Calendar year) throws ModuleException {
		if (year != null) {
			try {
				TypedQuery<PresenceEntity> query = null;
				query = em.createNamedQuery("findPresencesByYear", PresenceEntity.class);
				query.setParameter("year", year.get(YEAR));
				List<PresenceEntity> presenceList = query.getResultList();
				log.debug("pupil found: " + presenceList);
				return presenceList.stream().map(t -> PresenceEntityToPresence.apply(t)).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find presence";
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public Presence findPresenceByPupilAndDay(Presence presence) throws ModuleException {
		return PresenceEntityToPresence.apply(findPresenceEntityByPupilAndDay(presence));
	}

	@Override
	public Presence createPresence(Presence presence) throws ModuleException {
		if (presence != null) {
			PresenceEntity presenceEntity = PresenceToPresenceEntity.apply(presence);
			Pupil pupil = presence.getPupil();
			presenceEntity.setPupil(em.find(PupilEntity.class, pupil.getId()));
			em.persist(presenceEntity);
			log.debug("presence created: " + presenceEntity);
			return PresenceEntityToPresence.apply(presenceEntity);
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public void removePresence(Presence presence) throws ModuleException {
		PresenceEntity presenceEntity = findPresenceEntityByPupilAndDay(presence);
		em.remove(presenceEntity);
		log.debug("presence removed: " + presenceEntity);
	}

	private PresenceEntity findPresenceEntityByPupilAndDay(Presence presence) throws ModuleException {
		TypedQuery<PresenceEntity> query = em.createNamedQuery("findPresenceByPupilAndDay", PresenceEntity.class);
		query.setParameter("pupil", em.find(PupilEntity.class, presence.getPupil().getId()));
		query.setParameter("day", presence.getDay());
		return query.getSingleResult();
	}

}
