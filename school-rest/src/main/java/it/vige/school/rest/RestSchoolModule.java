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

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.model.PresenceEntity;
import it.vige.school.model.PupilEntity;

@Path("/school/")
public class RestSchoolModule implements SchoolModule {

	@EJB
	private SchoolModule schoolModule;

	@GET
	@Path("findAllPupils")
	@Produces(APPLICATION_JSON)
	@Override
	public List<PupilEntity> findAllPupils() throws ModuleException {
		return schoolModule.findAllPupils();
	}

	@GET
	@Path("findAllPresences")
	@Produces(APPLICATION_JSON)
	@Override
	public List<PresenceEntity> findAllPresences() throws ModuleException {
		return schoolModule.findAllPresences();
	}

	@GET
	@Path("findPupilsByRoom/{room}")
	@Produces(APPLICATION_JSON)
	@Override
	public List<PupilEntity> findPupilsByRoom(String room) throws ModuleException {
		return schoolModule.findPupilsByRoom(room);
	}

	@GET
	@Path("findPupilsBySchool/{school}")
	@Produces(APPLICATION_JSON)
	@Override
	public List<PupilEntity> findPupilsBySchool(String school) throws ModuleException {
		return schoolModule.findPupilsBySchool(school);
	}

	@POST
	@Path("findPresencesByPupil/{pupil}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@Override
	public List<PresenceEntity> findPresencesByPupil(PupilEntity pupil) throws ModuleException {
		return schoolModule.findPresencesByPupil(pupil);
	}

	@POST
	@Path("createPresence")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@Override
	public PresenceEntity createPresence(PupilEntity pupil) throws ModuleException {
		return schoolModule.createPresence(pupil);
	}

	@GET
	@Path("removePresence/{id}")
	@Override
	public void removePresence(int id) throws ModuleException {
		schoolModule.removePresence(id);
	}

}
