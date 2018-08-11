package it.vige.school.web;

import static javax.security.jacc.PolicyContext.getContext;

import javax.security.auth.Subject;
import javax.security.jacc.PolicyContextException;

public class Utils {

	private final static String SUBJECT_CONTAINER = "javax.security.auth.Subject.container";

	public static String getCurrentRole() throws PolicyContextException {
		Subject subject = (Subject) getContext(SUBJECT_CONTAINER);
		String role = subject.getPrincipals().toArray()[1].toString();
		role = role.substring(role.indexOf(":") + 1, role.indexOf(")"));
		return role;
	}
}
