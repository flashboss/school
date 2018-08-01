package it.vige.school;

import static org.jboss.logging.Logger.getLogger;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import it.vige.school.model.PresenceEntity;
import it.vige.school.model.PupilEntity;

@Stateless
public class SchoolModuleImpl implements SchoolModule {

	private static Logger log = getLogger(SchoolModuleImpl.class);

	@PersistenceContext(unitName = "school")
	private EntityManager em;

	@Override
	public List<PupilEntity> findAllPupils() throws ModuleException {
		try {
			TypedQuery<PupilEntity> query = em.createNamedQuery("findAllPupils", PupilEntity.class);
			List<PupilEntity> pupilList = query.getResultList();
			if (pupilList == null) {
				throw new ModuleException("No pupil found");
			}
			log.debug("pupil found: " + pupilList);
			return pupilList;
		} catch (Exception e) {
			String message = "Cannot find pupil";
			throw new ModuleException(message, e);
		}
	}

	@Override
	public List<PresenceEntity> findAllPresences() throws ModuleException {
		try {
			TypedQuery<PresenceEntity> query = em.createNamedQuery("findAllPresences", PresenceEntity.class);
			List<PresenceEntity> presenceList = query.getResultList();
			if (presenceList == null) {
				throw new ModuleException("No presence found");
			}
			log.debug("pupil found: " + presenceList);
			return presenceList;
		} catch (Exception e) {
			String message = "Cannot find presence";
			throw new ModuleException(message, e);
		}
	}

	@Override
	public List<PupilEntity> findPupilsByRoom(String room) throws ModuleException {
		if (room != null) {
			try {
				TypedQuery<PupilEntity> query = em.createNamedQuery("findPupilsByRoom", PupilEntity.class);
				query.setParameter("room", room);
				List<PupilEntity> pupilList = query.getResultList();
				if (pupilList == null) {
					throw new ModuleException("No pupil found for " + room);
				}
				log.debug("pupil found: " + pupilList);
				return pupilList;
			} catch (Exception e) {
				String message = "Cannot find pupil by room " + room;
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public List<PupilEntity> findPupilsBySchool(String school) throws ModuleException {
		if (school != null) {
			try {
				TypedQuery<PupilEntity> query = em.createNamedQuery("findPupilsBySchool", PupilEntity.class);
				query.setParameter("school", school);
				List<PupilEntity> pupilList = query.getResultList();
				if (pupilList == null) {
					throw new ModuleException("No pupil found for " + school);
				}
				log.debug("pupil found: " + pupilList);
				return pupilList;
			} catch (Exception e) {
				String message = "Cannot find pupil by room " + school;
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public List<PresenceEntity> findPresencesByPupil(PupilEntity pupil) throws ModuleException {
		try {
			TypedQuery<PresenceEntity> query = em.createNamedQuery("findPresencesByPupil", PresenceEntity.class);
			query.setParameter("pupil", pupil);
			List<PresenceEntity> presenceList = query.getResultList();
			if (presenceList == null) {
				throw new ModuleException("No presence found");
			}
			log.debug("pupil found: " + presenceList);
			return presenceList;
		} catch (Exception e) {
			String message = "Cannot find presence";
			throw new ModuleException(message, e);
		}
	}

	@Override
	public PresenceEntity createPresence(PupilEntity pupil) throws ModuleException {
		PresenceEntity presence = new PresenceEntity();
		presence.setDay(new Date());
		presence.setPupil(pupil);
		em.persist(presence);
		log.debug("presence created: " + presence);
		return presence;
	}

	@Override
	public void removePresence(int id) throws ModuleException {
		PresenceEntity presence = em.find(PresenceEntity.class, id);
		em.remove(presence);
		log.debug("presence removed: " + presence);
	}

}