package cn.com.ultrapower.eoms.util.user;

import java.util.Vector;

/**
 * 本类设置用户的值班机构信息
 * @author lijupeng
 *
 */
public class UserOrganization {
  private long organizationId;
  //private String currentDutySubId;
  private Vector arrangerId;
  private Vector dutyGroupId;
  private String organizationName;
  private long groupFlag;
public Vector getArrangerId() {
	return arrangerId;
}
public void setArrangerId(Vector arrangerId) {
	this.arrangerId = arrangerId;
}
//public String getCurrentDutySubId() {
//	return currentDutySubId;
//}
//public void setCurrentDutySubId(String currentDutySubId) {
//	this.currentDutySubId = currentDutySubId;
//}
public Vector getDutyGroupId() {
	return dutyGroupId;
}
public void setDutyGroupId(Vector dutyGroupId) {
	this.dutyGroupId = dutyGroupId;
}
public long getOrganizationId() {
	return organizationId;
}
public void setOrganizationId(long organizationId) {
	this.organizationId = organizationId;
}
public String getOrganizationName() {
	return organizationName;
}
public void setOrganizationName(String organizationName) {
	this.organizationName = organizationName;
}
public long getGroupFlag() {
	return groupFlag;
}
public void setGroupFlag(long groupFlag) {
	this.groupFlag = groupFlag;
}

  
}
