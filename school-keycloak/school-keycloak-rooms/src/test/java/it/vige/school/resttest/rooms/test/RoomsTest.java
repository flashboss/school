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
import java.util.Arrays;
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

import it.vige.school.RestCaller;
import it.vige.school.rooms.Room;
import it.vige.school.rooms.School;

public class RoomsTest extends RestCaller {

	private final static String url_schools = "http://localhost:8180/auth/admin/realms/school-domain/rooms/schools";
	private final static String url_rooms = "http://localhost:8180/auth/admin/realms/school-domain/rooms/rooms";

	@Test
	public void setSchool() throws IOException, VerificationException {
		String authorization = authenticate().getToken();
		Response response = get(authorization, url_rooms, null);
		List<Room> rooms = response.readEntity(new GenericType<List<Room>>() {
		});
		assertEquals(100, rooms.size(), "The query finds the first 100 rooms ");
		School school = new School();
		school.setDescription("My School");
		Room firstRoom = rooms.get(0);
		school.getRooms().put("myschool", Arrays.asList("" + firstRoom.getClazz() + firstRoom.getSection()));
		response = post(authorization, url_schools + "createSchool", school);
		school = response.readEntity(School.class);
		assertNotNull(school, "The school was inserted");
		response.close();
		response = get(authorization, url_schools + school.getId(), null);
		List<School> schools = response.readEntity(new GenericType<List<School>>() {
		});
		assertEquals(1, schools.size(), "The school is found");
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
