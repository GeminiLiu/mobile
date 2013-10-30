package cn.com.ultrapower.ultrawf.models.flowmap;

/**
 * 审批环节的环节实体类
 * @author BigMouse
 *
 */
public class AuditingProcessInfo extends ProcessInfo
{
	private String auditingWayPhaseNo;

	public String getAuditingWayPhaseNo()
	{
		return auditingWayPhaseNo;
	}

	public void setAuditingWayPhaseNo(String auditingWayPhaseNo)
	{
		this.auditingWayPhaseNo = auditingWayPhaseNo;
	}
	
	private String auditingWayNo;

	public String getAuditingWayNo()
	{
		return auditingWayNo;
	}

	public void setAuditingWayNo(String auditingWayNo)
	{
		this.auditingWayNo = auditingWayNo;
	}
}
