package it.vige.school.web;

import static it.vige.school.Constants.ADMIN_ROLE;
import static it.vige.school.Constants.ERROR;
import static it.vige.school.Utils.getCurrentRole;
import static java.util.stream.Collectors.toList;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.jboss.logging.Logger.getLogger;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.dto.Pupil;

@SessionScoped
@Named
public class PupilsController implements Serializable {

	private static final long serialVersionUID = -2260430424205388307L;

	private static Logger log = getLogger(PupilsController.class);

	@Inject
	private SchoolModule schoolModule;

	private List<Pupil> pupils;

	private List<String> rooms;

	private List<String> schools;

	private List<Pupil> filteredPupils;

	@PostConstruct
	public void init() {
		boolean isAdmin = isAdmin();
		try {
			if (isAdmin) {
				pupils = schoolModule.findAllPupils();
			} else {
				String role = getCurrentRole();
				pupils = schoolModule.findPupilsBySchool(role);
			}
			rooms = pupils.stream().map(x -> x.getRoom()).distinct().sorted().collect(toList());
			schools = pupils.stream().map(x -> x.getSchool()).distinct().sorted().collect(toList());
		} catch (ModuleException ex) {
			FacesMessage message = new FacesMessage(SEVERITY_INFO, // severity
					ERROR, ERROR);
			getCurrentInstance().addMessage(ERROR, message);
		}
	}

	public List<Pupil> getPupils() {
		return pupils;
	}

	public List<String> getRooms() {
		return rooms;
	}

	public List<String> getSchools() {
		return schools;
	}

	public List<Pupil> getFilteredPupils() {
		return filteredPupils;
	}

	public void setFilteredPupils(List<Pupil> filteredPupils) {
		this.filteredPupils = filteredPupils;
	}

	public void addPresence(Pupil pupil) throws ModuleException {
		if (pupil.isPresent())
			schoolModule.createPresence(pupil);
		else
			schoolModule.removePresence(pupil);
		log.debug("pupil: " + pupil);
	}

	public boolean isAdmin() {
		FacesContext facesContext = getCurrentInstance();
		return facesContext.getExternalContext().isUserInRole(ADMIN_ROLE);
	}
}
