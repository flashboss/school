package it.vige.school.rooms.jpa;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "findAllRooms", query = "from RoomEntity"),
		@NamedQuery(name = "findRoomsBySchool", query = "select r from RoomEntity as r where "
				+ "r.id.school.id = :school " + "order by r.id.school.id asc"),
		@NamedQuery(name = "findRoomByClazzSectionAndSchool", query = "select r from RoomEntity as r where "
				+ "r.id.clazz = :clazz and r.id.section = :section and r.id.school.id = :school "
				+ "order by r.id.school.id asc") })
@Entity
@Table
public class RoomEntity {

	@EmbeddedId
	private RoomId id;

	public RoomId getId() {
		return id;
	}

	public void setId(RoomId id) {
		this.id = id;
	}

}
