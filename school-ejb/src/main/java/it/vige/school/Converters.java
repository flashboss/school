package it.vige.school;

import java.util.function.Function;

import it.vige.school.dto.Presence;
import it.vige.school.dto.User;
import it.vige.school.model.PresenceEntity;
import it.vige.school.model.PresenceId;

public interface Converters {

	Function<String, User> StringToUser = new Function<String, User>() {

		public User apply(String t) {
			User user = new User();
			String[] splittedValue = t.split("-");
			user.setId(splittedValue[0]);
			user.setName(splittedValue[1]);
			user.setSurname(splittedValue[2]);
			user.setIncome(Integer.parseInt(splittedValue[3]));
			user.setRoom(splittedValue[4]);
			user.setSchool(splittedValue[5]);

			return user;
		}
	};

	Function<PresenceEntity, Presence> PresenceEntityToPresence = new Function<PresenceEntity, Presence>() {

		public Presence apply(PresenceEntity t) {
			Presence presence = new Presence();
			presence.setDay(t.getId().getDay());
			presence.setUser(StringToUser.apply(t.getId().getUser()));

			return presence;
		}
	};

	Function<Presence, PresenceEntity> PresenceToPresenceEntity = new Function<Presence, PresenceEntity>() {

		public PresenceEntity apply(Presence t) {
			PresenceEntity presenceEntity = new PresenceEntity();
			PresenceId presenceId = new PresenceId();
			presenceId.setDay(t.getDay());
			presenceId.setUser(t.getUser().getId());
			presenceEntity.setId(presenceId);

			return presenceEntity;
		}
	};
}