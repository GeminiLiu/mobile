/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Sat Oct 28 11:10:09 CST 2006 by MyEclipse Hibernate Tool.
 */
package cn.com.ultrapower.eoms.user.config.source.hibernate.po;

import java.io.Serializable;

/**
 * A class that represents a row in the SOURCEATTRIBUTEVALUE table. 
 * You can customize the behavior of this class by editing the class, {@link Sourceattributevalue()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractSourceattributevalue 
    implements Serializable
{

    /** The value of the simple sourceattvalueId property. */
    private java.lang.Long sourceattvalueId;

    /** The value of the simple sourceattvalueAttid property. */
    private java.lang.Long sourceattvalueAttid;

    /** The value of the simple sourceattvalueBelongrow property. */
    private java.lang.String sourceattvalueBelongrow;

    /** The value of the simple sourceattvalueValue property. */
    private java.lang.String sourceattvalueValue;

    /**
     * Simple constructor of AbstractSourceattributevalue instances.
     */
    public AbstractSourceattributevalue()
    {
    }

    /**
     * Return the value of the SOURCEATTVALUE_ID column.
     * @return java.lang.Long
     */
    public java.lang.Long getSourceattvalueId()
    {
        return this.sourceattvalueId;
    }

    /**
     * Set the value of the SOURCEATTVALUE_ID column.
     * @param sourceattvalueId
     */
    public void setSourceattvalueId(java.lang.Long sourceattvalueId)
    {
        this.sourceattvalueId = sourceattvalueId;
    }

    /**
     * Return the value of the SOURCEATTVALUE_ATTID column.
     * @return java.lang.Long
     */
    public java.lang.Long getSourceattvalueAttid()
    {
        return this.sourceattvalueAttid;
    }

    /**
     * Set the value of the SOURCEATTVALUE_ATTID column.
     * @param sourceattvalueAttid
     */
    public void setSourceattvalueAttid(java.lang.Long sourceattvalueAttid)
    {
        this.sourceattvalueAttid = sourceattvalueAttid;
    }

    /**
     * Return the value of the SOURCEATTVALUE_BELONGROW column.
     * @return java.lang.String
     */
    public java.lang.String getSourceattvalueBelongrow()
    {
        return this.sourceattvalueBelongrow;
    }

    /**
     * Set the value of the SOURCEATTVALUE_BELONGROW column.
     * @param sourceattvalueBelongrow
     */
    public void setSourceattvalueBelongrow(java.lang.String sourceattvalueBelongrow)
    {
        this.sourceattvalueBelongrow = sourceattvalueBelongrow;
    }

    /**
     * Return the value of the SOURCEATTVALUE_VALUE column.
     * @return java.lang.String
     */
    public java.lang.String getSourceattvalueValue()
    {
        return this.sourceattvalueValue;
    }

    /**
     * Set the value of the SOURCEATTVALUE_VALUE column.
     * @param sourceattvalueValue
     */
    public void setSourceattvalueValue(java.lang.String sourceattvalueValue)
    {
        this.sourceattvalueValue = sourceattvalueValue;
    }
}
