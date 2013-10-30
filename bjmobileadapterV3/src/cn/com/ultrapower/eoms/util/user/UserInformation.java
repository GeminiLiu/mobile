package cn.com.ultrapower.eoms.util.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 本类返回用户相关的信息，包括基本信息，所属的机构信息等。
 * @author lijupeng
 *
 */
public class UserInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 基本信息
	 */
	private String userID;//字符型ID号[SysUser表的字段为c1]
	private long userIntID;//数字类型的ID号[c630000029]
	private String userLoginName;//用户登录名[c630000001]
	private String userPassWord;//密码[c630000002]
	private String userFullName;//用户全名[c630000003]
	private String userPosition;//职位：0：员工，1：组长2：处长，3副处长4部长5副部长6其他[c630000005]
	private String userIsManager;//deprecated:是否是管理者，可用作升级管理 0:否1：是[c630000006]
	private String userType;//用户类型：0：正式，1：借调，2：专家，3：交流4：代维公司人员[c630000007]
	private String userMobie;//用户手机号[c630000008]
	private String userPhone;//用户电话[c630000009]
	private String userFax;//用户传真[c630000010]
	private String userMail;//用户邮箱[c630000011]
	private String userStatus;//用户状态0：活动，1：繁忙，2：度假中，3：出差，4：停用，5：其他，6：删除[c630000012]
	private String userCPID;//单位ID[c630000013]
	private String userCPType;//deprecated:0:总部 1：分公司[c630000014]
	private String userDPID;//部门ID[c630000015]
	private String userLicenseType;//License类型0：读取，1：固定，2：浮动[c630000016]
	private String userOrderBy;//排序值[c630000017]
	private String defaultFlag;//session中当前默认用户标识，
	private String userBelongGroupId;//所属组ID[c630000036]
	private String userCreateuser;//创建人[c630000004]
	/**
	 * 所属值班机构信息
	 */
	private Vector organization;
	 
	/**
	 * 代理其他人工作时，被代理人的id
	 * 
	 */
	private Vector companionId;

	/**
	 * 以下为属性的设置和获得方法
	 * 
	 */

	// 当前用户所在的值班室的ID List,Long型
	private List dutyOrgID;

	public List getDutyOrgID() {
		return dutyOrgID;
	}

	public void setDutyOrgID(List dutyOrgID) {
		this.dutyOrgID = dutyOrgID;
	}
	
	/**
	 * 返回字符型的可查看的值班室的ID列表(包括可管理的和所在的值班室)
	 * 
	 * @return
	 */
	public Collection getOrgIds() {
		Map ids = new HashMap();
		String key = null;
		if (this.organization != null) {
			for (int i = 0; i < this.organization.size(); i++) {
				UserOrganization org = (UserOrganization) this.organization
						.get(i);
				key = String.valueOf(org.getOrganizationId());
				ids.put(key, key);
			}
		}
		if (this.dutyOrgID != null) {
			for (int i = 0; i < this.dutyOrgID.size(); i++) {
				Long orgid = (Long) this.dutyOrgID.get(i);
				key = orgid.toString();
				ids.put(key, key);
			}
		}
		return ids.values();
	}

	public Vector getCompanionId() {
		return companionId;
	}

	public void setCompanionId(Vector companionId) {
		this.companionId = companionId;
	}

	public Vector getOrganization() {
		return organization;
	}

	public void setOrganization(Vector organization) {
		this.organization = organization;
	}

	public String getUserCPID() {
		return userCPID;
	}

	public void setUserCPID(String userCPID) {
		this.userCPID = userCPID;
	}

	public String getUserCPType() {
		return userCPType;
	}

	public void setUserCPType(String userCPType) {
		this.userCPType = userCPType;
	}

	public String getUserDPID() {
		return userDPID;
	}

	public void setUserDPID(String userDPID) {
		this.userDPID = userDPID;
	}

	public String getUserFax() {
		return userFax;
	}

	public void setUserFax(String userFax) {
		this.userFax = userFax;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public long getUserIntID() {
		return userIntID;
	}

	public void setUserIntID(long userIntID) {
		this.userIntID = userIntID;
	}

	public String getUserIsManager() {
		return userIsManager;
	}

	public void setUserIsManager(String userIsManager) {
		this.userIsManager = userIsManager;
	}

	public String getUserLicenseType() {
		return userLicenseType;
	}

	public void setUserLicenseType(String userLicenseType) {
		this.userLicenseType = userLicenseType;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserMobie() {
		return userMobie;
	}

	public void setUserMobie(String userMobie) {
		this.userMobie = userMobie;
	}

	public String getUserOrderBy() {
		return userOrderBy;
	}

	public void setUserOrderBy(String userOrderBy) {
		this.userOrderBy = userOrderBy;
	}

	public String getUserPassWord() {
		return userPassWord;
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	
	public String getUserBelongGroupId() {
		return userBelongGroupId;
	}

	public void setUserBelongGroupId(String userBelongGroupId) {
		this.userBelongGroupId = userBelongGroupId;
	}
	
	public String getUserCreateuser() 
	{
		return userCreateuser;
	}
	
	public void setUserCreateuser(String userCreateuser) 
	{
		this.userCreateuser = userCreateuser;
	}
}
