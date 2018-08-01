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
import it.vige.school.dto.Presences;
import it.vige.school.dto.Pupil;
import it.vige.school.dto.Pupils;

@Path("/school/")
public class RestSchoolModule {

	@EJB
	private SchoolModule schoolModule;

	@GET
	@Path("findAllPupils")
	@Produces(APPLICATION_JSON)
	public Pupils findAllPupils() throws ModuleException {
		Pupils pupils = new Pupils();
		pupils.setEntities(schoolModule.findAllPupils());
		return pupils;
	}

	@GET
	@Path("findAllPresences")
	@Produces(APPLICATION_JSON)
	public Presences findAllPresences() throws ModuleException {
		Presences presences = new Presences();
		presences.setEntities(schoolModule.findAllPresences());
		return presences;
	}

	@GET
	@Path("findPupilsByRoom/{room}")
	@Produces(APPLICATION_JSON)
	public Pupils findPupilsByRoom(@PathParam("room") String room) throws ModuleException {
		Pupils pupils = new Pupils();
		pupils.setEntities(schoolModule.findPupilsByRoom(room));
		return pupils;
	}

	@GET
	@Path("findPupilsBySchool/{school}")
	@Produces(APPLICATION_JSON)
	public Pupils findPupilsBySchool(@PathParam("school") String school) throws ModuleException {
		Pupils pupils = new Pupils();
		pupils.setEntities(schoolModule.findPupilsBySchool(school));
		return pupils;
	}

	@POST
	@Path("findPresencesByPupil")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Presences findPresencesByPupil(Pupil pupil) throws ModuleException {
		Presences presences = new Presences();
		presences.setEntities(schoolModule.findPresencesByPupil(pupil));
		return presences;
	}

	@POST
	@Path("createPresence")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Presence createPresence(Pupil pupil) throws ModuleException {
		return schoolModule.createPresence(pupil);
	}

	@GET
	@Path("removePresence/{id}")
	public void removePresence(@PathParam("id") int id) throws ModuleException {
		schoolModule.removePresence(id);
	}

}
