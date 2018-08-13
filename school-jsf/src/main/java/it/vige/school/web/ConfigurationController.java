package it.vige.school.web;

import static it.vige.school.Constants.ADMIN_ROLE;
import static it.vige.school.Utils.today;
import static java.lang.System.getProperty;
import static java.text.DateFormat.SHORT;
import static java.text.DateFormat.getDateInstance;
import static java.util.Locale.getDefault;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
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

	private DateFormat shortDateFormat = getDateInstance(SHORT, getDefault());

	private Date currentDay = today();

	private String currentLocale = getProperty("user.language");

	@Inject
	private PupilsController pupilsController;

	public boolean isAdmin() {
		FacesContext facesContext = getCurrentInstance();
		boolean isAdmin = facesContext.getExternalContext().isUserInRole(ADMIN_ROLE);
		log.debug("isAdmin: " + isAdmin);
		return isAdmin;
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
		setCurrentDay((Date) event.getObject());
		pupilsController.init();
	}
}
