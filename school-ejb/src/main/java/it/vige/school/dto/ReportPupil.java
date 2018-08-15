package it.vige.school.dto;

import it.vige.school.Constants;

public class ReportPupil extends Pupil implements Constants {

	private static final long serialVersionUID = 8684674995138083622L;

	private int presences;

	public ReportPupil(Pupil pupil) {
		setId(pupil.getId());
		setName(pupil.getName());
		setPresent(pupil.isPresent());
		setRoom(pupil.getRoom());
		setSchool(pupil.getSchool());
		setSurname(pupil.getSurname());
		setIncome(pupil.getIncome());
	}

	public double getQuote() {
		return presences * calculateQuote(getIncome());
	}

	public int getPresences() {
		return presences;
	}

	public void setPresences(int presences) {
		this.presences = presences;
	}

}
