package org.e2d.smartfarm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@link DateTimeValidatorUtil} class
 */
public class DateTimeValidatorUtil {

	private static Pattern pattern;
	private static Matcher matcher;

	/**
	 * Ctor DatetimeValidatorClass
	 */
	private DateTimeValidatorUtil() {
	}

	public static boolean validateDate(String dateToValidate, String datePattern) {
		pattern = Pattern.compile(datePattern);
		matcher = pattern.matcher(dateToValidate);

		if (matcher.matches()) {

			matcher.reset();

			if (matcher.find()) {

				String dd = matcher.group(1);
				String mm = matcher.group(2);
				int yy = Integer.parseInt(matcher.group(3));

				if (Integer.parseInt(dd) > 31 || Integer.parseInt(mm) > 12 || Integer.parseInt(dd) <= 0
						|| Integer.parseInt(mm) <= 0) {
					return false;
				} else if (dd.equals("31") && (mm.equals("4") || mm.equals("6") || mm.equals("9") || mm.equals("11")
						|| mm.equals("04") || mm.equals("06") || mm.equals("09"))) {
					return false;
				} else if (mm.equals("2") || mm.equals("02")) {

					if (yy % 4 == 0) {
						if (dd.equals("30") || dd.equals("31")) {
							return false;
						} else {
							return true;
						}
					} else {
						if (dd.equals("29") || dd.equals("30") || dd.equals("31")) {
							return false;
						} else {
							return true;
						}
					}
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean validateTime(String timeToValidate, String timePattern) {
		pattern = Pattern.compile(timePattern);
		matcher = pattern.matcher(timeToValidate);
		return matcher.matches();
	}

}
