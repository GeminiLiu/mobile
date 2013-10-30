package com.ultrapower.eoms.system.usermanager.service;

import java.util.List;

import com.ultrapower.eoms.common.service.impl.BaseManagerImpl;
import com.ultrapower.eoms.common.support.PageBean;
import com.ultrapower.eoms.system.usermanager.model.UserManagerUserInfor;

public class SendUserManagerUserInforImpl extends
		BaseManagerImpl<UserManagerUserInfor> {

	public void saveUserManagerUserInfo(
			UserManagerUserInfor userManagerUserInfor) throws Exception {
		save(userManagerUserInfor);
	}

	public List<UserManagerUserInfor> listUserManagerUserInfo(
			PageBean pageBean, UserManagerUserInfor userManagerUserInfor) {

		List<UserManagerUserInfor> list = null;
		StringBuffer hql = new StringBuffer(
				"from UserManagerUserInfor user where user.usertype = '派遣制' and user.status = '0' ");

		// 判断员工编号
		if (null != userManagerUserInfor)
		{
			if (null != userManagerUserInfor.getCode()
					&& !"".equals(userManagerUserInfor.getCode())) {
				hql.append(" and code ='" + userManagerUserInfor.getCode() + "' ");
			}
	
			// 判断姓名
			if (null != userManagerUserInfor.getName()
					&& !"".equals(userManagerUserInfor.getName())) {
				hql.append(" and user.name like  '%" + userManagerUserInfor.getName()
						+ "%' ");
			}
	
			// 判断性别
			if (null != userManagerUserInfor.getSex()
					&& !"-1".equals(userManagerUserInfor.getSex())) {
				hql.append(" and user.sex = '" + userManagerUserInfor.getSex()
						+ "' ");
			}
	
			// 判断民族
			if (null != userManagerUserInfor.getNational()
					&& !"".equals(userManagerUserInfor.getNational())) {
				hql.append(" and user.national = '"
						+ userManagerUserInfor.getNational() + "' ");
			}
	
			// 判断籍贯
			if (null != userManagerUserInfor.getNativeplace()
					&& !"".equals(userManagerUserInfor.getNativeplace())) {
				hql.append(" and user.nativeplace = '"
						+ userManagerUserInfor.getNativeplace() + "' ");
			}
	
			// 判断出生日期
			if (null != userManagerUserInfor.getBirthdayStart()	&& !"".equals(userManagerUserInfor.getBirthdayStart()))
			{
				hql.append(" and user.birthday > '" + userManagerUserInfor.getBirthdayStart() + "' ");
			}
			if (null != userManagerUserInfor.getBirthdayEnd() && !"".equals(userManagerUserInfor.getBirthdayEnd()))
			{
				hql.append(" and user.birthday <'" + userManagerUserInfor.getBirthdayEnd() + "' ");
			}
	
			//判断毕业时间
			if (null != userManagerUserInfor.getGraduateTimeStart()	&& !"".equals(userManagerUserInfor.getGraduateTimeStart()))
			{
				hql.append(" and user.graduatetime > '" + userManagerUserInfor.getGraduateTimeStart() +"' ");
			}
			if (null != userManagerUserInfor.getGraduateTimeEnd() && !"".equals(userManagerUserInfor.getGraduateTimeEnd()))
			{
				hql.append(" and user.graduatetime < '" + userManagerUserInfor.getGraduateTimeEnd() +"' ");
			}
			
			//判断参加工作时间
			if (null != userManagerUserInfor.getWorkTimeStart()	&& !"".equals(userManagerUserInfor.getWorkTimeStart()))
			{
				hql.append(" and user.worktime > '" + userManagerUserInfor.getWorkTimeStart() +"' ");
			}
			if (null != userManagerUserInfor.getWorkTimeEnd() && !"".equals(userManagerUserInfor.getWorkTimeEnd()))
			{
				hql.append(" and user.worktime < '" + userManagerUserInfor.getWorkTimeEnd() +"' ");
			}
			
			
			// 判断学历
			if (null != userManagerUserInfor.getDegree() && !"".equals(userManagerUserInfor.getDegree())) {
	
				hql.append(" and user.degree = '"+ userManagerUserInfor.getDegree() + "' ");
			}
	
			// 判断学位
			if (null != userManagerUserInfor.getEducation()
					&& !"".equals(userManagerUserInfor.getEducation())) {
				hql.append(" and user.education = '"
						+ userManagerUserInfor.getEducation() + "' ");
			}
	
			// 判断职称
			if (null != userManagerUserInfor.getZhichen()
					&& !"-1".equals(userManagerUserInfor.getZhichen())) {
				hql.append(" and user.zhichen = '"
						+ userManagerUserInfor.getZhichen() + "' ");
			}
	
			// 判断职位
			if (null != userManagerUserInfor.getZhiwei()
					&& !"-1".equals(userManagerUserInfor.getZhiwei())) {
				hql.append(" and user.zhiwei = '"
						+ userManagerUserInfor.getZhiwei() + "' ");
			}
	
			// 判断职级
			if (null != userManagerUserInfor.getZhiji()
					&& !"-1".equals(userManagerUserInfor.getZhiji())) {
				hql.append(" and user.zhiji = '" + userManagerUserInfor.getZhiji()
						+ "' ");
			}
	
			// 判断手机号码
			if (null != userManagerUserInfor.getMobile()
					&& !"".equals(userManagerUserInfor.getMobile())) {
				hql.append(" and user.mobile = '"
						+ userManagerUserInfor.getMobile() + "' ");
			}
	
			// 判断邮箱
			if (null != userManagerUserInfor.getMail()
					&& !"".equals(userManagerUserInfor.getMail())) {
				hql.append(" and user.mail = '" + userManagerUserInfor.getMail()
						+ "' ");
			}
			
			//查询备用电话
			if(userManagerUserInfor.getOtherphone() !=null && !"".equals(userManagerUserInfor.getOtherphone())){
				hql.append(" and user.otherphone like '%" + userManagerUserInfor.getOtherphone() +"%'");
			}
			
			//查询政治面貌
			if(userManagerUserInfor.getPoliticalstatus() !=null && !"-1".equals(userManagerUserInfor.getPoliticalstatus())){
				hql.append(" and user.politicalstatus like '%" + userManagerUserInfor.getPoliticalstatus() +"%'");
			}
			
			//查询家庭住址
			if(userManagerUserInfor.getHomeaddress() !=null && !"".equals(userManagerUserInfor.getHomeaddress())){
				hql.append(" and user.homeaddress like '%" + userManagerUserInfor.getHomeaddress() +"%'");
			}
			
			//查询入党时间
			if (userManagerUserInfor.getPartymemberjointimeStart() != null
					&& !"".equals(userManagerUserInfor.getPartymemberjointimeStart())) {
				hql.append(" and user.partymemberjointime > '"
						+ userManagerUserInfor.getPartymemberjointimeStart() + "' ");
			}
			if (userManagerUserInfor.getPartymemberjointimeEnd() != null
					&& !"".equals(userManagerUserInfor.getPartymemberjointimeEnd())) {
				hql.append(" and user.partymemberjointime < '"
						+ userManagerUserInfor.getPartymemberjointimeEnd() + "' ");
			}
			
			//查询参加工作时间
			if (userManagerUserInfor.getStartworktimeStart() != null
					&& !"".equals(userManagerUserInfor.getStartworktimeStart())) {
				hql.append(" and user.startworktime > '"
						+ userManagerUserInfor.getStartworktimeStart() + "' ");
			}
			if (userManagerUserInfor.getStartworktimeEnd() != null
					&& !"".equals(userManagerUserInfor.getStartworktimeEnd())) {
				hql.append(" and user.startworktime < '"
						+ userManagerUserInfor.getStartworktimeEnd() + "' ");
			}
		}
		hql.append(" order by user.name ");
		list = pagedQuery(pageBean, hql.toString());
		return list;

	}
	//为导出操作查询所有已存在的人员信息20120523
	public List<UserManagerUserInfor> listUserManagerInfoForImport(PageBean pageBean,UserManagerUserInfor userManagerUserInfo){
		List<UserManagerUserInfor> list = null;
		StringBuffer hql = new StringBuffer(
				"from UserManagerUserInfor user order by user.name");
		list=pagedQuery(pageBean, hql.toString());
		return list;
	}
	public String [] loginName(PageBean page,UserManagerUserInfor userManagerUserInfor){
		//定义已存在的人员登录名拼接字符串
		String userExistLoginName="";
		//获取已存在的人员信息列表
		List<UserManagerUserInfor> listExist= listUserManagerInfoForImport(page, userManagerUserInfor);
		if(listExist!=null){
			for(UserManagerUserInfor ue:listExist){
				if(null!=ue.getLoginname()){
					if(!userExistLoginName.equals("")){
						userExistLoginName=userExistLoginName+";"+ue.getLoginname();
					}else{
						userExistLoginName=ue.getLoginname();
					}
				}
			}
		}
		String [] userLoginName=userExistLoginName.split(";");//人员信息登录名数组
		return userLoginName;
	}
	public void updateUserManagerUserInfo(
			UserManagerUserInfor userManagerUserInfor) throws Exception {
		save(userManagerUserInfor);
	}

	public void deleteUserManagerUserInfo(String ids, UserManagerUserInfor uMUserInfor) throws Exception {
		String[] id = ids.split(",");
		UserManagerUserInfor userManagerUserInfor;
		
		// for (循环变量类型 循环变量名称 : 被遍历的对象)
		for (String userManagerUserInforId : id) {	
			userManagerUserInfor = get(Long.valueOf(userManagerUserInforId));
			if (null != userManagerUserInfor) {
				if(uMUserInfor != null){
					userManagerUserInfor.setDeletereason(uMUserInfor.getDeletereason());
					userManagerUserInfor.setDeleteremark(uMUserInfor.getDeleteremark());
					userManagerUserInfor.setDeletetime(uMUserInfor.getDeletetime());
					userManagerUserInfor.setStatus("1");
				}
				this.merge(userManagerUserInfor);
			}
		}
	}

	public UserManagerUserInfor findById(String id) throws Exception {
		UserManagerUserInfor userManagerUserInfor = this.get(Long.valueOf(id));
		return userManagerUserInfor;
	}

}
