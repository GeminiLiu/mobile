/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Wed Jan 17 11:09:50 GMT 2007 by MyEclipse Hibernate Tool.
 */
package cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.po;

import java.io.Serializable;

/**
 * A class that represents a row in the FILE_SUBSCRIBE table. 
 * You can customize the behavior of this class by editing the class, {@link FileSubscribe()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractFileSubscribe 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Long filesmsId;

    /** The value of the simple filesmsUserid property. */
    private java.lang.String filesmsUserid;

    /** The value of the simple filesmsSourceid property. */
    private java.lang.String filesmsSourceid;

    /** The value of the simple filesmsFlag property. */
    private java.lang.Long filesmsFlag;

    /** The value of the simple note property. */
    private java.lang.String note;

    /**
     * Simple constructor of AbstractFileSubscribe instances.
     */
    public AbstractFileSubscribe()
    {
    }

    /**
     * Constructor of AbstractFileSubscribe instances given a simple primary key.
     * @param filesmsId
     */
    public AbstractFileSubscribe(java.lang.Long filesmsId)
    {
        this.setFilesmsId(filesmsId);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return java.lang.Long
     */
    public java.lang.Long getFilesmsId()
    {
        return filesmsId;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param filesmsId
     */
    public void setFilesmsId(java.lang.Long filesmsId)
    {
        this.hashValue = 0;
        this.filesmsId = filesmsId;
    }

    /**
     * Return the value of the FILESMS_USERID column.
     * @return java.lang.String
     */
    public java.lang.String getFilesmsUserid()
    {
        return this.filesmsUserid;
    }

    /**
     * Set the value of the FILESMS_USERID column.
     * @param filesmsUserid
     */
    public void setFilesmsUserid(java.lang.String filesmsUserid)
    {
        this.filesmsUserid = filesmsUserid;
    }

    /**
     * Return the value of the FILESMS_SOURCEID column.
     * @return java.lang.String
     */
    public java.lang.String getFilesmsSourceid()
    {
        return this.filesmsSourceid;
    }

    /**
     * Set the value of the FILESMS_SOURCEID column.
     * @param filesmsSourceid
     */
    public void setFilesmsSourceid(java.lang.String filesmsSourceid)
    {
        this.filesmsSourceid = filesmsSourceid;
    }

    /**
     * Return the value of the FILESMS_FLAG column.
     * @return java.lang.Long
     */
    public java.lang.Long getFilesmsFlag()
    {
        return this.filesmsFlag;
    }

    /**
     * Set the value of the FILESMS_FLAG column.
     * @param filesmsFlag
     */
    public void setFilesmsFlag(java.lang.Long filesmsFlag)
    {
        this.filesmsFlag = filesmsFlag;
    }

    /**
     * Return the value of the NOTE column.
     * @return java.lang.String
     */
    public java.lang.String getNote()
    {
        return this.note;
    }

    /**
     * Set the value of the NOTE column.
     * @param note
     */
    public void setNote(java.lang.String note)
    {
        this.note = note;
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
        if (! (rhs instanceof FileSubscribe))
            return false;
        FileSubscribe that = (FileSubscribe) rhs;
        if (this.getFilesmsId() == null || that.getFilesmsId() == null)
            return false;
        return (this.getFilesmsId().equals(that.getFilesmsId()));
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
            int filesmsIdValue = this.getFilesmsId() == null ? 0 : this.getFilesmsId().hashCode();
            result = result * 37 + filesmsIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
