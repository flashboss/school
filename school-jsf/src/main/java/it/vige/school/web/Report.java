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
import it.vige.school.dto.Pupil;
import it.vige.school.dto.ReportPupil;

@RequestScoped
@Named
public class Report {

	private static Logger log = getLogger(Report.class);

	@Inject
	private Pupils pupils;

	private List<ReportPupil> reportPupils;

	private List<Pupil> filteredPupils;

	@Inject
	private SchoolModule schoolModule;

	@Inject
	private Configuration configuration;

	private ReportType type = MONTH;

	@PostConstruct
	public void init() {
		log.debug("calling the report controller");
		try {
			List<Pupil> oldPupils = pupils.getPupils();
			Calendar currentDate = getCalendarByDate(configuration.getCurrentDate());
			List<Presence> presencesByCurrentDate = null;
			if (type == MONTH)
				presencesByCurrentDate = schoolModule.findPresencesByMonth(currentDate);
			else
				presencesByCurrentDate = schoolModule.findPresencesByYear(currentDate);
			List<Presence> presences = presencesByCurrentDate;
			reportPupils = new ArrayList<ReportPupil>();
			oldPupils.forEach(x -> {
				ReportPupil reportPupil = new ReportPupil(x);
				presences.forEach(y -> {
					if (y.getPupil().equals(x))
						reportPupil.setPresences(reportPupil.getPresences() + 1);
				});
				reportPupils.add(reportPupil);
			});
		} catch (ModuleException ex) {
			FacesMessage message = new FacesMessage(SEVERITY_INFO, // severity
					ERROR, ERROR);
			getCurrentInstance().addMessage(ERROR, message);
		}
	}

	public List<ReportPupil> getReportPupils() {
		return reportPupils;
	}

	public List<Pupil> getFilteredPupils() {
		return filteredPupils;
	}

	public ReportType getType() {
		return type;
	}

	public void setType(ReportType type) {
		this.type = type;
	}

	public void setFilteredPupils(List<Pupil> filteredPupils) {
		this.filteredPupils = filteredPupils;
	}

	public void refresh() {
		init();
	}

	public void insert() throws IOException {
		log.debug("insert");
		configuration.redirect("/");
	}
}
