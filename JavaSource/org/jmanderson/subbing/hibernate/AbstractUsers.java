/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Fri Sep 09 17:40:11 EDT 2005 by MyEclipse Hibernate Tool.
 */
package org.jmanderson.subbing.hibernate;

import java.io.Serializable;

/**
 * A class that represents a row in the users table. 
 * You can customize the behavior of this class by editing the class, {@link Users()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractUsers 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.String username;

    /** The value of the simple password property. */
    private java.lang.String password;

    /** The value of the simple lastlogin property. */
    private java.util.Date lastlogin;
    
    /** The value of the simple status property. */
    private java.lang.Integer status;
    
    /** The optional comments property. */
    private java.lang.String comments;

    /**
     * Simple constructor of AbstractUsers instances.
     */
    public AbstractUsers()
    {
    }

    /**
     * Constructor of AbstractUsers instances given a simple primary key.
     * @param username
     */
    public AbstractUsers(java.lang.String username)
    {
        this.setUsername(username);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return java.lang.String
     */
    public java.lang.String getUsername()
    {
        return username;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param username
     */
    public void setUsername(java.lang.String username)
    {
        this.hashValue = 0;
        this.username = username;
    }

    /**
     * Return the value of the password column.
     * @return java.lang.String
     */
    public java.lang.String getPassword()
    {
        return this.password;
    }

    /**
     * Set the value of the password column.
     * @param password
     */
    public void setPassword(java.lang.String password)
    {
        this.password = password;
    }

    /**
     * Return the value of the lastlogin column.
     * @return java.util.Date
     */
    public java.util.Date getLastlogin()
    {
        return this.lastlogin;
    }

    /**
     * Set the value of the lastlogin column.
     * @param lastlogin
     */
    public void setLastlogin(java.util.Date lastlogin)
    {
        this.lastlogin = lastlogin;
    }
    
    /**
     * Set the value of the status column.
     * @param status
     */
    public void setStatus(java.lang.Integer status) {
    	this.status = status;
    }
    
    /**
     * Return the value of the lastlogin column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getStatus() {
    	return this.status;
    }

    public void setComments(java.lang.String comments) {
    	this.comments = comments;
    }
    
    public java.lang.String getComments() {
    	return this.comments;
    }
    
    /**
     * Implementation of the equals comparison on the basis of equality of the primary key values.
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs)
    {
        if (rhs == null)
            return false;
        if (! (rhs instanceof Users))
            return false;
        Users that = (Users) rhs;
        if (this.getUsername() == null || that.getUsername() == null)
            return false;
        return (this.getUsername().equals(that.getUsername()));
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern with
     * the exception of array properties (these are very unlikely primary key types).
     * @return int
     */
    public int hashCode()
    {
        if (this.hashValue == 0)
        {
            int result = 17;
            int usernameValue = this.getUsername() == null ? 0 : this.getUsername().hashCode();
            result = result * 37 + usernameValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
