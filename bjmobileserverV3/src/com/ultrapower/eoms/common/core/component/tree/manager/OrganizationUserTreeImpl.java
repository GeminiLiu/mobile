package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.constants.DutyUtil;
import com.ultrapower.eoms.common.core.component.tree.service.SelectTreeSrcDataService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultraduty.model.DutyGroup;
import com.ultrapower.eoms.ultraduty.model.DutyOrganization;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;

public class OrganizationUserTreeImpl implements SelectTreeSrcDataService {
	
	private IDao<DutyGroup> theDutyGroupDao;
	private DepManagerService depManagerService;
	
	private static final String SEARCHGROUP = "from DutyGroup dg where dg.dutyOrganization.pid = ?";
	
	/**
	 * 取得值班室内所有人员集合
	 * 
	 * @param par 值班室ID
	 */
	public Object getSourceDataObj(String par) {
		Map<String, String> userMap = new HashMap<String, String>();
		List<DutyGroup> list = theDutyGroupDao.find(SEARCHGROUP, par);
		if(list != null && list.size() > 0){
			for(int i = 0 ; i < list.size();i++){
				String groupId = list.get(i).getGroupid();
				List<UserInfo> userList = depManagerService.getUserByDepID(groupId, false);
				if (userList != null) {
					for (UserInfo ui : userList) {
						userMap.put(ui.getPid(), ui.getFullname());
					}
				}
			}
		}
		return userMap;
	}

	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}

	public void setTheDutyGroupDao(IDao<DutyGroup> theDutyGroupDao) {
		this.theDutyGroupDao = theDutyGroupDao;
	}
	
}
