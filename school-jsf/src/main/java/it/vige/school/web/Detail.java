package it.vige.school.web;

import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.inject.Named;

import org.jboss.logging.Logger;

import it.vige.school.dto.Pupil;

@RequestScoped
@Named
public class Detail {

	private static Logger log = getLogger(Detail.class);

	private Pupil pupil;

	@Produces
	public Pupil getPupil() {
		return pupil;
	}

	public void setPupil(Pupil pupil) {
		this.pupil = pupil;
	}

	public void page(Pupil pupil) throws IOException {
		log.debug("detail");
		setPupil(pupil);
		ExternalContext ec = getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/views/detail.xhtml");
	}
}
