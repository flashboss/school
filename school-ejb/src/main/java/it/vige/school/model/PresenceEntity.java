package it.vige.school.model;

import static javax.persistence.TemporalType.DATE;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Presences.
 * 
 * @author <a href="mailto:luca.stancapiano@vige.it">Luca Stancapiano </a>
 * @author <a href="mailto:toro.gm5s@gmail.com">Alessandro Toro </a>
 */

@NamedQueries({ @NamedQuery(name = "findAllPresences", query = "from PresenceEntity"),
		@NamedQuery(name = "findPresencesByDay", query = "select p from PresenceEntity as p where " + "p.day = :day "
				+ "order by p.day asc"),
		@NamedQuery(name = "findPresencesByMonth", query = "select p from PresenceEntity as p where "
				+ "MONTH(p.day) = :month " + "and YEAR(p.day) = :year " + "order by p.day asc"),
		@NamedQuery(name = "findPresencesByYear", query = "select p from PresenceEntity as p where "
				+ "YEAR(p.day) = :year " + "order by p.day asc"),
		@NamedQuery(name = "findPresencesByPupil", query = "select p from PresenceEntity as p where "
				+ "p.pupil = :pupil " + "order by p.pupil asc"),
		@NamedQuery(name = "findPresenceByPupilAndDay", query = "select p from PresenceEntity as p where "
				+ "p.pupil = :pupil and p.day = :day " + "order by p.pupil asc") })
@Entity
@Table
public class PresenceEntity {

	@Id
	private String id;

	@Temporal(DATE)
	private Calendar day;

	@ManyToOne
	@JoinColumn(name = "pupil_id")
	private PupilEntity pupil;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Calendar getDay() {
		return day;
	}

	public void setDay(Calendar day) {
		this.day = day;
	}

	public PupilEntity getPupil() {
		return pupil;
	}

	public void setPupil(PupilEntity pupil) {
		this.pupil = pupil;
	}

}
