package it.vige.school.web;

import static java.lang.Double.valueOf;

import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class Filters {

	public boolean filterByNumber(Comparable<Integer> value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}
		return value.compareTo(Integer.valueOf(filterText)) > 0;
	}

	public boolean filterByQuote(Comparable<Double> value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}
		return value.compareTo(valueOf(filterText)) > 0;
	}
}
