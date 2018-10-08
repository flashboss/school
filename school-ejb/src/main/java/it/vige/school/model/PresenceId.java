package it.vige.school.model;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Embeddable
public class PresenceId implements Serializable {

	private static final long serialVersionUID = -3002711450547460105L;

	@Temporal(DATE)
	private Calendar day;

	@ManyToOne
	@JoinColumn(name = "pupil_id")
	private PupilEntity pupil;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((pupil == null) ? 0 : pupil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PresenceId other = (PresenceId) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (pupil == null) {
			if (other.pupil != null)
				return false;
		} else if (!pupil.equals(other.pupil))
			return false;
		return true;
	}

}
