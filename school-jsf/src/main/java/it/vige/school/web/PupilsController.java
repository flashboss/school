package it.vige.school.web;

import static it.vige.school.Constants.ERROR;
import static it.vige.school.Utils.getCalendarByDate;
import static it.vige.school.Utils.getRoomByRole;
import static it.vige.school.Utils.getSchoolByRole;
import static java.util.stream.Collectors.toList;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.dto.Presence;
import it.vige.school.dto.Pupil;
import it.vige.school.dto.PupilByDay;

@SessionScoped
@Named
public class PupilsController implements Serializable {

	private static final long serialVersionUID = -2260430424205388307L;

	private static Logger log = getLogger(PupilsController.class);

	@Inject
	private SchoolModule schoolModule;

	@Inject
	private ConfigurationController configurationController;

	private List<Pupil> pupils;

	private List<Pupil> filteredPupils;

	private List<String> rooms;

	private List<String> schools;

	@PostConstruct
	public void init() {
		boolean isAdmin = configurationController.isAdmin();
		try {
			if (isAdmin) {
				pupils = schoolModule.findAllPupils();
			} else {
				String role = configurationController.getRole();
				if (configurationController.isSchoolOperator())
					pupils = schoolModule.findPupilsBySchool(role);
				else
					pupils = schoolModule.findPupilsBySchoolAndRoom(getSchoolByRole(role), getRoomByRole(role));
			}
			Calendar currentDay = getCalendarByDate(configurationController.getCurrentDay());
			List<Presence> presencesOfDay = schoolModule.findPresencesByDay(currentDay);
			pupils.forEach(x -> {
				for (Presence presence : presencesOfDay)
					if (presence.getPupil().equals(x) && presence.getDay().equals(currentDay))
						x.setPresent(true);
			});
			if (filteredPupils != null)
				filteredPupils.forEach(x -> {
					for (Pupil pupil : pupils)
						if (pupil.getId() == x.getId())
							x.setPresent(pupil.isPresent());
				});
			rooms = pupils.stream().map(x -> x.getRoom()).distinct().sorted().collect(toList());
			schools = pupils.stream().map(x -> x.getSchool()).distinct().sorted().collect(toList());
		} catch (ModuleException ex) {
			FacesMessage message = new FacesMessage(SEVERITY_INFO, // severity
					ERROR, ERROR);
			getCurrentInstance().addMessage(ERROR, message);
		}
	}

	public List<Pupil> getPupils() {
		return pupils;
	}

	public List<Pupil> getFilteredPupils() {
		return filteredPupils;
	}

	public void setFilteredPupils(List<Pupil> filteredPupils) {
		this.filteredPupils = filteredPupils;
	}

	public List<String> getRooms() {
		log.debug("get rooms: " + rooms);
		return rooms;
	}

	public List<String> getSchools() {
		log.debug("schools: " + schools);
		return schools;
	}

	public void addPresence(Pupil pupil) throws ModuleException {
		PupilByDay pupilByDay = new PupilByDay(pupil);
		pupilByDay.setDay(getCalendarByDate(configurationController.getCurrentDay()));
		if (pupil.isPresent())
			schoolModule.createPresence(pupilByDay);
		else
			schoolModule.removePresence(pupilByDay);
		log.debug("pupil: " + pupil);
	}

	public void refresh() {
		init();
	}

	public void report() throws IOException {
		log.debug("report");
		ExternalContext ec = getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/views/report.xhtml");
	}
}
