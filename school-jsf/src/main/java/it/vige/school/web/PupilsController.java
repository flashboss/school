package it.vige.school.web;

import static it.vige.school.Constants.ADMIN_ROLE;
import static javax.faces.context.FacesContext.getCurrentInstance;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import it.vige.school.ModuleException;
import it.vige.school.SchoolModule;
import it.vige.school.dto.Pupil;

@SessionScoped
public class PupilsController {

	@Inject
	private SchoolModule schoolModule;

	@Resource
	private SessionContext context;

	private List<Pupil> pupils;

	@PostConstruct
	public void init() throws ModuleException {
		FacesContext facesContext = getCurrentInstance();
		boolean isAdmin = facesContext.getExternalContext().isUserInRole(ADMIN_ROLE);
		if (isAdmin)
			pupils = schoolModule.findAllPupils();
		else
			pupils = schoolModule.findPupilsBySchool(context.getCallerPrincipal().getName());
	}

	public List<Pupil> getPupils() {
		return pupils;
	}
}
