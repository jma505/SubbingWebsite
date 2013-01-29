/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Fri Sep 09 17:43:04 EDT 2005 by MyEclipse Hibernate Tool.
 */
package org.jmanderson.subbing.hibernate;

import java.io.Serializable;

/**
 * A class that represents a row in the email table. 
 * You can customize the behavior of this class by editing the class, {@link Email()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractEmail 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the organists association. */
    private Organists organists;

    /** The value of the simple email property. */
    private java.lang.String email;

    /** The value of the simple preferred property. */
    private java.lang.Boolean preferred;

    /**
     * Simple constructor of AbstractEmail instances.
     */
    public AbstractEmail()
    {
    }

    /**
     * Constructor of AbstractEmail instances given a simple primary key.
     * @param id
     */
    public AbstractEmail(java.lang.Integer id)
    {
        this.setId(id);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return java.lang.Integer
     */
    public java.lang.Integer getId()
    {
        return id;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param id
     */
    public void setId(java.lang.Integer id)
    {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * Return the value of the username column.
     * @return Organists
     */
    public Organists getOrganists()
    {
        return this.organists;
    }

    /**
     * Set the value of the username column.
     * @param organists
     */
    public void setOrganists(Organists organists)
    {
        this.organists = organists;
    }

    /**
     * Return the value of the email column.
     * @return java.lang.String
     */
    public java.lang.String getEmail()
    {
        return this.email;
    }

    /**
     * Set the value of the email column.
     * @param email
     */
    public void setEmail(java.lang.String email)
    {
        this.email = email;
    }

    /**
     * Return the value of the preferred column.
     * @return java.lang.Byte
     */
    public java.lang.Boolean getPreferred()
    {
        return this.preferred;
    }

    /**
     * Set the value of the preferred column.
     * @param preferred
     */
    public void setPreferred(java.lang.Boolean preferred)
    {
        this.preferred = preferred;
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
        if (! (rhs instanceof Email))
            return false;
        Email that = (Email) rhs;
        if (this.getId() == null || that.getId() == null)
            return false;
        return (this.getId().equals(that.getId()));
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
            int idValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + idValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
