package it.vige.school.model;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

/**
 * Presences.
 * 
 * @author <a href="mailto:luca.stancapiano@vige.it">Luca Stancapiano </a>
 * @author <a href="mailto:toro.gm5s@gmail.com">Alessandro Toro </a>
 */

@NamedQueries({ @NamedQuery(name = "findAllPresences", query = "select p from Presence"),
		@NamedQuery(name = "findPresencesByDay", query = "select p from Presence where " + "p.day = :day "
				+ "order by p.room asc"),
		@NamedQuery(name = "findPresencesByPupil", query = "select p from Presence where " + "p.school = :school "
				+ "order by p.school asc"), })
@Entity
@Table
@Indexed(index = "indexes/presences")
public class Presence {

	@Id
	@DocumentId
	@GeneratedValue
	private Integer id;

	private Date day;

	private boolean ok;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "pupil_id")
	private Pupil pupil;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Pupil getPupil() {
		return pupil;
	}

	public void setPupil(Pupil pupil) {
		this.pupil = pupil;
	}

}
