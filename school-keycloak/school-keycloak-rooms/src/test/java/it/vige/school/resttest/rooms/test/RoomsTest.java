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
package it.vige.school.resttest.rooms.test;

import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.keycloak.authorization.client.AuthzClient.create;
import static org.keycloak.util.JsonSerialization.readValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessTokenResponse;

import it.vige.school.RestCaller;
import it.vige.school.rooms.Room;
import it.vige.school.rooms.School;

public class RoomsTest extends RestCaller {

	private final static String url_schools = "http://localhost:8180/auth/realms/school-domain/rooms/schools";
	private final static String url_rooms = "http://localhost:8180/auth/realms/school-domain/rooms/rooms";

	@Test
	public void setSchool() throws IOException, VerificationException {
		Response response = get(null, url_rooms, null);
		List<Room> rooms = response.readEntity(new GenericType<List<Room>>() {
		});
		assertEquals(111, rooms.size(), "The query finds all 111 rooms ");

		School school = new School();
		school.setDescription("My School");
		Room firstRoom = rooms.get(0);
		school.getRooms().put("myschool", asList("" + firstRoom.getClazz() + firstRoom.getSection()));
		String authorization = authenticate().getToken();
		response = post(authorization, url_schools, school);
		school = response.readEntity(School.class);
		assertNotNull(school, "The school was inserted");
		response.close();

		response = get(null, url_schools + school.getId(), null);
		List<School> schools = response.readEntity(new GenericType<List<School>>() {
		});
		assertEquals(1, schools.size(), "The school is found");
		response.close();

		school.setDescription("new description");
		response = put(authorization, url_schools, school);
		school = response.readEntity(School.class);
		assertEquals("new description", school.getDescription(), "The school was updated");
		response.close();

		response = delete(authorization, url_schools + school.getId(), null);
		school = response.readEntity(School.class);
		assertNull(school, "The school was deleted");
		response.close();
	}

	public AccessTokenResponse authenticate() throws IOException, VerificationException {

		InputStream configStream = currentThread().getContextClassLoader().getResourceAsStream("keycloak.json");
		AuthzClient authzClient = create(readValue(configStream, Configuration.class));
		// send the authorization request to the server in order to
		// obtain an access token granted to the user

		AccessTokenResponse tokenResp = authzClient.obtainAccessToken("root", "gtn");

		return tokenResp;

	}
}
