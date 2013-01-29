package org.jmanderson.subbing;

/**
 * This class contains helper methods which can translate a String for HTML display.
 * 
 * 03/04/04 Initial development.
 */
public final class HtmlHelper {

	private HtmlHelper() {
	}
	
	/**
	 * Returns true if the String passed has any characters which need to be translated for
	 * HTML display.  e.g. "&" should be "&amp;"
	 * @param s String being passed in
	 * @return true if conversion is required
	 */
	private static boolean requiresConversion(String s) {
		boolean requiresConversion = false;
		
		if (s.indexOf("&") >= 0) {
			requiresConversion = true;
		}
		else if (s.indexOf(">") >= 0) {
			requiresConversion = true;
		}
		else if (s.indexOf("<") >= 0) {
			requiresConversion = true;
		}
		else if (s.indexOf("\"") >= 0) {
			requiresConversion = true;
		}
		
		return requiresConversion;
	}
	
	/**
	 * Converts special characters in the String if required (see requiresConversion() doc).
	 * @param s String which will be displayed in HTML
	 * @return String for display with any special characters converted
	 */
	public static String convert(String s) {
		
		if (s == null || s.equals("")) {
			return s;
		}
		
		if (!requiresConversion(s)) {
			return s;
		}
		
		StringBuffer sb = new StringBuffer();
		
		char[] characters = s.toCharArray();
		
		int len = characters.length;
		for (int i = 0; i < len; i++) {
			switch (characters[i]) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '\"':
					sb.append("&quot;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				default:
					sb.append(characters[i]);
					break;
			}
		}
		
		return sb.toString();
	}
}
