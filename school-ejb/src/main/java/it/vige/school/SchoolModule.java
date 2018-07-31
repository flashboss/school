package it.vige.school;

import java.util.List;

import javax.ejb.Local;

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
	 * DOCUMENT_ME
	 * 
	 * @param category    DOCUMENT_ME
	 * @param name        DOCUMENT_ME
	 * @param description DOCUMENT_ME
	 * @return DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	Pupil createPupil(String name, String surname, String room, String school) throws ModuleException;

	/**
	 * removePupils methods to remove a Pupil.
	 * 
	 * @param id DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	void removePupil(int id) throws ModuleException;
}
