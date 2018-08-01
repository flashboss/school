package it.vige.school.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

/**
 * Presences.
 * 
 * @author <a href="mailto:luca.stancapiano@vige.it">Luca Stancapiano </a>
 * @author <a href="mailto:toro.gm5s@gmail.com">Alessandro Toro </a>
 */

@NamedQueries({ @NamedQuery(name = "findAllPresences", query = "from Presence"),
		@NamedQuery(name = "findPresencesByDay", query = "select p from Presence as p where " + "p.day = :day "
				+ "order by p.day asc"),
		@NamedQuery(name = "findPresencesByPupil", query = "select p from Presence as p where " + "p.pupil = :pupil "
				+ "order by p.pupil asc"), })
@Entity
@Table
@Indexed(index = "indexes/presences")
@SequenceGenerator(name = "seq_presence", initialValue = 1, allocationSize = 100)
public class Presence {

	@Id
	@DocumentId
	@GeneratedValue(strategy = SEQUENCE, generator = "seq_presence")
	private Integer id;

	private Date day;

	@ManyToOne
	@JoinColumn(name = "pupil_id")
	private Pupil pupil;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Pupil getPupil() {
		return pupil;
	}

	public void setPupil(Pupil pupil) {
		this.pupil = pupil;
	}

}
