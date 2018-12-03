package it.vige.school.web;

import static it.vige.school.Constants.ADMIN_ROLE;
import static it.vige.school.Constants.PUPIL_ROLE;
import static it.vige.school.Constants.SCHOOL_OPERATOR_ROLE;
import static it.vige.school.Constants.TEACHER_ROLE;
import static it.vige.school.Utils.getCalendarByDate;
import static it.vige.school.Utils.today;
import static it.vige.school.web.ReportType.MONTH;
import static it.vige.school.web.ReportType.YEAR;
import static java.lang.System.getProperty;
import static java.util.Locale.getDefault;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.IOException;
import java.io.InputStream;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.primefaces.event.DateViewChangeEvent;
import org.primefaces.event.SelectEvent;

@SessionScoped
@Named
public class Configuration implements Serializable {

	private static final long serialVersionUID = -2260430424205388307L;

	private static Logger log = getLogger(Configuration.class);

	private DateFormat monthDateFormat = new SimpleDateFormat("MMMM YYYY", getDefault());

	private DateFormat yearDateFormat = new SimpleDateFormat("YYYY", getDefault());

	private Date currentDay = today();

	private Date currentDate = today();

	private Date maxDate = today();

	private String formattedDate = monthDateFormat.format(currentDate);

	private String currentLocale = getProperty("user.language");

	private InputStream keycloakConfiguration = getCurrentInstance().getExternalContext()
			.getResourceAsStream("WEB-INF/keycloak.json");

	private String user;
	{
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		user = request.getUserPrincipal().getName();
	}

	@Inject
	private Users users;

	@Inject
	private Report report;

	public boolean isAdmin() {
		FacesContext facesContext = getCurrentInstance();
		boolean isAdmin = facesContext.getExternalContext().isUserInRole(ADMIN_ROLE);
		log.debug("isAdmin: " + isAdmin);
		return isAdmin;
	}

	public boolean isSchoolOperator() {
		FacesContext facesContext = getCurrentInstance();
		return facesContext.getExternalContext().isUserInRole(SCHOOL_OPERATOR_ROLE);
	}

	public boolean isTeacher() {
		FacesContext facesContext = getCurrentInstance();
		return facesContext.getExternalContext().isUserInRole(TEACHER_ROLE);
	}

	public boolean isPupil() {
		FacesContext facesContext = getCurrentInstance();
		return facesContext.getExternalContext().isUserInRole(PUPIL_ROLE);
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	public void setFormattedDate(Date currentDate, ReportType reportType) {
		if (reportType == MONTH)
			formattedDate = monthDateFormat.format(currentDate);
		else
			formattedDate = yearDateFormat.format(currentDate);
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

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public String getUser() {
		return user;
	}

	public String getCurrentLocale() {
		return currentLocale;
	}

	public void onDaySelect(SelectEvent event) {
		setCurrentDay((Date) event.getObject());
		users.init();
	}

	public void onMonthSelect(DateViewChangeEvent event) {
		Calendar calendar = getCalendarByDate(currentDate);
		calendar.set(Calendar.MONTH, event.getMonth() - 1);
		calendar.set(Calendar.YEAR, event.getYear());
		setCurrentDate(calendar.getTime());
		setFormattedDate(currentDate, MONTH);
		report.setType(MONTH);
		report.init();
	}

	public void onYearSelect(DateViewChangeEvent event) {
		Calendar calendar = getCalendarByDate(currentDate);
		calendar.set(Calendar.YEAR, event.getYear());
		setCurrentDate(calendar.getTime());
		setFormattedDate(currentDate, YEAR);
		report.setType(YEAR);
		report.init();
	}

	public void redirect(String page) throws IOException {
		FacesContext facesContext = getCurrentInstance();
		ExternalContext ec = facesContext.getExternalContext();
		ec.redirect(ec.getRequestContextPath() + page);
	}

	public void logout() throws IOException, ServletException {
		log.debug("logout");
		ExternalContext ec = getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		request.logout();
		redirect("/views/index.xhtml");
	}

	public InputStream getKeycloakConfiguration() {
		return keycloakConfiguration;
	}
}
