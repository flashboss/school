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
package it.vige.school.rooms;

import static javax.json.bind.JsonbBuilder.create;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

import javax.json.bind.Jsonb;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public abstract class RestCaller {

	protected Response get(String authorization, String url, Map<String, Object> params) {
		Client client = newClient();
		Supplier<String> accessTokenProvider = () -> {
			return authorization;
		};
		client.register(new BearerAuthRequestFilter(accessTokenProvider));
		WebTarget target = client.target(url);
		if (params != null) {
			for (Map.Entry<String, Object> param : params.entrySet())
				target = target.queryParam(param.getKey(), param.getValue());
		}
		return target.request().get();
	}

	protected Response post(String authorization, String url, Object entity) {
		Client client = newClient();
		Jsonb jsonb = create();
		String json = jsonb.toJson(entity);
		Supplier<String> accessTokenProvider = () -> {
			return authorization;
		};
		client.register(new BearerAuthRequestFilter(accessTokenProvider));
		WebTarget target = client.target(url);
		Entity<String> restEntity = entity(json, APPLICATION_JSON);
		return target.request().post(restEntity);
	}

	private static class BearerAuthRequestFilter implements ClientRequestFilter {

		private final Supplier<String> accessTokenProvider;

		public BearerAuthRequestFilter(Supplier<String> accessTokenProvider) {
			this.accessTokenProvider = accessTokenProvider;
		}

		@Override
		public void filter(ClientRequestContext requestContext) throws IOException {
			requestContext.getHeaders().putSingle("Authorization", "Bearer " + accessTokenProvider.get());
		}
	}
}
