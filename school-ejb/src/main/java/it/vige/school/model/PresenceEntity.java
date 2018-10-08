package it.vige.school.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Presences.
 * 
 * @author <a href="mailto:luca.stancapiano@vige.it">Luca Stancapiano </a>
 * @author <a href="mailto:toro.gm5s@gmail.com">Alessandro Toro </a>
 */

@NamedQueries({ @NamedQuery(name = "findAllPresences", query = "from PresenceEntity"),
		@NamedQuery(name = "findPresencesByDay", query = "select p from PresenceEntity as p where " + "p.id.day = :day "
				+ "order by p.id.day asc"),
		@NamedQuery(name = "findPresencesByMonth", query = "select p from PresenceEntity as p where "
				+ "MONTH(p.id.day) = :month " + "and YEAR(p.id.day) = :year " + "order by p.id.day asc"),
		@NamedQuery(name = "findPresencesByYear", query = "select p from PresenceEntity as p where "
				+ "YEAR(p.id.day) = :year " + "order by p.id.day asc"),
		@NamedQuery(name = "findPresencesByPupil", query = "select p from PresenceEntity as p where "
				+ "p.id.pupil = :pupil " + "order by p.id.pupil asc"),
		@NamedQuery(name = "findPresenceByPupilAndDay", query = "select p from PresenceEntity as p where "
				+ "p.id.pupil = :pupil and p.id.day = :day " + "order by p.id.pupil asc") })
@Entity
@Table
public class PresenceEntity {

	@EmbeddedId
	private PresenceId id;

	public PresenceId getId() {
		return id;
	}

	public void setId(PresenceId id) {
		this.id = id;
	}

}
