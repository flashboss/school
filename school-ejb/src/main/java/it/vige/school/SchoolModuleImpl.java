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
import it.vige.school.dto.User;
import it.vige.school.model.PresenceEntity;

@Stateless
public class SchoolModuleImpl implements SchoolModule, Converters {

	private static Logger log = getLogger(SchoolModuleImpl.class);

	@PersistenceContext(unitName = "school")
	private EntityManager em;

	@Override
	public List<Presence> findAllPresences() throws ModuleException {
		try {
			TypedQuery<PresenceEntity> query = em.createNamedQuery("findAllPresences", PresenceEntity.class);
			List<PresenceEntity> presenceList = query.getResultList();
			log.debug("user found: " + presenceList);
			return presenceList.stream().map(t -> PresenceEntityToPresence.apply(t)).collect(toList());
		} catch (Exception e) {
			String message = "Cannot find presence";
			throw new ModuleException(message, e);
		}
	}

	@Override
	public List<Presence> findPresencesByUser(User user) throws ModuleException {
		if (user != null) {
			try {
				TypedQuery<PresenceEntity> query = em.createNamedQuery("findPresencesByUser", PresenceEntity.class);
				query.setParameter("user", user.getId());
				List<PresenceEntity> presenceList = query.getResultList();
				log.debug("user found: " + presenceList);
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
				log.debug("user found: " + presenceList);
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
				log.debug("user found: " + presenceList);
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
				log.debug("user found: " + presenceList);
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
	public Presence findPresenceByUserAndDay(Presence presence) throws ModuleException {
		return PresenceEntityToPresence.apply(findPresenceEntityByUserAndDay(presence));
	}

	@Override
	public Presence createPresence(Presence presence) throws ModuleException {
		if (presence != null) {
			PresenceEntity presenceEntity = PresenceToPresenceEntity.apply(presence);
			User user = presence.getUser();
			presenceEntity.getId().setUser(user.getId());
			em.persist(presenceEntity);
			log.debug("presence created: " + presenceEntity);
			return PresenceEntityToPresence.apply(presenceEntity);
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	@Override
	public void removePresence(Presence presence) throws ModuleException {
		PresenceEntity presenceEntity = findPresenceEntityByUserAndDay(presence);
		em.remove(presenceEntity);
		log.debug("presence removed: " + presenceEntity);
	}

	private PresenceEntity findPresenceEntityByUserAndDay(Presence presence) throws ModuleException {
		TypedQuery<PresenceEntity> query = em.createNamedQuery("findPresenceByUserAndDay", PresenceEntity.class);
		query.setParameter("user", presence.getUser().getId());
		query.setParameter("day", presence.getDay());
		return query.getSingleResult();
	}

}
