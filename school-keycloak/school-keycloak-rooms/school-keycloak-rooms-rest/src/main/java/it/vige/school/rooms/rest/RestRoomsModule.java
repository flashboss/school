/******************************************************************************
 * Vige, Home of Professional Open Source Copyright 2010, Vige, and           *
 * individual contributors by the @authors tag. See the copyright.txt in the  *
 * distribution for a full listing of individual contributors.                *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may    *
 * not use this file except in compliance with the License. You may obtain    *
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0        *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 ******************************************************************************/
package it.vige.school.rooms.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import it.vige.school.rooms.ModuleException;
import it.vige.school.rooms.RoomsModule;
import it.vige.school.rooms.dto.Room;
import it.vige.school.rooms.dto.School;

@Path("/rooms/")
public class RestRoomsModule {

	@EJB
	private RoomsModule roomsModule;

	@GET
	@Path("findAllRooms")
	@Produces(APPLICATION_JSON)
	public List<Room> findAllRooms() throws ModuleException {
		return roomsModule.findAllRooms();
	}

	@GET
	@Path("findAllSchools")
	@Produces(APPLICATION_JSON)
	public List<School> findAllSchools() throws ModuleException {
		return roomsModule.findAllSchools();
	}

	@GET
	@Path("findSchoolById")
	@Produces(APPLICATION_JSON)
	public School findSchoolById(@QueryParam("school") String school) throws ModuleException {
		return roomsModule.findSchoolById(school);
	}

	@POST
	@Path("findRoomsBySchool")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public List<Room> findRoomsBySchool(School school) throws ModuleException {
		return roomsModule.findRoomsBySchool(school);
	}

	@POST
	@Path("createRoom")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Room createPresence(Room room) throws ModuleException {
		return roomsModule.createRoom(room);
	}

	@POST
	@Path("createSchool")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public School createSchool(School school) throws ModuleException {
		return roomsModule.createSchool(school);
	}

	@POST
	@Path("removeRoom")
	@Consumes(APPLICATION_JSON)
	public void removeRoom(Room room) throws ModuleException {
		roomsModule.removeRoom(room);
	}

	@POST
	@Path("removeSchool")
	@Consumes(APPLICATION_JSON)
	public void removeSchool(School school) throws ModuleException {
		roomsModule.removeSchool(school);
	}

}
