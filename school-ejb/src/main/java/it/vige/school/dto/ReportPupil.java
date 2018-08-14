package it.vige.school.dto;

public class ReportPupil extends Pupil {

	private static final long serialVersionUID = 8684674995138083622L;

	private double quote;

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
		return quote;
	}

	public void setQuote(double quote) {
		this.quote = quote;
	}

	public int getPresences() {
		return presences;
	}

	public void setPresences(int presences) {
		this.presences = presences;
	}

}
