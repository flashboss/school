package it.vige.school;

import java.util.function.Function;

import it.vige.school.dto.Presence;
import it.vige.school.dto.User;
import it.vige.school.model.PresenceEntity;
import it.vige.school.model.PresenceId;
import it.vige.school.model.UserEntity;

public interface Converters {

	Function<UserEntity, User> UserEntityToUser = new Function<UserEntity, User>() {

		public User apply(UserEntity t) {
			User user = new User();
			user.setId(t.getId());
			user.setName(t.getName());
			user.setSurname(t.getSurname());
			user.setIncome(t.getIncome());
			user.setRoom(t.getRoom());
			user.setSchool(t.getSchool());

			return user;
		}
	};

	Function<User, UserEntity> UserToUserEntity = new Function<User, UserEntity>() {

		public UserEntity apply(User t) {
			UserEntity userEntity = new UserEntity();
			userEntity.setId(t.getId());
			userEntity.setName(t.getName());
			userEntity.setSurname(t.getSurname());
			userEntity.setIncome(t.getIncome());
			userEntity.setRoom(t.getRoom());
			userEntity.setSchool(t.getSchool());

			return userEntity;
		}
	};

	Function<PresenceEntity, Presence> PresenceEntityToPresence = new Function<PresenceEntity, Presence>() {

		public Presence apply(PresenceEntity t) {
			Presence presence = new Presence();
			presence.setDay(t.getId().getDay());
			presence.setUser(UserEntityToUser.apply(t.getId().getUser()));

			return presence;
		}
	};

	Function<Presence, PresenceEntity> PresenceToPresenceEntity = new Function<Presence, PresenceEntity>() {

		public PresenceEntity apply(Presence t) {
			PresenceEntity presenceEntity = new PresenceEntity();
			PresenceId presenceId = new PresenceId();
			presenceId.setDay(t.getDay());
			presenceId.setUser(UserToUserEntity.apply(t.getUser()));
			presenceEntity.setId(presenceId);

			return presenceEntity;
		}
	};
}