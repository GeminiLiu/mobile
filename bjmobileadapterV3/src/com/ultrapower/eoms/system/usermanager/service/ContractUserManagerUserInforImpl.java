package com.ultrapower.eoms.system.usermanager.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ultrapower.eoms.common.service.impl.BaseManagerImpl;
import com.ultrapower.eoms.common.support.PageBean;
import com.ultrapower.eoms.system.usermanager.model.UserManagerUserInfor;

public class ContractUserManagerUserInforImpl extends
		BaseManagerImpl<UserManagerUserInfor> {

	private UserManagerUserInfor userManagerUserInfor;
	Log log = LogFactory.getLog(UserManagerUserInfor.class);

	/**
	 * 
	 */
	public ContractUserManagerUserInforImpl() {
		super();
	}

	/**
	 * @param userManagerUserInfor
	 * @param log
	 */

	public ContractUserManagerUserInforImpl(
			UserManagerUserInfor userManagerUserInfor, Log log) {
		super();
		this.userManagerUserInfor = userManagerUserInfor;
		this.log = log;
	}

	// public ContractUserManagerUserInforImpl(
	// UserManagerUserInfor userManagerUserInfor) {
	// this.userManagerUserInfor = userManagerUserInfor;
	// }
	public void saveUserManagerUserInfor(
			UserManagerUserInfor userManagerUserInfor) {
		save(userManagerUserInfor);
	}
	/**
	 * 根据用户登录名查询用户信息，不考虑用户类型：合同或者派遣
	 * @param loginName
	 * @return
	 */
	public UserManagerUserInfor getUserManagerUserInforByUserName(String loginName){
		UserManagerUserInfor userInfor = null;
		StringBuffer hql = new StringBuffer();
		hql.append("from UserManagerUserInfor umi where umi.loginname= ?");
		List<UserManagerUserInfor> list = find(hql.toString(), loginName);
		if(list!=null&&list.size()>0){
			userInfor = list.get(0);
		}
		return userInfor;
	}
	// 查询所有人员信息
	public List<UserManagerUserInfor> getUserManagerUserInfor(
			PageBean pageBean, UserManagerUserInfor userManagerUserInfor,
			String firstpage) {

		StringBuffer hql = new StringBuffer();
		// if("1".equals(firstpage)){
		// hql.append("from UserManager_UserInfor umi where umi.state=1 and
		// umi.result=1");
		// }else{
		// if(null==phoneComplain.getCreateTime() ||
		// "".equals(phoneComplain.getCreateTime())){
		// hql.append("from PhoneComplain pc where pc.state=1 ");
		// }else
		// {
		// String temp = phoneComplain.getCreateTime().substring(0,
		// phoneComplain.getCreateTime().indexOf(" "));
		// hql.append("from PhoneComplain pc where pc.state=1 and pc.createTime
		// like '%"+temp+"%'");

		// }
		hql.append("from UserManagerUserInfor");
		List<UserManagerUserInfor> list = pagedQuery(pageBean, hql.toString());
		return list;
	}
//	查询所有人员信息返回登录名字符串
	public String [] loginName(PageBean page,UserManagerUserInfor userManagerUserInfor){
		//定义已存在的人员登录名拼接字符串
		String userExistLoginName="";
		//获取已存在的人员信息列表
		List<UserManagerUserInfor> listExist= getUserManagerUserInfor(page, userManagerUserInfor,"1");
		if(listExist!=null){
			for(UserManagerUserInfor ue:listExist){
				if (null!=ue.getLoginname()) {
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
	// 删除信息
	public void deleteUserManagerUserInfor(String ids, UserManagerUserInfor uMUserInfor) {
		String[] id = ids.split(",");
		UserManagerUserInfor userManagerUserInfor;
		if (null != id) {
			for (String Id : id) {
				userManagerUserInfor = get(Long.valueOf(Id));
				// if(null!=userManagerUserInfor){
				// userManagerUserInfor.setState(2l);//删除状态
				// save(userManagerUserInfor);
				// }
				if(uMUserInfor != null){
					userManagerUserInfor.setDeletereason(uMUserInfor.getDeletereason());
					userManagerUserInfor.setDeleteremark(uMUserInfor.getDeleteremark());
					userManagerUserInfor.setDeletetime(uMUserInfor.getDeletetime());
					userManagerUserInfor.setStatus("1");
				}
				this.merge(userManagerUserInfor);
				//this.remove(userManagerUserInfor);
			}
		}
	}
	
	// 删除信息
	public void deleteUserManagerUserInforAdd(String ids, UserManagerUserInfor uMUserInfor) {
		String[] id = ids.split(",");
		UserManagerUserInfor userManagerUserInfor;
		if (null != id) {
			for (String Id : id) {
				userManagerUserInfor = get(Long.valueOf(Id));
				// if(null!=userManagerUserInfor){
				// userManagerUserInfor.setState(2l);//删除状态
				// save(userManagerUserInfor);
				// }
				if(uMUserInfor != null){
					userManagerUserInfor.setDeletetime(uMUserInfor.getDeletetime());
				}
				this.merge(userManagerUserInfor);
				//this.remove(userManagerUserInfor);
			}
		}
	}
	
	// 删除信息
	public void deleteUserManagerUserInforHuiFu(String ids, UserManagerUserInfor uMUserInfor) {
		String[] id = ids.split(",");
		UserManagerUserInfor userManagerUserInfor;
		if (null != id) {
			for (String Id : id) {
				userManagerUserInfor = get(Long.valueOf(Id));
				// if(null!=userManagerUserInfor){
				// userManagerUserInfor.setState(2l);//删除状态
				// save(userManagerUserInfor);
				// }
				//if(uMUserInfor != null){
					userManagerUserInfor.setStatus("0");
				//}
				this.merge(userManagerUserInfor);
				//this.remove(userManagerUserInfor);
			}
		}
	}

	// 查询个人信息
	public List<UserManagerUserInfor> getContractUserManagerUserInforById(
			String id, PageBean pageBean) {
		StringBuffer hql = new StringBuffer();
		hql.append("from UserManagerUserInfor umi where umi.id= " + id);
		List<UserManagerUserInfor> list = pagedQuery(pageBean, hql.toString());
		return list;
	}

	public List<UserManagerUserInfor> getContractUserManagerUserInforByID(
			String id) {
		StringBuffer hql = new StringBuffer();
		hql.append("from UserManagerUserInfor umi where umi.id= ?");
		List<UserManagerUserInfor> list = find(hql.toString(), id);
		return list;

	}

	public UserManagerUserInfor findById(String id) {
		userManagerUserInfor = get(Long.valueOf(id));
		return userManagerUserInfor;
	}

	// 查询所有合同制人员信息
	public List<UserManagerUserInfor> listUserManagerUserInfo(
			PageBean pageBean, UserManagerUserInfor userManagerUserInfor) {
		StringBuffer hql = new StringBuffer();
		hql.append("from UserManagerUserInfor umi where umi.usertype = '合同制' and umi.status = '0'");
		List<UserManagerUserInfor> list = pagedQuery(pageBean, hql.toString());

		return list;
	}
	
	
	
	
	
	//查询所有调离人员信息
	public List<UserManagerUserInfor> listUserManagerUserInfoByDiaoLi(
			PageBean pageBean, UserManagerUserInfor userManagerUserInfor) {
		StringBuffer hql = new StringBuffer();
		hql.append("from UserManagerUserInfor umi where umi.status = '1'");
		List<UserManagerUserInfor> list = pagedQuery(pageBean, hql.toString());

		return list;
	}

	// 按条件搜索
	public List<UserManagerUserInfor> getUserManagerUserInfor(PageBean page,
			UserManagerUserInfor userManagerUserInfor) {
		StringBuffer hql = new StringBuffer();
		hql.append("from UserManagerUserInfor umi where umi.usertype = '合同制'  and umi.status = '0'  ");
		if(null != userManagerUserInfor){
			if (userManagerUserInfor.getCode() != null
					&& !"".equals(userManagerUserInfor.getCode())) {
				hql.append(" and umi.code like  '%" + userManagerUserInfor.getCode()
						+ "%' ");
			}
			if (userManagerUserInfor.getDepartment() != null
					&& !"".equals(userManagerUserInfor.getDepartment())) {
				hql.append(" and umi.department like  '%"
						+ userManagerUserInfor.getDepartment() + "%' ");
			}
			if (userManagerUserInfor.getName() != null
					&& !"".equals(userManagerUserInfor.getName())) {
				hql.append(" and umi.name like  '%" + userManagerUserInfor.getName()
						+ "%' ");
			}
			if (userManagerUserInfor.getSex() != null
					&& !"-1".equals(userManagerUserInfor.getSex())) {
				hql.append(" and umi.sex like '%" + userManagerUserInfor.getSex()
						+ "%'");
			}
			if (userManagerUserInfor.getNational() != null
					&& !"".equals(userManagerUserInfor.getNational())) {
				hql.append(" and umi.national like '%"
						+ userManagerUserInfor.getNational() + "%' ");
			}
			if (userManagerUserInfor.getNativeplace() != null
					&& !"".equals(userManagerUserInfor.getNativeplace())) {
				hql.append(" and umi.nativeplace like '%"
						+ userManagerUserInfor.getNational() + "%' ");
			}
			if (userManagerUserInfor.getBirthdayStart() != null
					&& !"".equals(userManagerUserInfor.getBirthdayStart())) {
				hql.append(" and umi.birthday > '"
						+ userManagerUserInfor.getBirthdayStart() + "' ");
			}
			if (userManagerUserInfor.getBirthdayEnd() != null
					&& !"".equals(userManagerUserInfor.getBirthdayEnd())) {
				hql.append(" and umi.birthday < '"
						+ userManagerUserInfor.getBirthdayEnd() + "' ");
			}
			if (userManagerUserInfor.getGraduateTimeStart() != null
					&& !"".equals(userManagerUserInfor.getGraduateTimeStart())) {
				hql.append(" and umi.graduatetime > '"
						+ userManagerUserInfor.getGraduateTimeStart() + "' ");
			}
			if (userManagerUserInfor.getGraduateTimeEnd() != null
					&& !"".equals(userManagerUserInfor.getGraduateTimeEnd())) {
				hql.append(" and umi.graduatetime < '"
						+ userManagerUserInfor.getGraduateTimeEnd() + "' ");
			}
			if (userManagerUserInfor.getCollege() != null
					&& !"".equals(userManagerUserInfor.getCollege())) {
				hql.append(" and umi.college like '%"
						+ userManagerUserInfor.getCollege() + "%' ");
			}
			if (userManagerUserInfor.getEducation() != null
					&& !"-1".equals(userManagerUserInfor.getEducation())) {
				hql.append(" and umi.education like '%"
						+ userManagerUserInfor.getEducation() + "%' ");
			}
			if (userManagerUserInfor.getDegree() != null
					&& !"-1".equals(userManagerUserInfor.getDegree())) {
				hql.append(" and umi.degree like '%" + userManagerUserInfor.getDegree()
						+ "%' ");
			}
			if (userManagerUserInfor.getWorkTimeStart() != null
					&& !"".equals(userManagerUserInfor.getWorkTimeStart())) {
				hql.append(" and umi.worktime > '"
						+ userManagerUserInfor.getWorkTimeStart() + "' ");
			}
			if (userManagerUserInfor.getWorkTimeEnd() != null
					&& !"".equals(userManagerUserInfor.getWorkTimeEnd())) {
				hql.append(" and umi.worktime < '"
						+ userManagerUserInfor.getWorkTimeEnd() + "' ");
			}
			if (userManagerUserInfor.getZhichen() != null
					&& !"-1".equals(userManagerUserInfor.getZhichen())) {
				hql.append(" and umi.zhichen like '%"
						+ userManagerUserInfor.getZhichen() + "%' ");
			}
			if (userManagerUserInfor.getZhiwei() != null
					&& !"-1".equals(userManagerUserInfor.getZhiwei())) {
				hql.append(" and umi.zhiwei like '%" + userManagerUserInfor.getZhiwei()
						+ "%' ");
			}
			if (userManagerUserInfor.getZhiji() != null
					&& !"-1".equals(userManagerUserInfor.getZhiji())) {
				hql.append(" and umi.zhiji like '%" + userManagerUserInfor.getZhiji()
						+ "%' ");
			}
			if (userManagerUserInfor.getMobile() != null
					&& !"".equals(userManagerUserInfor.getMobile())) {
				hql.append(" and umi.mobile like '%" + userManagerUserInfor.getMobile()
						+ "%' ");
			}
			if (userManagerUserInfor.getTelephone() != null
					&& !"".equals(userManagerUserInfor.getTelephone())) {
				hql.append(" and umi.telephone like '%"
						+ userManagerUserInfor.getTelephone() + "%' ");
			}
			if (userManagerUserInfor.getMail() != null
					&& !"".equals(userManagerUserInfor.getMail())) {
				hql.append(" and umi.mail like '%" + userManagerUserInfor.getMail()
						+ "%' ");
			}
			if (userManagerUserInfor.getSpeciality1() != null
					&& !"".equals(userManagerUserInfor.getSpeciality1())) {
				hql.append(" and umi.speciality1 like '%"
						+ userManagerUserInfor.getSpeciality1() + "%' ");
			}
			if (userManagerUserInfor.getSpeciality2() != null
					&& !"".equals(userManagerUserInfor.getSpeciality2())) {
				hql.append(" and umi.speciality2 like  '%"
						+ userManagerUserInfor.getSpeciality2() + "%' ");
			}
			if (userManagerUserInfor.getIsPartymember() != null
					&& !"-1".equals(userManagerUserInfor.getIsPartymember())) {
				hql.append(" and umi.isPartymember like '%"
						+ userManagerUserInfor.getIsPartymember() + "%' ");
			}
			if (userManagerUserInfor.getIsLeaguemember() != null
					&& !"-1".equals(userManagerUserInfor.getIsPartymember())) {
				hql.append(" and umi.isLeaguemember like '%"
						+ userManagerUserInfor.getIsLeaguemember() + "%' ");
			}
			if (userManagerUserInfor.getTrainerlevel() != null
					&& !"-1".equals(userManagerUserInfor.getTrainerlevel())) {
				hql.append(" and umi.trainerlevel like '%"
						+ userManagerUserInfor.getTrainerlevel() + "%' ");
			}
			if (userManagerUserInfor.getExpertlevel() != null
					&& !"-1".equals(userManagerUserInfor.getExpertlevel())) {
				hql.append(" and umi.expertlevel like '%"
						+ userManagerUserInfor.getExpertlevel() + "%' ");
			}
			if (userManagerUserInfor.getProvinceExpertlevel() != null
					&& !"-1".equals(userManagerUserInfor.getProvinceExpertlevel())) {
				hql.append(" and umi.provinceExpertlevel like '%"
						+ userManagerUserInfor.getProvinceExpertlevel() + "%' ");
			}
			if (userManagerUserInfor.getRemark() != null
					&& !"".equals(userManagerUserInfor.getRemark())) {
				hql.append(" and umi.remark like '%" + userManagerUserInfor.getRemark()
						+ "%' ");
			}
			
			//查询备用电话
			if(userManagerUserInfor.getOtherphone() !=null && !"".equals(userManagerUserInfor.getOtherphone())){
				hql.append(" and umi.otherphone like '%" + userManagerUserInfor.getOtherphone() +"%'");
			}
			
			//查询政治面貌
			if(userManagerUserInfor.getPoliticalstatus() !=null && !"-1".equals(userManagerUserInfor.getPoliticalstatus())){
				hql.append(" and umi.politicalstatus like '%" + userManagerUserInfor.getPoliticalstatus() +"%'");
			}
			
			//查询家庭住址
			if(userManagerUserInfor.getHomeaddress() !=null && !"".equals(userManagerUserInfor.getHomeaddress())){
				hql.append(" and umi.homeaddress like '%" + userManagerUserInfor.getHomeaddress() +"%'");
			}

			//查询参加入党时间
			if (userManagerUserInfor.getPartymemberjointimeStart() != null
					&& !"".equals(userManagerUserInfor.getPartymemberjointimeStart())) {
				hql.append(" and umi.partymemberjointime > '"
						+ userManagerUserInfor.getPartymemberjointimeStart() + "' ");
			}
			if (userManagerUserInfor.getPartymemberjointimeEnd() != null
					&& !"".equals(userManagerUserInfor.getPartymemberjointimeEnd())) {
				hql.append(" and umi.partymemberjointime < '"
						+ userManagerUserInfor.getPartymemberjointimeEnd() + "' ");
			}
			
			//查询参加工作时间
			if (userManagerUserInfor.getStartworktimeStart() != null
					&& !"".equals(userManagerUserInfor.getStartworktimeStart())) {
				hql.append(" and umi.startworktime > '"
						+ userManagerUserInfor.getStartworktimeStart() + "' ");
			}
			if (userManagerUserInfor.getStartworktimeEnd() != null
					&& !"".equals(userManagerUserInfor.getStartworktimeEnd())) {
				hql.append(" and umi.startworktime < '"
						+ userManagerUserInfor.getStartworktimeEnd() + "' ");
			}
			
			hql.append(" order by umi.name ");
		}

		List<UserManagerUserInfor> list = pagedQuery(page, hql.toString());
		return list;
	}

	// 修改后保存的方法
	public void updateUserManagerUserInfo(String id,
			UserManagerUserInfor userManagerUserInfor) {
		StringBuffer hql = new StringBuffer();
		hql.append("");
		this.executeUpdate(hql.toString(), id);

	}
	public void updateUserManagerUserInfo(UserManagerUserInfor userManagerUserInfor) {
		save(userManagerUserInfor);
	}

	// get and set
	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public UserManagerUserInfor getUserManagerUserInfor() {
		return userManagerUserInfor;
	}

	public void setUserManagerUserInfor(
			UserManagerUserInfor userManagerUserInfor) {
		this.userManagerUserInfor = userManagerUserInfor;
	}

}
