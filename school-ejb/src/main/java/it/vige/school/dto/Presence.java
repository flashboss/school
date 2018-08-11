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

}
