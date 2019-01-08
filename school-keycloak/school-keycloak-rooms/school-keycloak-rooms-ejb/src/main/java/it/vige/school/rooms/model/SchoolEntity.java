package it.vige.school.rooms.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "findAllSchools", query = "from SchoolEntity"),
		@NamedQuery(name = "findSchoolById", query = "select s from SchoolEntity where " + "s.id = :school") })
@Entity
@Table
public class SchoolEntity {

	@Id
	private String id;

	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
