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
package it.vige.school.rooms.test;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;
import static org.keycloak.OAuth2Constants.GRANT_TYPE;
import static org.keycloak.adapters.KeycloakDeploymentBuilder.build;
import static org.keycloak.adapters.authentication.ClientCredentialsProviderUtils.setClientCredentials;
import static org.keycloak.util.JsonSerialization.readValue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessTokenResponse;

import it.vige.school.rooms.RestCaller;
import it.vige.school.rooms.dto.Room;
import it.vige.school.rooms.dto.School;

public class RoomsTest extends RestCaller {

	private final static String url = "http://localhost:8080/school-rest/services/school/";

	@Test
	public void setPresence() throws IOException, VerificationException {
		String authorization = authenticate().getToken();
		School school = new School();
		school.setId("schoolTest");
		school.setDescription("My School Test Description");
		Response response = post(authorization, url + "createSchool", school);
		school = response.readEntity(School.class);
		assertNotNull(school, "The school was inserted");
		response.close();
		Room room = new Room();
		room.setClazz(3);
		room.setSection('D');
		room.setSchool(school);
		response = post(authorization, url + "createRoom", room);
		room = response.readEntity(Room.class);
		response.close();
		assertNotNull(room, "The room was inserted");
		room = new Room();
		room.setClazz(4);
		room.setSection('B');
		room.setSchool(school);
		response = post(authorization, url + "createRoom", room);
		room = response.readEntity(Room.class);
		response.close();
		assertNotNull(room, "The room was inserted");
		response = post(authorization, url + "findRoomsBySchool", school);
		List<Room> rooms = response.readEntity(new GenericType<List<Room>>() {
		});
		assertEquals(2, rooms.size(), "The rooms are found");
		response.close();
		response = get(authorization, url + "findAllRooms", null);
		rooms = response.readEntity(new GenericType<List<Room>>() {
		});
		assertEquals(2, rooms.size(), "The rooms are found");
		response.close();
		response = get(authorization, url + "findAllSchools", null);
		List<School> schools = response.readEntity(new GenericType<List<School>>() {
		});
		assertEquals(1, schools.size(), "The school is found");
		response.close();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("school", school.getId());
		response = get(authorization, url + "findSchoolById", parameters);
		school = response.readEntity(School.class);
		assertEquals(1, schools.size(), "The school is found");
		response.close();
		for (Room rm : rooms) {
			response = post(authorization, url + "removeRoom", rm);
			response.close();
		}
		response = get(authorization, url + "findAllRooms", null);
		rooms = response.readEntity(new GenericType<List<Room>>() {
		});
		response.close();
		assertEquals(0, rooms.size(), "The rooms are not found");
		response = post(authorization, url + "removeSchool", school);
		response.close();
		response = get(authorization, url + "findAllSchools", null);
		schools = response.readEntity(new GenericType<List<School>>() {
		});
		assertEquals(0, schools.size(), "The schools are not found");
		response.close();
	}

	public AccessTokenResponse authenticate() throws IOException, VerificationException {

		FileInputStream config = new FileInputStream("src/main/webapp/WEB-INF/keycloak.json");
		KeycloakDeployment deployment = build(config);

		Form params = new Form();
		params.param(GRANT_TYPE, CLIENT_CREDENTIALS);
		Map<String, String> reqHeaders = new HashMap<>();
		Map<String, String> reqParams = new HashMap<>();
		setClientCredentials(deployment, reqHeaders, reqParams);

		Client client = newClient();
		Builder request = client.target(deployment.getTokenUrl()).request();

		for (Entry<String, String> header : reqHeaders.entrySet()) {
			request.header(header.getKey(), header.getValue());
		}
		for (Entry<String, String> param : reqParams.entrySet()) {
			params.param(param.getKey(), param.getValue());
		}

		String json = request.post(Entity.form(params), String.class);
		AccessTokenResponse tokenResp = readValue(json, AccessTokenResponse.class);

		return tokenResp;

	}
}
