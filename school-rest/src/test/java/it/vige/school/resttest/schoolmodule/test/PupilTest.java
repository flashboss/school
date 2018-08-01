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

import it.vige.school.model.PupilEntity;
import it.vige.school.resttest.RestCaller;

public class PupilTest extends RestCaller {

	private final static String url = "http://localhost:8080/school-rest/services/school/";
	private final static String authorization = "Basic cm9vdDpndG4=";

	@Test
	public void getPupil() {
		Response response = get(url + "findPupilBySchool/Maiorana", authorization);
		List<PupilEntity> result = response.readEntity(new GenericType<List<PupilEntity>>() {
		});
		assertNotNull(result, "The pupils from Maiorana are found");
		assertEquals(19, result.size(), "The pupils from Maiorana are ok");
		response = get(url + "findPupilByRoom/1A", authorization);
		result = response.readEntity(new GenericType<List<PupilEntity>>() {
		});
		assertNotNull(response, "The pupils from 1A are found");
		assertEquals(18, result.size(), "The pupils from 1A are ok");
	}
}