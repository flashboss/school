package it.vige.school.web;

import static it.vige.school.Constants.ADMIN_ROLE;
import static it.vige.school.Constants.ROOM_SEPARATOR;
import static it.vige.school.Utils.getCalendarByDate;
import static it.vige.school.Utils.getCurrentRole;
import static it.vige.school.Utils.today;
import static java.lang.System.getProperty;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Locale.getDefault;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.primefaces.event.SelectEvent;

@SessionScoped
@Named
public class ConfigurationController implements Serializable {

	private static final long serialVersionUID = -2260430424205388307L;

	private static Logger log = getLogger(ConfigurationController.class);

	private DateFormat monthDateFormat = new SimpleDateFormat("MMMM YYYY", getDefault());

	private Date currentDay = today();

	private Date currentDate = today();

	private String role = getCurrentRole();

	private String currentLocale = getProperty("user.language");

	@Inject
	private PupilsController pupilsController;

	@Inject
	private ReportController reportController;

	public boolean isAdmin() {
		FacesContext facesContext = getCurrentInstance();
		boolean isAdmin = facesContext.getExternalContext().isUserInRole(ADMIN_ROLE);
		log.debug("isAdmin: " + isAdmin);
		return isAdmin;
	}

	public boolean isSchoolOperator() {
		return role != ADMIN_ROLE && !role.contains(ROOM_SEPARATOR);
	}

	public boolean isTeacher() {
		return role != ADMIN_ROLE && role.contains(ROOM_SEPARATOR);
	}

	public String getFormattedDate() {
		String formattedDay = monthDateFormat.format(currentDate);
		return formattedDay;
	}

	public Date getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(Date currentDay) {
		this.currentDay = currentDay;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getRole() {
		return role;
	}

	public String getCurrentLocale() {
		return currentLocale;
	}

	public void onDaySelect(SelectEvent event) {
		setCurrentDay((Date) event.getObject());
		pupilsController.init();
	}

	public void decrementMonth() throws Exception {
		Calendar calendar = getCalendarByDate(currentDate);
		calendar.add(MONTH, -1);
		setCurrentDate(calendar.getTime());
		reportController.init();
	}

	public void incrementMonth() throws Exception {
		Calendar calendar = getCalendarByDate(currentDate);
		calendar.add(MONTH, 1);
		setCurrentDate(calendar.getTime());
		reportController.init();
	}

	public void decrementYear() throws Exception {
		Calendar calendar = getCalendarByDate(currentDate);
		calendar.add(YEAR, -1);
		setCurrentDate(calendar.getTime());
		reportController.init();
	}

	public void incrementYear() throws Exception {
		Calendar calendar = getCalendarByDate(currentDate);
		calendar.add(YEAR, 1);
		setCurrentDate(calendar.getTime());
		reportController.init();
	}

	public void logout() throws IOException {
		log.debug("logout");
		ExternalContext ec = getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		ec.redirect(ec.getRequestContextPath() + "/");
	}
}
