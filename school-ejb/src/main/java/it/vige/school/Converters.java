package it.vige.school;

import java.util.function.Function;

import it.vige.school.dto.Presence;
import it.vige.school.dto.Pupil;
import it.vige.school.model.PresenceEntity;
import it.vige.school.model.PupilEntity;

public interface Converters {

	Function<PupilEntity, Pupil> PupilEntityToPupil = new Function<PupilEntity, Pupil>() {

		public Pupil apply(PupilEntity t) {
			Pupil pupil = new Pupil();
			pupil.setId(t.getId());
			pupil.setName(t.getName());
			pupil.setSurname(t.getSurname());
			pupil.setRoom(t.getRoom());
			pupil.setSchool(t.getSchool());

			return pupil;
		}
	};

	Function<Pupil, PupilEntity> PupilToPupilEntity = new Function<Pupil, PupilEntity>() {

		public PupilEntity apply(Pupil t) {
			PupilEntity pupilEntity = new PupilEntity();
			pupilEntity.setId(t.getId());
			pupilEntity.setName(t.getName());
			pupilEntity.setSurname(t.getSurname());
			pupilEntity.setRoom(t.getRoom());
			pupilEntity.setSchool(t.getSchool());

			return pupilEntity;
		}
	};

	Function<PresenceEntity, Presence> PresenceEntityToPresence = new Function<PresenceEntity, Presence>() {

		public Presence apply(PresenceEntity t) {
			Presence presence = new Presence();
			presence.setId(t.getId());
			presence.setDay(t.getDay());
			presence.setPupil(PupilEntityToPupil.apply(t.getPupil()));

			return presence;
		}
	};

	Function<Presence, PresenceEntity> PresenceToPresenceEntity = new Function<Presence, PresenceEntity>() {

		public PresenceEntity apply(Presence t) {
			PresenceEntity presenceEntity = new PresenceEntity();
			presenceEntity.setId(t.getId());
			presenceEntity.setDay(t.getDay());
			presenceEntity.setPupil(PupilToPupilEntity.apply(t.getPupil()));

			return presenceEntity;
		}
	};
}