/*
 * Created Tue Oct 31 14:41:48 CST 2006 by MyEclipse Hibernate Tool.
 */ 
package cn.com.ultrapower.eoms.user.authorization.role.hibernate.po;

import java.io.Serializable;

/**
 * A class that represents a row in the 'T92' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Catagorygrandpo implements Serializable
{
    /**
     * Simple constructor of T92 instances.
     */
    public Catagorygrandpo()
    {
    }

    /* Add customized code below */
    private java.lang.String id;//id
    
    private java.lang.String Condition_RoleID;//角色id
    
    private java.lang.String Condition_sourceid;//资源目录id
    
    private java.lang.String Condition_Desc;//描述

	public java.lang.String getCondition_Desc() {
		return Condition_Desc;
	}

	public void setCondition_Desc(java.lang.String condition_Desc) {
		Condition_Desc = condition_Desc;
	}

	public java.lang.String getCondition_RoleID() {
		return Condition_RoleID;
	}

	public void setCondition_RoleID(java.lang.String condition_RoleID) {
		Condition_RoleID = condition_RoleID;
	}

	public java.lang.String getCondition_sourceid() {
		return Condition_sourceid;
	}

	public void setCondition_sourceid(java.lang.String condition_sourceid) {
		Condition_sourceid = condition_sourceid;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

}
