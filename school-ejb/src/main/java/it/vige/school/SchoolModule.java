package it.vige.school;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import it.vige.school.dto.Presence;
import it.vige.school.dto.Pupil;
import it.vige.school.dto.PupilByDay;

@Local
public interface SchoolModule {

	/**
	 * Find all pupils
	 * 
	 * @return pupils
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<Pupil> findAllPupils() throws ModuleException;

	/**
	 * Find all presences
	 * 
	 * @return presences
	 * @throws ModuleException Throws an exception if the presence cannot be found
	 */
	List<Presence> findAllPresences() throws ModuleException;

	/**
	 * Find a pupils by specifying its room
	 * 
	 * @param room room of the pupils to retrieve
	 * @return Pupils with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<Pupil> findPupilsByRoom(String room) throws ModuleException;

	/**
	 * Find a pupils by specifying its school
	 * 
	 * @param school school of the pupils to retrieve
	 * @return Pupils with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<Pupil> findPupilsBySchool(String school) throws ModuleException;

	/**
	 * Find a pupils by specifying its school and room
	 * 
	 * @param school school of the pupils to retrieve
	 * @param room room of the pupils to retrieve
	 * @return Pupils with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<Pupil> findPupilsBySchoolAndRoom(String school, String room) throws ModuleException;

	/**
	 * Find a presences by specifying its pupil
	 * 
	 * @param pupil pupil of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<Presence> findPresencesByPupil(Pupil pupil) throws ModuleException;

	/**
	 * Find a presences by specifying its day
	 * 
	 * @param day day of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<Presence> findPresencesByDay(Calendar day) throws ModuleException;

	/**
	 * Find a presences by specifying its month
	 * 
	 * @param month month of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<Presence> findPresencesByMonth(Calendar month) throws ModuleException;

	/**
	 * Find a presences by specifying its month
	 * 
	 * @param month month of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<Presence> findPresencesByYear(Calendar year) throws ModuleException;

	/**
	 * Find a presences by specifying its pupil and day
	 * 
	 * @param pupil pupil of the presences to retrieve
	 * @param day   day of the presences to retrieve
	 * @return Presence with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	Presence findPresenceByPupilAndDay(PupilByDay pupilByDay) throws ModuleException;

	/**
	 * DOCUMENT_ME
	 * 
	 * @param pupil DOCUMENT_ME
	 * @return DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	Presence createPresence(PupilByDay pupil) throws ModuleException;

	/**
	 * removePresence methods to remove a Presence.
	 * 
	 * @param pupil DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	void removePresence(PupilByDay pupil) throws ModuleException;
}
