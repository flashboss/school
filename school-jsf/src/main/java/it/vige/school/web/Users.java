package it.vige.school.web;

import static it.vige.school.Utils.getCalendarByDate;
import static java.util.stream.Collectors.toList;
import static org.jboss.logging.Logger.getLogger;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import it.vige.school.ModuleException;
import it.vige.school.RestCaller;
import it.vige.school.SchoolModule;
import it.vige.school.dto.Presence;
import it.vige.school.dto.User;

@SessionScoped
@Named
public class Users extends RestCaller implements Serializable {

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

	boolean initialized;

	public void init() throws ModuleException {
		if (!initialized) {
			boolean isAdmin = configuration.isAdmin();
			if (isAdmin) {
				users = findAllUsers();
			} else {
				User currentUser = findUserById(configuration.getUser());
				if (configuration.isSchoolOperator())
					users = findUsersBySchool(currentUser.getSchool());
				else {
					users = findUsersBySchoolAndRoom(currentUser.getSchool(), currentUser.getRoom());
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
			initialized = true;
		}
	}

	public List<User> findAllUsers() throws ModuleException {
		try {
			String url = configuration.getAuthServerUrl() + "/admin/realms/" + configuration.getRealm() + "/users";
			Response response = get(configuration.getAccessToken(), url);
			Map<String, Object> map = response.readEntity(new GenericType<Map<String, Object>>() {
			});
			List<User> userList = response.readEntity(new GenericType<List<User>>() {
			});
			response.close();
			log.debug("user found: " + userList);

			return userList;
		} catch (Exception e) {
			String message = "Cannot find user";
			throw new ModuleException(message, e);
		}
	}

	public List<User> findUsersByRoom(String room) throws ModuleException {
		if (room != null) {
			try {
				List<User> userList = findAllUsers();
				log.debug("user found: " + userList);
				return userList.stream().filter(t -> room.equals(t.getRoom())).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find user by room " + room;
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	public List<User> findUsersBySchool(String school) throws ModuleException {
		if (school != null) {
			try {
				List<User> userList = findAllUsers();
				log.debug("user found: " + userList);
				return userList.stream().filter(t -> school.equals(t.getSchool())).collect(toList());
			} catch (Exception e) {
				String message = "Cannot find user by room " + school;
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	public List<User> findUsersBySchoolAndRoom(String school, String room) throws ModuleException {
		if (school != null) {
			try {
				List<User> userList = findAllUsers();
				log.debug("user found: " + userList);
				return userList.stream().filter(t -> school.equals(t.getSchool()) && room.equals(t.getRoom()))
						.collect(toList());
			} catch (Exception e) {
				String message = "Cannot find user by school " + school;
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
		}
	}

	public User findUserById(String id) throws ModuleException {
		if (id != null) {
			try {
				String url = configuration.getAuthServerUrl() + "/realms/" + configuration.getRealm()
						+ "/protocol/openid-connect/userinfo";
				Response response = post(configuration.getAccessToken(),
						configuration.getAuthServerUrl() + url + "/" + id, "{}");
				User user = response.readEntity(User.class);
				response.close();
				log.debug("user found: " + user);

				return user;
			} catch (Exception e) {
				String message = "Cannot find user by id " + id;
				throw new ModuleException(message, e);
			}
		} else {
			throw new IllegalArgumentException("room cannot be null");
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

	public void refresh() throws ModuleException {
		init();
	}

	public void report() throws IOException {
		log.debug("report");
		report.init();
		report.setFilteredUsers(null);
		configuration.redirect("/views/report.xhtml");
	}
}
