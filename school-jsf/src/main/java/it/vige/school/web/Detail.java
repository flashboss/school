package it.vige.school.web;

import static it.vige.school.Utils.getCurrentUser;
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
import it.vige.school.dto.ReportUser;

@SessionScoped
@Named
public class Detail implements Serializable {

	private static final long serialVersionUID = 8290770985951173321L;

	private static Logger log = getLogger(Detail.class);

	private ReportUser user;

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
				create(id);
			} catch (ModuleException me) {
				log.error(me);
			}
		}
	}

	public ReportUser getUser() {
		return user;
	}

	public void setUser(ReportUser user) {
		this.user = user;
	}

	public String[] getDates() {
		return dates;
	}

	public void setDates(String[] dates) {
		this.dates = dates;
	}

	public void page(ReportUser user) throws IOException, ModuleException {
		log.debug("detail");
		update(user);
		configuration.redirect("/views/detail.xhtml");
	}

	public void refresh() throws IOException, ModuleException {
		init();
		if (!configuration.isPupil())
			create(user.getId());
		configuration.redirect("/views/detail.xhtml");
	}

	private void create(String id) throws ModuleException {
		user = new ReportUser(schoolModule.findUserById(id));
		update(user);
	}

	private void update(ReportUser user) throws ModuleException {
		setUser(user);
		String[] dates = schoolModule.findPresencesByUser(user).stream()
				.map(x -> format("'%s'", x.getDay().getTime().getTime())).toArray(String[]::new);
		setDates(dates);
		user.setPresences(dates.length);
	}
}
