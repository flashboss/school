package it.vige.school.web;

import static it.vige.school.Constants.ERROR;
import static it.vige.school.Utils.getCalendarByDate;
import static it.vige.school.web.ReportType.MONTH;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.dto.Presence;
import it.vige.school.dto.User;
import it.vige.school.dto.ReportUser;

@RequestScoped
@Named
public class Report {

	private static Logger log = getLogger(Report.class);

	@Inject
	private Users users;

	private List<ReportUser> reportUsers;

	private List<User> filteredUsers;

	@Inject
	private SchoolModule schoolModule;

	@Inject
	private Configuration configuration;

	private ReportType type = MONTH;

	@PostConstruct
	public void init() {
		log.debug("calling the report controller");
		try {
			List<User> oldUsers = users.getUsers();
			Calendar currentDate = getCalendarByDate(configuration.getCurrentDate());
			List<Presence> presencesByCurrentDate = null;
			if (type == MONTH)
				presencesByCurrentDate = schoolModule.findPresencesByMonth(currentDate);
			else
				presencesByCurrentDate = schoolModule.findPresencesByYear(currentDate);
			List<Presence> presences = presencesByCurrentDate;
			reportUsers = new ArrayList<ReportUser>();
			oldUsers.forEach(x -> {
				ReportUser reportUser = new ReportUser(x);
				presences.forEach(y -> {
					if (y.getUser().equals(x))
						reportUser.setPresences(reportUser.getPresences() + 1);
				});
				reportUsers.add(reportUser);
			});
		} catch (ModuleException ex) {
			FacesMessage message = new FacesMessage(SEVERITY_INFO, // severity
					ERROR, ERROR);
			getCurrentInstance().addMessage(ERROR, message);
		}
	}

	public List<ReportUser> getReportUsers() {
		return reportUsers;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public ReportType getType() {
		return type;
	}

	public void setType(ReportType type) {
		this.type = type;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public void refresh() {
		init();
	}

	public void insert() throws IOException {
		log.debug("insert");
		configuration.redirect("/views/insert.xhtml");
	}
}
