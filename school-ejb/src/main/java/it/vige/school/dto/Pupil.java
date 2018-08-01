package it.vige.school.dto;

import java.io.Serializable;

public class Pupil implements Serializable {

	private static final long serialVersionUID = -6713889813860348323L;

	private Integer id;

	private String name;

	private String surname;

	private String room;

	private String school;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
}
