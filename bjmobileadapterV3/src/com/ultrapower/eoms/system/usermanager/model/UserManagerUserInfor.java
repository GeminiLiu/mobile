package com.ultrapower.eoms.system.usermanager.model;

// default package
// Generated by MyEclipse Persistence Tools

/**
 * UserManagerUserInfor generated by MyEclipse Persistence Tools
 */
public class UserManagerUserInfor extends AbstractUserManagerUserInfor
		implements java.io.Serializable {

	private String birthdayStart;
	private String birthdayEnd;
	private String graduateTimeStart;
	private String graduateTimeEnd;
	private String workTimeStart;
	private String workTimeEnd;
	private String partymemberjointimeStart;
	private String partymemberjointimeEnd;
	private String startworktimeStart;
	private String startworktimeEnd;

	// Constructors

	/** default constructor */
	public UserManagerUserInfor() {
	}

	/** full constructor */
	public UserManagerUserInfor(String usertype, String code,
			String department, String sysuserid, String name, String sex,
			String national, String nativeplace, String birthday,
			String graduatetime, String college, String education,
			String degree, String worktime, String zhichen, String zhiwei,
			String zhiji, String mobile, String telephone, String mail,
			String speciality1, String speciality2, String isPartymember,
			String isLeaguemember, String trainerlevel, String expertlevel,
			String provinceExpertlevel, String remark, String otherphone,
			String partymemberjointime, String homeaddress, String startworktime,
			String politicalstatus,String loginname) {
		super(usertype, code, department, sysuserid, name, sex, national,
				nativeplace, birthday, graduatetime, college, education,
				degree, worktime, zhichen, zhiwei, zhiji, mobile, telephone,
				mail, speciality1, speciality2, isPartymember, isLeaguemember,
				trainerlevel, expertlevel, provinceExpertlevel, remark,
				otherphone, partymemberjointime, homeaddress, startworktime,
				politicalstatus,loginname);
	}

	public String getBirthdayStart() {
		return birthdayStart;
	}

	public void setBirthdayStart(String birthdayStart) {
		this.birthdayStart = birthdayStart;
	}

	public String getBirthdayEnd() {
		return birthdayEnd;
	}

	public void setBirthdayEnd(String birthdayEnd) {
		this.birthdayEnd = birthdayEnd;
	}

	public String getGraduateTimeStart() {
		return graduateTimeStart;
	}

	public void setGraduateTimeStart(String graduateTimeStart) {
		this.graduateTimeStart = graduateTimeStart;
	}

	public String getGraduateTimeEnd() {
		return graduateTimeEnd;
	}

	public void setGraduateTimeEnd(String graduateTimeEnd) {
		this.graduateTimeEnd = graduateTimeEnd;
	}

	public String getWorkTimeStart() {
		return workTimeStart;
	}

	public void setWorkTimeStart(String workTimeStart) {
		this.workTimeStart = workTimeStart;
	}

	public String getWorkTimeEnd() {
		return workTimeEnd;
	}

	public void setWorkTimeEnd(String workTimeEnd) {
		this.workTimeEnd = workTimeEnd;
	}

	public String getPartymemberjointimeStart() {
		return partymemberjointimeStart;
	}

	public void setPartymemberjointimeStart(String partymemberjointimeStart) {
		this.partymemberjointimeStart = partymemberjointimeStart;
	}

	public String getPartymemberjointimeEnd() {
		return partymemberjointimeEnd;
	}

	public void setPartymemberjointimeEnd(String partymemberjointimeEnd) {
		this.partymemberjointimeEnd = partymemberjointimeEnd;
	}

	public String getStartworktimeStart() {
		return startworktimeStart;
	}

	public void setStartworktimeStart(String startworktimeStart) {
		this.startworktimeStart = startworktimeStart;
	}

	public String getStartworktimeEnd() {
		return startworktimeEnd;
	}

	public void setStartworktimeEnd(String startworktimeEnd) {
		this.startworktimeEnd = startworktimeEnd;
	}

}
