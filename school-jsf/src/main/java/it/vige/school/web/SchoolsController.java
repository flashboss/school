package it.vige.school.web;

import static org.jboss.logging.Logger.getLogger;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;

@SessionScoped
@Named
public class SchoolsController implements Serializable {

	private static final long serialVersionUID = -2260430424205388307L;

	private static Logger log = getLogger(SchoolsController.class);

	private List<String> schools;

	public List<String> getSchools() {
		log.debug("schools: " + schools);
		return schools;
	}

	public void setSchools(List<String> schools) {
		this.schools = schools;
	}
}
