package it.vige.school.rooms.spi.impl;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import it.vige.school.rooms.School;
import it.vige.school.rooms.spi.RoomsService;

public class RoomsAuthenticator implements Authenticator {

	@Override
	public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
		return true;
	}

	@Override
	public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
		user.addRequiredAction("SECRET_QUESTION_CONFIG");
	}

	@Override
	public void authenticate(AuthenticationFlowContext context) {
		LoginFormsProvider loginFormsProvider = context.getSession().getProvider(LoginFormsProvider.class);
		RoomsService roomsService = context.getSession().getProvider(RoomsService.class);
		List<School> schools = roomsService.findAllSchools();
		Map<String, String> mapSchools = schools.stream().collect(toMap(School::getId, School::getDescription));
		loginFormsProvider.setAttribute("schools", mapSchools);
		context.success();
	}

	@Override
	public void action(AuthenticationFlowContext context) {
	}

	@Override
	public void close() {

	}

	@Override
	public boolean requiresUser() {
		return false;
	}
}
