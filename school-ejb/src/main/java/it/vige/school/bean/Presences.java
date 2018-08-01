package it.vige.school.bean;

import java.io.Serializable;
import java.util.List;

import it.vige.school.model.PresenceEntity;

public class Presences implements Serializable {

	private static final long serialVersionUID = -1584960118088297237L;
	
	private List<PresenceEntity> entities;

	public List<PresenceEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<PresenceEntity> entities) {
		this.entities = entities;
	}
}
