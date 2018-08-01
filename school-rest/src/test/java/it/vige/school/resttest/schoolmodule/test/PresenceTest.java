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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import it.vige.school.model.PresenceEntity;
import it.vige.school.model.PupilEntity;
import it.vige.school.resttest.RestCaller;

public class PresenceTest extends RestCaller {

	private final static String url = "http://localhost:8080/school-rest/services/school/";
	private final static String authorization = "Basic cm9vdDpndG4=";

	@Test
	public void setPresence() {
		Response response = get(url + "findAllPupils", authorization);
		List<PupilEntity> result = response.readEntity(new GenericType<List<PupilEntity>>() {
		});
		PupilEntity firstPupil = result.get(0);
		response = post(url + "findPresencesByPupil", authorization, firstPupil);
		PresenceEntity presence = new PresenceEntity();
		presence.setPupil(firstPupil);
		response = post(url + "createPresence", authorization, presence);
		response.close();
		presence = response.readEntity(PresenceEntity.class);
		response = post(url + "findPresencesByPupil", authorization, firstPupil);
		assertNotNull(response, "The presence is found");
		response = get(url + "removePresence/" + presence.getId(), authorization);
		response = post(url + "findPresencesByPupil", authorization, firstPupil);
		assertNotNull(response, "The presence is not found");
	}
}
