package org.jmanderson.subbing;

import java.net.URLDecoder;
import java.net.URLEncoder;

import sun.misc.BASE64Encoder;

public class EncodeDecode {

	public static String encode(String s) {
		if (s != null) {
			if (s.indexOf('&') > -1) {
				StringBuffer sb = new StringBuffer();
				char[] characters = s.toCharArray();
				int len = characters.length;
				for (int i = 0; i < len; i++ ) {
					switch (characters[i]) {
					case '&':
						sb.append("and");
						break;
					default:
						sb.append(characters[i]);
						break;
					}
				}
				s = sb.toString();
			}
			return URLEncoder.encode(s);
		} else {
			return s;
		}
	}

	public static String decode(String s) {
		if (s != null) {
			return URLDecoder.decode(s);
		} else {
			return s;
		}
	}
	
	public static String passwordEncode(String s) {
		if (s != null) {
			return new BASE64Encoder().encode(s.getBytes());
		}
		else {
			return s;
		}
	}

	}
