package it.vige.school.web;

import static java.lang.Integer.parseInt;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.keycloak.representations.idm.UserRepresentation;

import it.vige.school.dto.User;

public interface Converters {

	Function<UserRepresentation, User> UserRepresentationToUser = new Function<UserRepresentation, User>() {

		public User apply(UserRepresentation t) {
			User user = new User();
			user.setId(t.getUsername().toUpperCase());
			user.setName(t.getFirstName());
			user.setSurname(t.getLastName());
			Map<String, List<String>> attributes = t.getAttributes();
			int income = parseInt(attributes.get("income").get(0));
			List<String> rooms = attributes.get("room");
			String room = "";
			if (rooms != null)
				room = rooms.get(0);
			String school = attributes.get("school").get(0);
			user.setIncome(income);
			if (room != null)
				user.setRoom(room);
			user.setSchool(school);

			return user;
		}
	};
}