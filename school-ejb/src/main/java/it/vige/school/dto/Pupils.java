package it.vige.school.dto;

import java.io.Serializable;
import java.util.List;

public class Pupils implements Serializable {

	private static final long serialVersionUID = -8598234417259228545L;

	private List<Pupil> entities;

	public List<Pupil> getEntities() {
		return entities;
	}

	public void setEntities(List<Pupil> entities) {
		this.entities = entities;
	}

}
