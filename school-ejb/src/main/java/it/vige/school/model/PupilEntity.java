/******************************************************************************
 * Vige, Home of Professional Open Source Copyright 2010, Vige, and           *
 * individual contributors by the @authors tag. See the copyright.txt in the  *
 * distribution for a full listing of individual contributors.                *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may    *
 * not use this file except in compliance with the License. You may obtain    *
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0        *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 ******************************************************************************/
package it.vige.school.model;

import static javax.persistence.CascadeType.ALL;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Pupils.
 * 
 * @author <a href="mailto:luca.stancapiano@vige.it">Luca Stancapiano </a>
 * @author <a href="mailto:toro.gm5s@gmail.com">Alessandro Toro </a>
 */

@NamedQueries({
		@NamedQuery(name = "findAllPupils", query = "from PupilEntity p order by p.school,p.room,p.surname,p.name asc"),
		@NamedQuery(name = "findPupilsByRoom", query = "select p from PupilEntity as p where " + "p.room = :room "
				+ "order by p.room,p.surname,p.name,p.id asc"),
		@NamedQuery(name = "findPupilsBySchool", query = "select p from PupilEntity as p where " + "p.school = :school "
				+ "order by p.room,p.surname,p.name,p.id asc"), })
@Entity
@Table
public class PupilEntity {

	@Id
	private String id;

	private String name;

	private String surname;

	private double income;

	private double monthQuote;

	private String room;

	private String school;

	@OneToMany(mappedBy = "pupil", cascade = ALL, orphanRemoval = true)
	private List<PresenceEntity> presences;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getMonthQuote() {
		return monthQuote;
	}

	public void setMonthQuote(double monthQuote) {
		this.monthQuote = monthQuote;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public List<PresenceEntity> getPresences() {
		return presences;
	}

	public void setPresences(List<PresenceEntity> presences) {
		this.presences = presences;
	}
}