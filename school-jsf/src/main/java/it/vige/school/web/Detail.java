package it.vige.school.web;

import static it.vige.school.Utils.getCalendarByDate;
import static it.vige.school.Utils.getCurrentUser;
import static it.vige.school.Utils.today;
import static java.lang.String.format;
import static org.jboss.logging.Logger.getLogger;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.dto.ReportPupil;

@SessionScoped
@Named
public class Detail implements Serializable {

	private static final long serialVersionUID = 8290770985951173321L;

	private static Logger log = getLogger(Detail.class);

	private ReportPupil pupil;

	private String[] dates;

	@Inject
	private SchoolModule schoolModule;

	@Inject
	private Configuration configuration;

	@PostConstruct
	public void init() {
		if (configuration.isPupil()) {
			try {
				String id = getCurrentUser();
				pupil = new ReportPupil(schoolModule.findPupilById(id));
				pupil.setPresences(schoolModule.findPresencesByYear(getCalendarByDate(today())).size());
				update(pupil);
			} catch (ModuleException me) {
				log.error(me);
			}
		}
	}

	public ReportPupil getPupil() {
		return pupil;
	}

	public void setPupil(ReportPupil pupil) {
		this.pupil = pupil;
	}

	public String[] getDates() {
		return dates;
	}

	public void setDates(String[] dates) {
		this.dates = dates;
	}

	public void page(ReportPupil pupil) throws IOException, ModuleException {
		log.debug("detail");
		update(pupil);
		configuration.redirect("/views/detail.xhtml");
	}

	public void refresh() throws IOException, ModuleException {
		init();
		if (!configuration.isPupil()) {
			pupil = new ReportPupil(schoolModule.findPupilById(pupil.getId()));
			pupil.setPresences(schoolModule.findPresencesByYear(getCalendarByDate(today())).size());
			update(pupil);
		}
		configuration.redirect("/views/detail.xhtml");
	}

	private void update(ReportPupil pupil) throws ModuleException {
		setPupil(pupil);
		setDates(schoolModule.findPresencesByPupil(pupil).stream()
				.map(x -> format("'%s'", x.getDay().getTime().getTime())).toArray(String[]::new));
	}
}
