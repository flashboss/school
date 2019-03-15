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
import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.client.Entity.form;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;
import static org.keycloak.OAuth2Constants.GRANT_TYPE;
import static org.keycloak.adapters.KeycloakDeploymentBuilder.build;
import static org.keycloak.adapters.authentication.ClientCredentialsProviderUtils.setClientCredentials;
import static org.keycloak.util.JsonSerialization.readValue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;

import it.vige.school.RestCaller;
import it.vige.school.dto.Presence;
import it.vige.school.dto.User;

public class PresenceTest extends RestCaller {

	private final static String url = "http://localhost:8080/school-rest/services/school/";
	private final static String url_users = "http://localhost:8180/auth/admin/realms/school-domain/users";

	@Test
	public void setPresence() throws IOException, VerificationException {
		String authorization = authenticate().getToken();
		Response response = get(authorization, url_users, null);
		List<UserRepresentation> users = response.readEntity(new GenericType<List<UserRepresentation>>() {
		});
		assertEquals(100, users.size(), "The query finds the first 100 users ");
		
		Calendar currentDay = getCalendarByDate(today());
		Presence presence = new Presence();
		presence.setDay(currentDay);
		User firstUser = new User();
		firstUser.setId(users.get(0).getId());
		presence.setUser(firstUser);
		response = post(authorization, url + "createPresence", presence);
		presence = response.readEntity(Presence.class);
		assertNotNull(presence, "The presence was inserted");
		response.close();
		
		response = post(authorization, url + "findPresencesByUser", firstUser);
		List<Presence> presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(1, presences.size(), "The presence is found");
		response.close();
		
		response = post(authorization, url + "findPresencesByDay", currentDay);
		presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(1, presences.size(), "The presence is found");
		response.close();
		
		response = post(authorization, url + "findPresencesByMonth", currentDay);
		presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(1, presences.size(), "The presence is found");
		response.close();
		
		response = post(authorization, url + "findPresencesByYear", currentDay);
		presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(1, presences.size(), "The presence is found");
		response.close();
		
		response = post(authorization, url + "findPresenceByUserAndDay", presence);
		presence = response.readEntity(Presence.class);
		assertNotNull(presence, "The presence was inserted");
		response.close();
		response = post(authorization, url + "createPresence", presence);
		assertEquals(500, response.getStatus(), "We cannot insert duplicates presences");
		response.close();
		
		response = post(authorization, url + "removePresence", presence);
		response.close();
		
		response = post(authorization, url + "findPresencesByUser", firstUser);
		presences = response.readEntity(new GenericType<List<Presence>>() {
		});
		assertEquals(0, presences.size(), "The presence is not found");
		response.close();
	}

	public AccessTokenResponse authenticate() throws IOException, VerificationException {

		FileInputStream config = new FileInputStream("src/test/resources/keycloak.json");
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

		String json = request.post(form(params), String.class);
		AccessTokenResponse tokenResp = readValue(json, AccessTokenResponse.class);

		return tokenResp;

	}
}
