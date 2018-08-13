package it.vige.school.web;

import static org.jboss.logging.Logger.getLogger;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;

@SessionScoped
@Named
public class RoomsController implements Serializable {

	private static final long serialVersionUID = -2260430424205388307L;

	private static Logger log = getLogger(RoomsController.class);

	private List<String> rooms;

	public List<String> getRooms() {
		log.debug("get rooms: " + rooms);
		return rooms;
	}

	public void setRooms(List<String> rooms) {
		this.rooms = rooms;
	}
}
