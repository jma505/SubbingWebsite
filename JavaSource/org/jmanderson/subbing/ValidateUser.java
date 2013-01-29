package org.jmanderson.subbing;

import java.util.Date;

import org.hibernate.Session;
import org.jmanderson.subbing.hibernate.SessionFactory;
import org.jmanderson.subbing.hibernate.Users;

/**
 * Static helper class which retrieves a User from the database for the purposes
 * of validating a login.
 * 
 */
public class ValidateUser {

	private static final String VALID = "valid";
	private static final String INVALID = "Invalid username or password";
	private static final String LOCKED = "This user account is locked; contact the Webmaster";

	public static Users validate(String username) throws Exception {
		Session session = null;
		Users user = null;
		try {
			session = SessionFactory.currentSession();

			user = (Users) session.get(Users.class, username);

		} catch (Exception e) {
			System.out.println("Error trying to validate user " + username
					+ ": " + e);
		} finally {
			SessionFactory.closeSession();
		}
		
//		if (user == null) {
//			System.out.println("--Invalid username: " + username + " " + new Date().toString());
//			return INVALID;
//		}
//		if (!user.passwordMatches(password)) {
//			System.out.println("--Invalid password for: " + username + " (" + password + ") " + new Date().toString());
//			return INVALID;
//		}
//		if (!user.canLogin()) {
//			System.out.println("--User is not allowed to login: " + username + " " + new Date().toString());
//			return LOCKED;
//		}
//
//		// Update the lastlogin field in the database
//		DataUpdater.updateLastLogin(username);
//
//		System.out.println("--User '" + user.getUsername()
//				+ "' logged on: " + new Date().toString());
//		return VALID;
		return user;
		
	}
}
