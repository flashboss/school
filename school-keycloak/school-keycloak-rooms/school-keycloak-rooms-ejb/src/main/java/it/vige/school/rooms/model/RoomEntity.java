package it.vige.school.rooms.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Presences.
 * 
 * @author <a href="mailto:luca.stancapiano@vige.it">Luca Stancapiano </a>
 * @author <a href="mailto:toro.gm5s@gmail.com">Alessandro Toro </a>
 */

@NamedQueries({ @NamedQuery(name = "findAllRooms", query = "from RoomEntity"),
		@NamedQuery(name = "findRoomsBySchool", query = "select r from RoomEntity as r where "
				+ "r.id.school = :school " + "order by r.id.school asc"),
		@NamedQuery(name = "findRoomByClazzSectionAndSchool", query = "select r from RoomEntity as r where "
				+ "r.id.clazz = :clazz and r.id.section = :section and r.id.school = :school "
				+ "order by r.id.school asc") })
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
