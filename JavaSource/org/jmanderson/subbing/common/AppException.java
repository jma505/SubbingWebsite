package org.jmanderson.subbing.common;

public class AppException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9012170421822102471L;

	private Throwable e;

	public AppException(String s, Throwable e) {
		super(s);
		this.e = e;
	}

	public AppException(String s) {
		super(s);
		this.e = null;
	}

	public String getE() {
		return (e == null ? "" : e.toString());
	}

}
