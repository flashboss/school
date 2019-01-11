package it.vige.school.rooms.jpa;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "findAllSchools", query = "from SchoolEntity"),
		@NamedQuery(name = "findSchoolById", query = "select s from SchoolEntity as s where " + "s.id = :school") })
@Entity
@Table
public class SchoolEntity {

	@Id
	private String id;

	private String description;

	@OneToMany(mappedBy = "id.school", cascade = ALL, orphanRemoval = true)
	private List<RoomEntity> rooms = new ArrayList<>();

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

	public List<RoomEntity> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomEntity> rooms) {
		this.rooms = rooms;
	}

}
