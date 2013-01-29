/*
 * Created Wed Sep 07 17:40:18 EDT 2005 by MyEclipse Hibernate Tool.
 */ 
package org.jmanderson.subbing.hibernate;

import java.io.Serializable;

import org.jmanderson.subbing.EncodeDecode;

/**
 * A class that represents a row in the 'users' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Users
    extends AbstractUsers
    implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6765905656911448451L;
	public static final int ACTIVE = 0;
	public static final int INACTIVE = 1;
	public static final int LOCKED = 2;

	/**
     * Simple constructor of Users instances.
     */
    public Users()
    {
    	setStatus(Integer.valueOf(Users.ACTIVE));
    }

    /**
     * Constructor of Users instances given a simple primary key.
     * @param username
     */
    public Users(java.lang.String username)
    {
        super(username);
        setStatus(Integer.valueOf(Users.ACTIVE));
    }

    /* Add customized code below */

    public boolean passwordMatches(String oldPassword) {
    	
    	String encodedPassword = EncodeDecode.passwordEncode(oldPassword);
    	if (encodedPassword != null && encodedPassword.equals(getPassword())) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public void setActive() {
    	setStatus(Integer.valueOf(Users.ACTIVE));
    }
    public void setInactive() {
    	setStatus(Integer.valueOf(Users.INACTIVE));
    }
    public void setLocked() {
    	setStatus(Integer.valueOf(Users.LOCKED));
    }
    public boolean canLogin() {
    	return (getStatus().intValue() != Users.LOCKED);
    }
}
