package it.vige.school.web;

import static it.vige.school.Constants.ADMIN_ROLE;
import static it.vige.school.Constants.ERROR;
import static it.vige.school.Utils.getCalendarByDate;
import static it.vige.school.Utils.getCurrentRole;
import static it.vige.school.Utils.today;
import static java.lang.System.getProperty;
import static java.text.DateFormat.SHORT;
import static java.text.DateFormat.getDateInstance;
import static java.util.Locale.getDefault;
import static java.util.stream.Collectors.toList;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.primefaces.event.SelectEvent;

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

	private DateFormat shortDateFormat = getDateInstance(SHORT, getDefault());

	private Date currentDay = today();

	private String currentLocale = getProperty("user.language");

	private List<Pupil> pupils;

	private List<String> rooms;

	private List<String> schools;

	private List<Pupil> filteredPupils;

	@PostConstruct
	public void init() {
		boolean isAdmin = isAdmin();
		try {
			if (isAdmin) {
				pupils = schoolModule.findAllPupils();
			} else {
				String role = getCurrentRole();
				pupils = schoolModule.findPupilsBySchool(role);
			}
			List<Presence> presencesOfDay = schoolModule.findPresencesByDay(getCalendarByDate(currentDay));
			pupils.forEach(x -> {
				for (Presence presence : presencesOfDay)
					if (presence.getPupil().equals(x) && presence.getDay().getTime().equals(currentDay))
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

	public List<String> getRooms() {
		return rooms;
	}

	public List<String> getSchools() {
		return schools;
	}

	public List<Pupil> getFilteredPupils() {
		return filteredPupils;
	}

	public void setFilteredPupils(List<Pupil> filteredPupils) {
		this.filteredPupils = filteredPupils;
	}

	public void addPresence(Pupil pupil) throws ModuleException {
		PupilByDay pupilByDay = new PupilByDay(pupil);
		pupilByDay.setDay(getCalendarByDate(currentDay));
		if (pupil.isPresent())
			schoolModule.createPresence(pupilByDay);
		else
			schoolModule.removePresence(pupilByDay);
		log.debug("pupil: " + pupil);
	}

	public boolean isAdmin() {
		FacesContext facesContext = getCurrentInstance();
		return facesContext.getExternalContext().isUserInRole(ADMIN_ROLE);
	}

	public String getFormattedToday() {
		String formattedDay = shortDateFormat.format(today());
		return formattedDay;
	}

	public Date getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(Date currentDay) {
		this.currentDay = currentDay;
	}

	public String getCurrentLocale() {
		return currentLocale;
	}

	public void onDateSelect(SelectEvent event) {
		currentDay = (Date) event.getObject();
		init();
	}

	public void refresh() {
		init();
	}
}
