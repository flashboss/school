package it.vige.school;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import it.vige.school.dto.Presence;
import it.vige.school.dto.User;

@Local
public interface SchoolModule {

	/**
	 * Find all presences
	 * 
	 * @return presences
	 * @throws ModuleException Throws an exception if the presence cannot be found
	 */
	List<Presence> findAllPresences() throws ModuleException;

	/**
	 * Find a presences by specifying its user
	 * 
	 * @param user user of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the user cannot be found
	 */
	List<Presence> findPresencesByUser(User user) throws ModuleException;

	/**
	 * Find a presences by specifying its day
	 * 
	 * @param day day of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the user cannot be found
	 */
	List<Presence> findPresencesByDay(Calendar day) throws ModuleException;

	/**
	 * Find a presences by specifying its month
	 * 
	 * @param month month of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the user cannot be found
	 */
	List<Presence> findPresencesByMonth(Calendar month) throws ModuleException;

	/**
	 * Find a presences by specifying its month
	 * 
	 * @param year year of the presences to retrieve
	 * @return Presences with specified room
	 * @throws ModuleException Throws an exception if the user cannot be found
	 */
	List<Presence> findPresencesByYear(Calendar year) throws ModuleException;

	/**
	 * Find a presences by specifying its user and day
	 * 
	 * @param presence presence to retrieve
	 * @return Presence with specified room
	 * @throws ModuleException Throws an exception if the user cannot be found
	 */
	Presence findPresenceByUserAndDay(Presence presence) throws ModuleException;

	/**
	 * DOCUMENT_ME
	 * 
	 * @param presence DOCUMENT_ME
	 * @return DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	Presence createPresence(Presence presence) throws ModuleException;

	/**
	 * removePresence methods to remove a Presence.
	 * 
	 * @param presence DOCUMENT_ME
	 * @throws ModuleException DOCUMENT_ME
	 */
	void removePresence(Presence presence) throws ModuleException;
}
