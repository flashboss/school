package it.vige.school.rooms;

import java.util.function.Function;

import it.vige.school.rooms.dto.Room;
import it.vige.school.rooms.dto.School;
import it.vige.school.rooms.model.RoomEntity;
import it.vige.school.rooms.model.RoomId;
import it.vige.school.rooms.model.SchoolEntity;

public interface Converters {

	Function<RoomEntity, Room> RoomEntityToRoom = new Function<RoomEntity, Room>() {

		public Room apply(RoomEntity t) {
			Room room = new Room();
			room.setClazz(t.getId().getClazz());
			room.setSection(t.getId().getSection());

			return room;
		}
	};

	Function<Room, RoomEntity> RoomToRoomEntity = new Function<Room, RoomEntity>() {

		public RoomEntity apply(Room t) {
			RoomEntity roomEntity = new RoomEntity();
			RoomId roomId = new RoomId();
			roomId.setClazz(t.getClazz());
			roomId.setSection(t.getSection());
			roomEntity.setId(roomId);

			return roomEntity;
		}
	};

	Function<SchoolEntity, School> SchoolEntityToSchool = new Function<SchoolEntity, School>() {

		public School apply(SchoolEntity t) {
			School school = new School();
			school.setId(t.getId());
			school.setDescription(t.getDescription());

			return school;
		}
	};

	Function<School, SchoolEntity> SchoolToSchoolEntity = new Function<School, SchoolEntity>() {

		public SchoolEntity apply(School t) {
			SchoolEntity schoolEntity = new SchoolEntity();
			schoolEntity.setId(t.getId());
			schoolEntity.setDescription(t.getDescription());

			return schoolEntity;
		}
	};
}