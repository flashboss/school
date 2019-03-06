package it.vige.school.rooms.spi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import it.vige.school.rooms.Room;
import it.vige.school.rooms.School;
import it.vige.school.rooms.jpa.RoomEntity;
import it.vige.school.rooms.jpa.RoomId;
import it.vige.school.rooms.jpa.SchoolEntity;

public interface Converters {

	Function<RoomEntity, Room> RoomEntityToRoom = new Function<RoomEntity, Room>() {

		public Room apply(RoomEntity t) {
			Room room = new Room();
			room.setClazz(t.getId().getClazz());
			room.setSection(t.getId().getSection());
			School school = new School();
			school.setId(t.getId().getSchool().getId());
			school.setDescription(t.getId().getSchool().getDescription());
			room.setSchool(school);

			return room;
		}
	};

	Function<Room, RoomEntity> RoomToRoomEntity = new Function<Room, RoomEntity>() {

		public RoomEntity apply(Room t) {
			RoomEntity roomEntity = new RoomEntity();
			RoomId roomId = new RoomId();
			roomId.setClazz(t.getClazz());
			roomId.setSection(t.getSection());
			roomId.setSchool(SchoolToSchoolEntity.apply(t.getSchool()));
			roomEntity.setId(roomId);

			return roomEntity;
		}
	};

	Function<SchoolEntity, School> SchoolEntityToSchool = new Function<SchoolEntity, School>() {

		public School apply(SchoolEntity t) {
			School school = new School();
			school.setId(t.getId());
			school.setDescription(t.getDescription());
			Map<String, List<String>> rooms = school.getRooms();
			for (RoomEntity room : t.getRooms()) {
				List<String> classes = school.getRooms().get(room.getId().getSection() + "");
				if (classes == null)
					classes = new ArrayList<String>();
				classes.add(room.getId().getClazz() + "");
				rooms.put(room.getId().getSection() + "", classes);
			}

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