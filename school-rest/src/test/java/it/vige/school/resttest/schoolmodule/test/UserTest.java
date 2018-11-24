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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import it.vige.school.RestCaller;
import it.vige.school.dto.User;

public class UserTest extends RestCaller {

	private final static String url = "http://localhost:8080/school-rest/services/school/";
	private final static String authorization = "Basic cm9vdDpndG4=";

	@Test
	public void getUser() {
		Response response = get(url + "findUsersBySchool/donlorenzomilani", authorization);
		List<User> users = response.readEntity(new GenericType<List<User>>() {
		});
		response.close();
		assertNotNull(users, "The users from Maiorana are found");
		assertEquals(29, users.size(), "The users from Maiorana are ok");
		response = get(url + "findUsersByRoom/1A", authorization);
		users = response.readEntity(new GenericType<List<User>>() {
		});
		response.close();
		assertNotNull(response, "The users from 1A are found");
		assertEquals(9, users.size(), "The users from 1A are ok");
		response = get(url + "findUsersBySchoolAndRoom/donlorenzomilani/1A", authorization);
		users = response.readEntity(new GenericType<List<User>>() {
		});
		response.close();
		assertNotNull(response, "The users from 1A are found");
		assertEquals(5, users.size(), "The users from donlorenzomilani 1A are ok");
		response = get(url + "findUserById/STNLCU76E15H501X", authorization);
		User user = response.readEntity(User.class);
		assertEquals("Luca", user.getName(), "User by id received");
		response.close();
	}
}
