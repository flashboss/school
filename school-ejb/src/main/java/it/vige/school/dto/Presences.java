package it.vige.school.dto;

import java.io.Serializable;
import java.util.List;

public class Presences implements Serializable {

	private static final long serialVersionUID = -1584960118088297237L;

	private List<Presence> entities;

	public List<Presence> getEntities() {
		return entities;
	}

	public void setEntities(List<Presence> entities) {
		this.entities = entities;
	}
}
