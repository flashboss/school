package it.vige.school.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Presence implements Serializable {

	private static final long serialVersionUID = 5714119820308270263L;

	private Calendar day;

	private Pupil pupil;

	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public String getId() {
		return dateFormat.format(day.getTime()) + "--" + pupil.getId();
	}

	public Calendar getDay() {
		return day;
	}

	public void setDay(Calendar day) {
		this.day = day;
	}

	public Pupil getPupil() {
		return pupil;
	}

	public void setPupil(Pupil pupil) {
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
		Presence other = (Presence) obj;
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
