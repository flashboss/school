package it.vige.school.dto;

import java.util.Calendar;

public class PupilByDay extends Pupil {

	private static final long serialVersionUID = -2687557693166425255L;

	private Calendar day;

	public PupilByDay(Pupil pupil) {
		setId(pupil.getId());
		setName(pupil.getName());
		setPresent(pupil.isPresent());
		setRoom(pupil.getRoom());
		setSchool(pupil.getSchool());
		setSurname(pupil.getSurname());
		setIncome(pupil.getIncome());
	}

	public Calendar getDay() {
		return day;
	}

	public void setDay(Calendar day) {
		this.day = day;
	}

}
