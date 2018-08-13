package it.vige.school.web;

import static it.vige.school.Utils.getCalendarByDate;
import static org.jboss.logging.Logger.getLogger;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.dto.Pupil;
import it.vige.school.dto.PupilByDay;

@SessionScoped
@Named
public class PresencesController implements Serializable {

	private static final long serialVersionUID = -2260430424205388307L;

	private static Logger log = getLogger(PresencesController.class);

	@Inject
	private SchoolModule schoolModule;

	@Inject
	private ConfigurationController configurationController;

	public void addPresence(Pupil pupil) throws ModuleException {
		PupilByDay pupilByDay = new PupilByDay(pupil);
		pupilByDay.setDay(getCalendarByDate(configurationController.getCurrentDay()));
		if (pupil.isPresent())
			schoolModule.createPresence(pupilByDay);
		else
			schoolModule.removePresence(pupilByDay);
		log.debug("pupil: " + pupil);
	}
}
