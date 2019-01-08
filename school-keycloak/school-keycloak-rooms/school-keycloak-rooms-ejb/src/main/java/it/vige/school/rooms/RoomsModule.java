package it.vige.school.rooms;

import java.util.List;

import javax.ejb.Local;

import it.vige.school.rooms.dto.Room;
import it.vige.school.rooms.dto.School;

@Local
public interface RoomsModule {

	/**
	 * Find all rooms
	 * 
	 * @return rooms
	 * @throws ModuleException Throws an exception if the presence cannot be found
	 */
	List<Room> findAllRooms() throws ModuleException;

	/**
	 * Find all schools
	 * 
	 * @return schools
	 * @throws ModuleException Throws an exception if the presence cannot be found
	 */
	List<School> findAllSchools() throws ModuleException;

	/**
	 * Find schools by id
	 * 
	 * @param is is of the school to retrieve
	 * @return schools
	 * @throws ModuleException Throws an exception if the presence cannot be found
	 */
	School findSchoolById(String id) throws ModuleException;

	/**
	 * Find a room by specifying its school
	 * 
	 * @param school school of the rooms to retrieve
	 * @return Rooms with specified room
	 * @throws ModuleException Throws an exception if the user cannot be found
	 */
	List<Room> findRoomsBySchool(School school) throws ModuleException;

	/**
	 * DOCUMENT_ME
	 * 
	 * @param room DOCUMENT_ME
	 * @return DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	Room createRoom(Room room) throws ModuleException;

	/**
	 * DOCUMENT_ME
	 * 
	 * @param school DOCUMENT_ME
	 * @return DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	School createSchool(School school) throws ModuleException;

	/**
	 * removeRoom methods to remove a Room.
	 * 
	 * @param room DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	void removeRoom(Room room) throws ModuleException;

	/**
	 * removeSchool methods to remove a School.
	 * 
	 * @param school DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	void removeSchool(School school) throws ModuleException;
}
