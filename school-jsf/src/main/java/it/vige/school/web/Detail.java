package it.vige.school.web;

import static java.lang.String.format;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
		FacesContext facesContext = getCurrentInstance();
		ExternalContext ec = facesContext.getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/views/detail.xhtml");
	}

	private void update(ReportPupil pupil) throws ModuleException {
		setPupil(pupil);
		setDates(schoolModule.findPresencesByPupil(pupil).stream()
				.map(x -> format("'%s'", x.getDay().getTime().getTime())).toArray(String[]::new));
	}
}
