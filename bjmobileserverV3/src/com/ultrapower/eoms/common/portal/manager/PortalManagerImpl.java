package com.ultrapower.eoms.common.portal.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;
import com.ultrapower.eoms.workflow.sheet.agent.model.Agency;
import com.ultrapower.eoms.workflow.sheet.agent.service.AgencyService;
import com.ultrapower.eoms.workflow.sheet.role.model.ChildRole;
import com.ultrapower.eoms.workflow.sheet.role.model.RoleUser;
import com.ultrapower.eoms.workflow.sheet.role.service.IwfRoleManagerService;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;


@Transactional
public class PortalManagerImpl implements PortalManagerService
{
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private RoleManagerService roleManagerService;
	private DicManagerService dicManagerService;
	private AttachmentManagerService attachmentManagerService;
	private IDao<UserInfo> userManagerDao;
	
	private AgencyService agencyService;
	private IwfRoleManagerService wfRoleManager;

	public UserSession login(String loginName, String pwd, boolean isVerify)
	{
		String userId = userManagerService.getPidByLoginName(loginName);
		UserSession userSession = null;
		if(!"".equals(StringUtils.checkNullString(userId)))
		{
			UserInfo user = userManagerService.getUserByID(userId);
			if(user == null)
			{
				return null;
			}
			if("".equals(StringUtils.checkNullString(user.getPwd())))
			{
				return null;
			}
			
			if(!isVerify || StringUtils.checkNullString(pwd).equals(userManagerService.decodePwd(StringUtils.checkNullString(user.getPwd()))))
			{
				userSession = new UserSession();
				if("1".equals(StringUtils.checkNullString(user.getStatus())))
				{
					//设置用户信息			
					userSession.setPid(user.getPid());
					userSession.setLoginName(user.getLoginname());
					userSession.setFullName(StringUtils.checkNullString(user.getFullname()));
					userSession.setPwd(StringUtils.checkNullString(user.getPwd()));
					userSession.setPosition(dicManagerService.getTextByValue("userposition", StringUtils.checkNullString(user.getPosition())));
					userSession.setType(dicManagerService.getTextByValue("usertype", StringUtils.checkNullString(user.getType())));
					userSession.setMobile(StringUtils.checkNullString(user.getMobile()));
					userSession.setPhone(StringUtils.checkNullString(user.getPhone()));
					userSession.setFax(StringUtils.checkNullString(user.getFax()));
					userSession.setEmail(StringUtils.checkNullString(user.getEmail()));
					userSession.setAgencys(queryAgencys(loginName));
					userSession.setManagerChildRoleIds(queryManagerChildRoleIds(loginName));
					userSession.setChildRoleIds(queryChileRoleIds(loginName));
					String image = StringUtils.checkNullString(user.getImage());
					if("".equals(image))
						image = "default.png";
					else
					{
						List<Attachment> attachmentList = attachmentManagerService.getAttachmentByRelation(image);
						if(attachmentList != null && attachmentList.size() > 0)
							image = StringUtils.checkNullString(attachmentList.get(attachmentList.size()-1).getRealname());
					}
					userSession.setImage(image);
					userSession.setOrderNum(StringUtils.checkNullString(user.getOrdernum()+""));
					//userSession.setLocationZone(dicManagerService.getTextByValue("locationzone", StringUtils.checkNullString(user.getLocationzone())));
					String depid = StringUtils.checkNullString(user.getDepid());
					DepInfo dep = depManagerService.getCompanyByDepid(depid);
					if(dep != null)
					{
						String depname = StringUtils.checkNullString(dep.getDepname());
						String pid = StringUtils.checkNullString(dep.getPid());
						userSession.setCompanyId(pid);
						userSession.setCompanyName(depname);
					}
					userSession.setDepId(depid);
					userSession.setDepName(StringUtils.checkNullString(user.getDepname()));
					userSession.setGroupId(StringUtils.checkNullString(user.getGroupid()));
					userSession.setGroupName(StringUtils.checkNullString(user.getGroupname()));
					userSession.setPtdepId(StringUtils.checkNullString(user.getPtdepid()));
					userSession.setPtdepName(StringUtils.checkNullString(user.getPtdepname()));
					userSession.setManagerGroupId(queryManagerGroupIds(userSession.getGroupId(), loginName));
					//获取用户角色ID、名称、角色DNS 以;分割 如：001,002;角色1,角色2;001.001,001.002
					String roleInfo = StringUtils.checkNullString(roleManagerService.getRoleIdAndNameByUserId(userId));
					String roleDns = "";
					if(!roleInfo.equals(""))
					{
						String[] roles = roleInfo.split(";");
						if(roles.length > 2)
						{
							roleDns = roles[2];
							List dnsList = UltraSmUtil.arrayToList(roleDns.split(","));
							if(dnsList.indexOf("001") >= 0)
								userSession.setIsAdmin("1");
							else
								userSession.setIsAdmin("0");
							userSession.setRoleId(roles[0]);
							userSession.setRoleName(roles[1]);
							userSession.setRoleDns(roles[2]);
						}
					}
					if("".equals(roleDns))
						userSession.setIsAdmin("0");
//					String skin = StringUtils.checkNullString(user.getSystemskin());
					String skin = "";
					if("".equals(skin))
						skin = PropertiesUtils.getProperty("eoms.default.skin");
					userSession.setSkinType(skin);
					userSession.setCreater(StringUtils.checkNullString(user.getCreater()));
//					userSession.setCreateTime(TimeUtils.formatIntToDateString(user.getCreatetime()));
//					userSession.setLastModifier(StringUtils.checkNullString(user.getLastmodifier()));
//					userSession.setLastModifyTime(TimeUtils.formatIntToDateString(user.getLastmodifytime()));
					String lastLoginTime = "";
//					if(user.getLastlogintime()>0)
//						lastLoginTime = TimeUtils.formatIntToDateString(user.getLastlogintime());
					userSession.setLastLoginTime(lastLoginTime);
					//登陆成功修改最后登陆时间
					long currentTime = System.currentTimeMillis() / 1000;
					userSession.setLoginDate(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss") );
//					user.setLastlogintime(currentTime);
					//userManagerService.updateUserInfo(user);
					userManagerDao.saveOrUpdate(user);
				}
			}
		}
		return userSession;
	}
	
	/**
	 * 获取当前用户的代理列表
	 * @param loginName
	 * @return
	 */
	public List<Agency> queryAgencys(String loginName) {
		List<Agency> agencyList = agencyService.getAll();
		List<Agency> agencys = new ArrayList<Agency>();
		if (CollectionUtils.isNotEmpty(agencyList)) {
			for (int i = 0; i < agencyList.size(); i++) {
				Agency agency = agencyList.get(i);
				String agent = agency.getAgentId();
				if (org.apache.commons.lang.StringUtils.isNotBlank(loginName) && loginName.equals(agent)) {
					agencys.add(agency);
				}
			}
		}
		return agencys;
	}
	
	/**
	 * 获取系统中所有的流程类别
	 * @return
	 */
	private List<WfType> queryWfTypes() {
		List<WfType> wfTypes = new ArrayList<WfType>();
		try {
			IWfSortManager ver = WorkFlowServiceClient.clientInstance().getSortService();;
			wfTypes = ver.getAllWfType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wfTypes;
	}
	
	/**
	 * 获取以当前用户为管理员的角色细分id列表
	 * @param loginName
	 * @return
	 */
	public String queryManagerChildRoleIds(String loginName) {
		String childRoleIds = "";
		if (org.apache.commons.lang.StringUtils.isNotBlank(loginName)) {
			String hql = "from ChildRole where charge = '" + loginName + "'";
			List<ChildRole> childRoles = wfRoleManager.getChildRoleByHql(hql);
			if (CollectionUtils.isNotEmpty(childRoles)) {
				for (int i = 0; i < childRoles.size(); i++) {
					ChildRole childRole = childRoles.get(i);
					String childRoleId = childRole.getChildRoleId();
					childRoleIds += childRoleId;
					if (i != (childRoles.size() - 1)) {
						childRoleIds += ",";
					}
				}
			}
		}
		return childRoleIds;
	}
	
	/**
	 * 获取当前用户的所有角色细分id列表
	 * @param loginName
	 * @return
	 */
	public String queryChileRoleIds(String loginName) {
		String childRoleIds = "";
		if (org.apache.commons.lang.StringUtils.isNotBlank(loginName)) {
			String hql = "from RoleUser where loginName = '" + loginName + "'";
			List<RoleUser> roleUsers = wfRoleManager.getRoleUserByHql(hql);
			if (CollectionUtils.isNotEmpty(roleUsers)) {
				for (int i = 0; i < roleUsers.size(); i++) {
					RoleUser roleUser = roleUsers.get(i);
					String childRoleId = roleUser.getChildRoleId();
					childRoleIds += childRoleId;
					if (i != (roleUsers.size() - 1)) {
						childRoleIds += ",";
					}
				}
			}
		}
		return childRoleIds;
	}
	
	/**
	 * 获取以当前用户为管理员的组id列表
	 * @param groupIds
	 * @param loginName
	 * @return
	 */
	public String queryManagerGroupIds(String groupIds, String loginName) {
		String mangerGroupIds = "";
		if (org.apache.commons.lang.StringUtils.isNotBlank(groupIds) && org.apache.commons.lang.StringUtils.isNotBlank(loginName)) {
			String[] arys = groupIds.split(",");
			if (arys != null && arys.length > 0) {
				for (int i = 0; i < arys.length; i++) {
					DepInfo dep = depManagerService.getDepByID(arys[i]);
					if (dep != null) {
						String pid = dep.getPid();
						String depassginee = dep.getDepassginee();
						if (loginName.equals(depassginee)) {
							mangerGroupIds += pid + ",";
						}
					}
				}
			}
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(mangerGroupIds)) {
			mangerGroupIds = mangerGroupIds.substring(0, mangerGroupIds.length() - 1);
		}
		return mangerGroupIds;
	}

	public boolean isValidateLoginInfo(String loginName, String pwd)
	{
		boolean tag = false;
		if(!"".equals(StringUtils.checkNullString(loginName)))
		{
			tag = userManagerService.canLogin(loginName, StringUtils.checkNullString(pwd));
		}
		return tag;
	}
	
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}

	public void setAttachmentManagerService(
			AttachmentManagerService attachmentManagerService) {
		this.attachmentManagerService = attachmentManagerService;
	}

	public void setUserManagerDao(IDao<UserInfo> userManagerDao) {
		this.userManagerDao = userManagerDao;
	}

	public AgencyService getAgencyService() {
		return agencyService;
	}

	public void setAgencyService(AgencyService agencyService) {
		this.agencyService = agencyService;
	}

	public IwfRoleManagerService getWfRoleManager() {
		return wfRoleManager;
	}

	public void setWfRoleManager(IwfRoleManagerService wfRoleManager) {
		this.wfRoleManager = wfRoleManager;
	}
}
