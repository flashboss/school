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
import static org.keycloak.admin.client.Keycloak.getInstance;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;

import it.vige.school.RestCaller;
import it.vige.school.dto.Presence;
import it.vige.school.dto.User;

public class PresenceTest extends RestCaller {

	private final static String url = "http://localhost:8080/school-rest/services/school/";
	
	private final static String user = "root";
	
	private final static String password = "gtn";

	@Test
	public void setPresence() {
		Calendar currentDay = getCalendarByDate(today());
		Presence presence = new Presence();
		presence.setDay(currentDay);
		User firstUser = new User();
		firstUser.setId("STNLCU76E15H501X");
		presence.setUser(firstUser);
		Response response = post(user, password, url + "createPresence", presence);
		presence = response.readEntity(Presence.class);
		assertNotNull(presence, "The presence was inserted");
		response.close();
		response = post(user, password, url + "findPresencesByUser", firstUser);
		List<Presence> presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(1, presences.size(), "The presence is found");
		response.close();
		response = post(user, password, url + "findPresencesByDay", currentDay);
		presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(1, presences.size(), "The presence is found");
		response.close();
		response = post(user, password, url + "findPresencesByMonth", currentDay);
		presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(1, presences.size(), "The presence is found");
		response.close();
		response = post(user, password, url + "findPresencesByYear", currentDay);
		presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(1, presences.size(), "The presence is found");
		response.close();
		response = post(user, password, url + "findPresenceByUserAndDay", presence);
		presence = response.readEntity(Presence.class);
		assertNotNull(presence, "The presence was inserted");
		response.close();
		response = post(user, password, url + "createPresence", presence);
		assertEquals(500, response.getStatus(), "We cannot insert duplicates presences");
		response.close();
		response = post(user, password, url + "removePresence", presence);
		response.close();
		response = post(user, password, url + "findPresencesByUser", firstUser);
		presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(0, presences.size(), "The presence is not found");
		response.close();
	}
}
