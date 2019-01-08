package it.vige.school.dto;

import it.vige.school.Constants;

public class ReportUser extends User implements Constants {

	private static final long serialVersionUID = 8684674995138083622L;

	private int presences;

	public ReportUser(User user) {
		update(user);
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

	public void update(User user) {
		setId(user.getId());
		setName(user.getName());
		setPresent(user.isPresent());
		setRoom(user.getRoom());
		setSchool(user.getSchool());
		setSurname(user.getSurname());
		setIncome(user.getIncome());
	}

}
