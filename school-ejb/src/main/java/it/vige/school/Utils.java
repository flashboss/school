package it.vige.school;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.getInstance;
import static javax.security.jacc.PolicyContext.getContext;
import static org.jboss.logging.Logger.getLogger;

import java.util.Calendar;
import java.util.Date;

import javax.security.auth.Subject;
import javax.security.jacc.PolicyContextException;

import org.jboss.logging.Logger;

public class Utils {

	private final static String SUBJECT_CONTAINER = "javax.security.auth.Subject.container";

	private static Logger log = getLogger(Utils.class);

	public static String getCurrentRole() {
		String role = null;
		try {
			Subject subject;
			subject = (Subject) getContext(SUBJECT_CONTAINER);
			role = subject.getPrincipals().toArray()[1].toString();
			role = role.substring(role.indexOf(":") + 1, role.indexOf(")"));
		} catch (PolicyContextException e) {
			log.error(e);
		}
		return role;
	}

	public static String getCurrentUser() {
		String user = null;
		try {
			Subject subject;
			subject = (Subject) getContext(SUBJECT_CONTAINER);
			user = subject.getPrincipals().toArray()[0].toString();
		} catch (PolicyContextException e) {
			log.error(e);
		}
		return user;
	}

	public static Calendar getCalendarByDate(Date date) {
		Calendar day = getInstance();
		day.setTime(date);
		return day;
	}

	public static Date today() {
		Calendar day = getInstance();
		day.setTime(new Date());
		day.set(HOUR_OF_DAY, 0);
		day.set(MINUTE, 0);
		day.set(SECOND, 0);
		day.set(MILLISECOND, 0);
		return day.getTime();
	}
}
