package it.vige.school.dto;

import java.io.Serializable;
import java.util.Calendar;

public class Presence implements Serializable {

	private static final long serialVersionUID = 5714119820308270263L;

	private Integer id;

	private Calendar day;

	private Pupil pupil;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
