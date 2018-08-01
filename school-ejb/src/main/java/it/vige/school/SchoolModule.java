package it.vige.school;

import java.util.List;

import javax.ejb.Local;

import it.vige.school.model.PresenceEntity;
import it.vige.school.model.PupilEntity;

@Local
public interface SchoolModule {

	/**
	 * Find all pupils
	 * 
	 * @return pupils
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<PupilEntity> findAllPupils() throws ModuleException;

	/**
	 * Find all presences
	 * 
	 * @return presences
	 * @throws ModuleException Throws an exception if the presence cannot be found
	 */
	List<PresenceEntity> findAllPresences() throws ModuleException;

	/**
	 * Find a pupils by specifying its room
	 * 
	 * @param room room of the pupils to retrieve
	 * @return Pupils with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<PupilEntity> findPupilsByRoom(String room) throws ModuleException;

	/**
	 * Find a pupils by specifying its school
	 * 
	 * @param school school of the pupils to retrieve
	 * @return Pupils with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<PupilEntity> findPupilsBySchool(String school) throws ModuleException;

	/**
	 * Find a presences by specifying its pupil
	 * 
	 * @param pupil pupil of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the pupil cannot be found
	 */
	List<PresenceEntity> findPresencesByPupil(PupilEntity pupil) throws ModuleException;

	/**
	 * DOCUMENT_ME
	 * 
	 * @param pupil    DOCUMENT_ME
	 * @return DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	PresenceEntity createPresence(PupilEntity pupil) throws ModuleException;

	/**
	 * removePresence methods to remove a Presence.
	 * 
	 * @param id DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	void removePresence(int id) throws ModuleException;
}
