package it.vige.school.bean;

import java.io.Serializable;
import java.util.List;

import it.vige.school.model.PupilEntity;

public class Pupils implements Serializable {

	private static final long serialVersionUID = -8598234417259228545L;

	private List<PupilEntity> entities;

	public List<PupilEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<PupilEntity> entities) {
		this.entities = entities;
	}

}
