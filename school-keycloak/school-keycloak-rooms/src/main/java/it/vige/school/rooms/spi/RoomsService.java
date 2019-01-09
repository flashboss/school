package it.vige.school.rooms.spi;

import java.util.List;

import org.keycloak.provider.Provider;

import it.vige.school.rooms.CompanyRepresentation;
import it.vige.school.rooms.Room;
import it.vige.school.rooms.School;

public interface RoomsService extends Provider {

	List<CompanyRepresentation> listCompanies();

	CompanyRepresentation findCompany(String id);

	CompanyRepresentation addCompany(CompanyRepresentation company);

	List<Room> findAllRooms();

	List<School> findAllSchools();

	School findSchoolById(String school);

	List<Room> findRoomsBySchool(School school);

	Room createPresence(Room room);

	School createSchool(School school);

	void removeRoom(Room room);

	void removeSchool(School school);
}
