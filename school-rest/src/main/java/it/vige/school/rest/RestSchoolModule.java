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
package it.vige.school.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.dto.Presence;
import it.vige.school.dto.Pupil;

@Path("/school/")
public class RestSchoolModule {

	@EJB
	private SchoolModule schoolModule;

	@GET
	@Path("findAllPupils")
	@Produces(APPLICATION_JSON)
	public List<Pupil> findAllPupils() throws ModuleException {
		return schoolModule.findAllPupils();
	}

	@GET
	@Path("findAllPresences")
	@Produces(APPLICATION_JSON)
	public List<Presence> findAllPresences() throws ModuleException {
		return schoolModule.findAllPresences();
	}

	@GET
	@Path("findPupilsByRoom/{room}")
	@Produces(APPLICATION_JSON)
	public List<Pupil> findPupilsByRoom(@PathParam("room") String room) throws ModuleException {
		return schoolModule.findPupilsByRoom(room);
	}

	@GET
	@Path("findPupilsBySchool/{school}")
	@Produces(APPLICATION_JSON)
	public List<Pupil> findPupilsBySchool(@PathParam("school") String school) throws ModuleException {
		return schoolModule.findPupilsBySchool(school);
	}

	@GET
	@Path("findPupilsBySchoolAndRoom/{school}/{room}")
	@Produces(APPLICATION_JSON)
	public List<Pupil> findPupilsBySchoolAndRoom(@PathParam("school") String school, @PathParam("room") String room)
			throws ModuleException {
		return schoolModule.findPupilsBySchoolAndRoom(school, room);
	}

	@GET
	@Path("findPupilById/{id}")
	@Produces(APPLICATION_JSON)
	public Pupil findPupilById(@PathParam("id") String id) throws ModuleException {
		return schoolModule.findPupilById(id);
	}

	@POST
	@Path("findPresencesByPupil")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public List<Presence> findPresencesByPupil(Pupil pupil) throws ModuleException {
		return schoolModule.findPresencesByPupil(pupil);
	}

	@POST
	@Path("findPresencesByDay")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public List<Presence> findPresencesByDay(Calendar day) throws ModuleException {
		return schoolModule.findPresencesByDay(day);
	}

	@POST
	@Path("findPresencesByMonth")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public List<Presence> findPresencesByMonth(Calendar month) throws ModuleException {
		return schoolModule.findPresencesByMonth(month);
	}

	@POST
	@Path("findPresencesByYear")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public List<Presence> findPresencesByYear(Calendar year) throws ModuleException {
		return schoolModule.findPresencesByYear(year);
	}

	@POST
	@Path("findPresenceByPupilAndDay")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Presence findPresenceByPupilAndDay(Presence presence) throws ModuleException {
		return schoolModule.findPresenceByPupilAndDay(presence);
	}

	@POST
	@Path("createPresence")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Presence createPresence(Presence presence) throws ModuleException {
		return schoolModule.createPresence(presence);
	}

	@POST
	@Path("removePresence")
	@Consumes(APPLICATION_JSON)
	public void removePresence(Presence presence) throws ModuleException {
		schoolModule.removePresence(presence);
	}

}
