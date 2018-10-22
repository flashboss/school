package it.vige.school.web;

import static it.vige.school.Constants.ERROR;
import static it.vige.school.Utils.getCalendarByDate;
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
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.dto.Presence;
import it.vige.school.dto.User;

@SessionScoped
@Named
public class Users implements Serializable {

	private static final long serialVersionUID = -2260430424205388307L;

	private static Logger log = getLogger(Users.class);

	@Inject
	private SchoolModule schoolModule;

	@Inject
	private Configuration configuration;

	@Inject
	private Report report;

	private List<User> users;

	private List<User> filteredUsers;

	private List<String> rooms;

	private List<String> schools;

	@PostConstruct
	public void init() {
		boolean isAdmin = configuration.isAdmin();
		try {
			if (isAdmin) {
				users = schoolModule.findAllUsers();
			} else {
				User currentUser = schoolModule.findUserById(configuration.getUser());
				if (configuration.isSchoolOperator())
					users = schoolModule.findUsersBySchool(currentUser.getSchool());
				else {
					users = schoolModule.findUsersBySchoolAndRoom(currentUser.getSchool(), currentUser.getRoom());
				}
			}
			Calendar currentDay = getCalendarByDate(configuration.getCurrentDay());
			List<Presence> presencesOfDay = schoolModule.findPresencesByDay(currentDay);
			users.forEach(x -> {
				for (Presence presence : presencesOfDay)
					if (presence.getUser().equals(x) && presence.getDay().equals(currentDay))
						x.setPresent(true);
			});
			if (filteredUsers != null)
				filteredUsers.forEach(x -> {
					for (User user : users)
						if (user.getId() == x.getId())
							x.setPresent(user.isPresent());
				});
			rooms = users.stream().map(x -> x.getRoom()).distinct().sorted().collect(toList());
			schools = users.stream().map(x -> x.getSchool()).distinct().sorted().collect(toList());
		} catch (ModuleException ex) {
			FacesMessage message = new FacesMessage(SEVERITY_INFO, // severity
					ERROR, ERROR);
			getCurrentInstance().addMessage(ERROR, message);
		}
	}

	public List<User> getUsers() {
		return users;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public List<String> getRooms() {
		log.debug("get rooms: " + rooms);
		return rooms;
	}

	public List<String> getSchools() {
		log.debug("schools: " + schools);
		return schools;
	}

	public void addPresence(User user) throws ModuleException {
		Presence presence = new Presence();
		presence.setDay(getCalendarByDate(configuration.getCurrentDay()));
		presence.setUser(user);
		if (user.isPresent())
			schoolModule.createPresence(presence);
		else
			schoolModule.removePresence(presence);
		log.debug("user: " + user);
	}

	public void refresh() {
		init();
	}

	public void report() throws IOException {
		log.debug("report");
		report.init();
		configuration.redirect("/views/report.xhtml");
	}
}
