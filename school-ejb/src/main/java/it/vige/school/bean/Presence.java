package it.vige.school.bean;

import java.io.Serializable;
import java.util.Date;

public class Presence implements Serializable {

	private static final long serialVersionUID = 5714119820308270263L;

	private Integer id;

	private Date day;

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
