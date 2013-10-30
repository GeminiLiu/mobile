package cn.com.ultrapower.eoms.user.sla.hibernate.po;

import java.io.Serializable;

public class SlaConfigPo implements Serializable {

	private java.lang.String id;
	private java.lang.String SlaType;
	private java.lang.String Slasupertime;
	private java.lang.String SlaSchema;
	private java.lang.String Slacompany;
	private java.lang.String Sendobj;
	
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getSlacompany() {
		return Slacompany;
	}

	public void setSlacompany(java.lang.String slacompany) {
		Slacompany = slacompany;
	}

	public java.lang.String getSlaSchema() {
		return SlaSchema;
	}

	public void setSlaSchema(java.lang.String slaSchema) {
		SlaSchema = slaSchema;
	}

	public java.lang.String getSlasupertime() {
		return Slasupertime;
	}

	public void setSlasupertime(java.lang.String slasupertime) {
		Slasupertime = slasupertime;
	}

	public java.lang.String getSlaType() {
		return SlaType;
	}

	public void setSlaType(java.lang.String slaType) {
		SlaType = slaType;
	}

	public SlaConfigPo(){
		
	}

	public java.lang.String getSendobj() {
		return Sendobj;
	}

	public void setSendobj(java.lang.String sendobj) {
		Sendobj = sendobj;
	}
}
