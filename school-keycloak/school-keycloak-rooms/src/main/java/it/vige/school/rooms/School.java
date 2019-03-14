package it.vige.school.rooms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.json.StringListMapDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class School implements Serializable {

	private static final long serialVersionUID = -6713889813860348323L;

	private String id;

	private String description;

	@JsonDeserialize(using = StringListMapDeserializer.class)
	private Map<String, List<String>> rooms = new HashMap<String, List<String>>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, List<String>> getRooms() {
		return rooms;
	}

	public void setRooms(Map<String, List<String>> rooms) {
		this.rooms = rooms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		School other = (School) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
