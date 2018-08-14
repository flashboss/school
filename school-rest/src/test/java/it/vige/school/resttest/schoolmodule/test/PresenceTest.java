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
package it.vige.school.resttest.schoolmodule.test;

import static it.vige.school.Utils.getCalendarByDate;
import static it.vige.school.Utils.today;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import it.vige.school.dto.Presence;
import it.vige.school.dto.Presences;
import it.vige.school.dto.Pupil;
import it.vige.school.dto.PupilByDay;
import it.vige.school.dto.Pupils;
import it.vige.school.resttest.RestCaller;

public class PresenceTest extends RestCaller {

	private final static String url = "http://localhost:8080/school-rest/services/school/";
	private final static String authorization = "Basic cm9vdDpndG4=";

	@Test
	public void setPresence() {
		Response response = get(url + "findAllPupils", authorization);
		Pupils result = response.readEntity(Pupils.class);
		List<Pupil> pupils = result.getEntities();
		assertEquals(36, pupils.size(), "The pupils from are all");
		response.close();
		Pupil firstPupil = pupils.get(0);
		Calendar currentDay = getCalendarByDate(today());
		PupilByDay pupilByDay = new PupilByDay(firstPupil);
		pupilByDay.setDay(currentDay);
		Presence presence = new Presence();
		presence.setPupil(firstPupil);
		response = post(url + "createPresence", authorization, pupilByDay);
		presence = response.readEntity(Presence.class);
		assertNotNull(presence, "The presence was inserted");
		response.close();
		response = post(url + "findPresencesByPupil", authorization, firstPupil);
		Presences presences = response.readEntity(Presences.class);
		assertEquals(1, presences.getEntities().size(), "The presence is found");
		response.close();
		response = post(url + "findPresencesByDay", authorization, currentDay);
		presences = response.readEntity(Presences.class);
		assertEquals(1, presences.getEntities().size(), "The presence is found");
		response.close();
		response = post(url + "findPresencesByMonth", authorization, currentDay);
		presences = response.readEntity(Presences.class);
		assertEquals(1, presences.getEntities().size(), "The presence is found");
		response.close();
		response = post(url + "findPresenceByPupilAndDay", authorization, pupilByDay);
		presence = response.readEntity(Presence.class);
		assertNotNull(presence, "The presence was inserted");
		response.close();
		response = post(url + "removePresence", authorization, pupilByDay);
		response.close();
		response = post(url + "findPresencesByPupil", authorization, firstPupil);
		presences = response.readEntity(Presences.class);
		assertEquals(0, presences.getEntities().size(), "The presence is not found");
		response.close();
	}
}
