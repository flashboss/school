package it.vige.school.web;

import static it.vige.school.Constants.ERROR;
import static it.vige.school.Utils.getCalendarByDate;
import static it.vige.school.web.ReportType.MONTH;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.dto.Presence;
import it.vige.school.dto.ReportUser;
import it.vige.school.dto.User;

@SessionScoped
@Named
public class Report implements Serializable {

	private static final long serialVersionUID = -717207127748548665L;

	private static Logger log = getLogger(Report.class);

	@Inject
	private Users users;

	private List<ReportUser> reportUsers = new ArrayList<ReportUser>();

	private List<User> filteredUsers;

	@EJB
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
			configuration.setFormattedDate(currentDate.getTime(), type);
			List<Presence> presences = presencesByCurrentDate;
			List<ReportUser> toRemove = new ArrayList<ReportUser>();
			oldUsers.forEach(x -> {
				ReportUser reportUser = new ReportUser(x);
				if (!reportUsers.contains(reportUser))
					reportUsers.add(reportUser);
				reportUsers.forEach(z -> {
					if (z.equals(reportUser)) {
						z.update(reportUser);
						z.setPresences(0);
						presences.forEach(y -> {
							if (y.getUser().getId().equals(z.getId()))
								z.setPresences(z.getPresences() + 1);
						});
					}
					if (oldUsers.stream().filter(j -> j.getId().equals(z.getId())).count() == 0)
						toRemove.add(z);
				});
			});
			reportUsers.removeAll(toRemove);
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
