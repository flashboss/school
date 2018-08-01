package it.vige.school;

import java.util.List;

import javax.ejb.Local;

import it.vige.school.model.Presence;
import it.vige.school.model.Pupil;

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
	 * Find a presences by specifying its pupil
	 * 
	 * @param pupil pupil of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<Presence> findPresencesByPupil(Pupil pupil) throws ModuleException;

	/**
	 * DOCUMENT_ME
	 * 
	 * @param pupil    DOCUMENT_ME
	 * @return DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	Presence createPresence(Pupil pupil) throws ModuleException;

	/**
	 * removePresence methods to remove a Presence.
	 * 
	 * @param id DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	void removePresence(int id) throws ModuleException;
}
