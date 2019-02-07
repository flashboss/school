package it.vige.school.rooms.spi.impl;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.DISABLED;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.REQUIRED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.provider.ProviderConfigProperty;

import it.vige.school.rooms.School;
import it.vige.school.rooms.spi.RoomsService;

public class RoomsFormAction implements FormAction, FormActionFactory {

	public static final String PROVIDER_ID = "rooms-schools-creation";

	private static Requirement[] REQUIREMENT_CHOICES = { REQUIRED, DISABLED };

	@Override
	public void close() {

	}

	@Override
	public void buildPage(FormContext context, LoginFormsProvider form) {
		RoomsService roomsService = context.getSession().getProvider(RoomsService.class);
		List<School> schools = roomsService.findAllSchools();
		Map<String, String> mapSchools = schools.stream().collect(toMap(School::getId, School::getDescription));
		Map<String, List<String>> mapRooms = new HashMap<String, List<String>>();
		for (School school : schools) {
			String id = school.getId();
			List<String> rooms = new ArrayList<String>();
			rooms.add("");
			rooms.addAll(roomsService.findRoomsBySchool(id).stream().map(x -> "" + x.getClazz() + x.getSection())
					.collect(toList()));
			mapRooms.put(id, rooms);
		}
		form.setAttribute("schools", mapSchools);
		form.setAttribute("rooms", mapRooms);
	}

	@Override
	public void validate(ValidationContext context) {
		context.success();
	}

	@Override
	public void success(FormContext context) {

	}

	@Override
	public boolean requiresUser() {
		return false;
	}

	@Override
	public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
		return true;
	}

	@Override
	public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

	}

	@Override
	public FormAction create(KeycloakSession session) {
		return this;
	}

	@Override
	public void init(Scope config) {

	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {

	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public String getDisplayType() {
		return "Rooms Schools Creation";
	}

	@Override
	public String getReferenceCategory() {
		return null;
	}

	@Override
	public boolean isConfigurable() {
		return true;
	}

	@Override
	public Requirement[] getRequirementChoices() {
		return REQUIREMENT_CHOICES;
	}

	@Override
	public boolean isUserSetupAllowed() {
		return true;
	}

	@Override
	public String getHelpText() {
		return "Creation of rooms and schools for the form";
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return null;
	}

}
