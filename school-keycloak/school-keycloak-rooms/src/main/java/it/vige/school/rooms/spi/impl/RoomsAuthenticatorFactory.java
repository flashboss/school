package it.vige.school.rooms.spi.impl;

import static org.keycloak.models.AuthenticationExecutionModel.Requirement.DISABLED;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.REQUIRED;
import static org.keycloak.provider.ProviderConfigProperty.STRING_TYPE;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class RoomsAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

	private static Requirement[] REQUIREMENT_CHOICES = { REQUIRED, DISABLED };

	public static final String PROVIDER_ID = "secret-question-authenticator";

	private static final RoomsAuthenticator SINGLETON = new RoomsAuthenticator();

	private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

	static {
		ProviderConfigProperty property;
		property = new ProviderConfigProperty();
		property.setName("cookie.max.age");
		property.setLabel("Cookie Max Age");
		property.setType(STRING_TYPE);
		property.setHelpText("Max age in seconds of the SECRET_QUESTION_COOKIE.");
		configProperties.add(property);
	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public Authenticator create(KeycloakSession session) {
		return SINGLETON;
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
	public List<ProviderConfigProperty> getConfigProperties() {
		return configProperties;
	}

	@Override
	public void init(Scope config) {

	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {

	}

	@Override
	public void close() {

	}

	@Override
	public String getHelpText() {
		return "HELP!!!!!!!";
	}

	@Override
	public String getDisplayType() {
		return "DISPLAY!!!!!";
	}

	@Override
	public String getReferenceCategory() {
		return "REFERENCE!!!!";
	}

	@Override
	public boolean isConfigurable() {
		return true;
	}
}