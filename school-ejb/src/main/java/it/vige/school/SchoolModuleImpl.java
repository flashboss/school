package it.vige.school;

import static java.util.stream.Collectors.toList;
import static org.jboss.logging.Logger.getLogger;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
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

	@Resource
	private SessionContext context;

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

		try {
			if (school == null)
				school = context.getCallerPrincipal().getName();
			TypedQuery<PupilEntity> query = em.createNamedQuery("findPupilsBySchool", PupilEntity.class);
			query.setParameter("school", school);
			List<PupilEntity> pupilList = query.getResultList();
			log.debug("pupil found: " + pupilList);
			return pupilList.stream().map(t -> PupilEntityToPupil.apply(t)).collect(toList());
		} catch (Exception e) {
			String message = "Cannot find pupil by room " + school;
			throw new ModuleException(message, e);
		}
	}

	@Override
	public List<Presence> findPresencesByPupil(Pupil pupil) throws ModuleException {
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
	}

	@Override
	public Presence createPresence(Pupil pupil) throws ModuleException {
		PresenceEntity presence = new PresenceEntity();
		presence.setDay(new Date());
		presence.setPupil(em.find(PupilEntity.class, pupil.getId()));
		em.persist(presence);
		log.debug("presence created: " + presence);
		return PresenceEntityToPresence.apply(presence);
	}

	@Override
	public void removePresence(int id) throws ModuleException {
		PresenceEntity presence = em.find(PresenceEntity.class, id);
		em.remove(presence);
		log.debug("presence removed: " + presence);
	}

}
